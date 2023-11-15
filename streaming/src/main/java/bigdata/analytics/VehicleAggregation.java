package bigdata.analytics;

import bigdata.pojo.VehicleInfo;
import bigdata.pojo.cassandra.VehicleSummary;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.Date;

public class VehicleAggregation  extends ProcessWindowFunction<VehicleInfo, VehicleSummary, String, TimeWindow> {
    @Override
    public void process(String key, ProcessWindowFunction<VehicleInfo, VehicleSummary, String, TimeWindow>.Context context, Iterable<VehicleInfo> iterable, Collector<VehicleSummary> collector) throws Exception {
        float sumCO = 0F;
        float sumCO2 = 0F;
        float sumHC = 0F;
        float sumNOx = 0F;
        float sumPMx = 0F;

        for (VehicleInfo v : iterable) {
            sumCO += v.VehicleCO;
            sumCO2 += v.VehicleCO2;
            sumHC += v.VehicleHC;
            sumNOx += v.VehicleNOx;
            sumPMx += v.VehiclePMx;
        }

        collector.collect(new VehicleSummary(new Date(), key, sumCO, sumCO2, sumHC, sumNOx, sumPMx));
    }
}

