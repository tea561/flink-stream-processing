package bigdata.pojo.cassandra;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "bigdata", name = "lane_summary")
public class LaneSummary {
    @Column(name = "laneId")
    public String LaneId;
    @Column(name = "laneLength")
    public Double LaneLength;
    @Column(name = "avgTrafficVolume")
    public Double AvgTrafficVolume;
    @Column(name = "avgNumOfVehicles")
    public Double AvgNumOfVehicles;

    public LaneSummary(String laneId, Double laneLength, Double avgTrafficVolume, Double avgNumOfVehicles) {
        LaneId = laneId;
        LaneLength = laneLength;
        AvgTrafficVolume = avgTrafficVolume;
        AvgNumOfVehicles = avgNumOfVehicles;
    }

    public LaneSummary() {
    }

    public String getLaneId() {
        return LaneId;
    }

    public void setLaneId(String laneId) {
        LaneId = laneId;
    }

    public Double getLaneLength() {
        return LaneLength;
    }

    public void setLaneLength(Double laneLength) {
        LaneLength = laneLength;
    }

    public Double getAvgTrafficVolume() {
        return AvgTrafficVolume;
    }

    public void setAvgTrafficVolume(Double avgTrafficVolume) {
        AvgTrafficVolume = avgTrafficVolume;
    }

    public Double getAvgNumOfVehicles() {
        return AvgNumOfVehicles;
    }

    public void setAvgNumOfVehicles(Double avgNumOfVehicles) {
        AvgNumOfVehicles = avgNumOfVehicles;
    }
}
