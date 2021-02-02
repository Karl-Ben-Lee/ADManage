package com.example.admanage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Config extends AppCompatActivity {
    private EditText editText_ip;
    private EditText editText_port;
    private EditText editText_paintlevel;

    private Button but_save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        init();
        setHandle();
    }
    void init(){
        editText_ip=findViewById(R.id.editText_ip);
        editText_port=findViewById(R.id.editText_port);
        editText_paintlevel=findViewById(R.id.editText_paintlevel);
        but_save=findViewById(R.id.but_save);
        if(getCongfig("ip")!=null){
            editText_ip.setText(getCongfig("ip"));
        }
        if(getCongfig("端口")!=null){
            editText_port.setText(getCongfig("端口"));
        }
        if(getCongfig("地图缩放等级")!=null){
            editText_paintlevel.setText(getCongfig("地图缩放等级"));
        }
    }
    void setHandle(){
        but_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip=editText_ip.getText().toString();
                String port=editText_port.getText().toString();
                String paintlevel=editText_paintlevel.getText().toString();
                if("".equals(ip)&&ip==null&&"".equals(port)&&port==null&&"".equals(paintlevel)&&paintlevel==null){
                    Toast.makeText(Config.this,"请将所有信息填写完整",Toast.LENGTH_SHORT).show();
                }
                else{
                    SharedPreferences getdata=getSharedPreferences("config",MODE_PRIVATE);
                    SharedPreferences.Editor saver=getdata.edit();
                    saver.putString("ip",ip);
                    saver.putString("端口",port);
                    saver.putString("地图缩放等级",paintlevel);
                    saver.commit();
                }
                finish();
            }
        });
    }

    public String getCongfig(String key){
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
