package com.example.admanage.system_main.data;

import com.example.admanage.system_main.data.model.User;

public class UserModifyRepository {
    private static volatile UserModifyRepository instance;

    private UserModifyDataSource userModifyDataSource;

    private User user;
    private String res;

    private UserModifyRepository(UserModifyDataSource dataSource){
        this.userModifyDataSource=dataSource;
    }

    public static UserModifyRepository getInstance(UserModifyDataSource dataSource) {
        if (instance == null) {
            instance = new UserModifyRepository(dataSource);
        }
        return instance;
    }

    public Result<String> modify(String id,String username, String truename, String newpass,String authority){
        User user=new User(id,username,truename,newpass,authority);
        Result<String> result = userModifyDataSource.modify(user);
        if (result instanceof Result.Success) {
            //如果登录成功，则从result获取数据给LoggedInUser
            setRes(((Result.Success<String>) result).getData());
        }
        return result;
    }
    public Result<String> add(String id,String username, String truename, String newpass,String authority){
        User user=new User(id,username,truename,newpass,authority);
        Result<String> result = userModifyDataSource.add(user);
        if (result instanceof Result.Success) {
            //如果登录成功，则从result获取数据给LoggedInUser
            setRes(((Result.Success<String>) result).getData());
        }
        return result;
    }
    public Result<String> delete(String id){
        Result<String> result = userModifyDataSource.delete(id);
        if (result instanceof Result.Success) {
            //如果登录成功，则从result获取数据给LoggedInUser
            setRes(((Result.Success<String>) result).getData());
        }
        return result;
    }


    private void setUser(User data) {
        this.user=data;
    }
    private void setRes(String res) {
        this.res=res;
    }

}
