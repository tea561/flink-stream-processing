package bigdata.analytics.vehicles;

import bigdata.pojo.VehicleInfo;
import bigdata.pojo.cassandra.VehicleSummary;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.Date;
/**
 * Aggregates vehicle information within a time window.
 * The result is a VehicleSummary containing the sum of emissions for each type.
 */
public class VehicleAggregator extends ProcessWindowFunction<VehicleInfo, VehicleSummary, String, TimeWindow> {
    @Override
    public void process(String key, ProcessWindowFunction<VehicleInfo, VehicleSummary, String, TimeWindow>.Context context, Iterable<VehicleInfo> iterable, Collector<VehicleSummary> collector) throws Exception {
        double sumCO = 0.0;
        double sumCO2 = 0.0;
        double sumHC = 0.0;
        double sumNOx = 0.0;
        double sumPMx = 0.0;

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

