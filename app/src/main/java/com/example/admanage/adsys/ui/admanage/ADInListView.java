package com.example.admanage.system_main.ui.admanage;

public class ADInListView {
    private String id;
    private String adname;
    private String duedate;
    private String feestate;

    public ADInListView() {
    }

    public ADInListView(String id, String adname, String duedate, String feestate) {
        this.id = id;
        this.adname = adname;
        this.duedate = duedate;
        this.feestate = feestate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdName() {
        return adname;
    }

    public void setAdName(String adname) {
        this.adname = adname;
    }

    public String getDueDate() {
        return duedate;
    }

    public void setDueDate(String durdate) {
        this.duedate = durdate;
    }

    public String getFeeState() {
        return feestate;
    }

    public void setFeeState(String feestate) {
        this.feestate = feestate;
    }
}
