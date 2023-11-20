package bigdata.pojo.geojson;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;

public class Suburb {
    private MultiPolygon multiPolygon;
    private String name;

    public Suburb() {
    }

    public Suburb(MultiPolygon multiPolygon, String name) {
        this.multiPolygon = multiPolygon;
        this.name = name;
    }

    public MultiPolygon getMultiPolygon() {
        return multiPolygon;
    }

    public void setMultiPolygon(MultiPolygon multiPolygon) {
        this.multiPolygon = multiPolygon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVehicleInSuburb(Point point) {
        GeometryFactory geometryFactory = new GeometryFactory();
        return multiPolygon.contains(point);
    }
}
