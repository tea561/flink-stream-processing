package bigdata.pojo.cassandra;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Date;

@Table(keyspace = "bigdata", name = "lane_aggregation")
public class LaneAggregationData {

    @Column(name = "date")
    public Date Date;
    @Column(name = "laneId")
    public String LaneId;
    @Column(name = "laneCO")

    public Double LaneCO;
    @Column(name = "laneCO2")

    public Double LaneCO2;
    @Column(name = "laneHC")

    public Double LaneHC;
    @Column(name = "laneNOx")

    public Double LaneNOx;
    @Column(name = "lanePMx")

    public Double LanePMx;

    @Column(name = "avgSpeed")
    public Double AvgSpeed;

    @Column(name = "avgWaitingTime")
    public Double AvgWaitingTime;

    public LaneAggregationData(java.util.Date date, String laneId, Double laneCO, Double laneCO2, Double laneHC, Double laneNOx, Double lanePMx, Double avgSpeed, Double avgWaitingTime) {
        Date = date;
        LaneId = laneId;
        LaneCO = laneCO;
        LaneCO2 = laneCO2;
        LaneHC = laneHC;
        LaneNOx = laneNOx;
        LanePMx = lanePMx;
        AvgSpeed = avgSpeed;
        AvgWaitingTime = avgWaitingTime;
    }

    public String getLaneId() {
        return LaneId;
    }

    public void setLaneId(String laneId) {
        LaneId = laneId;
    }

    public Double getLaneCO() {
        return LaneCO;
    }

    public void setLaneCO(Double laneCO) {
        LaneCO = laneCO;
    }

    public Double getLaneCO2() {
        return LaneCO2;
    }

    public void setLaneCO2(Double laneCO2) {
        LaneCO2 = laneCO2;
    }

    public Double getLaneHC() {
        return LaneHC;
    }

    public void setLaneHC(Double laneHC) {
        LaneHC = laneHC;
    }

    public Double getLaneNOx() {
        return LaneNOx;
    }

    public void setLaneNOx(Double laneNOx) {
        LaneNOx = laneNOx;
    }

    public Double getLanePMx() {
        return LanePMx;
    }

    public void setLanePMx(Double lanePMx) {
        LanePMx = lanePMx;
    }

    public LaneAggregationData() {

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

    @Override
    public String toString() {
        return "LaneEmissions{" +
                "Date=" + Date +
                ", LaneId='" + LaneId + '\'' +
                ", LaneCO=" + LaneCO +
                ", LaneCO2=" + LaneCO2 +
                ", LaneHC=" + LaneHC +
                ", LaneNOx=" + LaneNOx +
                ", LanePMx=" + LanePMx +
                ", AvgSpeed=" + AvgSpeed +
                ", AvgWaitingTime=" + AvgWaitingTime +
                '}';
    }
}
