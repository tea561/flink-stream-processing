package bigdata.analytics.suburbs;

import bigdata.pojo.VehicleInfo;
import bigdata.pojo.cassandra.SuburbEmission;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.Date;

public class SuburbEmissionAggregation extends ProcessWindowFunction<Tuple2<String, VehicleInfo>, SuburbEmission, String, TimeWindow> {


    @Override
    public void process(String s, ProcessWindowFunction<Tuple2<String, VehicleInfo>, SuburbEmission, String, TimeWindow>.Context context, Iterable<Tuple2<String, VehicleInfo>> iterable, Collector<SuburbEmission> collector) throws Exception {
        double totalCO = 0.0;
        double totalCO2 = 0.0;
        double totalHC = 0.0;
        double totalNOx = 0.0;
        double totalPMx = 0.0;
        String name = null;

        for (Tuple2<String, VehicleInfo> vehicle : iterable) {
            if (name == null) {
                name = vehicle.f0;
            }
            totalCO += vehicle.f1.VehicleCO;
            totalCO2 += vehicle.f1.VehicleCO2;
            totalHC += vehicle.f1.VehicleHC;
            totalNOx += vehicle.f1.VehicleNOx;
            totalPMx += vehicle.f1.VehiclePMx;
        }

        collector.collect(new SuburbEmission(name, new Date(), totalCO, totalCO2, totalHC, totalNOx, totalPMx));
    }
}