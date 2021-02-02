package com.example.admanage.system_main.ui.admanage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.admanage.R;
import com.example.admanage.system_main.data.ADManageRepository;
import com.example.admanage.system_main.data.Result;
import com.example.admanage.system_main.data.model.Advertise;
import com.example.admanage.checktools.ADMes_check;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ADManageViewModel extends ViewModel {

    private MutableLiveData<ADFormState> adFormState = new MutableLiveData<>();
    private MutableLiveData<ADSearchResult> searchResult = new MutableLiveData<>();
    private MutableLiveData<ADModifyResult> modifyResult = new MutableLiveData<>();
    private ADManageRepository adManageRepository;

    public ADManageViewModel(ADManageRepository adManageRepository) {
        this.adManageRepository=adManageRepository;
    }

    public LiveData<ADFormState> getADFromFormState() {
        return adFormState;
    }

    public LiveData<ADSearchResult> getSearchResult() {
        return searchResult;
    }

    public LiveData<ADModifyResult> getModifyResult() {
        return modifyResult;
    }

    public void search(String adname, String sourposit, String userposit, String streetname, String duetime, boolean feestate, boolean planstate, boolean movestate) {
        Result<ArrayList<Advertise>> result = adManageRepository.search(adname,sourposit,userposit,streetname,duetime,feestate,planstate,movestate);

        //获取后台验证结果，开始设置通知UI的相关结果和数据
        if (result instanceof Result.Success) {
            ArrayList<Advertise> arrayListResult=((Result.Success<ArrayList<Advertise>>) result).getData();
            searchResult.setValue(new ADSearchResult(arrayListResult));
        } else {
            searchResult.setValue(new ADSearchResult(R.string.search_failed));
        }
    }

    //这里需要按照输入顺序反序检查，否则第一项不符合则下面不会显示不符合
    public void adSearchChanged(String adname, String sourposit, String userposit, String streetname, String duetime) {
        if(!isDateValid(duetime,true)) {
            adFormState.setValue(new ADFormState(null, null,null,null,R.string.invalid_date));
        }else if(!isStreetValid(streetname,true)) {
            adFormState.setValue(new ADFormState(null, null,null,R.string.invalid_streetname,null));
        }else if(!isUsePositionValid(userposit,true)){
            adFormState.setValue(new ADFormState(null, null,R.string.invalid_position,null,null));
        }else if (!isSourcePositionValid(sourposit,true)) {
            adFormState.setValue(new ADFormState(null, R.string.invalid_position,null,null,null));
        }else if (!isADNameValid(adname,true)) {
            //R.string.invalid_username需要具体设计，要更改
            adFormState.setValue(new ADFormState(R.string.invalid_username, null,null,null,null));
        }else {
            adFormState.setValue(new ADFormState(true));
        }
    }

    /*public void getADList() {
        Result<ArrayList<Advertise>> result = adManageRepository.getADList();

        //获取后台验证结果，开始设置通知UI的相关结果和数据
        if (result instanceof Result.Success) {
            ArrayList<Advertise> arrayListResult=((Result.Success<ArrayList<Advertise>>) result).getData();
            searchResult.setValue(new ADSearchResult(arrayListResult));
        } else {
            searchResult.setValue(new ADSearchResult(R.string.search_failed));
        }
    }*/

    /*public void modify(String id,String adname,String adtype,String adstander, String adsize, String sourposit,
                       String sourcontact, String sourphone, String useposit,String usecontact,
                       String userphone,String approvalposit, String approvaldate,String startdate,
                       String duedate,String movedate, String fee,boolean fee_state,boolean plan_state,
                       boolean move_state,String imageobverse,String imagereverse, String adcontent) {*/

        /*Result<String> result = adManageRepository.modify(id,adname,adtype,adstander,adsize,sourposit,sourcontact,sourphone,
                useposit, usecontact,userphone,approvalposit,approvaldate,startdate,duedate,movedate,fee,
                fee_state,plan_state,move_state,imageobverse,imagereverse,adcontent);*/
    public void modify(Advertise advertise) {
        Result<String> result = adManageRepository.modify(advertise);

        //获取后台验证结果，开始设置通知UI的相关结果和数据
        if (result instanceof Result.Success) {
            String res = ((Result.Success<String>) result).getData();
            modifyResult.setValue(new ADModifyResult(res));
        } else {
            modifyResult.setValue(new ADModifyResult(R.string.ad_modify_failed));
        }
    }

    /*public void add(String id,String adname,String adtype,String adstander, String adsize, String sourposit,
                       String sourcontact, String sourphone, String useposit,String usecontact,
                       String userphone,String approvalposit, String approvaldate,String startdate,
                       String duedate,String movedate, String fee,boolean fee_state,boolean plan_state,
                       boolean move_state,String imageobverse,String imagereverse, String adcontent) {*/

        /*Result<String> result = adManageRepository.add(id,adname,adtype,adstander,adsize,sourposit,sourcontact,sourphone,
                useposit, usecontact,userphone,approvalposit,approvaldate,startdate,duedate,movedate,fee,
                fee_state,plan_state,move_state,imageobverse,imagereverse,adcontent);*/
    public void add(Advertise advertise) {
        Result<String> result = adManageRepository.add(advertise);

        //获取后台验证结果，开始设置通知UI的相关结果和数据
        if (result instanceof Result.Success) {
            String res = ((Result.Success<String>) result).getData();
            modifyResult.setValue(new ADModifyResult(res));
        } else {
            modifyResult.setValue(new ADModifyResult(R.string.ad_add_failed));
        }
    }

    public void delete(String id) {
        Result<String> result = adManageRepository.delete(id);

        //获取后台验证结果，开始设置通知UI的相关结果和数据
        if (result instanceof Result.Success) {
            String res = ((Result.Success<String>) result).getData();
            modifyResult.setValue(new ADModifyResult(res));
        } else {
            modifyResult.setValue(new ADModifyResult(R.string.ad_delete_failed));
        }
    }

    public void adModifyChanged(String adname,String adstander, String adsize, String sourposit, String sourcontact, String sourphone, String useposit,
                                String usecontact, String userphone, String approvaldate,String startdate,String duedate,String movedate,
                                String fee, String adcontent,String location,String adlong,String lat) {
        if(!isADNameValid(adname,false)){
            adFormState.setValue(new ADFormState(R.string.invalid_adname, null,null,
                    null,null,null,null,null,
                    null,null,null,null,null,null,null,null,null,null));
        }else if(!isStanderValid(adstander)){
            adFormState.setValue(new ADFormState(null, null,null,
                    null,null,null,null,null,
                    null,null,null,null,null,null,null,null,null,R.string.invalid_stander));
        }else if(!isSizeValid(adsize)){
            adFormState.setValue(new ADFormState(null, null,null,
                    null,null,R.string.invalid_size,null,null,
                    null,null,null,null,null,null,null,null,null,null));
        }else if(!isSourcePositionValid(sourposit,false)){
            adFormState.setValue(new ADFormState(null, R.string.invalid_position,null,
                    null,null,null,null,null,
                    null,null,null,null,null,null,null,null,null,null));
        }else if(!isContactValid(sourcontact)){
            adFormState.setValue(new ADFormState(null, null,null,
                    null,null,null,R.string.invalid_personname,null,
                    null,null,null,null,null,null,null,null,null,null));
        }else if(!isPhoneNumValid(sourphone)){
            adFormState.setValue(new ADFormState(null, null,null,
                    null,null,null,null,R.string.invalid_phonenum,
                    null,null,null,null,null,null,null,null,null,null));
        }else if(!isUsePositionValid(useposit,false)){
            adFormState.setValue(new ADFormState(null, null,R.string.invalid_position,
                    null,null,null,null,null,
                    null,null,null,null,null,null,null,null,null,null));
        }else if(!isContactValid(usecontact)){
            adFormState.setValue(new ADFormState(null, null,null,
                    null,null,null,null,null,
                    R.string.invalid_personname,null,null,null,null,null,null,null,null,null));
        }else if(!isPhoneNumValid(userphone)){
            adFormState.setValue(new ADFormState(null, null,null,
                    null,null,null,null,null,
                    null,R.string.invalid_phonenum,null,null,null,null,null,null,null,null));
        }else if (!isDateValid(approvaldate,false)) {
            adFormState.setValue(new ADFormState(null, null,null,
                    null,null,null,null,null,
                    null,null,R.string.invalid_date,null,null,null,null,null,null,null));
        }else if (!isDateValid(startdate,false)) {
            adFormState.setValue(new ADFormState(null, null,null,
                    null,null,null,null,null,
                    null,null,null,R.string.invalid_date,null,null,null,null,null,null));
        }else if(!isDateValid(duedate,false)){
            adFormState.setValue(new ADFormState(null, null,null,
                    null,R.string.invalid_date,null,null,null,
                    null,null,null,null,null,null,null,null,null,null));
        }else if(!isDateValid(movedate,false)) {
            adFormState.setValue(new ADFormState(null, null,null,
                    null,null,null,null,null,
                    null,null,null,null,R.string.invalid_date,null,null,null,null,null));
        }else if(!isStreetValid(location,false)) {
            adFormState.setValue(new ADFormState(null, null,null,
                    null,null,null,null,null,
                    null,null,null,null,null,null,R.string.invalid_streetname,null,null,null));
        }else if(!isJingWeiDuValid(adlong)) {
            adFormState.setValue(new ADFormState(null, null,null,
                    null,null,null,null,null,
                    null,null,null,null,null,null,null,R.string.invalid_jingweidu,null,null));
        }else if(!isadLatValid(lat)) {
            adFormState.setValue(new ADFormState(null, null,null,
                    null,null,null,null,null,
                    null,null,null,null,null,null,null,null,R.string.invalid_jingdu,null));
        }else if(!isContentValid(adcontent)) {
            adFormState.setValue(new ADFormState(null, null,null,
                    R.string.invalid_adcontent,null,null,null,null,
                    null,null,null,null,null,null,null,null,null,null));
        }else{
            adFormState.setValue(new ADFormState(true));
        }
    }

    private boolean isSizeValid(String adsize) {
        if ("".equals(adsize) ) {
            return false;
        }else if(adsize.contains("\'")||adsize.contains("<")||adsize.contains(">")) {
            return false;
        }else if (!adsize.contains("*")) {
            //return Patterns.EMAIL_ADDRESS.matcher(date).matches();
            return false;
        }  else{
            //return !date.trim().isEmpty();
            return true;
        }
    }

    private boolean isStanderValid(String adsize) {
        if ("".equals(adsize) ) {
            return false;
        }
        else if (adsize.contains("\'")||adsize.contains("<")||adsize.contains(">")) {
            return false;
        } else {
            //return !date.trim().isEmpty();
            return true;
        }
    }

    private boolean isContactValid(String contact) {
        if ("".equals(contact)) {
            return false;
        }
        else if (contact.contains("\'")||contact.contains("<")||contact.contains(">")) {
            return false;
        } else {
            //return !date.trim().isEmpty();
            return true;
        }
    }

    private boolean isPhoneNumValid(String phonenum) {
        return new ADMes_check().phonenum_check(phonenum);
    }

    private boolean isContentValid(String adcontent) {
        if ("".equals(adcontent )) {
            return false;
        }
        else if (adcontent.contains("\'")||adcontent.contains("<")||adcontent.contains(">")) {
            //return Patterns.EMAIL_ADDRESS.matcher(date).matches();
            return false;
        } else {
            //return !date.trim().isEmpty();
            return true;
        }
    }

    //这些方法都要改
    private boolean isDateValid(String date,boolean permitNull) {
        boolean format1_result=true;
        boolean format2_result=true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        if(permitNull){
            if("".equals(date)||null==date){
                return true;
            }
            else{
                try {
                    format1.setLenient(false);
                    format1.parse(date);
                } catch (ParseException e) {
                    format1_result=false;
                }
                try {
                    format2.setLenient(false);
                    format2.parse(date);
                } catch (ParseException e) {
                    format2_result=false;
                }
                return format1_result|format2_result;
            }
        }
        else{
            try {
                format1.setLenient(false);
                format1.parse(date);
            } catch (ParseException e) {
                format1_result=false;
            }
            try {
                format2.setLenient(false);
                format2.parse(date);
            } catch (ParseException e) {
                format2_result=false;
            }
            return format1_result|format2_result;
        }
    }

    private boolean isStreetValid(String streetname,boolean permitNull) {
        if ("".equals(streetname)) {
            if(permitNull){
                return true;
            }
            else{
                return false;
            }
        }
        else if (streetname.contains("\'")||streetname.contains("<")||streetname.contains(">")) {
            return false;
        } else {
            //return !streetname.trim().isEmpty();
            return true;
        }
    }

    private boolean isSourcePositionValid(String postion,boolean permitNull) {
        if ("".equals(postion)) {
            if(permitNull){
                return true;
            }
            else{
                return false;
            }
        }
        else if (postion.contains("\'")||postion.contains("<")||postion.contains(">")) {
            return false;
        } else {
            //return !postion.trim().isEmpty();
            return true;
        }
    }

    private boolean isUsePositionValid(String postion,boolean permitNull) {
        if ("".equals(postion)) {
            if(permitNull){
                return true;
            }
            else{
                return false;
            }
        }
        else if (postion.contains("\'")||postion.contains("<")||postion.contains(">")) {
            return false;
        } else {
            //return !postion.trim().isEmpty();
            return true;
        }
    }

    private boolean isADNameValid(String adname,boolean permitNull) {
        if ("".equals(adname)) {
            if(permitNull){
                return true;
            }
            else{
                return false;
            }
        }else{
            if (adname.contains("\'")||adname.contains("<")||adname.contains(">")) {
                return false;
            }
            else {
            //return !adname.trim().isEmpty();
            return true;
            }
        }
    }
    private boolean isadLatValid(String adlat) {
        if(!isJingWeiDuValid(adlat)){
            return false;
        }
        else {
            double ad_long=Double.valueOf(adlat);
            if (ad_long >= -90.0 && ad_long <= 90.0) {
                return true;
            }
            else {
                //return !date.trim().isEmpty();
                return false;
            }
        }
    }
    private boolean isJingWeiDuValid(String jingweidu) {
        if ("".equals(jingweidu)||jingweidu==null) {
            return false;
        }
        else {
            double ad_long=Double.valueOf(jingweidu);
            if (ad_long >= -180.0 && ad_long <= 180.0) {
                return true;
            }
            else {
                //return !date.trim().isEmpty();
                return false;
            }
        }
    }

}