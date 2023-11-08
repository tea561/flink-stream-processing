package bigdata.pojo;

import com.google.gson.annotations.SerializedName;

public class VehicleInfo {
    @SerializedName("data_timestep")
    public Float DataTimeStep;
    @SerializedName("vehicle_CO")
    public Float VehicleCO;
    @SerializedName("vehicle_CO2")
    public Float VehicleCO2;
    @SerializedName("vehicle_HC")
    public Float VehicleHC;
    @SerializedName("vehicle_NOx")
    public Float VehicleNOx;
    @SerializedName("vehicle_PMx")
    public Float VehiclePMx;
    @SerializedName("vehicle_angle")
    public Float VehicleAngle;
    @SerializedName("vehicle_eclass")
    public String VehicleClass;
    @SerializedName("vehicle_electricity")
    public Float VehicleElectricity;
    @SerializedName("vehicle_fuel")
    public Float VehicleFuel;
    @SerializedName("vehicle_id")
    public String VehicleId;
    @SerializedName("vehicle_lane")
    public String VehicleLane;
    @SerializedName("vehicle_noise")
    public Float VehicleNoise;
    @SerializedName("vehicle_pos")
    public Float VehiclePos;
    @SerializedName("vehicle_route")
    public String VehicleRoute;
    @SerializedName("vehicle_speed")
    public Float VehicleSpeed;
    @SerializedName("vehicle_type")
    public String VehicleType;
    @SerializedName("vehicle_waiting")
    public String VehicleWaiting;
    @SerializedName("vehicle_x")
    public Float VehicleX;
    @SerializedName("vehicle_y")
    public Float VehicleY;
}
