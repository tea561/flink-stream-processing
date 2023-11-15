package bigdata.pojo.cassandra;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Date;

@Table(keyspace = "bigdata", name = "lane_emissions")
public class LaneEmissions {

    @Column(name = "date")
    public Date Date;
    @Column(name = "laneId")
    public String LaneId;
    @Column(name = "laneCO")

    public Float LaneCO;
    @Column(name = "laneCO2")

    public Float LaneCO2;
    @Column(name = "laneHC")

    public Float LaneHC;
    @Column(name = "laneNOx")

    public Float LaneNOx;
    @Column(name = "lanePMx")

    public Float LanePMx;


    public LaneEmissions(String laneId, Float laneCO, Float laneCO2, Float laneHC, Float laneNOx, Float lanePMx) {
        LaneId = laneId;
        LaneCO = laneCO;
        LaneCO2 = laneCO2;
        LaneHC = laneHC;
        LaneNOx = laneNOx;
        LanePMx = lanePMx;
        Date = new Date();
    }

    public String getLaneId() {
        return LaneId;
    }

    public void setLaneId(String laneId) {
        LaneId = laneId;
    }

    public Float getLaneCO() {
        return LaneCO;
    }

    public void setLaneCO(Float laneCO) {
        LaneCO = laneCO;
    }

    public Float getLaneCO2() {
        return LaneCO2;
    }

    public void setLaneCO2(Float laneCO2) {
        LaneCO2 = laneCO2;
    }

    public Float getLaneHC() {
        return LaneHC;
    }

    public void setLaneHC(Float laneHC) {
        LaneHC = laneHC;
    }

    public Float getLaneNOx() {
        return LaneNOx;
    }

    public void setLaneNOx(Float laneNOx) {
        LaneNOx = laneNOx;
    }

    public Float getLanePMx() {
        return LanePMx;
    }

    public void setLanePMx(Float lanePMx) {
        LanePMx = lanePMx;
    }

    public LaneEmissions() {

    }

    @Override
    public String toString() {
        return "LaneEmissionsValues{" +
                "LaneId='" + LaneId + '\'' +
                ", LaneCO=" + LaneCO +
                ", LaneCO2=" + LaneCO2 +
                ", LaneHC=" + LaneHC +
                ", LaneNOx=" + LaneNOx +
                ", LanePMx=" + LanePMx +
                '}';
    }
}
