package com.example.admanage.system_main.data.model;

public class SearchModel {

    private String adname;
    private String sourposit;
    private String userposit;
    private String streetname;
    private String duetime;
    private int feestate;
    private int planstate;
    private int movestate;

    public SearchModel(String adname, String sourposit, String userposit, String streetname, String duetime, int feestate, int planstate, int movestate) {
        this.adname = adname;
        this.sourposit = sourposit;
        this.userposit = userposit;
        this.streetname = streetname;
        this.duetime = duetime;
        this.feestate = feestate;
        this.planstate = planstate;
        this.movestate = movestate;
    }

    public String getAdname() {
        return adname;
    }

    public void setAdname(String adname) {
        this.adname = adname;
    }

    public String getSourposit() {
        return sourposit;
    }

    public void setSourposit(String sourposit) {
        this.sourposit = sourposit;
    }

    public String getUserposit() {
        return userposit;
    }

    public void setUserposit(String userposit) {
        this.userposit = userposit;
    }

    public String getStreetname() {
        return streetname;
    }

    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }

    public String getDuetime() {
        return duetime;
    }

    public void setDuetime(String duetime) {
        this.duetime = duetime;
    }

    public int getFeestate() {
        return feestate;
    }

    public void setFeestate(int feestate) {
        this.feestate = feestate;
    }

    public int getPlanstate() {
        return planstate;
    }

    public void setPlanstate(int planstate) {
        this.planstate = planstate;
    }

    public int getMovestate() {
        return movestate;
    }

    public void setMovestate(int movestate) {
        this.movestate = movestate;
    }
}
