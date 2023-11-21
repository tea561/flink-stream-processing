package bigdata.analytics.vehicles;

import bigdata.pojo.cassandra.VehicleSummary;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.List;

/**
 * Extracts the top 5 vehicles with the highest PMx emissions
 */
public class TopNEventsProcessor extends ProcessAllWindowFunction<VehicleSummary, VehicleSummary, TimeWindow> {

    @Override
    public void process(ProcessAllWindowFunction<VehicleSummary, VehicleSummary, TimeWindow>.Context context, Iterable<VehicleSummary> iterable, Collector<VehicleSummary> collector) throws Exception {
        List<VehicleSummary> vehicleList = new ArrayList<>();
        for (VehicleSummary veh : iterable) {
            vehicleList.add(veh);
        }
        vehicleList.sort((veh1, veh2) -> (int) (veh1.getVehiclePMx() - veh2.getVehiclePMx()));

        List<VehicleSummary> topN = vehicleList.subList(0, 5);
        for (VehicleSummary v : topN) {
            collector.collect(v);
        }
    }
}
