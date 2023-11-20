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

import bigdata.analytics.*;
import bigdata.deserializers.GeoJSONObjectDeserializer;
import bigdata.deserializers.LaneInfoDeserializer;
import bigdata.deserializers.VehicleInfoDeserializer;
import bigdata.pojo.JoinedData;
import bigdata.pojo.LaneInfo;
import bigdata.pojo.VehicleInfo;
import bigdata.pojo.cassandra.*;
import bigdata.pojo.geojson.GeoJSONObject;
import bigdata.pojo.geojson.Suburb;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.mapping.Mapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.cassandra.CassandraSink;
import org.apache.flink.streaming.connectors.cassandra.ClusterBuilder;
import org.locationtech.jts.geom.GeometryFactory;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
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
        final DeserializationSchema<GeoJSONObject> geoSchema = new GeoJSONObjectDeserializer();

        ClusterBuilder clusterBuilder = new ClusterBuilder() {
            private static final long serialVersionUID = 1L;

            @Override
            protected Cluster buildCluster(Builder builder) {
                return builder.addContactPoints("cassandra-node").withPort(9042).build();
            }
        };

        KafkaSource<VehicleInfo> vehicle_emissions_source = KafkaSource.<VehicleInfo>builder()
                .setBootstrapServers("kafka:9092")
                .setTopics("belgrade-vehicle-emission")
                .setStartingOffsets(OffsetsInitializer.latest())
                .setDeserializer(KafkaRecordDeserializationSchema.valueOnly(vehicleSchema))
                .build();

//        KafkaSource<LaneInfo> lane_source = KafkaSource.<LaneInfo>builder()
//                .setBootstrapServers("kafka:9092")
//                .setTopics("belgrade-lanes")
//                .setStartingOffsets(OffsetsInitializer.latest())
//                .setDeserializer(KafkaRecordDeserializationSchema.valueOnly(edgeSchema))
//                .build();

//        KafkaSource<GeoJSONObject> source = KafkaSource.<GeoJSONObject>builder()
//                .setBootstrapServers("kafka:9092")
//                .setTopics("belgrade-areas")
//                .setStartingOffsets(OffsetsInitializer.earliest())
//                .setDeserializer(KafkaRecordDeserializationSchema.valueOnly(geoSchema))
//                .build();
//
//        DataStream<GeoJSONObject> area_stream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "source");
//
//        GeometryFactory geomFactory = new GeometryFactory();
//        DataStream<Suburb> suburbs = area_stream
//                .map(new MapFunction<GeoJSONObject, Suburb>() {
//                    @Override
//                    public Suburb map(GeoJSONObject geoTemp) throws Exception {
//                        return geoTemp.createSuburb();
//                    }
//                });
//
//        List<Suburb> multiPolygonList = suburbs.executeAndCollect(10);
//        CitySuburbsMap citySuburbsMap = new CitySuburbsMap(multiPolygonList);
//
//
//        //todo: watermarks
////        DataStream<LaneInfo> lane_stream = env.fromSource(lane_source, WatermarkStrategy.noWatermarks(), "lane-source")
////                .filter(new FilterFunction<LaneInfo>() {
////                    @Override
////                    public boolean filter(LaneInfo laneInfo) throws Exception {
////                        return laneInfo.LaneId != null;
////                    }
////                });
//        DataStream<VehicleInfo> vehicle_emission_stream = env.fromSource(vehicle_emissions_source, WatermarkStrategy.noWatermarks(), "veh-source")
//                .filter(new FilterFunction<VehicleInfo>() {
//                    @Override
//                    public boolean filter(VehicleInfo vehicleEmissionInfo) throws Exception {
//                        return vehicleEmissionInfo.VehicleLane != null;
//                    }
//                })
//                        .flatMap(new LoadFile());
//        DataStream<Tuple2<Date, Double>> correlationCoefficient = vehicle_emission_stream
//                .windowAll(TumblingProcessingTimeWindows.of(Time.minutes(3)))
//                .process(new CorrelationCalc())
//                .map(new MapFunction<Double, Tuple2<Date, Double>>() {
//                    @Override
//                    public Tuple2<Date, Double> map(Double coefficient) throws Exception {
//                        return new Tuple2<>(new Date(), coefficient);
//                    }
//                });


//        CassandraSink.addSink(correlationCoefficient)
//                .setQuery("INSERT INTO bigdata.speed_CO2_correlation(date, coefficient) values (?, ?);")
//                .setClusterBuilder(clusterBuilder)
//                //.setHost("cassandra-node")
//                .build();
//
//        WindowedStream<VehicleInfo, String, TimeWindow> windowedStream = vehicle_emission_stream
//                .keyBy((VehicleInfo veh) -> veh.VehicleLane)
//                .window(TumblingProcessingTimeWindows.of(Time.minutes(1)));
//
//        //suburb emissions
//        DataStream<SuburbEmission> suburbEmissionDataStream =
//                vehicle_emission_stream
//                        .map(new MapFunction<VehicleInfo, Tuple2<String, VehicleInfo>>() {
//                            @Override
//                            public Tuple2<String, VehicleInfo> map(VehicleInfo vehicleInfo) throws Exception {
//                                String suburb = citySuburbsMap.findSuburb(vehicleInfo.VehicleX, vehicleInfo.VehicleY);
//                                return new Tuple2<>(suburb, vehicleInfo);
//                            }
//                        })
//                        .keyBy((Tuple2<String, VehicleInfo> veh) -> veh.f0)
//                        .window(TumblingProcessingTimeWindows.of(Time.minutes(1)))
//                        .process(new SuburbEmissionAggregation());
//
//        CassandraSink.addSink(suburbEmissionDataStream)
//                .setMapperOptions(() -> new Mapper.Option[]{
//                        Mapper.Option.saveNullFields(true)
//                })
//                .setClusterBuilder(clusterBuilder)
//                .build();
//
//        //lane emissions
//        DataStream<LaneEmissions> laneEmissions =
//                windowedStream
//                        .process(new LaneAggregation.AddEmissionValues());
//
//        CassandraSink.addSink(laneEmissions)
//                .setMapperOptions(() -> new Mapper.Option[]{
//                        Mapper.Option.saveNullFields(true)
//                })
//                .setClusterBuilder(clusterBuilder)
//                .build();
//
//        //vehicle count by lane
//        DataStream<LaneVehicleCount> vehicleCount = windowedStream
//                .aggregate(new VehicleCountOnLane())
//                .map(new MapFunction<Tuple2<String, Integer>, LaneVehicleCount>() {
//                    @Override
//                    public LaneVehicleCount map(Tuple2<String, Integer> vehCounts) throws Exception {
//                        return new LaneVehicleCount(new Date(), vehCounts.f0, vehCounts.f1);
//                    }
//                });
//
//
//        CassandraSink.addSink(vehicleCount)
//                .setMapperOptions(() -> new Mapper.Option[]{
//                        Mapper.Option.saveNullFields(true)
//                })
//                .setClusterBuilder(clusterBuilder)
//                .build();
//
////        DataStream<LaneSummary> lane_summary = lane_stream.map(new MapFunction<LaneInfo, LaneSummary>() {
////            @Override
////            public LaneSummary map(LaneInfo lane) throws Exception {
////                Double length = lane.LaneSampledSeconds / 24.0 / lane.LaneDensity;
////                Double avgTrafficVolume = lane.LaneSpeed * 3.6 * lane.LaneDensity;
////                Double avgVehCount = lane.LaneSampledSeconds / 24000.0;
////                return new LaneSummary(lane.LaneId, length, avgTrafficVolume, avgVehCount);
////            }
////        });
////
////        CassandraSink.addSink(lane_summary)
////                .setMapperOptions(() -> new Mapper.Option[]{
////                        Mapper.Option.saveNullFields(true)
////                })
////                .setClusterBuilder(clusterBuilder)
////                .build();
//
//
//        //joining two streams
////        DataStream<LaneSummary> laneKeyedStream = lane_summary.keyBy(laneInfo -> laneInfo.LaneId);
////        DataStream<LaneEmissions> vehicleKeyedStream = laneEmissions.keyBy(lane -> lane.LaneId);
////
////        DataStream<JoinedData> connectedStream = laneKeyedStream
////                .connect(vehicleKeyedStream)
////                .flatMap(new EnrichmentFunction())
////                .uid("enrichment")
////                .name("enrichment");
//
//        //group by vehicle
//        WindowedStream<VehicleInfo, String, TimeWindow> vehicleWindowedStream = vehicle_emission_stream
//                .keyBy((VehicleInfo veh) -> veh.VehicleId)
//                .window(TumblingProcessingTimeWindows.of(Time.minutes(1)));
//
//        DataStream<VehicleSummary> vehicle_summary = vehicleWindowedStream
//                .process(new VehicleAggregation());
//
//        //top N vehicles with the highest PMx emissions
//        DataStream<VehicleSummary> topN = vehicle_summary
//                .windowAll(TumblingProcessingTimeWindows.of(Time.minutes(1)))
//                .process(new TopNEvents());
//
//        CassandraSink.addSink(topN)
//                .setMapperOptions(() -> new Mapper.Option[]{
//                        Mapper.Option.saveNullFields(true)
//                })
//                .setClusterBuilder(clusterBuilder)
//                .build();

        // Execute program, beginning computation.
        env.execute("Belgrade");
    }
}
