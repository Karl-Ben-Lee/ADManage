package com.example.admanage.system_main.ui.admanage;

import androidx.annotation.Nullable;

import com.example.admanage.system_main.data.model.Advertise;

public class ADModifyResult {
    // 成功则返回数据
    @Nullable
    private String success;
    // 错误则返回一个错误码
    @Nullable
    private Integer error;


    ADModifyResult(@Nullable Integer error) {
        this.error = error;
    }

    ADModifyResult(@Nullable String success) {
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
