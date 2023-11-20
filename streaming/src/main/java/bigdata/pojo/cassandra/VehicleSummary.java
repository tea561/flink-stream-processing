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

    public Double VehicleCO;
    @Column(name = "vehicleCO2")

    public Double VehicleCO2;
    @Column(name = "vehicleHC")

    public Double VehicleHC;
    @Column(name = "vehicleNOx")

    public Double VehicleNOx;
    @Column(name = "vehiclePMx")

    public Double VehiclePMx;

    public VehicleSummary() {
    }

    public VehicleSummary(java.util.Date date, String vehicleId, Double vehicleCO, Double vehicleCO2, Double vehicleHC, Double vehicleNOx, Double vehiclePMx) {
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

    public Double getVehicleCO() {
        return VehicleCO;
    }

    public void setVehicleCO(Double vehicleCO) {
        VehicleCO = vehicleCO;
    }

    public Double getVehicleCO2() {
        return VehicleCO2;
    }

    public void setVehicleCO2(Double vehicleCO2) {
        VehicleCO2 = vehicleCO2;
    }

    public Double getVehicleHC() {
        return VehicleHC;
    }

    public void setVehicleHC(Double vehicleHC) {
        VehicleHC = vehicleHC;
    }

    public Double getVehicleNOx() {
        return VehicleNOx;
    }

    public void setVehicleNOx(Double vehicleNOx) {
        VehicleNOx = vehicleNOx;
    }

    public Double getVehiclePMx() {
        return VehiclePMx;
    }

    public void setVehiclePMx(Double vehiclePMx) {
        VehiclePMx = vehiclePMx;
    }
}
