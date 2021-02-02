package com.example.admanage.system_main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.admanage.R;
import com.example.admanage.system_main.ui.usermanage.UserManageViewModel;

import static android.content.Context.MODE_PRIVATE;

public class UserManageFragment extends Fragment {

    private UserManageViewModel userManageViewModel;

    private Button but_altermymes;
    private Button but_usermanageaad;
    private Button but_logout;
    private View root;

    private Intent intent;
    private Bundle bundle;

    private int user_id;
    private int user_authority;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        userManageViewModel =
                ViewModelProviders.of(this).get(UserManageViewModel.class);
        root = inflater.inflate(R.layout.fragment_user_manage, container, false);
        /*final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        init();
        setHandle();
        return root;
    }

    private void init(){
        intent=getActivity().getIntent();
        bundle=intent.getExtras();
        user_id=bundle.getInt("user_id");
        Log.e("用户id", String.valueOf(user_id));
        user_authority=bundle.getInt("authority");

        but_altermymes=root.findViewById(R.id.but_altermymes);
        but_usermanageaad=root.findViewById(R.id.but_usermanage);
        but_logout=root.findViewById(R.id.but_logout);

        if(user_authority==1){
            but_usermanageaad.setVisibility(View.VISIBLE);
        }
        else {
            but_usermanageaad.setVisibility(View.GONE);
        }
    }

    private void setHandle(){
        but_altermymes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("useractivity");
                Bundle bundle=new Bundle();
                bundle.putInt("user_id",user_id);
                bundle.putInt("authority",user_authority);
                bundle.putString("操作","修改我的信息");
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
        but_usermanageaad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("userlist");
                Bundle bundle=new Bundle();
                Log.e("用户id", String.valueOf(user_id));
                bundle.putInt("user_id",user_id);
                bundle.putInt("authority",user_authority);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
        but_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("relogin");
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}