package com.example.admanage.system_login.data;

import com.example.admanage.system_main.data.model.User;
import com.example.admanage.system_login.data.model.LoggedInUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 * 类，该类从远程数据源请求身份验证和用户信息，并维护登录状态和用户凭据信息的内存缓存。
 * LoginRepository这个类来充当共享的单例类
 * 如果需要共享登录的方法，甚至退出登录的方法、登录的状态、登录后用户的信息就用这个类
 */
public class    LoginRepository {

    /*Java语言提供了一种稍弱的同步机制，即volatile变量，用来确保将变量的更新操作通知到其他线程。当把变量声明为volatile类型后，编译器与运行时都会注意到这个变量是共享的，因此不会将该变量上的操作与其他内存操作一起重排序。volatile变量不会被缓存在寄存器或者对其他处理器不可见的地方，因此在读取volatile类型的变量时总会返回最新写入的值。
            　　在访问volatile变量时不会执行加锁操作，因此也就不会使执行线程阻塞，因此volatile变量是一种比sychronized关键字更轻量级的同步机制。*/
    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    //如果用户凭据将缓存在本地存储中，建议对其进行加密
    // @see https://developer.android.com/training/articles/keystore
    private User user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    // 用户的登录状态，用户已登录则 返回true，否则返回false
    public boolean isLoggedIn() {
        return user != null;
    }

    // 退出时，清空LoginRepository单例类里所有的数据
    public void logout() {
        user = null;
        dataSource.logout();
    }

    // 登录成功后，我们就将用户的信息保存在LoginRepository单例类里
    private void setLoggedInUser(User user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        //如果在本地存储中缓存用户凭据，则建议将其缓存在本地存储中
        // @see https://developer.android.com/training/articles/keystore
    }

    // 共享的登录方法
    public Result<User> login(String username, String password) {
        // handle login
        Result<User> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            //如果登录成功，则从result获取数据给LoggedInUser
            setLoggedInUser( ( (Result.Success<User>) result).getData());
        }
        return result;
    }

    public void setIp(String ip) {
        dataSource.setIp(ip);
    }

    public void setPort(String port) {
        dataSource.setPort(port);
    }
}