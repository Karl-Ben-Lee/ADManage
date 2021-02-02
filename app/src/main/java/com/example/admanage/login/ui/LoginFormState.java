package com.example.admanage.system_login.ui;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 * 登录表单的数据验证状态。
 * UI控件输入数据验证类：登录数据有效性验证，如用户名、密码的有效性验证，可以用一个类来记录这些登录表单的状态
 * 下一步LoginResult
 */
public class LoginFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    // 我们用这个字段来控制登录按钮的状态
    private boolean isDataValid;

    LoginFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    LoginFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    public Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    public Integer getPasswordError() {
        return passwordError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}