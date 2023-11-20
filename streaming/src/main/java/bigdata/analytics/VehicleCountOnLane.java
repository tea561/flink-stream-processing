package bigdata.analytics;

import bigdata.pojo.VehicleInfo;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.windowing.AggregateApplyWindowFunction;


public class VehicleCountOnLane implements AggregateFunction<VehicleInfo, Tuple2<String, Integer>, Tuple2<String, Integer>> {
    @Override
    public Tuple2<String, Integer> createAccumulator() {
        return new Tuple2<>("", 0);
    }
    @Override
    public Tuple2<String, Integer> add(VehicleInfo vehicleInfo, Tuple2<String, Integer> acc) {
        return new Tuple2<>(vehicleInfo.VehicleLane, acc.f1 + 1);
    }
    @Override
    public Tuple2<String, Integer> getResult(Tuple2<String, Integer> acc) {
        return acc;
    }
    @Override
    public Tuple2<String, Integer> merge(Tuple2<String, Integer> acc1, Tuple2<String, Integer> acc2) {
        return new Tuple2<>(acc1.f0, acc1.f1 + acc2.f1);
    }
}
