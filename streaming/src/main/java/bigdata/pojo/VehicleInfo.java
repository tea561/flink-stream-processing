package bigdata.pojo;

import com.google.gson.annotations.SerializedName;

public class VehicleInfo {
    @SerializedName("timestep_time")
    public Double TimeStep;
    @SerializedName("vehicle_CO")
    public Double VehicleCO;
    @SerializedName("vehicle_CO2")
    public Double VehicleCO2;
    @SerializedName("vehicle_HC")
    public Double VehicleHC;
    @SerializedName("vehicle_NOx")
    public Double VehicleNOx;
    @SerializedName("vehicle_PMx")
    public Double VehiclePMx;
    @SerializedName("vehicle_angle")
    public Double VehicleAngle;
    @SerializedName("vehicle_eclass")
    public String VehicleClass;
    @SerializedName("vehicle_electricity")
    public Double VehicleElectricity;
    @SerializedName("vehicle_fuel")
    public Double VehicleFuel;
    @SerializedName("vehicle_id")
    public String VehicleId;
    @SerializedName("vehicle_lane")
    public String VehicleLane;
    @SerializedName("vehicle_noise")
    public Double VehicleNoise;
    @SerializedName("vehicle_pos")
    public Double VehiclePos;
    @SerializedName("vehicle_route")
    public String VehicleRoute;
    @SerializedName("vehicle_speed")
    public Double VehicleSpeed;
    @SerializedName("vehicle_type")
    public String VehicleType;
    @SerializedName("vehicle_waiting")
    public Double VehicleWaiting;
    @SerializedName("vehicle_x")
    public Double VehicleX;
    @SerializedName("vehicle_y")
    public Double VehicleY;
}
