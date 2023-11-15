package bigdata.pojo.cassandra;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Date;

@Table(keyspace = "bigdata", name = "lane_vehicle_count")
public class LaneVehicleCount {
    @Column(name = "date")
    public Date Date;

    @Column(name = "laneId")
    public String LaneId;

    @Column(name = "vehicleCount")
    public Integer VehicleCount;

    public LaneVehicleCount(Date date, String laneId, Integer vehicleCount) {
        Date = date;
        LaneId = laneId;
        VehicleCount = vehicleCount;
    }

    public LaneVehicleCount() {
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        Date = date;
    }

    public String getLaneId() {
        return LaneId;
    }

    public void setLaneId(String laneId) {
        LaneId = laneId;
    }

    public Integer getVehicleCount() {
        return VehicleCount;
    }

    public void setVehicleCount(Integer vehicleCount) {
        VehicleCount = vehicleCount;
    }
}
