package bigdata.analytics;

import bigdata.pojo.JoinedData;
import bigdata.pojo.cassandra.LaneEmissions;
import bigdata.pojo.cassandra.LaneSummary;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.co.RichCoFlatMapFunction;
import org.apache.flink.util.Collector;

public class EnrichmentFunction  extends RichCoFlatMapFunction<LaneSummary, LaneEmissions, JoinedData> {

    private ValueState<LaneSummary> laneSummaryState;
    private ValueState<LaneEmissions> laneEmissionsState;

    @Override
    public void open(Configuration parameters) throws Exception {
        laneSummaryState = getRuntimeContext()
                .getState(new ValueStateDescriptor<>("saved summary", LaneSummary.class));

        laneEmissionsState = getRuntimeContext()
                .getState(new ValueStateDescriptor<>("saved emissions", LaneEmissions.class));
    }

    @Override
    public void flatMap1(LaneSummary laneSummary, Collector<JoinedData> collector) throws Exception {

        LaneEmissions laneEmissions = laneEmissionsState.value();
        if(laneEmissions != null ) {
            laneEmissionsState.clear();
            collector.collect(new JoinedData(laneSummary, laneEmissions));
        } else {
            laneSummaryState.update(laneSummary);
        }
    }

    @Override
    public void flatMap2(LaneEmissions laneEmissions, Collector<JoinedData> collector) throws Exception {
        LaneSummary laneSummary = laneSummaryState.value();
        if(laneSummary != null) {
            laneSummaryState.clear();
            collector.collect(new JoinedData(laneSummary, laneEmissions));
        }
        else {
            laneEmissionsState.update(laneEmissions);
        }
    }
}
