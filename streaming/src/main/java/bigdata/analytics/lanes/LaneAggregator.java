package bigdata.analytics.lanes;

import bigdata.pojo.cassandra.LaneAggregationData;
import bigdata.pojo.VehicleInfo;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.Date;

public class LaneAggregator extends ProcessWindowFunction<VehicleInfo, LaneAggregationData, String, TimeWindow> {

    @Override
    public void process(String key, Context context, Iterable<VehicleInfo> emissions, Collector<LaneAggregationData> out) throws Exception {

        double totalCO = 0.0;
        double totalCO2 = 0.0;
        double totalHC = 0.0;
        double totalNOx = 0.0;
        double totalPMx = 0.0;
        int count = 0;
        double totalSpeed = 0.0;
        double totalWaitingTime = 0.0;

        for (VehicleInfo e : emissions) {
            totalCO += e.VehicleCO;
            totalCO2 += e.VehicleCO2;
            totalHC += e.VehicleHC;
            totalNOx += e.VehicleNOx;
            totalPMx += e.VehiclePMx;
            totalSpeed += e.VehicleSpeed;
            totalWaitingTime += e.VehicleWaiting;
            count++;
        }

        double avgSpeed = totalSpeed / count;
        double avgWaitingTime = totalWaitingTime / count;

        out.collect(new LaneAggregationData(new Date(), key, totalCO, totalCO2, totalHC, totalNOx, totalPMx, avgSpeed, avgWaitingTime));
    }
}

