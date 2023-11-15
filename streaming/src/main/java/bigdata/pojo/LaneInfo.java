package bigdata.pojo;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;
import com.google.gson.annotations.SerializedName;
import scala.Int;

public class LaneInfo {
    @SerializedName("interval_begin")
    public Float IntervalBegin;
    @SerializedName("interval_end")
    public Float IntervalEnd;
    @SerializedName("interval_id")
    public String IntervalId;
    @SerializedName("edge_id")
    public String EdgeId;
    @SerializedName("lane_arrived")
    public Integer LaneArrived;
    @SerializedName("lane_density")
    public Float LaneDensity;
    @SerializedName("lane_departed")
    public Integer LaneDeparted;
    @SerializedName("lane_entered")
    public Integer LaneEntered;
    @SerializedName("lane_id")
    public String LaneId;
    @SerializedName("lane_left")
    public Integer LaneLeft;
    @SerializedName("lane_occupancy")
    public Float LaneOccupancy;
    @SerializedName("lane_overlapTraveltime")
    public Float LaneOverlapTraveltime;
    @SerializedName("lane_sampledSeconds")
    public Float LaneSampledSeconds;
    @SerializedName("lane_speed")
    public Float LaneSpeed;
    @SerializedName("lane_speedRelative")
    public Float LaneSpeedRelative;
    @SerializedName("lane_timeLoss")
    public Float LaneTimeLoss;
    @SerializedName("lane_traveltime")
    public Float LaneTravelTime;
    @SerializedName("lane_waitingTime")
    public Float LaneWaitingTime;



    public LaneInfo() {
    }

    }
