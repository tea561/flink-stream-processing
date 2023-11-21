package bigdata.analytics.lanes;

import bigdata.pojo.CompositeLaneData;
import bigdata.pojo.cassandra.LaneAggregationData;
import bigdata.pojo.cassandra.LaneMetrics;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.co.RichCoFlatMapFunction;
import org.apache.flink.util.Collector;

public class EnrichmentFunction  extends RichCoFlatMapFunction<LaneMetrics, LaneAggregationData, CompositeLaneData> {

    private ValueState<LaneMetrics> laneSummaryState;
    private ValueState<LaneAggregationData> laneEmissionsState;

    @Override
    public void open(Configuration parameters) throws Exception {
        laneSummaryState = getRuntimeContext()
                .getState(new ValueStateDescriptor<>("saved summary", LaneMetrics.class));

        laneEmissionsState = getRuntimeContext()
                .getState(new ValueStateDescriptor<>("saved emissions", LaneAggregationData.class));
    }

    @Override
    public void flatMap1(LaneMetrics laneSummary, Collector<CompositeLaneData> collector) throws Exception {

        LaneAggregationData laneEmissions = laneEmissionsState.value();
        if(laneEmissions != null ) {
            laneEmissionsState.clear();
            collector.collect(new CompositeLaneData(laneSummary, laneEmissions));
        } else {
            laneSummaryState.update(laneSummary);
        }
    }

    @Override
    public void flatMap2(LaneAggregationData laneEmissions, Collector<CompositeLaneData> collector) throws Exception {
        LaneMetrics laneSummary = laneSummaryState.value();
        if(laneSummary != null) {
            laneSummaryState.clear();
            collector.collect(new CompositeLaneData(laneSummary, laneEmissions));
        }
        else {
            laneEmissionsState.update(laneEmissions);
        }
    }
}
