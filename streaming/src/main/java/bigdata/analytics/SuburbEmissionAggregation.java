package bigdata.analytics;

import bigdata.pojo.VehicleInfo;
import bigdata.pojo.cassandra.SuburbEmission;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class SuburbEmissionAggregation extends ProcessWindowFunction<Tuple2<String, VehicleInfo>, SuburbEmission, String, TimeWindow> {


    @Override
    public void process(String s, ProcessWindowFunction<Tuple2<String, VehicleInfo>, SuburbEmission, String, TimeWindow>.Context context, Iterable<Tuple2<String, VehicleInfo>> iterable, Collector<SuburbEmission> collector) throws Exception {

    }
}