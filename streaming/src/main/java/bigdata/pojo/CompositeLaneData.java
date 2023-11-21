package bigdata.pojo;

import bigdata.pojo.cassandra.LaneAggregationData;
import bigdata.pojo.cassandra.LaneMetrics;

import java.io.Serializable;

public class CompositeLaneData implements Serializable {
    public LaneMetrics laneMetrics;
    public LaneAggregationData laneEmissions;

    public CompositeLaneData(LaneMetrics laneMetrics, LaneAggregationData laneEmissions) {
        this.laneMetrics = laneMetrics;
        this.laneEmissions = laneEmissions;
    }

    public CompositeLaneData() {
    }
}
