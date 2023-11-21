package bigdata.analytics.lanes;

import bigdata.pojo.CompositeLaneData;
import bigdata.pojo.LaneInfo;
import bigdata.pojo.VehicleInfo;
import bigdata.pojo.cassandra.LaneAggregationData;
import bigdata.pojo.cassandra.LaneMetrics;
import bigdata.pojo.cassandra.LaneVehicleCount;
import bigdata.pojo.cassandra.TrafficJamData;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import java.util.Date;

public class LaneAnalyzer {
    public static DataStream<LaneAggregationData> laneAggregation(WindowedStream<VehicleInfo, String, TimeWindow> stream) {
        return stream
                .process(new LaneAggregator());
    }

    public static DataStream<LaneVehicleCount> calculateVehiclesOnLane(WindowedStream<VehicleInfo, String, TimeWindow> stream) {
        return stream
                .aggregate(new CountVehiclesOnLane())
                .map(new MapFunction<Tuple2<String, Integer>, LaneVehicleCount>() {
                    @Override
                    public LaneVehicleCount map(Tuple2<String, Integer> vehCounts) throws Exception {
                        return new LaneVehicleCount(new Date(), vehCounts.f0, vehCounts.f1);
                    }
                });
    }

    public static DataStream<LaneMetrics> calculateLaneSummary(DataStream<LaneInfo> stream) {
        return stream.map(new MapFunction<LaneInfo, LaneMetrics>() {
            @Override
            public LaneMetrics map(LaneInfo lane) throws Exception {
                Double length = lane.LaneSampledSeconds / 24.0 / lane.LaneDensity;
                Double avgTrafficVolume = lane.LaneSpeed * 3.6 * lane.LaneDensity;
                Double avgVehCount = lane.LaneSampledSeconds / 24000.0;
                return new LaneMetrics(lane.LaneId, length, avgTrafficVolume, avgVehCount);
            }
        });
    }

    public static DataStream<CompositeLaneData> joinLaneData(DataStream<LaneMetrics> metricsStream, DataStream<LaneAggregationData> emissionStream) {
        DataStream<LaneMetrics> metricsKeyedStream = metricsStream.keyBy(LaneMetrics::getLaneId);
        DataStream<LaneAggregationData> emissionsKeyedStream = emissionStream.keyBy(LaneAggregationData::getLaneId);

        return metricsKeyedStream
                .connect(emissionsKeyedStream)
                .flatMap(new EnrichmentFunction())
                .uid("enrichment")
                .name("enrichment");
    }

    public static DataStream<TrafficJamData> detectTrafficJamLanes(DataStream<CompositeLaneData> stream) {
        return stream
                .filter(new FilterFunction<CompositeLaneData>() {
                    @Override
                    public boolean filter(CompositeLaneData compositeLaneData) throws Exception {
                        double trafficVolume = compositeLaneData.laneMetrics.getAvgTrafficVolume();
                        double waitingTime = compositeLaneData.laneEmissions.getAvgWaitingTime();
                        double speed = compositeLaneData.laneEmissions.getAvgSpeed();

                        return trafficVolume > 10.0 && waitingTime > 2.0 && speed < 10.0;
                    }
                })
                .map(new MapFunction<CompositeLaneData, TrafficJamData>() {
                    @Override
                    public TrafficJamData map(CompositeLaneData compositeLaneData) throws Exception {
                        double trafficVolume = compositeLaneData.laneMetrics.getAvgTrafficVolume();
                        double waitingTime = compositeLaneData.laneEmissions.getAvgWaitingTime();
                        double speed = compositeLaneData.laneEmissions.getAvgSpeed();
                        String id = compositeLaneData.laneEmissions.getLaneId();
                        return new TrafficJamData(id, new Date(), trafficVolume, speed, waitingTime);
                    }
                });
    }
}
