package bigdata.pojo;

import bigdata.pojo.cassandra.LaneEmissions;
import bigdata.pojo.cassandra.LaneSummary;

import java.io.Serializable;

public class JoinedData implements Serializable {
    public LaneSummary laneSummary;
    public LaneEmissions laneEmissions;

    public JoinedData(LaneSummary laneSummary, LaneEmissions laneEmissions) {
        this.laneSummary = laneSummary;
        this.laneEmissions = laneEmissions;
    }

    public JoinedData() {
    }
}
