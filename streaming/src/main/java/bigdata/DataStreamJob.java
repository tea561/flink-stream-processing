/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bigdata;

import bigdata.analytics.LaneAggregation;
import bigdata.analytics.TopNEvents;
import bigdata.analytics.VehicleAggregation;
import bigdata.analytics.VehicleCountOnLane;
import bigdata.deserializers.LaneInfoDeserializer;
import bigdata.deserializers.VehicleInfoDeserializer;
import bigdata.pojo.LaneInfo;
import bigdata.pojo.cassandra.LaneEmissions;
import bigdata.pojo.VehicleInfo;
import bigdata.pojo.cassandra.LaneSummary;
import bigdata.pojo.cassandra.LaneVehicleCount;
import bigdata.pojo.cassandra.VehicleSummary;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.cassandra.ClusterBuilder;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.cassandra.CassandraSink;
import com.datastax.driver.mapping.Mapper;
import scala.Tuple2;

import java.util.Date;
import java.util.List;


/**
 * Skeleton for a Flink DataStream Job.
 *
 * <p>For a tutorial how to write a Flink application, check the
 * tutorials and examples on the <a href="https://flink.apache.org">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class DataStreamJob {

	public static void main(String[] args) throws Exception {
		// Sets up the execution environment, which is the main entry point
		// to building Flink applications.
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		final DeserializationSchema<VehicleInfo> vehicleSchema = new VehicleInfoDeserializer();
		final DeserializationSchema<LaneInfo> edgeSchema = new LaneInfoDeserializer();

		KafkaSource<VehicleInfo> vehicle_emissions_source = KafkaSource.<VehicleInfo>builder()
				.setBootstrapServers("kafka:9092")
				.setTopics("belgrade-vehicle-emission")
				.setStartingOffsets(OffsetsInitializer.latest())
				.setDeserializer(KafkaRecordDeserializationSchema.valueOnly(vehicleSchema))
				.build();

//		KafkaSource<LaneInfo> lane_source = KafkaSource.<LaneInfo>builder()
//				.setBootstrapServers("kafka:9092")
//				.setTopics("belgrade-lanes")
//				.setStartingOffsets(OffsetsInitializer.latest())
//				.setDeserializer(KafkaRecordDeserializationSchema.valueOnly(edgeSchema))
//				.build();

		//todo: watermarks
//		DataStream<LaneInfo> lane_stream = env.fromSource(lane_source, WatermarkStrategy.noWatermarks(), "lane-source")
//				.filter(new FilterFunction<LaneInfo>() {
//					@Override
//					public boolean filter(LaneInfo laneInfo) throws Exception {
//						return laneInfo.LaneId != null;
//					}
//				});
		DataStream<VehicleInfo> vehicle_emission_stream = env.fromSource(vehicle_emissions_source, WatermarkStrategy.noWatermarks(), "veh-source")
				.filter(new FilterFunction<VehicleInfo>() {
			@Override
			public boolean filter(VehicleInfo vehicleEmissionInfo) throws Exception {
				return vehicleEmissionInfo.VehicleLane != null;
			}
		});

		WindowedStream<VehicleInfo, String, TimeWindow> windowedStream = vehicle_emission_stream
				.keyBy((VehicleInfo veh) -> veh.VehicleLane)
					.window(TumblingProcessingTimeWindows.of(Time.minutes(1)));

		//lane emissions
		DataStream<LaneEmissions> laneEmissions =
				windowedStream
						.process(new LaneAggregation.AddEmissionValues());

		CassandraSink.addSink(laneEmissions)
				.setMapperOptions(() -> new Mapper.Option[] {
						Mapper.Option.saveNullFields(true)
				})
				.setClusterBuilder(new ClusterBuilder() {
					private static final long serialVersionUID = 1L;

					@Override
					protected Cluster buildCluster(Builder builder) {
						return builder.addContactPoints("cassandra-node").withPort(9042).build();
					}
				})
				.build();

		//vehicle count by lane
		DataStream<LaneVehicleCount> vehicleCount = windowedStream
				.aggregate(new VehicleCountOnLane())
				.map(new MapFunction<Tuple2<String, Integer>, LaneVehicleCount>() {
					@Override
					public LaneVehicleCount map(Tuple2<String, Integer> vehCounts) throws Exception {
						return new LaneVehicleCount(new Date(), vehCounts._1, vehCounts._2);
					}
				});

		CassandraSink.addSink(vehicleCount)
				.setMapperOptions(() -> new Mapper.Option[] {
						Mapper.Option.saveNullFields(true)
				})
				.setClusterBuilder(new ClusterBuilder() {
					private static final long serialVersionUID = 1L;

					@Override
					protected Cluster buildCluster(Builder builder) {
						return builder.addContactPoints("cassandra-node").withPort(9042).build();
					}
				})
				.build();

//		DataStream<LaneSummary> lane_summary = lane_stream.map(new MapFunction<LaneInfo, LaneSummary>() {
//			@Override
//			public LaneSummary map(LaneInfo lane) throws Exception {
//				Double length = lane.LaneSampledSeconds / 24.0 / lane.LaneDensity;
//				Double avgTrafficVolume = lane.LaneSpeed * 3.6 * lane.LaneDensity;
//				Double avgVehCount = lane.LaneSampledSeconds / 24000.0;
//				return new LaneSummary(lane.LaneId, length, avgTrafficVolume, avgVehCount);
//			}
//		});
//
//		CassandraSink.addSink(lane_summary)
//				.setMapperOptions(() -> new Mapper.Option[] {
//						Mapper.Option.saveNullFields(true)
//				})
//				.setClusterBuilder(new ClusterBuilder() {
//					private static final long serialVersionUID = 1L;
//
//					@Override
//					protected Cluster buildCluster(Builder builder) {
//						return builder.addContactPoints("cassandra-node").withPort(9042).build();
//					}
//				})
//				.build();


		//joining two streams
//		DataStream<LaneInfo> laneKeyedStream = lane_stream.keyBy(laneInfo -> laneInfo.LaneId);
//		DataStream<VehicleInfo> vehicleKeyedStream = vehicle_emission_stream.keyBy(vehicleInfo -> vehicleInfo.VehicleLane);
//
//		laneKeyedStream
//				.connect(vehicleKeyedStream);

		//grouped by vehicle
		WindowedStream<VehicleInfo, String, TimeWindow> vehicleWindowedStream  = vehicle_emission_stream
				.keyBy((VehicleInfo veh) -> veh.VehicleId)
				.window(TumblingProcessingTimeWindows.of(Time.minutes(1)));

		DataStream<VehicleSummary> vehicle_summary = vehicleWindowedStream
				.process(new VehicleAggregation());

		//top N vehicles with the highest PMx emissions
		DataStream<VehicleSummary> topN = vehicle_summary
				.windowAll(TumblingProcessingTimeWindows.of(Time.minutes(1)))
						.process(new TopNEvents());

		CassandraSink.addSink(topN)
				.setMapperOptions(() -> new Mapper.Option[] {
						Mapper.Option.saveNullFields(true)
				})
				.setClusterBuilder(new ClusterBuilder() {
					private static final long serialVersionUID = 1L;

					@Override
					protected Cluster buildCluster(Builder builder) {
						return builder.addContactPoints("cassandra-node").withPort(9042).build();
					}
				})
				.build();


		/*
		 * Here, you can start creating your execution plan for Flink.
		 *
		 * Start with getting some data from the environment, like
		 * 	env.fromSequence(1, 10);
		 *
		 * then, transform the resulting DataStream<Long> using operations
		 * like
		 * 	.filter()
		 * 	.flatMap()
		 * 	.window()
		 * 	.process()
		 *
		 * and many more.
		 * Have a look at the programming guide:
		 *
		 * https://nightlies.apache.org/flink/flink-docs-stable/
		 *
		 */

		// Execute program, beginning computation.
		env.execute("Belgrade");
	}
}
