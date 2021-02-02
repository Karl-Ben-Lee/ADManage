package com.example.admanage.system_login.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 * 为从LoginRepository检索到的已登录用户捕获用户信息的数据类
 * 用户数据实体类：后台的，用于登录后的逻辑的数据对象，而非登陆前的
 * 数据来源LoginDataSource
 */
public class LoggedInUser {

    private String userId;
    private String displayName;

    public LoggedInUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }
}