package com.example.admanage.system_login.data;

import android.util.Log;

import com.example.admanage.system_main.data.model.User;
import com.example.admanage.system_login.data.model.LoggedInUser;
import com.example.admanage.webthread.LoginThread;
import com.example.admanage.webthread.UserDataThread;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 * 类处理带有登录凭据的身份验证并检索用户信息。
 * 负责处理用户登录和验证，并获取用户数据的数据源：（数据源来自于数据库等数据源）
 */
public class LoginDataSource {

    private String ip;
    private String port;

    /*public LoginDataSource(String ip,String port) {
        this.ip=ip;
        this.port=port;
    }*/

    public LoginDataSource() {
    }

    //后台验证登录信息是否正确的方法
    public Result<User> login(String username, String password) {
        try {
            // TODO: handle loggedInUser authentication

            LoginThread loginThread=new LoginThread(username, password,ip,port);

            ExecutorService executor = Executors.newCachedThreadPool();
            Future future = executor.submit(loginThread);
            User user = null;
            try {
                user=(User) future.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (user.getU_name()==null||"".equals(user.getU_name())){
                return new Result.Error("密码错误");
            }
            else{
                return new Result.Success<>(user);
            }
        } catch (Exception e) {
            Log.i("异常","异常");
            return new Result.Error("请求异常");
        }
    }

    public void logout() {
        // TODO: revoke authentication
        //可以给数据库的用户表添加一状态属性，从而避免同一账户多个地方同时登录在线
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(String port) {
        this.port = port;
    }
}