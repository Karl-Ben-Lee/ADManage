package com.example.admanage.webthread;

import android.util.Log;

import com.example.admanage.system_main.data.model.Shp;
import com.example.admanage.system_main.data.model.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SpinnerTypeThread implements Callable<ArrayList<Type>> {

    private  String ip;
    private  String port;

    public SpinnerTypeThread() {
    }

    public SpinnerTypeThread(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public ArrayList<Type> call() throws Exception{
        ArrayList<Type> arrayList_shp=null;
        OkHttpClient okHttpClient=new OkHttpClient();
        HashMap hashMap=new HashMap();
        hashMap.put("操作","加载类型");
        Gson gson = new Gson();
        String json=gson.toJson(hashMap);
        //Log.e("发送的数据",json);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , json);
        Request request= new Request.Builder()
                .url("http://"+ip+":"+port+"/jsp_d/servlet/AdvertisePhoneServlet")
                .post(requestBody)
                .build();
        Response response= null;
        response = okHttpClient.newCall(request).execute();
        String s= response.body().string();
        Log.e("返回的数据",s);
        arrayList_shp=gson.fromJson(s,new TypeToken<ArrayList<Type>>(){}.getType());

        return arrayList_shp;
    }
}
