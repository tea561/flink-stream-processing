package bigdata.deserializers;

import bigdata.pojo.geojson.GeoJSONObject;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import com.google.gson.Gson;

import java.io.IOException;

public class GeoJSONObjectDeserializer implements DeserializationSchema<GeoJSONObject> {
    private static final Gson gson = new Gson();

    @Override
    public GeoJSONObject deserialize(byte[] bytes) throws IOException {
        return gson.fromJson(new String(bytes), GeoJSONObject.class);
    }

    @Override
    public boolean isEndOfStream(GeoJSONObject geoJSONObject) {
        return false;
    }

    @Override
    public TypeInformation<GeoJSONObject> getProducedType() {
        return TypeInformation.of(GeoJSONObject.class);
    }
}
