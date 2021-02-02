package com.example.admanage.system_main;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.admanage.R;
import com.example.admanage.ToastUtil;
import com.example.admanage.system_main.data.model.Advertise;
import com.example.admanage.system_main.ui.admanage.ADFormState;
import com.example.admanage.system_main.ui.admanage.ADManageViewModel;
import com.example.admanage.system_main.ui.admanage.ADManageViewModelFactory;
import com.example.admanage.system_main.ui.admanage.ADSearchResult;
import com.example.admanage.system_main.ui.admanage.ADListViewAdapter;
import com.example.admanage.system_main.ui.admanage.ADInListView;
import com.example.admanage.system_login.ui.LoginViewModel;
import com.example.admanage.system_login.ui.LoginViewModelFactory;
import com.example.admanage.webthread.ADListThread;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static android.content.Context.MODE_PRIVATE;

public class ADManageFragment extends Fragment {

    private ADManageViewModel adManageViewModel;
    private View root;

    //private TextView textView_adid;

    private EditText editText_adname;
    private EditText editText_sourposit;
    private EditText editText_useposit;
    private EditText editText_streetname;
    private EditText editText_calendar;

    private Spinner spinner_feestate;
    private Spinner spinner_planstate;
    private Spinner spinner_movestate;

    private FloatingActionButton fab;
    private Button but_search;

    private ListView listView_adlist;

    private Intent intent;
    private Bundle bundle;

    private int user_id;
    private int user_authority;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main_ad_manage, container, false);
        adManageViewModel =
                ViewModelProviders.of(this, new ADManageViewModelFactory(getCongfig("ip"),getCongfig("port")))
                        .get(ADManageViewModel.class);

        //调用函数加载UI控件对象，并初始化某些控件的常规数据
        init();
        //从Web服务器获取数据，加载在指定控件上
        initdata();
        //给UI控件对象设置观察者，当用户输入数据发生变化时，进行合法性判断；事务提交之后，利用返回的数据对象进行UI的刷新
        setObverse();
        //给UI控件设置事件监听
        setHandle();

        return root;
    }

    private void init(){
        intent=getActivity().getIntent();
        bundle=intent.getExtras();
        user_id=bundle.getInt("user_id");
        user_authority=bundle.getInt("authority");

        editText_adname=root.findViewById(R.id.editText_adname);
        editText_sourposit=root.findViewById(R.id.editText_sourposit);
        editText_useposit=root.findViewById(R.id.editText_userposit);
        editText_streetname=root.findViewById(R.id.editText_streetname);
        editText_calendar=root.findViewById(R.id.editText_calendar);

        spinner_feestate=root.findViewById(R.id.spinner_feestate);
        spinner_planstate=root.findViewById(R.id.spinner_planstate);
        spinner_movestate=root.findViewById(R.id.spinner_movestate);

        fab = root.findViewById(R.id.fab);
        but_search=root.findViewById(R.id.but_search);

        listView_adlist=root.findViewById(R.id.list_ads);

        //设置点击的时候不弹出输入法
        editText_calendar.setInputType(InputType.TYPE_NULL);
        //设置点击后弹出日期选择框
        editText_calendar.setOnClickListener(new View.OnClickListener() {
            Calendar cd = Calendar.getInstance();

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        int mYear = year;
                        String mm;
                        String dd;
                        int mMonth = monthOfYear + 1; //因为month是从0开始计数的
                        if (mMonth <= 9) {
                            mm = "0" + mMonth;
                        } else {
                            mm = String.valueOf(mMonth);
                        }
                        int mDay = dayOfMonth;
                        if (mDay <= 9) {
                            dd = "0" + mDay;
                        } else {
                            dd = String.valueOf(mDay);
                        }
                        mDay = dayOfMonth;

                        editText_calendar.setText(String.valueOf(mYear) + "-" + mm + "-" + dd);
                        //获取开始的时间 并进行转换
                        String date = editText_calendar.getText().toString().trim();
                    }
                }, cd.get(Calendar.YEAR), cd.get(Calendar.MONTH), cd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        List list_feestate=new ArrayList();
        list_feestate.add("未缴费");
        list_feestate.add("已缴费");
        List list_planstate=new ArrayList();
        list_planstate.add("符合规划");
        list_planstate.add("不符合规划");
        List list_movestate=new ArrayList();
        list_movestate.add("未拆除");
        list_movestate.add("已拆除");


        ArrayAdapter arrayAdapter_feestate=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,list_feestate);
        arrayAdapter_feestate.setDropDownViewResource(R.layout.wid_spinner);
        ArrayAdapter arrayAdapter_planstate=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,list_planstate);
        arrayAdapter_planstate.setDropDownViewResource(R.layout.wid_spinner);
        ArrayAdapter arrayAdapter_movestate=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,list_movestate);
        arrayAdapter_movestate.setDropDownViewResource(R.layout.wid_spinner);

        spinner_feestate.setPrompt("请选择");
        spinner_planstate.setPrompt("请选择");
        spinner_movestate.setPrompt("请选择");
        spinner_feestate.setAdapter(arrayAdapter_feestate);
        spinner_planstate.setAdapter(arrayAdapter_planstate);
        spinner_movestate.setAdapter(arrayAdapter_movestate);

    }

    private void initdata(){

        ADListThread adListThread=new ADListThread(getCongfig("ip"),getCongfig("端口"));

        ExecutorService executor = Executors.newCachedThreadPool();
        Future future = executor.submit(adListThread);
        ArrayList<Advertise> adArrayList=null;

        //ArrayList<Advertise> adArrayList=new ArrayList<Advertise>();
        //Advertise advertise=new Advertise(1,"","","","","","",new Long(11),new Long(22),"","","","","","","","","","","","",1,1,1,"",1,"","");
        //adArrayList.add(advertise);

        try {
            adArrayList= (ArrayList<Advertise>) future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(adArrayList==null){
            ToastUtil.showToast(getContext(),"数据加载失败");
            return;
        }

        ADListViewAdapter ADListViewAdapter =new ADListViewAdapter(getContext(),adArrayList,R.layout.item_listview_ad);
        listView_adlist.setAdapter(ADListViewAdapter);

        listView_adlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent("adactivity");
                Bundle bundle=new Bundle();
                bundle.putString("操作","修改");
                bundle.putInt("user_id",user_id);
                bundle.putInt("authority",user_authority);
                TextView textView_adid=view.findViewById(R.id.textView_id);
                bundle.putString("ad_id",textView_adid.getText().toString());

                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
    }

    private void setObverse(){
        adManageViewModel.getADFromFormState().observe(getViewLifecycleOwner(), new Observer<ADFormState>() {
            @Override
            public void onChanged(@Nullable ADFormState adFormState) {
                //Toast.makeText(getContext(),adFormState.toString()+adFormState.isDataValid(),Toast.LENGTH_SHORT).show();
                if (adFormState == null) {
                    return;
                }
                // 设置按钮状态
                but_search.setEnabled(adFormState.isDataValid());
                // 广告名错误
                if (adFormState.getAdnameError() != null) {
                    editText_adname.setError(getString(adFormState.getAdnameError()));
                }
                // 产权单位错误
                if (adFormState.getSourpositError() != null) {
                    editText_sourposit.setError(getString(adFormState.getSourpositError()));
                }
                // 使用单位错误
                if (adFormState.getUsepositError() != null) {
                    editText_useposit.setError(getString(adFormState.getUsepositError()));
                }
                // 街道名称错误
                if (adFormState.getStreetnameError() != null) {
                    editText_streetname.setError(getString(adFormState.getStreetnameError()));
                }
                // 到期时间错误
                if (adFormState.getDuetimeError() != null) {
                    editText_calendar.setError(getString(adFormState.getDuetimeError()));
                }
            }
        });

        adManageViewModel.getSearchResult().observe(getViewLifecycleOwner(), new Observer<ADSearchResult>() {
            @Override
            public void onChanged(@Nullable ADSearchResult adSearchResult) {
                if (adSearchResult == null) {
                    return;
                }
                // 关闭loading动画
                //loadingProgressBar.setVisibility(View.GONE);

                // 登录失败
                if (adSearchResult.getError() != null) {
                    // 显示登录失败消息
                    showSearchFailed(adSearchResult.getError());
                }
                if (adSearchResult.getSuccess() != null) {
                    // 显示登录成功消息
                    updateUiWithUser(adSearchResult.getSuccess());
                }
            }
        });
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
                adManageViewModel.adSearchChanged(editText_adname.getText().toString(),
                        editText_sourposit.getText().toString(),editText_useposit.getText().toString(),
                        editText_streetname.getText().toString(),editText_calendar.getText().toString());
            }
        };
        editText_adname.addTextChangedListener(afterTextChangedListener);
        editText_sourposit.addTextChangedListener(afterTextChangedListener);
        editText_useposit.addTextChangedListener(afterTextChangedListener);
        editText_streetname.addTextChangedListener(afterTextChangedListener);
        editText_calendar.addTextChangedListener(afterTextChangedListener);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("adactivity");
                Bundle bundle=new Bundle();
                bundle.putString("操作","添加");
                bundle.putInt("user_id",user_id);
                bundle.putInt("authority",user_authority);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });

        but_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean feestate;
                boolean planstate;
                boolean movestate;
                if ("已缴费".equals(spinner_feestate.getSelectedItem())){feestate=true;}
                else{feestate=false;}
                if ("符合规划".equals(spinner_planstate.getSelectedItem())){planstate=true;}
                else{planstate=false;}
                if ("已拆除".equals(spinner_movestate.getSelectedItem())){movestate=true;}
                else{movestate=false;}
                adManageViewModel.search(editText_adname.getText().toString(),
                        editText_sourposit.getText().toString(),editText_useposit.getText().toString(),
                        editText_streetname.getText().toString(),editText_calendar.getText().toString(),feestate,planstate,movestate);
            }
        });

    }

    private void showSearchFailed(Integer errorString) {
        Toast.makeText(getContext().getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void updateUiWithUser(ArrayList<Advertise> arrayList) {
        ADListViewAdapter ADListViewAdapter =new ADListViewAdapter(getContext(),arrayList,R.layout.item_listview_ad);
        listView_adlist.setAdapter(ADListViewAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        initdata();
    }
    String getCongfig(String key){
        SharedPreferences getdata=getActivity().getSharedPreferences("config",MODE_PRIVATE);
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