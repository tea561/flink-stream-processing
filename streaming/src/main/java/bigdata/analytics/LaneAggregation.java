package bigdata.analytics;

import bigdata.pojo.cassandra.LaneEmissions;
import bigdata.pojo.VehicleInfo;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.Date;

public class LaneAggregation {

    public static class AddEmissionValues
            extends ProcessWindowFunction<VehicleInfo, LaneEmissions, String, TimeWindow> {

        @Override
        public void process(String key, Context context, Iterable<VehicleInfo> emissions, Collector<LaneEmissions> out) throws Exception {

            double sumCO = 0.0;
            double sumCO2 = 0.0;
            double sumHC =  0.0;
            double sumNOx =  0.0;
            double sumPMx =  0.0;
            int count = 0;
            double sumSpeed =  0.0;
            double sumWaitingTime =  0.0;

            for (VehicleInfo e : emissions) {
                sumCO += e.VehicleCO;
                sumCO2 += e.VehicleCO2;
                sumHC += e.VehicleHC;
                sumNOx += e.VehicleNOx;
                sumPMx += e.VehiclePMx;
                sumSpeed += e.VehicleSpeed;
                sumWaitingTime += e.VehicleWaiting;
                count++;
            }

            double avgSpeed = sumSpeed / count;
            double avgWaitingTime = sumWaitingTime / count;

            out.collect(new LaneEmissions(new Date(), key, sumCO, sumCO2, sumHC, sumNOx, sumPMx, avgSpeed, avgWaitingTime));
        }
    }
}
