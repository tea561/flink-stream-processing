package bigdata.pojo;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "bigdata", name = "edgeemission")
public class EdgeEmissionValues {
    @Column(name = "edgeId")
    public String EdgeId;
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

    public EdgeEmissionValues(String edgeId, Float laneCO, Float laneCO2, Float laneHC, Float laneNOx, Float lanePMx) {
        EdgeId = edgeId;
        LaneCO = laneCO;
        LaneCO2 = laneCO2;
        LaneHC = laneHC;
        LaneNOx = laneNOx;
        LanePMx = lanePMx;
    }

    public String getEdgeId() {
        return EdgeId;
    }

    public void setEdgeId(String edgeId) {
        EdgeId = edgeId;
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

    public EdgeEmissionValues() {

    }

    @Override
    public String toString() {
        return "EdgeEmissionValues{" +
                "EdgeId='" + EdgeId + '\'' +
                ", LaneCO=" + LaneCO +
                ", LaneCO2=" + LaneCO2 +
                ", LaneHC=" + LaneHC +
                ", LaneNOx=" + LaneNOx +
                ", LanePMx=" + LanePMx +
                '}';
    }
}
