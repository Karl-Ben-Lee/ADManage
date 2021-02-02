package com.example.admanage.system_main;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.admanage.R;
import com.example.admanage.ToastUtil;
import com.example.admanage.system_main.data.model.Advertise;
import com.example.admanage.system_main.data.model.Shp;
import com.example.admanage.system_main.data.model.Type;
import com.example.admanage.system_main.ui.admanage.ADFormState;
import com.example.admanage.system_main.ui.admanage.ADManageViewModel;
import com.example.admanage.system_main.ui.admanage.ADManageViewModelFactory;
import com.example.admanage.system_main.ui.admanage.ADModifyResult;
import com.example.admanage.system_main.ui.admanage.SpinnerShpAdapter;
import com.example.admanage.system_main.ui.admanage.SpinnerTypeAdapter;
import com.example.admanage.webthread.ADDataThread;
import com.example.admanage.webthread.SpinnerShpThread;
import com.example.admanage.webthread.SpinnerTypeThread;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ADActivity extends AppCompatActivity {

    ADManageViewModel adManageViewModel;

    private TextView textView_adid;

    private EditText editText_adname;
    private EditText editText_adsize;
    private EditText editText_sourposit;
    private EditText editText_sourcontact;
    private EditText editText_sourphone;
    private EditText editText_useposit;
    private EditText editText_usecontact;
    private EditText editText_userphone;
    private EditText editText_approvaldate;
    private EditText editText_startdate;
    private EditText editText_duedate;
    private EditText editText_movedate;
    private EditText editText_fee;
    private EditText editText_adcontent;
    private EditText editText_adlocation;
    private EditText editText_adlong;
    private EditText editText_adlat;

    private Spinner spinner_adtype;
    private EditText editText_adstander;
    private Spinner spinner_approvalposit;

    RadioButton radioBut_fee_false;
    RadioButton radioBut_fee_true;
    RadioButton radioBut_plan_false;
    RadioButton radioBut_plan_true;
    RadioButton radioBut_move_false;
    RadioButton radioBut_move_true;
    RadioGroup radioGroup_feestate;
    RadioGroup radioGroup_planstate;
    RadioGroup radioGroup_movestate;

    private ImageView imageView_ad_obverse;
    private ImageView imageView_ad_reverse;

    private Button but_commit;
    private Button but_delete;

    private Intent intent;
    private Bundle bundle;
    private String work;

    private int user_id;
    private String ad_id;
    private int user_authority;

    private Integer type_id;
    private Integer shp_id;

    ArrayList<Type> list_adtype=null;
    ArrayList<Shp> list_approvalpsit=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_alterad);
        setContentView(R.layout.activity_addad);
        adManageViewModel= ViewModelProviders.of(ADActivity.this,new ADManageViewModelFactory(getCongfig("ip"),getCongfig("端口"))).get(ADManageViewModel.class);
        init();
        setObserver();
        setHandle();
    }

    private void init(){
        SpinnerShpThread spinnerShpThread =new SpinnerShpThread(getCongfig("ip"),getCongfig("端口"));
        ExecutorService executor = Executors.newCachedThreadPool();
        Future future = executor.submit(spinnerShpThread);
        try {
            list_approvalpsit= (ArrayList<Shp>) future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SpinnerTypeThread spinnerTypeThread=new SpinnerTypeThread(getCongfig("ip"),getCongfig("端口"));
        Future future1 = executor.submit(spinnerTypeThread);
        try {
            list_adtype= (ArrayList<Type>) future1.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(list_adtype==null||list_approvalpsit==null){
            ToastUtil.showToast(ADActivity.this,"数据加载失败");
            return;
        }

        intent=getIntent();
        bundle=intent.getExtras();
        work=bundle.getString("操作");
        user_id=bundle.getInt("user_id");
        user_authority=bundle.getInt("authority");
        ad_id=bundle.getString("ad_id");
        //Log.e("是什么",ad_id);

        textView_adid=findViewById(R.id.textview_adid);

        editText_adname=findViewById(R.id.editText_adname);
        editText_adstander=findViewById(R.id.editText_adstander);
        editText_adsize=findViewById(R.id.editText_adsize);
        editText_sourposit=findViewById(R.id.editText_sourposit);
        editText_sourcontact=findViewById(R.id.editText_sourcontact);
        editText_sourphone=findViewById(R.id.editText_sourphone);
        editText_useposit=findViewById(R.id.editText_useposit);
        editText_usecontact=findViewById(R.id.editText_usecontact);
        editText_userphone=findViewById(R.id.editText_userphone);
        editText_approvaldate=findViewById(R.id.editText_approvaldate);
        editText_startdate=findViewById(R.id.editText_startdate);
        editText_duedate=findViewById(R.id.editText_duedate);
        editText_movedate=findViewById(R.id.editText_movedate);
        editText_fee=findViewById(R.id.editText_fee);
        editText_adcontent=findViewById(R.id.editText_adcontent);
        editText_adlocation=findViewById(R.id.editText_adlocation);
        editText_adlong=findViewById(R.id.editText_adlong);
        editText_adlat=findViewById(R.id.editText_adlat);

        radioBut_fee_true=findViewById(R.id.radioButton_fee_ture);
        radioBut_fee_false=findViewById(R.id.radioButton_fee_false);
        radioBut_plan_true=findViewById(R.id.radioButton_plan_true);
        radioBut_plan_false=findViewById(R.id.radioButton_plan_false);
        radioBut_move_true=findViewById(R.id.radioButton_move_true);
        radioBut_move_false=findViewById(R.id.radioButton_move_false);
        radioGroup_feestate=findViewById(R.id.radioGroup_feestate);
        radioGroup_planstate=findViewById(R.id.radioGroup_planstate);
        radioGroup_movestate=findViewById(R.id.radioGroup_movestate);

        radioBut_fee_false.setChecked(true);
        radioBut_plan_false.setChecked(true);
        radioBut_move_false.setChecked(true);


        spinner_adtype=findViewById(R.id.spinner_adtype);
        spinner_approvalposit=findViewById(R.id.spinner_approvalposit);

        but_commit=findViewById(R.id.but_commit);
        but_commit.setEnabled(false);
        but_delete=findViewById(R.id.but_delete);
        if("添加".equals(work)){
            but_commit.setText("添加");

        }
        if("地图添加".equals(work)){
            but_commit.setText("添加");
            String streetname=bundle.getString("街道名");
            double lon=bundle.getDouble("经度");
            double lat=bundle.getDouble("纬度");
            //ToastUtil.showToast(ADActivity.this,"经度"+lon+"\n纬度"+lat);
            editText_adlocation.setText(streetname);
            editText_adlong.setText(String.valueOf(lon));
            editText_adlat.setText(String.valueOf(lat));
        }
        if("修改".equals(work)){
            initdata();
            but_commit.setText("修改");
            but_delete.setVisibility(View.VISIBLE);
            textView_adid.setText(ad_id);
        }
        /*if("查看".equals(work)){
            //initdata();
            but_commit.setVisibility(View.GONE);
        }*/

        /*list_adtype= (ArrayList) map.get("广告牌类型");
        list_approvalpsit= (ArrayList) map.get("审批单位");
*/
        ArrayList list_adtype_spinner=new ArrayList();
        //ArrayList list_adstander=new ArrayList();
        ArrayList list_approvalpsit_spinner=new ArrayList();
        for(Type type:list_adtype){
            list_adtype_spinner.add(type.getType());
        }
        for(Shp shp:list_approvalpsit){
            list_approvalpsit_spinner.add(shp.getShp());
        }

        ArrayAdapter arrayAdapter_adtype=new ArrayAdapter(ADActivity.this,android.R.layout.simple_spinner_dropdown_item,  list_adtype_spinner);
        //SpinnerTypeAdapter arrayAdapter_adtype=new SpinnerTypeAdapter(ADActivity.this,  list_adtype,R.layout.wid_spinner);
        arrayAdapter_adtype.setDropDownViewResource(R.layout.wid_spinner);
        ArrayAdapter arrayAdapter_approvalpsit=new ArrayAdapter(ADActivity.this,android.R.layout.simple_spinner_dropdown_item,  list_approvalpsit_spinner);
        //SpinnerShpAdapter arrayAdapter_approvalpsit=new SpinnerShpAdapter(ADActivity.this,  list_approvalpsit,R.layout.wid_spinner);
        arrayAdapter_approvalpsit.setDropDownViewResource(R.layout.wid_spinner);

        spinner_adtype.setAdapter(arrayAdapter_adtype);
        spinner_approvalposit.setAdapter(arrayAdapter_approvalpsit);

        shp_id=((Shp)list_approvalpsit.get(0)).getId();
        type_id=((Type)list_adtype.get(0)).getId();

    }

    private void initdata(){
        ADDataThread adDataThread=new ADDataThread(ad_id,getCongfig("ip"),getCongfig("端口"));

        ExecutorService executor = Executors.newCachedThreadPool();
        Future future = executor.submit(adDataThread);
        Advertise advertise = null;
        try {
            advertise = (Advertise) future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(advertise ==null){
            ToastUtil.showToast(ADActivity.this,"数据加载失败");
            return;
        }

        editText_adname.setText(advertise.getName());
        editText_adstander.setText(advertise.getStandard());
        editText_adsize.setText(advertise.getStyle());
        editText_sourposit.setText(advertise.getOwner());
        editText_sourcontact.setText(advertise.getOwener_contacts());
        editText_sourphone.setText(advertise.getOwener_tel());
        editText_useposit.setText(advertise.getUser());
        editText_usecontact.setText(advertise.getUser_contacts());
        editText_userphone.setText(advertise.getUser_tel());
        editText_approvaldate.setText(advertise.getShptime());
        editText_startdate.setText(advertise.getPeriod_start());
        editText_duedate.setText(advertise.getPeriod_end());
        editText_movedate.setText(advertise.getRemove_time());
        editText_fee.setText(String.valueOf(advertise.getMoney()));
        editText_adlocation.setText(advertise.getLocation());
        editText_adlong.setText(String.valueOf(advertise.getA_long()));
        editText_adlat.setText(String.valueOf(advertise.getA_lat()));
        editText_adcontent.setText(advertise.getContent());


        if(advertise.getFee_status()==1){
            radioBut_fee_true.setChecked(true);
            radioBut_fee_false.setChecked(false);
        }
        else {
            radioBut_fee_true.setChecked(false);
            radioBut_fee_false.setChecked(true);
        }
        if(advertise.getPlan_status()==1){
            radioBut_plan_true.setChecked(true);
            radioBut_plan_false.setChecked(false);
        }
        else {
            radioBut_plan_true.setChecked(false);
            radioBut_plan_false.setChecked(true);
        }
        if(advertise.getRemove_status()==1){
            radioBut_move_true.setChecked(true);
            radioBut_move_false.setChecked(false);
        }
        else {
            radioBut_move_true.setChecked(false);
            radioBut_move_false.setChecked(true);
        }
        String adtype_id=advertise.getType_id();
        String adshp_id=advertise.getShp_id();

        int position1=0;
        int position2=0;
        for(Type type:list_adtype){
            if(adtype_id.equals(type.getId())){
                final int position=position1;
                spinner_adtype.post(new Runnable(){
                    @Override
                    public void run(){
                        spinner_adtype.setSelection(position);
                    }
                });
            }
            position1++;
        }
        for(Shp shp:list_approvalpsit){
            if(adshp_id.equals(String.valueOf(shp.getId()))){
                Log.e("String的id",adshp_id);
                Log.e("对象中的id", String.valueOf(shp.getId()));
                Log.e("对象中的id", String.valueOf(position2));
                final int position=position2;
                spinner_approvalposit.post(new Runnable(){
                    @Override
                    public void run(){
                        spinner_approvalposit.setSelection(position);
                    }
                });
                //spinner_approvalposit.setSelection(position2,true);没效果
                break;
            }
            position2++;
        }
        radioGroup_feestate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                but_commit.setEnabled(true);
            }
        });
        radioGroup_planstate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                but_commit.setEnabled(true);
            }
        });
        radioGroup_movestate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                but_commit.setEnabled(true);
            }
        });

    }

    private void setObserver() {
        adManageViewModel.getADFromFormState().observe(ADActivity.this, new Observer<ADFormState>() {
            @Override
            public void onChanged(@Nullable ADFormState adFormState) {
                if (adFormState == null) {
                    return;
                }
                // 设置按钮状态
                but_commit.setEnabled(adFormState.isDataValid());

                if (adFormState.getAdnameError() != null) {
                    editText_adname.setError(getString(adFormState.getAdnameError()));
                }
                if (adFormState.getAdstanderError() != null) {
                    editText_adstander.setError(getString(adFormState.getAdstanderError()));
                }
                if (adFormState.getAdsizeError() != null) {
                    editText_adsize.setError(getString(adFormState.getAdsizeError()));
                }
                if (adFormState.getSourpositError() != null) {
                    editText_sourposit.setError(getString(adFormState.getSourpositError()));
                }
                if (adFormState.getSourcontactError() != null) {
                    editText_sourcontact.setError(getString(adFormState.getSourcontactError()));
                }
                if (adFormState.getSourphoneError() != null) {
                    editText_sourphone.setError(getString(adFormState.getSourphoneError()));
                }
                if (adFormState.getUsepositError() != null) {
                    editText_useposit.setError(getString(adFormState.getUsepositError()));
                }
                if (adFormState.getUsecontactError() != null) {
                    editText_usecontact.setError(getString(adFormState.getUsecontactError()));
                }
                if (adFormState.getUserphoneError() != null) {
                    editText_userphone.setError(getString(adFormState.getUserphoneError()));
                }
                /*if (adFormState.getApprovaldate() != null) {
                    editText_approvaldate.setError(getString(adFormState.getApprovaldate()));
                }
                if (adFormState.getDuetimeError() != null) {
                    editText_duedate.setError(getString(adFormState.getDuetimeError()));
                }
                if (adFormState.getMovedateError() != null) {
                    editText_movedate.setError(getString(adFormState.getMovedateError()));
                }*/
                if (adFormState.getFeeError() != null) {
                    editText_fee.setError(getString(adFormState.getFeeError()));
                }
                if (adFormState.getAdcontentError() != null) {
                    editText_adcontent.setError(getString(adFormState.getAdcontentError()));
                }
                if (adFormState.getAdlocationError() != null) {
                    editText_adlocation.setError(getString(adFormState.getAdlocationError()));
                }
                if (adFormState.getAdlongError() != null) {
                    editText_adlong.setError(getString(adFormState.getAdlongError()));
                }
                if (adFormState.getAdlatError() != null) {
                    editText_adlat.setError(getString(adFormState.getAdlatError()));
                }


            }
        });
        adManageViewModel.getModifyResult().observe(ADActivity.this, new Observer<ADModifyResult>() {
            @Override
            public void onChanged(@Nullable ADModifyResult adModifyResult) {
                if (adModifyResult == null) {
                    return;
                }
                /*// 关闭loading动画
                loadingProgressBar.setVisibility(View.GONE);*/

                // 登录失败
                if (adModifyResult.getError() != null) {
                    // 显示登录失败消息
                    showModifyFailed(adModifyResult.getError());
                }
                if (adModifyResult.getSuccess() != null) {
                    // 显示登录成功消息
                    updateUiWithUser(adModifyResult.getSuccess());
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
                adManageViewModel.adModifyChanged(editText_adname.getText().toString(),editText_adstander.getText().toString(),editText_adsize.getText().toString(),
                        editText_sourposit.getText().toString(),editText_sourcontact.getText().toString(),editText_sourphone.getText().toString(),
                        editText_useposit.getText().toString(),editText_usecontact.getText().toString(),editText_userphone.getText().toString(),
                        editText_approvaldate.getText().toString(),editText_startdate.getText().toString(), editText_duedate.getText().toString(),
                        editText_movedate.getText().toString(), editText_fee.getText().toString(),editText_adcontent.getText().toString(),
                        editText_adlocation.getText().toString(),editText_adlong.getText().toString(),editText_adlat.getText().toString());
            }
        };
        editText_adname.addTextChangedListener(afterTextChangedListener);
        editText_adstander.addTextChangedListener(afterTextChangedListener);
        editText_adsize.addTextChangedListener(afterTextChangedListener);
        editText_sourposit.addTextChangedListener(afterTextChangedListener);
        editText_sourcontact.addTextChangedListener(afterTextChangedListener);
        editText_sourphone.addTextChangedListener(afterTextChangedListener);
        editText_useposit.addTextChangedListener(afterTextChangedListener);
        editText_usecontact.addTextChangedListener(afterTextChangedListener);
        editText_userphone.addTextChangedListener(afterTextChangedListener);
        //editText_approvaldate.addTextChangedListener(afterTextChangedListener);
        //editText_startdate.addTextChangedListener(afterTextChangedListener);
        //editText_duedate.addTextChangedListener(afterTextChangedListener);
        //editText_movedate.addTextChangedListener(afterTextChangedListener);
        editText_fee.addTextChangedListener(afterTextChangedListener);
        editText_adlocation.addTextChangedListener(afterTextChangedListener);
        editText_adlong.addTextChangedListener(afterTextChangedListener);
        editText_adlat.addTextChangedListener(afterTextChangedListener);
        editText_adcontent.addTextChangedListener(afterTextChangedListener);

        but_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int fee_state;
                int plan_state;
                int move_state;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String imageobverse="";
                String imagereverse="";
                if(radioBut_fee_true.isChecked()){
                    fee_state=1;
                }
                else{
                    fee_state=0;
                }
                if(radioBut_plan_true.isChecked()){
                    plan_state=1;
                }
                else{
                    plan_state=0;
                }
                if(radioBut_move_true.isChecked()){
                    move_state=1;
                }
                else{
                    move_state=0;
                }
                Log.e("类型id",String.valueOf(type_id));
                Log.e("审批单位id",String.valueOf(shp_id));
                if("添加".equals(but_commit.getText())){
                    Advertise advertise=new Advertise(0,editText_adname.getText().toString(),editText_adcontent.getText().toString(),
                            editText_adstander.getText().toString(),String.valueOf(type_id),editText_adsize.getText().toString(),
                            editText_adlocation.getText().toString(),Double.valueOf(editText_adlong.getText().toString()),Double.valueOf(editText_adlat.getText().toString()),
                            editText_sourposit.getText().toString(),editText_sourcontact.getText().toString(),editText_sourphone.getText().toString(),
                            editText_useposit.getText().toString(),editText_usecontact.getText().toString(),editText_userphone.getText().toString(),
                            String.valueOf(shp_id), editText_approvaldate.getText().toString(),
                            editText_startdate.getText().toString(),editText_duedate.getText().toString(),
                            Float.valueOf(editText_fee.getText().toString()),fee_state,plan_state,
                            editText_movedate.getText().toString(),move_state,String.valueOf(user_id),formatter.format(new Date()));
                    adManageViewModel.add(advertise);
                }
                if("修改".equals(but_commit.getText())){
                    Advertise advertise=new Advertise(Integer.parseInt(ad_id),editText_adname.getText().toString(),editText_adcontent.getText().toString(),
                            editText_adstander.getText().toString(),String.valueOf(type_id),editText_adsize.getText().toString(),
                            editText_adlocation.getText().toString(),Double.valueOf(editText_adlong.getText().toString()),Double.valueOf(editText_adlat.getText().toString()),
                            editText_sourposit.getText().toString(),editText_sourcontact.getText().toString(),editText_sourphone.getText().toString(),
                            editText_useposit.getText().toString(),editText_usecontact.getText().toString(),editText_userphone.getText().toString(),
                            String.valueOf(shp_id), editText_approvaldate.getText().toString(),
                            editText_startdate.getText().toString(),editText_duedate.getText().toString(),
                            Float.valueOf(editText_fee.getText().toString()),fee_state,plan_state,
                            editText_movedate.getText().toString(),move_state,String.valueOf(user_id),formatter.format(new Date()));
                    adManageViewModel.modify(advertise);
                }
            }
        });

        but_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adManageViewModel.delete(ad_id);
            }
        });

        editText_approvaldate.setInputType(InputType.TYPE_NULL);
        editText_startdate.setInputType(InputType.TYPE_NULL);
        editText_duedate.setInputType(InputType.TYPE_NULL);
        editText_movedate.setInputType(InputType.TYPE_NULL);
        editText_approvaldate.setOnClickListener(new View.OnClickListener() {
            Calendar cd = Calendar.getInstance();

            @Override
            public void onClick(View v) {
                new DatePickerDialog(ADActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                        editText_approvaldate.setText(String.valueOf(mYear) + "-" + mm + "-" + dd);
                        //获取开始的时间 并进行转换
                        String date = editText_approvaldate
                                .getText().toString().trim();
                    }
                }, cd.get(Calendar.YEAR), cd.get(Calendar.MONTH), cd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editText_startdate.setOnClickListener(new View.OnClickListener() {
            Calendar cd = Calendar.getInstance();

            @Override
            public void onClick(View v) {
                new DatePickerDialog(ADActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                        editText_startdate.setText(String.valueOf(mYear) + "-" + mm + "-" + dd);
                        //获取开始的时间 并进行转换
                        String date = editText_startdate.getText().toString().trim();
                    }
                }, cd.get(Calendar.YEAR), cd.get(Calendar.MONTH), cd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editText_duedate.setOnClickListener(new View.OnClickListener() {
            Calendar cd = Calendar.getInstance();

            @Override
            public void onClick(View v) {
                new DatePickerDialog(ADActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                        editText_duedate.setText(String.valueOf(mYear) + "-" + mm + "-" + dd);
                        //获取开始的时间 并进行转换
                        String date = editText_duedate.getText().toString().trim();
                    }
                }, cd.get(Calendar.YEAR), cd.get(Calendar.MONTH), cd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editText_movedate.setOnClickListener(new View.OnClickListener() {
            Calendar cd = Calendar.getInstance();

            @Override
            public void onClick(View v) {
                new DatePickerDialog(ADActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                        editText_movedate.setText(String.valueOf(mYear) + "-" + mm + "-" + dd);
                        //获取开始的时间 并进行转换
                        String date = editText_movedate.getText().toString().trim();
                    }
                }, cd.get(Calendar.YEAR), cd.get(Calendar.MONTH), cd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        spinner_adtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Type type= (Type) list_adtype.get(position);
                type_id=type.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_approvalposit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Shp shp= (Shp) list_approvalpsit.get(position);
                shp_id=shp.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void updateUiWithUser(String s) {
        ToastUtil.showToast(ADActivity.this, s);
        finish();
    }

    private void showModifyFailed(Integer errorString) {
        Toast.makeText(ADActivity.this, errorString, Toast.LENGTH_SHORT).show();
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
