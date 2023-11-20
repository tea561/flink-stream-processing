package bigdata.pojo;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;
import com.google.gson.annotations.SerializedName;

public class LaneInfo {
    @SerializedName("interval_begin")
    public Double IntervalBegin;
    @SerializedName("interval_end")
    public Double IntervalEnd;
    @SerializedName("interval_id")
    public String IntervalId;
    @SerializedName("edge_id")
    public String EdgeId;
    @SerializedName("lane_arrived")
    public Integer LaneArrived;
    @SerializedName("lane_density")
    public Double LaneDensity;
    @SerializedName("lane_departed")
    public Integer LaneDeparted;
    @SerializedName("lane_entered")
    public Integer LaneEntered;
    @SerializedName("lane_id")
    public String LaneId;
    @SerializedName("lane_left")
    public Integer LaneLeft;
    @SerializedName("lane_occupancy")
    public Double LaneOccupancy;
    @SerializedName("lane_overlapTraveltime")
    public Double LaneOverlapTraveltime;
    @SerializedName("lane_sampledSeconds")
    public Double LaneSampledSeconds;
    @SerializedName("lane_speed")
    public Double LaneSpeed;
    @SerializedName("lane_speedRelative")
    public Double LaneSpeedRelative;
    @SerializedName("lane_timeLoss")
    public Double LaneTimeLoss;
    @SerializedName("lane_traveltime")
    public Double LaneTravelTime;
    @SerializedName("lane_waitingTime")
    public Double LaneWaitingTime;



    public LaneInfo() {
    }

    }
