package com.example.admanage.system_main.ui.adstatistics;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ADStatisticsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ADStatisticsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}