package com.example.admanage.system_main.ui.admanage;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.admanage.system_main.data.ADManageDataSource;
import com.example.admanage.system_main.data.ADManageRepository;

public class ADManageViewModelFactory implements ViewModelProvider.Factory {

    private String ip;
    private String port;

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    //创建LoginViewModel时传了一个LoginRepository单例进去。LoginRepository在实例化时传了一个LoginDataSource实例进去。
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ADManageViewModel.class)) {
            return (T) new ADManageViewModel(ADManageRepository.getInstance(new ADManageDataSource(ip,port)));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

    public ADManageViewModelFactory(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    public ADManageViewModelFactory() {
    }
}