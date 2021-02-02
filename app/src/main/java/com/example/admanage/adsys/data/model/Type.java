package com.example.admanage.system_main.data.model;

public class Type {
    private Integer id = null;
    private String type;

    public Type(){}

    public Type(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public Type(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
