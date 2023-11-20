package bigdata.analytics;

import bigdata.pojo.cassandra.VehicleSummary;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.apache.flink.api.java.tuple.Tuple2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TopNEvents extends ProcessAllWindowFunction<VehicleSummary, VehicleSummary, TimeWindow> {

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
