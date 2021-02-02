package com.example.admanage.system_main.data;

import android.util.Log;

import com.example.admanage.system_main.data.model.Advertise;
import com.example.admanage.system_main.data.model.SearchModel;
import com.example.admanage.system_main.ui.admanage.ADInListView;
import com.example.admanage.system_login.data.model.LoggedInUser;
import com.example.admanage.webthread.ADAddThread;
import com.example.admanage.webthread.ADDeleteThread;
import com.example.admanage.webthread.ADListThread;
import com.example.admanage.webthread.ADModifyThread;
import com.example.admanage.webthread.ADSearchThread;
import com.example.admanage.webthread.UserDataThread;
import com.example.admanage.webthread.UserModifyThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ADManageDataSource {

    private String ip;
    private String port;

    public ADManageDataSource(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    public ADManageDataSource() {
    }

    //后台验证登录信息是否正确的方法
    //public Result<LoggedInUser> login(String username, String password) {
    public Result<ArrayList<Advertise>> search(String adname, String sourposit, String userposit, String streetname, String duetime, boolean feestate, boolean planstate, boolean movestate) {
        int fee_state,plan_state,move_state;
        if(feestate){
            fee_state=1;
        }
        else{
            fee_state=0;
        }
        if(planstate){
            plan_state=1;
        }
        else{
            plan_state=0;
        }
        if(movestate){
            move_state=1;
        }
        else{
            move_state=0;
        }
        SearchModel searchModel=new SearchModel(adname,sourposit,userposit,streetname,duetime,fee_state,plan_state,move_state);
        ADSearchThread adSearchThread = new ADSearchThread(searchModel,ip,port);
        ArrayList<Advertise> advertiseArrayList = null;
        ExecutorService executor = Executors.newCachedThreadPool();
        Future future = executor.submit(adSearchThread);
        try {
            advertiseArrayList = (ArrayList<Advertise>) future.get();
            return new Result.Success<>(advertiseArrayList);
        } catch (Exception e) {
            Log.i("异常","异常");
            return new Result.Error(new IOException("查询失败", e));
        }
    }
    /*public Result<String> modify(String id, String adname, String adtype, String adstander, String adsize, String sourposit,
                                    String sourcontact, String sourphone, String useposit, String usecontact,
                                    String userphone, String approvalposit, String approvaldate, String startdate,
                                    String duedate, String movedate, String fee, boolean fee_state, boolean plan_state,
                                    boolean move_state, String adcontent,
                                 String adloction,float adlong,float adlat) {*/
    public Result<String> modify(Advertise advertise) {

        ADModifyThread adModifyThread =new ADModifyThread(advertise,ip,port);

        String s=null;
        ExecutorService executor = Executors.newCachedThreadPool();
        Future future = executor.submit(adModifyThread);
        try {
            // TODO: handle loggedInUser authentication
            s= (String) future.get();
        } catch (Exception e) {
            Log.i("异常","异常");
            return new Result.Error(new IOException("修改失败", e));
        }
        if("修改成功".equals(s)){
            return new Result.Success<>(s);
        }
        else{
            return new Result.Error(new IOException(s));
        }
    }

    /*public Result<String> add(String id, String adname, String adtype, String adstander, String adsize, String sourposit,
                               String sourcontact, String sourphone, String useposit, String usecontact,
                               String userphone, String approvalposit, String approvaldate, String startdate,
                               String duedate, String movedate, String fee, boolean fee_state, boolean plan_state,
                               boolean move_state, String imageobverse, String imagereverse, String adcontent,
                              String adloction,float adlong,float adlat) {*/
    public Result<String> add(Advertise advertise) {

        ADAddThread adAddThread =new ADAddThread(advertise,ip,port);

        String s=null;
        ExecutorService executor = Executors.newCachedThreadPool();
        Future future = executor.submit(adAddThread);
        try {
            // TODO: handle loggedInUser authentication
            s= (String) future.get();
        } catch (Exception e) {
            Log.i("异常","异常");
            return new Result.Error(new IOException("添加失败", e));
        }
        if("添加成功".equals(s)){
            return new Result.Success<>(s);
        }
        else{
            return new Result.Error(new IOException(s));
        }
    }

    public Result<String> delete(String id) {

        ADDeleteThread adDeleteThread =new ADDeleteThread(id,ip,port);

        String s=null;
        ExecutorService executor = Executors.newCachedThreadPool();
        Future future = executor.submit(adDeleteThread);
        try {
            // TODO: handle loggedInUser authentication
            s= (String) future.get();
        } catch (Exception e) {
            Log.i("异常","异常");
            return new Result.Error(new IOException("删除失败", e));
        }
        if("删除成功".equals(s)){
            return new Result.Success<>(s);
        }
        else{
            return new Result.Error(new IOException(s));
        }
    }

    /*public Result<ArrayList<Advertise>> getADList() {
            // TODO: handle loggedInUser authentication
            ADListThread adListThread = new ADListThread();
            ArrayList<Advertise> advertiseArrayList=null;
            ExecutorService executor = Executors.newCachedThreadPool();
            Future future = executor.submit(adListThread);
            try {
                advertiseArrayList = (ArrayList<Advertise>) future.get();
                return new Result.Success<>(advertiseArrayList);
            } catch (Exception e) {
                Log.i("异常","异常");
                return new Result.Error(new IOException("获取广告列表失败", e));
            }
    }*/
}