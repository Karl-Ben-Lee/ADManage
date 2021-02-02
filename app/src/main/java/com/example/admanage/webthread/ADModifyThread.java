package com.example.admanage.webthread;

import android.util.Log;

import com.example.admanage.system_main.data.model.Advertise;
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

public class ADModifyThread implements Callable<String> {

    Advertise advertise;
    private  String ip;
    private  String port;

    public ADModifyThread(Advertise advertise) {
        this.advertise=advertise;
    }
    public ADModifyThread(Advertise advertise,String ip,String port) {
        this.advertise=advertise;
        this.ip=ip;
        this.port=port;
    }

    public String call() throws Exception {
        OkHttpClient okHttpClient = new OkHttpClient();
        Gson gson = new Gson();
        HashMap hashMap=new HashMap();
        hashMap.put("操作","update");
        hashMap.put("数据",gson.toJson(advertise));
        String json=gson.toJson(hashMap);
        Log.e("发送的数据",json);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , json);
        Request request = new Request.Builder()
                .url("http://"+ip+":"+port+"/jsp_d/servlet/AdvertisePhoneServlet")
                .post(requestBody)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        String s = response.body().string();
        Log.e("返回的数据",s);
        //user = gson.fromJson(s, User.class);
        return s;
    }
}