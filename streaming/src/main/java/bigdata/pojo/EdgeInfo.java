package bigdata.pojo;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;
import com.google.gson.annotations.SerializedName;

@Table(keyspace = "bigdata", name="edges")
public class EdgeInfo {
    @Column(name = "dataTimeStep")
    @SerializedName("data_timestep")
    public Float DataTimeStep;

    @SerializedName("edge_id")
    @Column(name = "edgeId")
    public String EdgeId;

    @SerializedName("edge_traveltime")
    @Column(name = "edgeTravelTime")
    public Float EdgeTravelTime;

    @SerializedName("lane_CO")
    @Column(name = "laneCO")
    public Float LaneCO;
    @SerializedName("lane_CO2")
    @Column(name = "laneCO2")
    public Float LaneCO2;
    @SerializedName("lane_HC")
    @Column(name = "laneHC")
    public Float LaneHC;
    @SerializedName("lane_NOx")
    @Column(name = "laneNOx")
    public Float LaneNOx;
    @SerializedName("lane_PMx")
    @Column(name = "lanePMx")
    public Float LanePMx;
    @SerializedName("lane_electricity")
    @Column(name = "laneElectricity")
    public Float LaneElectricity;
    @SerializedName("lane_fuel")
    @Column(name = "laneFuel")
    public Float LaneFuel;
    @SerializedName("lane_id")
    @Column(name = "laneId")
    public String LaneId;
    @SerializedName("lane_maxspeed")
    @Column(name = "laneMaxSpeed")
    public Float LaneMaxSpeed;
    @SerializedName("lane_meanspeed")
    @Column(name = "laneMeanSpeed")
    public Float LaneMeanSpeed;
    @SerializedName("lane_noise")
    @Column(name = "laneNoise")
    public Float LaneNoise;
    @SerializedName("lane_occupancy")
    @Column(name = "laneOccupancy")
    public Float LaneOccupancy;
    @SerializedName("lane_vehicle_count")
    @Column(name = "laneVehicleCount")
    public Integer LaneVehicleCount;


    public EdgeInfo() {
    }

    public Float getDataTimeStep() {
        return DataTimeStep;
    }

    public void setDataTimeStep(Float dataTimeStep) {
        DataTimeStep = dataTimeStep;
    }

    public String getEdgeId() {
        return EdgeId;
    }

    public void setEdgeId(String edgeId) {
        EdgeId = edgeId;
    }

    public Float getEdgeTravelTime() {
        return EdgeTravelTime;
    }

    public void setEdgeTravelTime(Float edgeTravelTime) {
        EdgeTravelTime = edgeTravelTime;
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

    public Float getLaneElectricity() {
        return LaneElectricity;
    }

    public void setLaneElectricity(Float laneElectricity) {
        LaneElectricity = laneElectricity;
    }

    public Float getLaneFuel() {
        return LaneFuel;
    }

    public void setLaneFuel(Float laneFuel) {
        LaneFuel = laneFuel;
    }

    public String getLaneId() {
        return LaneId;
    }

    public void setLaneId(String laneId) {
        LaneId = laneId;
    }

    public Float getLaneMaxSpeed() {
        return LaneMaxSpeed;
    }

    public void setLaneMaxSpeed(Float laneMaxSpeed) {
        LaneMaxSpeed = laneMaxSpeed;
    }

    public Float getLaneMeanSpeed() {
        return LaneMeanSpeed;
    }

    public void setLaneMeanSpeed(Float laneMeanSpeed) {
        LaneMeanSpeed = laneMeanSpeed;
    }

    public Float getLaneNoise() {
        return LaneNoise;
    }

    public void setLaneNoise(Float laneNoise) {
        LaneNoise = laneNoise;
    }

    public Float getLaneOccupancy() {
        return LaneOccupancy;
    }

    public void setLaneOccupancy(Float laneOccupancy) {
        LaneOccupancy = laneOccupancy;
    }

    public Integer getLaneVehicleCount() {
        return LaneVehicleCount;
    }

    public void setLaneVehicleCount(Integer laneVehicleCount) {
        LaneVehicleCount = laneVehicleCount;
    }
}
