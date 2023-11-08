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

import bigdata.analytics.EdgeEmission;
import bigdata.deserializers.EdgeInfoDeserializer;
import bigdata.deserializers.VehicleInfoDeserializer;
import bigdata.pojo.EdgeEmissionValues;
import bigdata.pojo.EdgeInfo;
import bigdata.pojo.VehicleInfo;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import org.apache.flink.streaming.connectors.cassandra.ClusterBuilder;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.cassandra.CassandraSink;
import com.datastax.driver.mapping.Mapper;



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
		final DeserializationSchema<EdgeInfo> edgeSchema = new EdgeInfoDeserializer();

		KafkaSource<VehicleInfo> vehicle_source = KafkaSource.<VehicleInfo>builder()
				.setBootstrapServers("kafka:9092")
				.setTopics("belgrade-vehicle")
				.setStartingOffsets(OffsetsInitializer.latest())
				.setDeserializer(KafkaRecordDeserializationSchema.valueOnly(vehicleSchema))
				.build();

		KafkaSource<EdgeInfo> edge_source = KafkaSource.<EdgeInfo>builder()
				.setBootstrapServers("kafka:9092")
				.setTopics("belgrade-edge")
				.setStartingOffsets(OffsetsInitializer.latest())
				.setDeserializer(KafkaRecordDeserializationSchema.valueOnly(edgeSchema))
				.build();

		//todo: watermarks
		DataStream<VehicleInfo> vehicle_stream = env.fromSource(vehicle_source, WatermarkStrategy.noWatermarks(), "vehicle-source");
		DataStream<EdgeInfo> edge_stream = env.fromSource(edge_source, WatermarkStrategy.noWatermarks(), "edge-source");
//				.filter(new FilterFunction<EdgeInfo>() {
//			@Override
//			public boolean filter(EdgeInfo edgeInfo) throws Exception {
//				return edgeInfo.EdgeId != null;
//			}
//		});

//		DataStream<EdgeEmissionValues> edgeEmission =
//				edge_stream.keyBy((EdgeInfo edge) -> edge.EdgeId)
//						.window(TumblingEventTimeWindows.of(Time.minutes(1)))
//								.process(new EdgeEmission.AddEmissionValues());
//
//		//find the edge with the highest CO emission for each window
//		DataStream<EdgeEmissionValues> maxCO =
//				edgeEmission.windowAll(TumblingEventTimeWindows.of(Time.minutes(1))).maxBy("LaneCO", false);
//
		CassandraSink.addSink(edge_stream)
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

		//edge_stream.print();
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
