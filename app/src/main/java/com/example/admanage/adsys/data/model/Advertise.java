package com.example.admanage.system_main.data.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Advertise {
    private int id;
    private String name;
    private String content;
    private String standard;
    private String type_id;
    private String style;
    private String location;
    private double a_long;
    private double a_lat;
    private String fontpicture;
    private String reversepicture;
    private String owner;
    private String owener_contacts;
    private String owener_tel;
    private String user;
    private String user_contacts;
    private String user_tel;
    private String shp_id;
    private String shptime;
    private String period_start;
    private String period_end;
    private float money;
    private int fee_status;
    private int plan_status;
    private String remove_time;
    private int remove_status;
    private String operator;
    private String operator_time;

    public Advertise() {}

	public Advertise(int id, String name, String content, String standard, String type_id, String style, String location, double a_long, double a_lat, String owner, String owener_contacts, String owener_tel, String user, String user_contacts, String user_tel, String shp_id, String shptime, String period_start, String period_end, float money, int fee_status, int plan_status, String remove_time, int remove_status, String operator, String operator_time) {
		this.id = id;
		this.name = name;
		this.content = content;
		this.standard = standard;
		this.type_id = type_id;
		this.style = style;
		this.location = location;
		this.a_long = a_long;
		this.a_lat = a_lat;
		this.owner = owner;
		this.owener_contacts = owener_contacts;
		this.owener_tel = owener_tel;
		this.user = user;
		this.user_contacts = user_contacts;
		this.user_tel = user_tel;
		this.shp_id = shp_id;
		this.shptime = shptime;
		this.period_start = period_start;
		this.period_end = period_end;
		this.money = money;
		this.fee_status = fee_status;
		this.plan_status = plan_status;
		this.remove_time = remove_time;
		this.remove_status = remove_status;
		this.operator = operator;
		this.operator_time = operator_time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getA_long() {
		return a_long;
	}

	public void setA_long(double a_long) {
		this.a_long = a_long;
	}

	public double getA_lat() {
		return a_lat;
	}

	public void setA_lat(double a_lat) {
		this.a_lat = a_lat;
	}

	public String getFontpicture() {
		return fontpicture;
	}

	public void setFontpicture(String fontpicture) {
		this.fontpicture = fontpicture;
	}

	public String getReversepicture() {
		return reversepicture;
	}

	public void setReversepicture(String reversepicture) {
		this.reversepicture = reversepicture;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwener_contacts() {
		return owener_contacts;
	}

	public void setOwener_contacts(String owener_contacts) {
		this.owener_contacts = owener_contacts;
	}

	public String getOwener_tel() {
		return owener_tel;
	}

	public void setOwener_tel(String owener_tel) {
		this.owener_tel = owener_tel;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser_contacts() {
		return user_contacts;
	}

	public void setUser_contacts(String user_contacts) {
		this.user_contacts = user_contacts;
	}

	public String getUser_tel() {
		return user_tel;
	}

	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}

	public String getShp_id() {
		return shp_id;
	}

	public void setShp_id(String shp_id) {
		this.shp_id = shp_id;
	}

	public String getShptime() {
		return shptime;
	}

	public void setShptime(String shptime) {
		this.shptime = shptime;
	}

	public String getPeriod_start() {
		return period_start;
	}

	public void setPeriod_start(String period_start) {
		this.period_start = period_start;
	}

	public String getPeriod_end() {
		return period_end;
	}

	public void setPeriod_end(String period_end) {
		this.period_end = period_end;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public int getFee_status() {
		return fee_status;
	}

	public void setFee_status(int fee_status) {
		this.fee_status = fee_status;
	}

	public int getPlan_status() {
		return plan_status;
	}

	public void setPlan_status(int plan_status) {
		this.plan_status = plan_status;
	}

	public String getRemove_time() {
		return remove_time;
	}

	public void setRemove_time(String remove_time) {
		this.remove_time = remove_time;
	}

	public int getRemove_status() {
		return remove_status;
	}

	public void setRemove_status(int remove_status) {
		this.remove_status = remove_status;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperator_time() {
		return operator_time;
	}

	public void setOperator_time(String operator_time) {
		this.operator_time = operator_time;
	}

	public JSONObject toJSON() {
		JSONObject data = new JSONObject();
		try {
			data.put("id",id);
			data.put("name",name);
			data.put("content",content);
			data.put("standard",standard);
			data.put("type_id",type_id);
			data.put("style",style);
			data.put("location",location);
			data.put("a_long",a_long);
			data.put("a_lat",a_lat);
			data.put("fontpicture",fontpicture);
			data.put("reversepicture",reversepicture);
			data.put("owner",owner);
			data.put("owener_contacts",owener_contacts);
			data.put("owener_tel",owener_tel);
			data.put("user",user);
			data.put("user_contacts",user_contacts);
			data.put("user_tel",user_tel);
			data.put("shp_id",shp_id);
			data.put("shptime",shptime);
			data.put("period_start",period_start);
			data.put("period_end",period_end);
			data.put("money",money);
			data.put("fee_status",fee_status);
			data.put("plan_status",plan_status);
			data.put("remove_time",remove_time);
			data.put("remove_status",remove_status);
			data.put("operator",operator);
			data.put("operator_time",operator_time);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data;
	}
}
