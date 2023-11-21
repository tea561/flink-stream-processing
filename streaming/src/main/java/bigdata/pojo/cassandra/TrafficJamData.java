package bigdata.pojo.cassandra;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Date;

@Table(keyspace = "bigdata", name = "traffic_jam")
public class TrafficJamData {
    @Column(name = "laneId")
    public String LaneId;

    @Column(name = "avgTrafficVolume")
    public Double AvgTrafficVolume;

    @Column(name = "date")
    public java.util.Date Date;

    @Column(name = "avgSpeed")
    public Double AvgSpeed;

    @Column(name = "avgWaitingTime")
    public Double AvgWaitingTime;


    public TrafficJamData() {
    }

    public TrafficJamData(String laneId, java.util.Date date, Double avgTrafficVolume, Double avgSpeed, Double avgWaitingTime) {
        LaneId = laneId;
        AvgTrafficVolume = avgTrafficVolume;
        Date = date;
        AvgSpeed = avgSpeed;
        AvgWaitingTime = avgWaitingTime;
    }

    public String getLaneId() {
        return LaneId;
    }

    public void setLaneId(String laneId) {
        LaneId = laneId;
    }

    public Double getAvgTrafficVolume() {
        return AvgTrafficVolume;
    }

    public void setAvgTrafficVolume(Double avgTrafficVolume) {
        AvgTrafficVolume = avgTrafficVolume;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public Double getAvgSpeed() {
        return AvgSpeed;
    }

    public void setAvgSpeed(Double avgSpeed) {
        AvgSpeed = avgSpeed;
    }

    public Double getAvgWaitingTime() {
        return AvgWaitingTime;
    }

    public void setAvgWaitingTime(Double avgWaitingTime) {
        AvgWaitingTime = avgWaitingTime;
    }
}
