package com.example.admanage.webthread;

import android.util.Log;

import com.example.admanage.system_main.data.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserListThread implements Callable<ArrayList<User>> {

    User user;
    private  String ip;
    private  String port;
    ArrayList<User> userArrayList;
    String authority;

    public UserListThread(int authority) {
        this.authority= String.valueOf(authority);
    }

    public UserListThread( int authority,String ip, String port) {
        this.ip = ip;
        this.port = port;
        this.authority= String.valueOf(authority);
    }

    public ArrayList<User> call() throws Exception
    {
        OkHttpClient okHttpClient=new OkHttpClient();
        HashMap hashMap=new HashMap();
        hashMap.put("操作","queryUserAll");
        hashMap.put("数据",authority);
        Gson gson = new Gson();
        String json=gson.toJson(hashMap);
        Log.e("发送的数据",json);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , json);
        Request request= new Request.Builder()
                .url("http://"+ip+":"+port+"/jsp_d/servlet/UserPhoneServlet")
                .post(requestBody)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        String s= response.body().string();
        Log.e("返回的数据",s);
        userArrayList=gson.fromJson(s,new TypeToken<ArrayList<User>>(){}.getType());
        return userArrayList;
    }
}
