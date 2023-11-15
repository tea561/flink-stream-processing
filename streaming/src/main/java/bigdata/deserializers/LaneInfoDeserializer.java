package bigdata.deserializers;

import bigdata.pojo.LaneInfo;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import com.google.gson.Gson;
import java.io.IOException;


public class LaneInfoDeserializer implements DeserializationSchema<LaneInfo> {
    private static final Gson gson = new Gson();
    @Override
    public LaneInfo deserialize(byte[] bytes) throws IOException {
        return gson.fromJson(new String(bytes), LaneInfo.class);
    }

    @Override
    public boolean isEndOfStream(LaneInfo laneInfo) {
        return false;
    }

    @Override
    public TypeInformation<LaneInfo> getProducedType() {
        return TypeInformation.of(LaneInfo.class);
    }
}
