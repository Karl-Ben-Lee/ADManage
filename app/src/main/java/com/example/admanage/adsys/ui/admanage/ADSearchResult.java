package com.example.admanage.system_main.ui.admanage;

import androidx.annotation.Nullable;

import com.example.admanage.system_main.data.model.Advertise;

import java.util.ArrayList;

public class ADSearchResult {

    // 成功则返回数据
    @Nullable
    private ArrayList<Advertise> success;
    // 错误则返回一个错误码
    @Nullable
    private Integer error;


    ADSearchResult(@Nullable Integer error) {
        this.error = error;
    }

    ADSearchResult(@Nullable ArrayList<Advertise> success) {
        this.success = success;
    }

    @Nullable
    public ArrayList<Advertise> getSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}