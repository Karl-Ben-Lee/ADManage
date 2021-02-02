package com.example.admanage.system_login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.admanage.R;
import com.example.admanage.ToastUtil;
import com.example.admanage.system_main.data.model.User;
import com.example.admanage.system_login.ui.LoginViewModel;
import com.example.admanage.system_login.ui.LoginViewModelFactory;
import com.example.admanage.system_login.ui.LoggedInUserView;
import com.example.admanage.system_login.ui.LoginFormState;
import com.example.admanage.system_login.ui.LoginResult;

import java.nio.BufferUnderflowException;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    private TextView textView_config;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ProgressBar loadingProgressBar;

    SharedPreferences getdata;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //使用该类的工厂类创建一个该类对象，LoginViewModel的获取方式
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        loginViewModel.configRepository(getCongfig("ip"),getCongfig("端口"));
        init();
        setObverse();
        setHanle();
    }

    void init(){
        usernameEditText=findViewById(R.id.userId);
        passwordEditText = findViewById(R.id.userPass);
        loginButton = findViewById(R.id.but_login);
        loadingProgressBar = findViewById(R.id.loading);

        textView_config=findViewById(R.id.textView_config);
        getdata=getSharedPreferences("user",MODE_PRIVATE);
        if(!"没有信息".equals(getdata.getString("用户名","没有信息"))){
            String value=getdata.getString("用户名","没有信息");
            usernameEditText.setText(value);
        }
    }

    void setObverse(){
        // 观察表单数据的变化
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                if(getCongfig("ip")!=null&&getCongfig("端口")!=null&&getCongfig("地图缩放等级")!=null){
                    // 设置按钮状态
                    loginButton.setEnabled(loginFormState.isDataValid());
                }
                else{
                    Toast.makeText(getApplicationContext(),"请完成配置" , Toast.LENGTH_SHORT).show();
                }
                // 用户名错误
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                // 密码错误
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        // 观察登录结果的变化
        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                // 关闭loading动画
                loadingProgressBar.setVisibility(View.GONE);
                // 登录失败
                if (loginResult.getError() != null) {
                    // 显示登录失败消息
                    showLoginFailed(loginResult.getError());
                    //passwordEditText.setText("");
                }
                if (loginResult.getSuccess() != null) {
                    User user=loginResult.getSuccess();
                    // 显示登录成功消息
                    updateUiWithUser(user);

                    SharedPreferences.Editor saver=getdata.edit();
                    saver.putString("用户名",usernameEditText.getText().toString());
                    saver.commit();

                    Intent  sysaccess_intent=new Intent("managesys");
                    Bundle bundle =new Bundle();
                    bundle.putInt("user_id",user.getU_id());
                    bundle.putInt("authority",user.getU_authority());
                    sysaccess_intent.putExtras(bundle);
                    LoginActivity.this.startActivity(sysaccess_intent);

                    //Complete and destroy login activity once successful
                    finish();
                }
                // 对使用startActivityForResult有效（直接就不要用这个了，直接用startActivityForResult）
                //setResult(Activity.RESULT_OK);

            }
        });

        // EditText输入框的变化，用于输入信息之后，调用相关的方法检查输入合法性，并进行相应的UI响应
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
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        // 软键盘Done事件，对应设置android:imeOptions="actionDone"的控件，一般来讲只有最后一个输入的控件设置此事件监听
        //这里有BUG，UI控件没有检查合法性，就进行了后台登陆验证  已经修复
        passwordEditText.setOnEditorActionListener( new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                    if(loginViewModel.isAllDataValid(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString())){
                        loginViewModel.login(usernameEditText.getText().toString(),
                                passwordEditText.getText().toString());
                    }

                }
                return false;
            }
        });
    }

    void setHanle(){

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
        textView_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("config");
                startActivity(intent);
            }
        });

    }


    //利用出具元处理返回的结果刷新UI
    private void updateUiWithUser(User model) {
        String welcome = getString(R.string.action_sign_in) + model.getU_name();
        // TODO : initiate successful logged in experience
        ToastUtil.showToast(getApplicationContext(), welcome);
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onResume() {
        super.onResume();
        if(getCongfig("ip")!=null&&getCongfig("端口")!=null&&getCongfig("地图缩放等级")!=null){
            // 设置按钮状态
            //loginButton.setEnabled(true);
        }
        else{
            Toast.makeText(getApplicationContext(),"请完成配置" , Toast.LENGTH_SHORT).show();
        }
        loginViewModel.configRepository(getCongfig("ip"),getCongfig("端口"));
    }
}