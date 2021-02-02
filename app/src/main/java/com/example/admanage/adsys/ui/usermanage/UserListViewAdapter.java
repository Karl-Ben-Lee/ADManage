package com.example.admanage.system_main.ui.usermanage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.admanage.R;
import com.example.admanage.system_main.data.model.User;

import java.util.List;

public class UserListViewAdapter extends ArrayAdapter<User> {
    private Context context;
    private List<User> data;
    private int layout;

    private LayoutInflater inflater;

    public UserListViewAdapter(Context context, List<User> userList, int layout) {
        super(context,layout,userList);
        //如果继承的类是ArrayAdapter，则super（）这个方法才有参数，如果继承的是BaseAdapter，则super这个方法没有参数
        this.context=context;
        this.data=userList;
        this.layout=layout;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vie;
        if(view==null){
            vie = LayoutInflater.from(getContext()).inflate(layout,null);
        }
        else{
            vie=view;
        }

        TextView textView_userid= (TextView)vie.findViewById(R.id.textView_useriid);
        TextView textView_username= (TextView)vie.findViewById(R.id.textView_username);
        TextView textView_usertruename= (TextView)vie.findViewById(R.id.textView_usertruename);
        TextView textView_userrole= (TextView)vie.findViewById(R.id.textView_userrole);

        User user= data.get(i);
        textView_userid.setText(String.valueOf(user.getU_id()));
        textView_username.setText(user.getU_name());
        textView_usertruename.setText(user.getU_truename());
        textView_userrole.setText(String.valueOf(user.getU_authority()));

        return vie;
    }

}
