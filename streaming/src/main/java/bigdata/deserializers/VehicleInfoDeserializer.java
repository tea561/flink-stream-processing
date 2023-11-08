package bigdata.deserializers;

import bigdata.pojo.VehicleInfo;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import com.google.gson.Gson;
import java.io.IOException;

public class VehicleInfoDeserializer implements DeserializationSchema<VehicleInfo> {
    private static final Gson gson = new Gson();

    @Override
    public VehicleInfo deserialize(byte[] bytes) throws IOException {
        return gson.fromJson(new String(bytes), VehicleInfo.class);
    }

    @Override
    public boolean isEndOfStream(VehicleInfo vehicleInfo) {
        return false;
    }

    @Override
    public TypeInformation<VehicleInfo> getProducedType() {
        return TypeInformation.of(VehicleInfo.class);
    }
}
