package com.example.admanage.system_main.ui.admanage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.admanage.R;
import com.example.admanage.system_main.data.model.Advertise;

import java.util.ArrayList;
import java.util.List;

public class ADListViewAdapter extends ArrayAdapter<Advertise> {
    private Context context;
    private List<Advertise> data;
    private ArrayList<Advertise> viewlist;
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

    public ADListViewAdapter(Context context, ArrayList<Advertise> arrayList, int layout) {
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

        TextView textView_adid=(TextView)vie.findViewById(R.id.textView_id);

        TextView textView_adname= (TextView)vie.findViewById(R.id.textView_adname);

        TextView textView_duetime= (TextView)vie.findViewById(R.id.textView_duetime);

        //TextView textView_useposit= (TextView)vie.findViewById(R.id.textView_useposit);

        TextView textView_feestate= (TextView)vie.findViewById(R.id.textView_feestate);

        TextView textView_planstate= (TextView)vie.findViewById(R.id.textView_planstate);

        Advertise advertise =viewlist.get(i);
        textView_adid.setText(String.valueOf(advertise.getId()));
        textView_adname.setText(advertise.getName());
        //textView_duetime.setText(ad.getDueTime().toString());
        textView_duetime.setText(advertise.getPeriod_end());
        //textView_useposit.setText(ad.getUsepostion());
        int fee_state=advertise.getFee_status();
        if(fee_state==1){
            textView_feestate.setText("√");
        }
        else{
            textView_feestate.setText("×");
        }

        //textView_planstate.setText(" ");

        return vie;
    }


}
