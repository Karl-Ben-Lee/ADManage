package com.example.admanage.system_main.ui.usermanage;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.admanage.system_main.data.ADManageDataSource;
import com.example.admanage.system_main.data.ADManageRepository;
import com.example.admanage.system_main.data.UserModifyDataSource;
import com.example.admanage.system_main.data.UserModifyRepository;
import com.example.admanage.system_main.ui.admanage.ADManageViewModel;
import com.example.admanage.webthread.UserModifyThread;

public class UserManageViewModelFactory implements ViewModelProvider.Factory {

    private String ip;
    private String port;

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    //创建LoginViewModel时传了一个LoginRepository单例进去。LoginRepository在实例化时传了一个LoginDataSource实例进去。
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserManageViewModel.class)) {
            return (T) new UserManageViewModel(UserModifyRepository.getInstance(new UserModifyDataSource(ip,port)));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

    public UserManageViewModelFactory(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    public UserManageViewModelFactory() {
    }
}