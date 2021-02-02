package com.example.admanage.system_login.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;
import android.util.Patterns;

import com.example.admanage.system_main.data.model.User;
import com.example.admanage.system_login.data.LoginRepository;
import com.example.admanage.system_login.data.Result;
import com.example.admanage.system_login.data.model.LoggedInUser;
import com.example.admanage.R;

/**
 * 通过LoginFormState判断数据的合法性
 * 通过LoginResult返回验证是否通过
 * 通过LoggedInUsesr返回提供给接下来UI的数据
 */


public class LoginViewModel extends ViewModel {

    // 持有一个可观察的数据LoginFormState的LiveData类
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    // 持有一个可观察的数据LoginResult的LiveData类
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    // 缓存类，所有需要共享的方法数据都在里面
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public void configRepository(String ip,String port){
        loginRepository.setIp(ip);
        loginRepository.setPort(port);
    }

    // 返回一个可观察的数据持有者类LiveData，它持有LoginFormState数据
    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    // 返回一个可观察的数据持有者类LiveData，它持有LoginResult数据
    public LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    // 处理用户登录的（与数据库的等数据源验证），并设置前台的数据与结果对象
    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<User> result = loginRepository.login(username, password);

        //获取后台验证结果，开始设置通知UI的相关结果和数据
        if (result instanceof Result.Success) {
            User data = ((Result.Success<User>) result).getData();
            loginResult.setValue(new LoginResult(data));
        } else if("密码错误".equals(((Result.Error) result).getError_string())){
            Log.e("错误", String.valueOf(((Result.Error) result).getError_string()));
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
        else{
            loginResult.setValue(new LoginResult(R.string.conn_failed));
        }
    }

    // 处理表单数据变化的，数据输入之后的UI变化
    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        }else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    //检查输入的用户名是否合法
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("\'")||username.contains("<")||username.contains(">")) {
            return false;
        } else {
            return !username.trim().isEmpty();
        }
    }

    //检查输入的密码是否合法
    private boolean isPasswordValid(String password) {
        if(password.contains("\'")||password.contains("<")||password.contains(">")) {
            return false;
        }
        else{
            return password != null && password.trim().length() > 3;
        }
    }

    public boolean isAllDataValid(String usename,String password){
        boolean result=false;
        result=(isPasswordValid(password)&&isUserNameValid(usename));
        return result;
    }
}