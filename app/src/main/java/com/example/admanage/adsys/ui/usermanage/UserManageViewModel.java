package com.example.admanage.system_main.ui.usermanage;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.admanage.R;
import com.example.admanage.ToastUtil;
import com.example.admanage.system_main.data.Result;
import com.example.admanage.system_main.data.UserModifyRepository;
import com.example.admanage.system_main.data.model.User;

public class UserManageViewModel extends ViewModel {

    private MutableLiveData<UserFormState> userFormState = new MutableLiveData<>();
    private MutableLiveData<UserModifyResult> usermodifyResult = new MutableLiveData<>();
    private MutableLiveData<String> mText;

    private UserModifyRepository userModifyRepository;

    public UserManageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public UserManageViewModel(UserModifyRepository userModifyRepository) {
        this.userModifyRepository = userModifyRepository;
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<UserFormState> getUserFormState() {
        return userFormState;
    }

    public MutableLiveData<UserModifyResult> getUsermodifyResult() {
        return usermodifyResult;
    }

    public void modify(String id,String username, String truename, String newpass,String authority){
        Result<String> result = userModifyRepository.modify(id,username,truename,newpass,authority);

        //获取后台验证结果，开始设置通知UI的相关结果和数据
        if (result instanceof Result.Success) {
            String res = ((Result.Success<String>) result).getData();
            usermodifyResult.setValue(new UserModifyResult(res));
        } else {
            usermodifyResult.setValue(new UserModifyResult(R.string.user_modify_failed));
        }
    }
    public void add(String id,String username, String truename, String newpass,String authority){
        Result<String> result = userModifyRepository.add(id,username,truename,newpass,authority);

        //获取后台验证结果，开始设置通知UI的相关结果和数据
        if (result instanceof Result.Success) {
            String res = ((Result.Success<String>) result).getData();
            usermodifyResult.setValue(new UserModifyResult(res));
        } else {
            usermodifyResult.setValue(new UserModifyResult(R.string.user_add_failed));
        }
    }
    public void delete(String id){
        Result<String> result = userModifyRepository.delete(id);

        //获取后台验证结果，开始设置通知UI的相关结果和数据
        if (result instanceof Result.Success) {
            String res = ((Result.Success<String>) result).getData();
            Log.e("删除结果",res);
            usermodifyResult.setValue(new UserModifyResult(res));
        } else {
            Log.e("删除结果","删除失败");
            usermodifyResult.setValue(new UserModifyResult(R.string.user_delete_failed));
        }
    }


    //这里需要按照输入顺序反序检查，否则第一项不符合则下面不会显示不符合
    public void userMesChanged(String username, String truename,String pass, String newpass,String confirmpass){
        if(!isNameValid(username)) {
            userFormState.setValue(new UserFormState(R.string.invalid_personname,null,null,null,null));
        }else if(!isNameValid(truename)) {
            userFormState.setValue(new UserFormState(null,R.string.invalid_personname,null,null,null));
        }else if(!isPasswordValid(pass)){
            userFormState.setValue(new UserFormState(null,null,R.string.invalid_password,null,null));
        }else if(!isPasswordValid(newpass)){
            userFormState.setValue(new UserFormState(null,null,null,R.string.invalid_password,null));
        }else if (!isConfirmPassValid(newpass,confirmpass)) {
            if(!isPasswordValid(confirmpass)){
                userFormState.setValue(new UserFormState(null,null,null,null,R.string.invalid_password));
            }
            else if(!(confirmpass.equals(newpass))){
                userFormState.setValue(new UserFormState(null, null, null, null, R.string.password_inconsist));
            }
        }else {
            userFormState.setValue(new UserFormState(true));
        }
    }

    private boolean isConfirmPassValid(String newpass,String confirmpass) {

        if(isPasswordValid(confirmpass)&&confirmpass.equals(newpass)){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isNameValid(String name) {
        if ("".equals(name)) {
            return false;
        }
        else if (name.contains("\'")||name.contains("<")||name.contains(">")) {
            return false;
        } else {
            //return !date.trim().isEmpty();
            return true;
        }
    }

    private boolean isPasswordValid(String password) {
        if(password.contains("\'")||password.contains("<")||password.contains(">")) {
            return false;
        }
        else{
            return password != null && password.trim().length() > 3;
        }
    }

}