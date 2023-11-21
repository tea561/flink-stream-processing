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

import bigdata.analytics.lanes.LaneAnalyzer;
import bigdata.analytics.suburbs.SuburbAnalyzer;
import bigdata.analytics.vehicles.VehicleAnalyzer;
import bigdata.deserializers.GeoJSONObjectDeserializer;
import bigdata.deserializers.LaneInfoDeserializer;
import bigdata.deserializers.VehicleInfoDeserializer;
import bigdata.pojo.CompositeLaneData;
import bigdata.pojo.LaneInfo;
import bigdata.pojo.VehicleInfo;
import bigdata.pojo.cassandra.*;
import bigdata.pojo.geojson.GeoJSONObject;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.mapping.Mapper;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.cassandra.CassandraSink;
import org.apache.flink.streaming.connectors.cassandra.ClusterBuilder;

import java.time.Duration;
import java.util.Date;


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
        final DeserializationSchema<GeoJSONObject> geoSchema = new GeoJSONObjectDeserializer();

        ClusterBuilder clusterBuilder = new ClusterBuilder() {
            private static final long serialVersionUID = 1L;

            @Override
            protected Cluster buildCluster(Builder builder) {
                return builder.addContactPoints("cassandra-node").withPort(9042).build();
            }
        };

        KafkaSource<VehicleInfo> vehicleInfoKafkaSource = KafkaSource.<VehicleInfo>builder()
                .setBootstrapServers("kafka:9092")
                .setTopics("belgrade-vehicle-emission")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setDeserializer(KafkaRecordDeserializationSchema.valueOnly(vehicleSchema))
                .build();

        KafkaSource<LaneInfo> lane_source = KafkaSource.<LaneInfo>builder()
                .setBootstrapServers("kafka:9092")
                .setTopics("belgrade-lanes")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setDeserializer(KafkaRecordDeserializationSchema.valueOnly(edgeSchema))
                .build();

        DataStream<LaneInfo> lane_stream = env.fromSource(lane_source,
                        WatermarkStrategy.forBoundedOutOfOrderness(Duration.ofSeconds(20)), "lane-source")
                .filter(new FilterFunction<LaneInfo>() {
                    @Override
                    public boolean filter(LaneInfo laneInfo) throws Exception {
                        return laneInfo.LaneId != null;
                    }
                });
        DataStream<VehicleInfo> vehicleInfoDataStream = env.fromSource(vehicleInfoKafkaSource,
                        WatermarkStrategy.forBoundedOutOfOrderness(Duration.ofSeconds(20)), "veh-source")
                .filter(new FilterFunction<VehicleInfo>() {
                    @Override
                    public boolean filter(VehicleInfo vehicleInfo) throws Exception {
                        return vehicleInfo.VehicleLane != null && vehicleInfo.VehicleId != null;
                    }
                });


        DataStream<Tuple2<Date, Double>> correlationCoefficient = VehicleAnalyzer.calculateCorrelationCoefficient(vehicleInfoDataStream);

        CassandraSink.addSink(correlationCoefficient)
                .setQuery("INSERT INTO bigdata.speed_CO2_correlation(date, coefficient) values (?, ?);")
                .setClusterBuilder(clusterBuilder)
                .build();

        //group by vehicle
        WindowedStream<VehicleInfo, String, TimeWindow> vehicleWindowedStream = vehicleInfoDataStream
                .keyBy((VehicleInfo veh) -> veh.VehicleId)
                .window(TumblingProcessingTimeWindows.of(Time.minutes(1)));

        //top N vehicles with the highest PMx emissions
        DataStream<VehicleSummary> topN = VehicleAnalyzer.findTopNVehicles(vehicleWindowedStream);
        CassandraSink.addSink(topN)
                .setMapperOptions(() -> new Mapper.Option[]{
                        Mapper.Option.saveNullFields(true)
                })
                .setClusterBuilder(clusterBuilder)
                .build();



        //lane analysis
        //group by lane
        WindowedStream<VehicleInfo, String, TimeWindow> laneGroupedWindowedStream = vehicleInfoDataStream
                .keyBy(VehicleInfo::getVehicleLane)
                .window(SlidingEventTimeWindows.of(Time.minutes(2), Time.seconds(30)));

        //lane aggregation
        DataStream<LaneAggregationData> laneEmissions = LaneAnalyzer.laneAggregation(laneGroupedWindowedStream);

        CassandraSink.addSink(laneEmissions)
                .setMapperOptions(() -> new Mapper.Option[]{
                        Mapper.Option.saveNullFields(true)
                })
                .setClusterBuilder(clusterBuilder)
                .build();

        //vehicle count by lane
        DataStream<LaneVehicleCount> vehicleCount = LaneAnalyzer.calculateVehiclesOnLane(laneGroupedWindowedStream);

        CassandraSink.addSink(vehicleCount)
                .setMapperOptions(() -> new Mapper.Option[]{
                        Mapper.Option.saveNullFields(true)
                })
                .setClusterBuilder(clusterBuilder)
                .build();

        //lane metrics
        DataStream<LaneMetrics> laneMetricsDataStream = LaneAnalyzer.calculateLaneSummary(lane_stream);
//        CassandraSink.addSink(laneMetricsDataStream)
//                .setMapperOptions(() -> new Mapper.Option[]{
//                        Mapper.Option.saveNullFields(true)
//                })
//                .setClusterBuilder(clusterBuilder)
//                .build();

        //join streams
        DataStream<CompositeLaneData> compositeLaneData = LaneAnalyzer.joinLaneData(laneMetricsDataStream, laneEmissions);

        //detect traffic jams
        DataStream<TrafficJamData> trafficJamDataDataStream = LaneAnalyzer.detectTrafficJamLanes(compositeLaneData);
        CassandraSink.addSink(trafficJamDataDataStream)
                .setMapperOptions(() -> new Mapper.Option[]{
                        Mapper.Option.saveNullFields(true)
                })
                .setClusterBuilder(clusterBuilder)
                .build();

        //suburb analysis
        //suburb emissions
        DataStream<SuburbEmission> suburbEmissionDataStream = SuburbAnalyzer.calculateSuburbEmission(vehicleInfoDataStream);
        CassandraSink.addSink(suburbEmissionDataStream)
                .setMapperOptions(() -> new Mapper.Option[]{
                        Mapper.Option.saveNullFields(true)
                })
                .setClusterBuilder(clusterBuilder)
                .build();


        // Execute program, beginning computation.
        env.execute("Belgrade");
    }
}
