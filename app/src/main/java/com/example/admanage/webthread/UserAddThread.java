package com.example.admanage.webthread;

import android.util.Log;

import com.example.admanage.system_main.data.model.User;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.concurrent.Callable;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserAddThread implements Callable<String> {

    User user;

    private  String ip;
    private  String port;

    public UserAddThread(User user) {
        this.user=user;
    }

    public UserAddThread(User user, String ip, String port) {
        this.user = user;
        this.ip = ip;
        this.port = port;
    }

    public String call() throws Exception
    {
        OkHttpClient okHttpClient=new OkHttpClient();
        Gson gson = new Gson();
        HashMap hashMap=new HashMap();
        hashMap.put("操作","add");
        hashMap.put("数据",gson.toJson(user));
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
        return s;
    }
}
