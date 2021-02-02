package com.example.admanage.system_main.ui.admanage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.admanage.R;
import com.example.admanage.system_main.data.model.Advertise;
import com.example.admanage.system_main.data.model.Type;

import java.util.ArrayList;
import java.util.List;

public class SpinnerTypeAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList viewlist;
    private int layout;

    private LayoutInflater inflater;

    /*public ADListViewAdapter(Context context, List<AD> adList, int layout) {
        super(context,layout,adList);
        //如果继承的类是ArrayAdapter，则super（）这个方法才有参数，如果继承的是BaseAdapter，则super这个方法没有参数
        this.context=context;
        this.data=adList;
        this.layout=layout;
        inflater = LayoutInflater.from(context);
    }*/

    public SpinnerTypeAdapter(Context context, ArrayList arrayList, int layout) {
        super(context,layout,arrayList);
        this.context=context;
        this.viewlist=arrayList;
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

        TextView textView= (TextView)vie.findViewById(R.id.tv_name);

        Type type = (Type) viewlist.get(i);

        textView.setText(type.getType());

        return vie;
    }
}
