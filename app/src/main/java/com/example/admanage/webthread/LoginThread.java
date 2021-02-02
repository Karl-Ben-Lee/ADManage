package com.example.admanage.webthread;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.admanage.Config;
import com.example.admanage.system_main.data.model.User;
import com.google.gson.Gson;

import java.util.concurrent.Callable;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginThread implements Callable<User> {
    private final String username;
    private final String password;
    private  String ip;
    private  String port;

    User user=new User();

    public LoginThread(String username, String password,String ip,String port) {
        this.username = username;
        this.password = password;
        this.ip=ip;
        this.port=port;
    }

    public LoginThread(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User call() throws Exception
    {
        OkHttpClient okHttpClient=new OkHttpClient();
        user.setU_name(username);
        user.setU_password(password);
        Gson gson = new Gson();
        String json=gson.toJson(user);
        //Log.e("发送的数据",ip);
        //Log.e("发送的数据",port);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , json);
        Request request= new Request.Builder()
                .url("http://"+ip+":"+port+"/jsp_d/servlet/LoginPhoneServlet")
                .post(requestBody)
                .build();
        /*Request request= new Request.Builder()
                .url("http://192.168.1.4:8011/hwgg/servlet/UserServlet?operate=add&isphone=true")
                .post(requestBody)
                .build();*/

        Response response = okHttpClient.newCall(request).execute();

        String s= response.body().string();
        //Log.e("返回的数据",s);
        user=gson.fromJson(s,User.class);
        return user;
    }

    /*String getCongfig(String key){
        String a=new Config().getCongfig("端口");
        SharedPreferences getdata=getSharedPreferences("config",MODE_PRIVATE);
        String value;
        if(!"没有信息".equals(getdata.getString(key,"没有信息"))){
            value=getdata.getString(key,"没有信息");
        }
        else{
            value=null;
        }
        return value;
    }*/
}
