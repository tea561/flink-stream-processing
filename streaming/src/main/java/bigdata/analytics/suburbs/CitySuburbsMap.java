package bigdata.analytics.suburbs;

import bigdata.pojo.VehicleInfo;
import bigdata.pojo.geojson.GeoJSONObject;
import bigdata.pojo.geojson.Suburb;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CitySuburbsMap extends RichFlatMapFunction<VehicleInfo, Tuple2<String, VehicleInfo>> {
    private final List<Suburb> suburbs = new ArrayList<>();
    private static final Logger LOG = LoggerFactory.getLogger(CitySuburbsMap.class);

    @Override
    public void open(Configuration parameters) throws Exception {
        //super.open(parameters);
        String filePath = "/areas.geojson";
        InputStream inputStream = CitySuburbsMap.class.getResourceAsStream(filePath);

        ObjectMapper objectMapper = new ObjectMapper();
        GeoJSONObject[] d = objectMapper.readValue(inputStream, GeoJSONObject[].class);
        loadSuburbs(Arrays.asList(d));
    }

    @Override
    public void flatMap(VehicleInfo vehicleInfo, Collector<Tuple2<String, VehicleInfo>> collector) throws Exception {
        String name = findSuburb(vehicleInfo.VehicleX, vehicleInfo.VehicleY);
        Tuple2<String, VehicleInfo> veh = new Tuple2<>(name, vehicleInfo);
        collector.collect(veh);
    }

    public String findSuburb(double posX, double posY) {
        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(posX, posY));
        for (Suburb suburb : suburbs) {
            if (suburb.isVehicleInSuburb(point)) {
                return suburb.getName();
            }
        }
        return "";
    }

    public void loadSuburbs(List<GeoJSONObject> geoJSONObjects) {
        for (GeoJSONObject geoJSONObject : geoJSONObjects) {
            suburbs.add(geoJSONObject.createSuburb());
        }
    }
}
