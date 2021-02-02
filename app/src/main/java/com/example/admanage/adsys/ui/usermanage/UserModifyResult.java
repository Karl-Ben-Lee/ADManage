package com.example.admanage.system_main.ui.usermanage;

import androidx.annotation.Nullable;

public class UserModifyResult {
    @Nullable
    private String success;
    // 错误则返回一个错误码
    @Nullable
    private Integer error;


    UserModifyResult(@Nullable Integer error) {
        this.error = error;
    }

    UserModifyResult(@Nullable String success) {
        this.success = success;
    }

    @Nullable
    public String getSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}
