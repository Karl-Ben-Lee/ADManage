package com.example.admanage.system_login.ui;

import androidx.annotation.Nullable;

import com.example.admanage.system_main.data.model.User;
import com.example.admanage.system_login.ui.LoggedInUserView;

/**
 * Authentication result : success (user details) or error message.
 * 验证结果：成功（用户详细信息）或错误消息。
 * 上一步LoginFormState检验表单数据的合法性
 * UI控件数据验证结果：当数据有效性通过后，就开始登录，登录或成功或失败，我们用一个类来记录这些登录的结果：
 * 下一步LoggedInUserView提供给UI所需的数据
 */
public class LoginResult {
    // 成功则返回数据
    @Nullable
    private User success;
    // 错误则返回一个错误码
    @Nullable
    private Integer error;


    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    LoginResult(@Nullable User success) {
        this.success = success;
    }

    @Nullable
    public User getSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}