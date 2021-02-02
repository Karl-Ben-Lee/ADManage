package com.example.admanage.system_main.data.model;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	private int u_id;
	private String u_name;
	private String u_truename;
	private String u_password;
	private int u_authority;
	
	public User(){}
	
	public User(String id, String username, String truename, String newpass) {
		this.u_id=Integer.parseInt(id);
		this.u_name=username;
		this.u_truename=truename;
		this.u_password=newpass;
	}

	public User(String id, String username, String truename, String newpass,String authority) {
		this.u_id=Integer.parseInt(id);
		this.u_name=username;
		this.u_truename=truename;
		this.u_password=newpass;
		this.u_authority=Integer.parseInt(authority);
	}

	public int getU_id() {
		return (int)u_id;
	}

	public void setU_id(int uId) {
		u_id = uId;
	}

	public String getU_name() {
		return u_name;
	}

	public void setU_name(String uName) {
		u_name = uName;
	}

	public String getU_truename() {
		return u_truename;
	}

	public void setU_truename(String uTruename) {
		u_truename = uTruename;
	}

	public String getU_password() {
		return u_password;
	}

	public void setU_password(String uPassword) {
		u_password = uPassword;
	}

	public int getU_authority() {
		return (int)u_authority;
	}

	public void setU_authority(int uAuthority) {
		u_authority = uAuthority;
	}

	public JSONObject toJSON() {
		JSONObject data = new JSONObject();
		try {
			data.put("u_id", u_id);
			data.put("u_name", u_name);
			data.put("u_password",u_password);
			data.put("u_truename", u_truename);
			data.put("u_authority", u_authority);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data;
	}
}
