package bigdata.pojo;

import java.io.Serializable;

public class JoinedData implements Serializable {
    public LaneInfo laneInfo;
    public VehicleInfo vehicleInfo;
    public JoinedData(LaneInfo laneInfo, VehicleInfo vehicleInfo) {
        this.laneInfo = laneInfo;
        this.vehicleInfo = vehicleInfo;
    }
    public JoinedData() {

    }


}
