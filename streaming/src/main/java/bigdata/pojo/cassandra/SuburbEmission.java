package bigdata.pojo.cassandra;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Date;

@Table(keyspace = "bigdata", name = "suburb_emission")
public class SuburbEmission {
    
    @Column(name = "name")
    private String Name;
    @Column(name = "date")
    public java.util.Date Date;
    @Column(name = "suburbCO")

    public Double LaneCO;
    @Column(name = "suburbCO2")

    public Double LaneCO2;
    @Column(name = "suburbHC")

    public Double LaneHC;
    @Column(name = "suburbNOx")

    public Double LaneNOx;
    @Column(name = "suburbPMx")

    public Double LanePMx;
}
