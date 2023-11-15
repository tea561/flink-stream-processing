package bigdata.pojo.cassandra;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Date;

@Table(keyspace = "bigdata", name = "vehicle_summary")
public class VehicleSummary {
    @Column(name = "date")
    public java.util.Date Date;

    @Column(name = "vehicleId")
    public String VehicleId;
    @Column(name = "vehicleCO")

    public Float VehicleCO;
    @Column(name = "vehicleCO2")

    public Float VehicleCO2;
    @Column(name = "vehicleHC")

    public Float VehicleHC;
    @Column(name = "vehicleNOx")

    public Float VehicleNOx;
    @Column(name = "vehiclePMx")

    public Float VehiclePMx;

    public VehicleSummary() {
    }

    public VehicleSummary(java.util.Date date, String vehicleId, Float vehicleCO, Float vehicleCO2, Float vehicleHC, Float vehicleNOx, Float vehiclePMx) {
        Date = date;
        VehicleId = vehicleId;
        VehicleCO = vehicleCO;
        VehicleCO2 = vehicleCO2;
        VehicleHC = vehicleHC;
        VehicleNOx = vehicleNOx;
        VehiclePMx = vehiclePMx;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public String getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(String vehicleId) {
        VehicleId = vehicleId;
    }

    public Float getVehicleCO() {
        return VehicleCO;
    }

    public void setVehicleCO(Float vehicleCO) {
        VehicleCO = vehicleCO;
    }

    public Float getVehicleCO2() {
        return VehicleCO2;
    }

    public void setVehicleCO2(Float vehicleCO2) {
        VehicleCO2 = vehicleCO2;
    }

    public Float getVehicleHC() {
        return VehicleHC;
    }

    public void setVehicleHC(Float vehicleHC) {
        VehicleHC = vehicleHC;
    }

    public Float getVehicleNOx() {
        return VehicleNOx;
    }

    public void setVehicleNOx(Float vehicleNOx) {
        VehicleNOx = vehicleNOx;
    }

    public Float getVehiclePMx() {
        return VehiclePMx;
    }

    public void setVehiclePMx(Float vehiclePMx) {
        VehiclePMx = vehiclePMx;
    }
}
