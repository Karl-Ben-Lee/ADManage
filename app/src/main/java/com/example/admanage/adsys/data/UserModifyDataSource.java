package com.example.admanage.system_main.data;

import android.util.Log;

import com.example.admanage.system_main.data.model.User;
import com.example.admanage.system_login.data.model.LoggedInUser;
import com.example.admanage.webthread.UserAddThread;
import com.example.admanage.webthread.UserDataThread;
import com.example.admanage.webthread.UserDeleteThread;
import com.example.admanage.webthread.UserModifyThread;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserModifyDataSource {
    private String ip;
    private String port;

    public UserModifyDataSource(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    public UserModifyDataSource() {
    }

    public Result<String> modify(User user) {
            // TODO: handle loggedInUser authentication
            String s;
            UserModifyThread userModifyThread=new UserModifyThread(user,ip,port);

            ExecutorService executor = Executors.newCachedThreadPool();
            Future future = executor.submit(userModifyThread);
            try {
                s= (String) future.get();
            }catch (Exception e) {
                Log.i("异常","异常");
                return new Result.Error(new IOException("用户修改失败", e));
            }
            if("修改成功".equals(s)){
                return new Result.Success<>(s);
            }
            else{
                return new Result.Error(new IOException(s));
            }
    }

    public Result<String> add(User user) {
        // TODO: handle loggedInUser authentication
        String s;
        UserAddThread userAddThread=new UserAddThread(user,ip,port);

        ExecutorService executor = Executors.newCachedThreadPool();
        Future future = executor.submit(userAddThread);
        try {
            s= (String) future.get();
        }catch (Exception e) {
            Log.i("异常","异常");
            return new Result.Error(new IOException("用户添加失败", e));
        }
        if("添加成功".equals(s)){
            return new Result.Success<>(s);
        }
        else{
            return new Result.Error(new IOException(s));
        }
    }

    public Result<String> delete(String id) {
        // TODO: handle loggedInUser authentication
        String s;
        UserDeleteThread userDeleteThread=new UserDeleteThread(id,ip,port);

        ExecutorService executor = Executors.newCachedThreadPool();
        Future future = executor.submit(userDeleteThread);
        try {
            s= (String) future.get();
        }catch (Exception e) {
            Log.i("异常","异常");
            return new Result.Error(new IOException("用户删除失败", e));
        }
        if("删除成功".equals(s)){
            return new Result.Success<>(s);
        }
        else{
            return new Result.Error(new IOException(s));
        }
    }
}
