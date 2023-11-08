package bigdata.analytics;

import bigdata.pojo.EdgeEmissionValues;
import bigdata.pojo.EdgeInfo;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class EdgeEmission {

    public static class AddEmissionValues
            extends ProcessWindowFunction<EdgeInfo, EdgeEmissionValues, String, TimeWindow> {

        @Override
        public void process(String key, Context context, Iterable<EdgeInfo> edges, Collector<EdgeEmissionValues> out) throws Exception {

            float sumCO = 0F;
            float sumCO2 = 0F;
            float sumHC = 0F;
            float sumNOx = 0F;
            float sumPMx = 0F;

            for (EdgeInfo e : edges) {
                sumCO += e.LaneCO;
                sumCO2 += e.LaneCO2;
                sumHC += e.LaneHC;
                sumNOx += e.LaneNOx;
                sumPMx += e.LanePMx;
            }

            out.collect(new EdgeEmissionValues(key, sumCO, sumCO2, sumHC, sumNOx, sumPMx));
        }
    }
}
