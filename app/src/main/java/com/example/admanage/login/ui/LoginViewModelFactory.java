package com.example.admanage.system_login.ui;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.example.admanage.system_login.data.LoginDataSource;
import com.example.admanage.system_login.data.LoginRepository;

/**
 * 用于实例化LoginViewModel的ViewModel提供程序工厂。
 * 必需的给定LoginViewModel具有非空构造函数
 * 用LoginDataSource实例来实例化LoginRepository
 * 用LoginRepository实例来实例化LoginViewModel
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {
/*
    private String ip;
    private String port;*/

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    //创建LoginViewModel时传了一个LoginRepository单例进去。LoginRepository在实例化时传了一个LoginDataSource实例进去。
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(LoginRepository.getInstance(new LoginDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

  /*  public LoginViewModelFactory(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }*/

    public LoginViewModelFactory() {
    }
}