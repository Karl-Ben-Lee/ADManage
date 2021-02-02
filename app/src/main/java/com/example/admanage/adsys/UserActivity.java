package com.example.admanage.system_main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.admanage.R;
import com.example.admanage.ToastUtil;
import com.example.admanage.system_main.data.model.User;
import com.example.admanage.system_main.ui.usermanage.UserFormState;
import com.example.admanage.system_main.ui.usermanage.UserManageViewModel;
import com.example.admanage.system_main.ui.usermanage.UserManageViewModelFactory;
import com.example.admanage.system_main.ui.usermanage.UserModifyResult;
import com.example.admanage.webthread.UserDataThread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserActivity extends AppCompatActivity {

    UserManageViewModel userManageViewModel;

    private TextView textView_userid;
    private TextView textView_quondampass;
    private TextView textView_newpass;

    private EditText editText_username;
    private EditText editText_truename;
    private EditText editText_quondampass;
    private EditText editText_newpass;
    private EditText editText_confirmpass;
    private EditText editText_authority;

    private Button but_commit;
    private Button but_delete;

    private Intent intent;
    private Bundle bundle;
    private String work;

    private int user_id;
    private int user_authority;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_mymes);
        userManageViewModel= ViewModelProviders.of(UserActivity.this,new UserManageViewModelFactory(getCongfig("ip"),getCongfig("端口"))).get(UserManageViewModel.class);
        init();

        userManageViewModel.getUserFormState().observe(this, new Observer<UserFormState>() {
            @Override
            public void onChanged(UserFormState userFormState) {
                if (userFormState == null) {
                    return;
                }
                // 设置按钮状态
                but_commit.setEnabled(userFormState.isDataValid());

                if (userFormState.getUsernameError() != null) {
                    editText_username.setError(getString(userFormState.getUsernameError()));
                }
                if (userFormState.getTruenameError() != null) {
                    editText_truename.setError(getString(userFormState.getTruenameError()));
                }
                if (userFormState.getQuondampassError() != null) {
                    editText_quondampass.setError(getString(userFormState.getQuondampassError()));
                }
                if (userFormState.getNewpassError() != null) {
                    editText_newpass.setError(getString(userFormState.getNewpassError()));
                }
                if (userFormState.getConfirmpassError() != null) {
                    editText_confirmpass.setError(getString(userFormState.getConfirmpassError()));
                }
            }
        });

        userManageViewModel.getUsermodifyResult().observe(UserActivity.this, new Observer<UserModifyResult>() {
            @Override
            public void onChanged(@Nullable UserModifyResult userModifyResult) {
                if (userModifyResult == null) {
                    return;
                }
                /*// 关闭loading动画
                loadingProgressBar.setVisibility(View.GONE);*/

                // 登录失败
                if (userModifyResult.getError() != null) {
                    // 显示登录失败消息
                    showModifyFailed(userModifyResult.getError());
                    //ToastUtil.showToast(UserActivity.this,"执行失败的");
                }
                if (userModifyResult.getSuccess() != null) {
                    // 显示登录成功消息
                    updateUiWithUser(userModifyResult.getSuccess());
                    //ToastUtil.showToast(UserActivity.this,"执行成功的");
                    finish();
                }
            }
        });

        setHandle();

    }

    private void init(){

        intent=getIntent();
        bundle=intent.getExtras();
        work=bundle.getString("操作");
        user_id=bundle.getInt("user_id");
        user_authority=bundle.getInt("authority");

        textView_userid=findViewById(R.id.textView_id);
        textView_quondampass=findViewById(R.id.textView28);
        textView_newpass=findViewById(R.id.textView29);

        editText_username=findViewById(R.id.editText_ip);
        editText_truename=findViewById(R.id.editText_port);
        editText_quondampass=findViewById(R.id.editText_quondampass);
        editText_newpass=findViewById(R.id.editText_newpass);

        editText_confirmpass=findViewById(R.id.editText_confirmpass);

        editText_authority=findViewById(R.id.editText_authority);
        editText_authority.setEnabled(false);

        but_commit=findViewById(R.id.but_commit);
        but_commit.setEnabled(false);
        but_delete=findViewById(R.id.but_delete);
        if("添加".equals(work)){
            but_commit.setText("添加");
            textView_quondampass.setVisibility(View.GONE);
            editText_quondampass.setVisibility(View.GONE);
            editText_quondampass.setText("ddddddddddd");
            textView_newpass.setText("密码");
            editText_authority.setText("2");
        }
        if("修改".equals(work)){
            but_delete.setVisibility(View.VISIBLE);
            initData();
            but_commit.setText("修改");
            textView_userid.setText(String.valueOf(user_id));
        }
        if("修改我的信息".equals(work)){
            initData();
            but_commit.setText("修改");
            textView_userid.setText(String.valueOf(user_id));
        }

    }

    private void initData(){
        UserDataThread userDataThread=new UserDataThread(String.valueOf(user_id),getCongfig("ip"),getCongfig("端口"));

        ExecutorService executor = Executors.newCachedThreadPool();
        Future future = executor.submit(userDataThread);
        User user = null;
        try {
            user = (User) future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(user ==null){
            ToastUtil.showToast(UserActivity.this,"数据加载失败");
            return;
        }

        textView_userid.setText(String.valueOf(user.getU_id()));
        editText_username.setText(user.getU_name());
        editText_truename.setText(user.getU_truename());
        editText_quondampass.setText(user.getU_password());
        editText_newpass.setText(user.getU_password());
        editText_confirmpass.setText(user.getU_password());
        editText_authority.setText(String.valueOf(user.getU_authority()));
    }

    private void setHandle(){
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                userManageViewModel.userMesChanged(editText_username.getText().toString(),editText_truename.getText().toString(),
                        editText_quondampass.getText().toString(),editText_newpass.getText().toString(),editText_confirmpass.getText().toString());
            }
        };
        editText_username.addTextChangedListener(afterTextChangedListener);
        editText_truename.addTextChangedListener(afterTextChangedListener);
        editText_quondampass.addTextChangedListener(afterTextChangedListener);
        editText_newpass.addTextChangedListener(afterTextChangedListener);
        editText_confirmpass.addTextChangedListener(afterTextChangedListener);

        but_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("添加".equals(but_commit.getText())){
                    /*String id = UUID.randomUUID().toString();
                    Log.i("添加用户的id",id);*/
                    userManageViewModel.add("0",editText_username.getText().toString(),
                            editText_truename.getText().toString(),editText_newpass.getText().toString(), String.valueOf(editText_authority.getText()));
                }
                if("修改".equals(but_commit.getText())){
                    Log.e("id",textView_userid.getText().toString());
                    userManageViewModel.modify(textView_userid.getText().toString(),editText_username.getText().toString(),
                            editText_truename.getText().toString(),editText_newpass.getText().toString(), String.valueOf(editText_authority.getText()));
                }
            }
        });

        but_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userManageViewModel.delete(String.valueOf(user_id));
            }
        });

    }

    private void showModifyFailed(Integer errorString) {
        Toast.makeText(this, errorString, Toast.LENGTH_SHORT).show();
    }

    private void updateUiWithUser(String res) {
        if("修改我的信息".equals(work)){
            ToastUtil.showToast(getApplicationContext(),res+"请重新登录");
            Intent intent=new Intent("relogin");
            startActivity(intent);
        }
        else {
            //Toast.makeText(UserActivity.this,res, Toast.LENGTH_SHORT).show();
            ToastUtil.showToast(getApplicationContext(),res);
        }
    }
    String getCongfig(String key){
        SharedPreferences getdata=getSharedPreferences("config",MODE_PRIVATE);
        String value;
        if(!"没有信息".equals(getdata.getString(key,"没有信息"))){
            value=getdata.getString(key,"没有信息");
        }
        else{
            value=null;
        }
        return value;
    }
}
