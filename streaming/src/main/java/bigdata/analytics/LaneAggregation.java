package bigdata.analytics;

import bigdata.pojo.cassandra.LaneEmissions;
import bigdata.pojo.VehicleInfo;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class LaneAggregation {

    public static class AddEmissionValues
            extends ProcessWindowFunction<VehicleInfo, LaneEmissions, String, TimeWindow> {

        @Override
        public void process(String key, Context context, Iterable<VehicleInfo> emissions, Collector<LaneEmissions> out) throws Exception {

            float sumCO = 0F;
            float sumCO2 = 0F;
            float sumHC = 0F;
            float sumNOx = 0F;
            float sumPMx = 0F;
            //Integer sumVehicleCount = 0;

            for (VehicleInfo e : emissions) {
                sumCO += e.VehicleCO;
                sumCO2 += e.VehicleCO2;
                sumHC += e.VehicleHC;
                sumNOx += e.VehicleNOx;
                sumPMx += e.VehiclePMx;
            }

            out.collect(new LaneEmissions(key, sumCO, sumCO2, sumHC, sumNOx, sumPMx));
        }
    }
}
