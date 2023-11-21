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

    public Double SuburbCO;
    @Column(name = "suburbCO2")

    public Double SuburbCO2;
    @Column(name = "suburbHC")

    public Double SuburbHC;
    @Column(name = "suburbNOx")

    public Double SuburbNOx;
    @Column(name = "suburbPMx")

    public Double SuburbPMx;

    public SuburbEmission(String name, java.util.Date date, Double suburbCO, Double suburbCO2, Double suburbHC, Double suburbNOx, Double suburbPMx) {
        Name = name;
        Date = date;
        SuburbCO = suburbCO;
        SuburbCO2 = suburbCO2;
        SuburbHC = suburbHC;
        SuburbNOx = suburbNOx;
        SuburbPMx = suburbPMx;
    }

    public SuburbEmission() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public Double getSuburbCO() {
        return SuburbCO;
    }

    public void setSuburbCO(Double suburbCO) {
        SuburbCO = suburbCO;
    }

    public Double getSuburbCO2() {
        return SuburbCO2;
    }

    public void setSuburbCO2(Double suburbCO2) {
        SuburbCO2 = suburbCO2;
    }

    public Double getSuburbHC() {
        return SuburbHC;
    }

    public void setSuburbHC(Double suburbHC) {
        SuburbHC = suburbHC;
    }

    public Double getSuburbNOx() {
        return SuburbNOx;
    }

    public void setSuburbNOx(Double suburbNOx) {
        SuburbNOx = suburbNOx;
    }

    public Double getSuburbPMx() {
        return SuburbPMx;
    }

    public void setSuburbPMx(Double suburbPMx) {
        SuburbPMx = suburbPMx;
    }
}
