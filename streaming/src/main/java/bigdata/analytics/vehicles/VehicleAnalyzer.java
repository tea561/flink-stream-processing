package bigdata.analytics.vehicles;

import bigdata.pojo.VehicleInfo;
import bigdata.pojo.cassandra.VehicleSummary;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import java.util.Date;

public class VehicleAnalyzer {
    public static DataStream<Tuple2<Date, Double>> calculateCorrelationCoefficient(DataStream<VehicleInfo> vehicleInfoDataStream) {
        return vehicleInfoDataStream
                .windowAll(TumblingEventTimeWindows.of(Time.minutes(3)))
                .process(new CorrelationCalculator())
                .map(new MapFunction<Double, Tuple2<Date, Double>>() {
                    @Override
                    public Tuple2<Date, Double> map(Double coefficient) throws Exception {
                        return new Tuple2<>(new Date(), coefficient);
                    }
                });
    }

    public static DataStream<VehicleSummary> findTopNVehicles(WindowedStream<VehicleInfo, String, TimeWindow> vehiclewindowedStream) {
        return vehiclewindowedStream
                .process(new VehicleAggregator())
                .windowAll(TumblingEventTimeWindows.of(Time.minutes(1)))
                .process(new TopNEventsProcessor());
    }

}
