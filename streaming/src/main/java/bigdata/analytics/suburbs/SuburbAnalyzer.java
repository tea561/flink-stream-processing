package bigdata.analytics.suburbs;

import bigdata.pojo.VehicleInfo;
import bigdata.pojo.cassandra.SuburbEmission;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

public class SuburbAnalyzer {

    public static DataStream<SuburbEmission> calculateSuburbEmission(DataStream<VehicleInfo> vehicles) {
        return vehicles
                .flatMap(new CitySuburbsMap())
                .keyBy((Tuple2<String, VehicleInfo> vehicle) -> vehicle.f0)
                .window(SlidingEventTimeWindows.of(Time.minutes(2), Time.seconds(30)))
                .process(new SuburbEmissionAggregation());
    }
}
