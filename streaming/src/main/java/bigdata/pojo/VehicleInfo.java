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

    public Double getTimeStep() {
        return TimeStep;
    }

    public Double getVehicleCO() {
        return VehicleCO;
    }

    public Double getVehicleCO2() {
        return VehicleCO2;
    }

    public Double getVehicleHC() {
        return VehicleHC;
    }

    public Double getVehicleNOx() {
        return VehicleNOx;
    }

    public Double getVehiclePMx() {
        return VehiclePMx;
    }

    public Double getVehicleAngle() {
        return VehicleAngle;
    }

    public String getVehicleClass() {
        return VehicleClass;
    }

    public Double getVehicleElectricity() {
        return VehicleElectricity;
    }

    public Double getVehicleFuel() {
        return VehicleFuel;
    }

    public String getVehicleId() {
        return VehicleId;
    }

    public String getVehicleLane() {
        return VehicleLane;
    }

    public Double getVehicleNoise() {
        return VehicleNoise;
    }

    public Double getVehiclePos() {
        return VehiclePos;
    }

    public String getVehicleRoute() {
        return VehicleRoute;
    }

    public Double getVehicleSpeed() {
        return VehicleSpeed;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public Double getVehicleWaiting() {
        return VehicleWaiting;
    }

    public Double getVehicleX() {
        return VehicleX;
    }

    public Double getVehicleY() {
        return VehicleY;
    }

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
