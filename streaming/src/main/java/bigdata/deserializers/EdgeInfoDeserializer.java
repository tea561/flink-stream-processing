package bigdata.deserializers;

import bigdata.pojo.EdgeInfo;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import com.google.gson.Gson;
import java.io.IOException;


public class EdgeInfoDeserializer implements DeserializationSchema<EdgeInfo> {
    private static final Gson gson = new Gson();
    @Override
    public EdgeInfo deserialize(byte[] bytes) throws IOException {
        return gson.fromJson(new String(bytes), EdgeInfo.class);
    }

    @Override
    public boolean isEndOfStream(EdgeInfo edgeInfo) {
        return false;
    }

    @Override
    public TypeInformation<EdgeInfo> getProducedType() {
        return TypeInformation.of(EdgeInfo.class);
    }
}
