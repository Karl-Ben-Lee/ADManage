package com.example.admanage.system_login.ui;

/**
 * Class exposing authenticated user details to the UI.
 * 类向UI公开已验证的用户详细信息。
 * 上一步LoginResult
 * 数据经过验证后，返回给接下来UI数据的数据对象：
 * 数据来源LoggedInUser在LoginViewModel的login方法给与的
 * 如果验证通过，该类向接下来的UI提供什么数据
 * 下一步
 */
public class LoggedInUserView {
    private String displayName;
    //... other data fields that may be accessible to the UI
    //UI可以访问的其他数据字段

    LoggedInUserView(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}