package com.example.admanage.system_main.data;

import com.example.admanage.system_main.data.model.Advertise;
import com.example.admanage.system_main.ui.admanage.ADInListView;

import java.util.ArrayList;

public class ADManageRepository {


    /*Java语言提供了一种稍弱的同步机制，即volatile变量，用来确保将变量的更新操作通知到其他线程。当把变量声明为volatile类型后，编译器与运行时都会注意到这个变量是共享的，因此不会将该变量上的操作与其他内存操作一起重排序。volatile变量不会被缓存在寄存器或者对其他处理器不可见的地方，因此在读取volatile类型的变量时总会返回最新写入的值。
            　　在访问volatile变量时不会执行加锁操作，因此也就不会使执行线程阻塞，因此volatile变量是一种比sychronized关键字更轻量级的同步机制。*/
    private static volatile ADManageRepository instance;

    private ADManageDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    //如果用户凭据将缓存在本地存储中，建议对其进行加密
    // @see https://developer.android.com/training/articles/keystore
    private Advertise advertise;
    private ArrayList<ADInListView> adInListViewArrayList;
    private ArrayList<Advertise> advertiseArrayList;
    private String res;

    // private constructor : singleton access
    private ADManageRepository(ADManageDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static ADManageRepository getInstance(ADManageDataSource dataSource) {
        if (instance == null) {
            instance = new ADManageRepository(dataSource);
        }
        return instance;
    }

    /*// 登录成功后，我们就将用户的信息保存在LoginRepository单例类里
    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }*/

    public Result<ArrayList<Advertise>> search(String adname, String sourposit, String userposit, String streetname, String duetime, boolean feestate, boolean planstate, boolean movestate) {
        Result<ArrayList<Advertise>> result = dataSource.search(adname,sourposit,userposit,streetname,duetime,feestate,planstate,movestate);
        if (result instanceof Result.Success) {
            //如果登录成功，则从result获取数据给LoggedInUser
            setADList(((Result.Success<ArrayList<Advertise>>) result).getData());
        }
        return result;
    }
    /*public Result<ArrayList<Advertise>> getADList() {
        Result<ArrayList<Advertise>> result = dataSource.getADList();
        if (result instanceof Result.Success) {
            //如果登录成功，则从result获取数据给LoggedInUser
            setADList(((Result.Success<ArrayList<Advertise>>) result).getData());
        }
        return result;
    }*/



   /* public Result<String> modify(String id, String adname, String adtype, String adstander, String adsize, String sourposit,
                                    String sourcontact, String sourphone, String useposit, String usecontact,
                                    String userphone, String approvalposit, String approvaldate, String startdate,
                                    String duedate, String movedate, String fee, boolean fee_state, boolean plan_state,
                                    boolean move_state, String imageobverse, String imagereverse, String adcontent) {*/

        /*Result<String> result = dataSource.modify(id,adname,adtype,adstander,adsize,sourposit,sourcontact,sourphone,
                useposit, usecontact,userphone,approvalposit,approvaldate,startdate,duedate,movedate,fee,
                fee_state,plan_state,move_state,imageobverse,imagereverse,adcontent);*/
    public Result<String> modify(Advertise advertise) {
       Result<String> result = dataSource.modify(advertise);
        if (result instanceof Result.Success) {
            //如果登录成功，则从result获取数据给LoggedInUser
            setRes(((Result.Success<String>) result).getData());
        }
        return result;
    }

    /*public Result<String> add(String id, String adname, String adtype, String adstander, String adsize, String sourposit,
                                 String sourcontact, String sourphone, String useposit, String usecontact,
                                 String userphone, String approvalposit, String approvaldate, String startdate,
                                 String duedate, String movedate, String fee, boolean fee_state, boolean plan_state,
                                 boolean move_state, String imageobverse, String imagereverse, String adcontent) {*/

        /*Result<String> result = dataSource.add(id,adname,adtype,adstander,adsize,sourposit,sourcontact,sourphone,
                useposit, usecontact,userphone,approvalposit,approvaldate,startdate,duedate,movedate,fee,
                fee_state,plan_state,move_state,imageobverse,imagereverse,adcontent);*/
    public Result<String> add(Advertise advertise) {
        Result<String> result = dataSource.add(advertise);
        if (result instanceof Result.Success) {
            //如果登录成功，则从result获取数据给LoggedInUser
            setRes(((Result.Success<String>) result).getData());
        }
        return result;
    }
    public Result<String> delete(String id) {
        Result<String> result = dataSource.delete(id);
        if (result instanceof Result.Success) {
            //如果登录成功，则从result获取数据给LoggedInUser
            setRes(((Result.Success<String>) result).getData());
        }
        return result;
    }

    private void setAD(Advertise data) {
        this.advertise =data;
    }

    private void setADList(ArrayList<Advertise> data) {
        this.advertiseArrayList=data;
    }

    private void setRes(String res) {
        this.res=res;
    }
}