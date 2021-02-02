package com.example.admanage.system_main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.admanage.R;
import com.example.admanage.ToastUtil;
import com.example.admanage.system_main.data.model.Advertise;
import com.example.admanage.system_main.data.model.User;
import com.example.admanage.system_main.ui.usermanage.UserListViewAdapter;
import com.example.admanage.webthread.UserListThread;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserListActivity extends AppCompatActivity {

    private ListView listView_userlist;
    private FloatingActionButton fab;

    private Intent intent;
    private Bundle bundle;

    private int user_id;
    private int user_authority;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_userlist);
        init();
        initdata();
        setHandle();
    }

    private void init() {
        intent=getIntent();
        bundle=intent.getExtras();
        //user_id=bundle.getInt("user_id");
        user_authority=bundle.getInt("authority");

        listView_userlist=findViewById(R.id.userlist);
        fab = findViewById(R.id.fab);
    }

    private void initdata() {

        UserListThread userListThread=new UserListThread(user_authority,getCongfig("ip"),getCongfig("端口"));

        ExecutorService executor = Executors.newCachedThreadPool();
        Future future = executor.submit(userListThread);
        ArrayList<User> userArrayList=null;

        /*ArrayList<User> userArrayList=new ArrayList<User>();
        User user=new User("","","","");
        userArrayList.add(user);*/

        try {
            userArrayList= (ArrayList<User>) future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(userArrayList==null){
            ToastUtil.showToast(UserListActivity.this,"数据加载失败");
            return;
        }

        UserListViewAdapter userListViewAdapter=new UserListViewAdapter(UserListActivity.this,userArrayList,R.layout.item_listview_user);
        listView_userlist.setAdapter(userListViewAdapter);

        listView_userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent("useractivity");
                Bundle bundle=new Bundle();
                bundle.putString("操作","修改");
                //bundle.putInt("user_id",user_id);
                //bundle.putInt("authority",user_authority);
                TextView textView_adid=view.findViewById(R.id.textView_useriid);
                bundle.putInt("user_id",Integer.parseInt(textView_adid.getText().toString()));

                intent.putExtras(bundle);
                UserListActivity.this.startActivity(intent);
            }
        });
    }

    private void setHandle() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("useractivity");
                Bundle bundle=new Bundle();
                bundle.putString("操作","添加");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initdata();
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
