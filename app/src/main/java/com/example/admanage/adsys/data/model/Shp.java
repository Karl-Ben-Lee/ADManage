package com.example.admanage.system_main.data.model;

import java.util.UUID;

public class Shp {
    private Integer id = null;
    private String shp;

    public Shp(){}

    public Shp(int id, String shp) {
        this.id = id;
        this.shp = shp;
    }

    public Shp(String shp) {
        this.shp = shp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShp() {
        return shp;
    }

    public void setShp(String shp) {
        this.shp = shp;
    }
}
