package com.example.admanage.system_main.ui.adstatistics;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.admanage.R;
import com.example.admanage.ToastUtil;
import com.example.admanage.system_main.data.model.Advertise;
import com.example.admanage.system_main.ui.admanage.ADFormState;
import com.example.admanage.system_main.ui.admanage.ADManageViewModel;
import com.example.admanage.system_main.ui.admanage.ADManageViewModelFactory;
import com.example.admanage.system_main.ui.admanage.ADSearchResult;
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

public class ADStatisticsFragment extends Fragment implements AMapLocationListener, AMap.OnMapClickListener {

    private ADManageViewModel adManageViewModel;
    private View root;

    private TextView textView_adname;
    private TextView textView_sourposit;
    private TextView textView_userposit;
    private TextView textView_streetname;
    private TextView textView_calendar;
    private TextView textView_feestate;
    private TextView textView_planstate;
    private TextView textView_movestate;

    private EditText editText_adname;
    private EditText editText_sourposit;
    private EditText editText_useposit;
    private EditText editText_streetname;
    private EditText editText_calendar;

    private Spinner spinner_feestate;
    private Spinner spinner_planstate;
    private Spinner spinner_movestate;

    private Button but_search;
    private ImageButton searchView;
    private FloatingActionButton fab;
    private Intent intent;
    private Bundle bundle;
    private int user_id;

    String streetname;
    double currentlat;
    double currentlon;
    double myLocationlat;
    double myLocationlon;

    private MapView mMapView;
    private AMap aMap;

    boolean add_byMyLocation=true;
    boolean isgetMyLocation=false;

    public AMapLocationClient mLocationClient=null;
    AMapLocationClientOption mLocationOption;
    MyLocationStyle myLocationStyle;
    AMap.OnMyLocationChangeListener onMyLocationChangeListener;

    ArrayList<Advertise> adArrayList=null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        adManageViewModel =
                ViewModelProviders.of(this, new ADManageViewModelFactory(getCongfig("ip"),getCongfig("端口")))
                        .get(ADManageViewModel.class);
        root = inflater.inflate(R.layout.fragment_main_ad_statistics, container, false);

        init();

        //获取地图控件引用
        mMapView = (MapView) root.findViewById(R.id.location);

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);

        init_map();

        init_markers(adArrayList);

        setObverse();
        //给UI控件设置事件监听
        setHandle();

        return root;
    }
    //加载控件和广告列表
    void init(){
        intent=getActivity().getIntent();
        bundle=intent.getExtras();
        user_id=bundle.getInt("user_id");

        textView_adname=root.findViewById(R.id.textView_adname);
        textView_sourposit=root.findViewById(R.id.textView_sourposit);
        textView_userposit=root.findViewById(R.id.textView_useposit);
        textView_streetname=root.findViewById(R.id.textView_streetname);
        textView_calendar=root.findViewById(R.id.textView_duedate);
        textView_feestate=root.findViewById(R.id.textView_feestate);
        textView_planstate=root.findViewById(R.id.textView_planstate);
        textView_movestate=root.findViewById(R.id.textView_movestate);
        editText_adname=root.findViewById(R.id.editText_adname);
        editText_sourposit=root.findViewById(R.id.editText_sourposit);
        editText_useposit=root.findViewById(R.id.editText_userposit);
        editText_streetname=root.findViewById(R.id.editText_streetname);
        editText_calendar=root.findViewById(R.id.editText_calendar);

        spinner_feestate=root.findViewById(R.id.spinner_feestate);
        spinner_planstate=root.findViewById(R.id.spinner_planstate);
        spinner_movestate=root.findViewById(R.id.spinner_movestate);

        but_search=root.findViewById(R.id.but_search);
        searchView=root.findViewById(R.id.searchView);
        fab = root.findViewById(R.id.fab);

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
        init_ADlist();
    }

    void init_ADlist(){
        ADListThread adListThread=new ADListThread(getCongfig("ip"),getCongfig("端口"));
        ExecutorService executor = Executors.newCachedThreadPool();
        Future future = executor.submit(adListThread);

        try {
            adArrayList= (ArrayList<Advertise>) future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(adArrayList==null){
            ToastUtil.showToast(getContext(),"广告信息加载失败");
            return;
        }
    }

    //加载map和定位监听，并在定位监听种设置定位蓝点（否则不显示）
    void init_map(){

        if(aMap==null){
            aMap=mMapView.getMap();
        }

        /*如果要把地图中心定在哪个城市可以指定下面的经纬度
        //参数依次是：视角调整区域的中心点坐标、希望调整到的缩放级别、俯仰角0°~45°（垂直与地图时为0）、偏航角 0~360° (正北方为0)
        CameraPosition cameraPosition=new CameraPosition(new LatLng(36.977290,160.337000),18,30,0);
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        AMapOptions mapOptions = new AMapOptions();
        mapOptions.camera(cameraPosition);
        mMapView = new MapView(getContext(), mapOptions);*/

        //设置地图的放缩级别
        if(getCongfig("地图缩放等级")!=null){
            int paintlevel = Integer.parseInt(getCongfig("地图缩放等级"));
            Log.e("等级", String.valueOf(paintlevel));
            aMap.moveCamera(CameraUpdateFactory.zoomTo(paintlevel));
        }
        else {
            aMap.moveCamera(CameraUpdateFactory.zoomTo(4));
        }

        //初始化定位
        mLocationClient = new AMapLocationClient(getContext());

        //设置定位回调监听
        mLocationClient.setLocationListener(this);

        mLocationOption= new AMapLocationClientOption();

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        //高精度定位模式：会同时使用网络定位和GPS定位，优先返回最高精度的定位结果，以及对应的地址描述信息。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        //低功耗定位模式：不会使用GPS和其他传感器，只会使用网络定位（Wi-Fi和基站定位）；
        //mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
        //仅用设备定位模式：不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位，需要在室外环境下才可以成功定位。注意，自 v2.9.0 版本之后，仅设备定位模式下支持返回地址描述信息。
        //mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);

        //如果您需要使用单次定位，需要进行如下设置：
        //获取一次定位结果：//该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);

        /*自定义连续定位
        SDK默认采用连续定位模式，时间间隔2000ms。如果您需要自定义调用间隔：
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。*/
        mLocationOption.setInterval(15000);

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);

        //设置定位请求超时时间，默认为30秒。单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        //mLocationOption.setHttpTimeOut(20000);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);

        //接下来为定位蓝点的代码
        myLocationStyle = new MyLocationStyle();
        //定位模式：定位一次，且将视角移动到地图中心点（共8种）。第一次定位不成功，定位不到
        //myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;
        //myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
        //连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        //是否显示小蓝点
        myLocationStyle.showMyLocation(true);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //定其为蓝点添加在onLocationchange，结果：不行，会重复挪动定位位置
        //在OnMylocation种设置了，也不行，location对象吧位置定到大西洋，而且没有显示蓝点
        //设置定位蓝点的Style
        aMap.setMyLocationStyle(myLocationStyle);

        //启动定位
        mLocationClient.startLocation();
        //定位结果通过AMapLocationListener获取
        //mLocationClient.stopLocation();

        //不好使
        AMap.OnMyLocationChangeListener onMyLocationChangeListener=new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                myLocationlat=location.getLatitude();
                myLocationlon=location.getLongitude();
                isgetMyLocation=true;
            }
        };

        //设置默认定位按钮是否显示，非必需设置。(定位到大西洋了)
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setOnMyLocationChangeListener(onMyLocationChangeListener);//怎么定位到大西洋了
        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setMyLocationEnabled(true);

        aMap.setOnMapClickListener(this);
    }

    //加载广告标记点
    void init_markers(ArrayList<Advertise> adArrayList){

        String feestate;
        String planstate;
        double adlong;
        double adlat;
        LatLng latLng1;
        //MarkerOptions 是设置 Marker 参数变量的类
        MarkerOptions markerOption = new MarkerOptions();
        // 定义 Marker 点击事件监听
        AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
            // marker 对象被点击时回调的接口
            // 返回 true 则表示接口已响应事件，否则返回false
            @Override
            public boolean onMarkerClick(Marker marker) {
                if("此点位置".equals(marker.getTitle())){
                    marker.remove();
                    add_byMyLocation=true;
                    isgetMyLocation=false;
                }
                else {
                    if(marker.isInfoWindowShown()){
                        marker.hideInfoWindow();
                    }
                    else{
                        marker.showInfoWindow();
                    }
                }

                return true;
            }
        };

        if(adArrayList==null){
            ToastUtil.showToast(getContext(),"未查询到相关的广告");
        }
        else{
            for(Advertise advertise:adArrayList){

                feestate="已缴费";
                planstate="符合规划";
                adlong=advertise.getA_long();
                adlat=advertise.getA_lat();

                latLng1 = new LatLng(adlat, adlong);
                markerOption.position(latLng1);

                //根据广告的状态显示不同颜色的标记点
                if(advertise.getFee_status()==0){
                    feestate="未缴费";
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(),R.drawable.markicon4)));
                }else  if(advertise.getPlan_status()==0){
                    planstate="不符合规划";
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(),R.drawable.markicon2)));
                }else if(advertise.getRemove_status()==1){
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(),R.drawable.markicon3)));
                }else{
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(),R.drawable.markicon1)));
                }

            /*String string ="名称:"+advertise.getName()+"\n类型:"+advertise.getType_id()+"\n规格:"+advertise.getStandard()
                    +"\n尺寸:"+advertise.getStyle()+"\n产权:"+advertise.getOwner()+"\n使用:"+advertise.getUser()
                    +"\n审批:"+advertise.getShp_id()+"\n过期日:"+advertise.getPeriod_end()+"\n缴费:"+feestate
                    +"\n规划:"+planstate+"\n经度："+advertise.getA_long()+"\n纬度："+advertise.getA_lat()
                    +"\n内容:"+advertise.getContent();*/
                String string ="名称:"+advertise.getName()+"\n规格:"+advertise.getStandard()
                        +"\n尺寸:"+advertise.getStyle()+"\n产权:"+advertise.getOwner()+"\n使用:"+advertise.getUser()
                        +"\n过期日:"+advertise.getPeriod_end()+"\n缴费:"+feestate
                        +"\n规划:"+planstate+"\n经度："+advertise.getA_long()+"\n纬度："+advertise.getA_lat()
                        +"\n内容:"+advertise.getContent();

                markerOption.title(advertise.getLocation())
                        .snippet(string);

                markerOption.draggable(true);//设置Marker可拖动
                // 将Marker设置为贴地显示，可以双指下拉地图查看效果
                markerOption.setFlat(true);//设置marker平贴地图效果
                //final Marker marker2 =
                aMap.addMarker(markerOption);
                // 绑定 Marker 被点击事件
            }
        }

        aMap.setOnMarkerClickListener(markerClickListener);
    }

    private void setObverse(){
        adManageViewModel.getADFromFormState().observe(getViewLifecycleOwner(), new Observer<ADFormState>() {
            @Override
            public void onChanged(@Nullable ADFormState adFormState) {
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

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setVisibility(View.GONE);
                textView_adname.setVisibility(View.VISIBLE);
                textView_sourposit.setVisibility(View.VISIBLE);
                textView_userposit.setVisibility(View.VISIBLE);
                textView_streetname.setVisibility(View.VISIBLE);
                textView_calendar.setVisibility(View.VISIBLE);
                textView_feestate.setVisibility(View.VISIBLE);
                textView_planstate.setVisibility(View.VISIBLE);
                textView_movestate.setVisibility(View.VISIBLE);
                editText_adname.setVisibility(View.VISIBLE);
                editText_sourposit.setVisibility(View.VISIBLE);
                editText_useposit.setVisibility(View.VISIBLE);
                editText_streetname.setVisibility(View.VISIBLE);
                editText_calendar.setVisibility(View.VISIBLE);

                spinner_feestate.setVisibility(View.VISIBLE);
                spinner_planstate.setVisibility(View.VISIBLE);
                spinner_movestate.setVisibility(View.VISIBLE);

                but_search.setVisibility(View.VISIBLE);
            }
        });

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
                bundle.putString("操作","地图添加");
                bundle.putInt("user_id",user_id);

                if(!add_byMyLocation){
                    ToastUtil.showToast(getContext(),"标记点\n经度"+currentlon+"\n纬度"+currentlat);
                    bundle.putDouble("经度",currentlon);
                    bundle.putDouble("纬度",currentlat);
                    bundle.putString("街道名",streetname);
                    //bundle.putInt("authority",user_authority);
                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                }else if(isgetMyLocation){
                    ToastUtil.showToast(getContext(),"我的位置\n经度"+myLocationlon+"\n纬度"+myLocationlat);
                    bundle.putDouble("经度",myLocationlon);
                    bundle.putDouble("纬度",myLocationlat);
                    bundle.putString("街道名",streetname);
                    //bundle.putInt("authority",user_authority);
                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                }else{
                    ToastUtil.showToast(getContext(),"请先定位获取您的位置");
                }

            }
        });

        but_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchView.setVisibility(View.VISIBLE);
                textView_adname.setVisibility(View.GONE);
                textView_sourposit.setVisibility(View.GONE);
                textView_userposit.setVisibility(View.GONE);
                textView_streetname.setVisibility(View.GONE);
                textView_calendar.setVisibility(View.GONE);
                textView_feestate.setVisibility(View.GONE);
                textView_planstate.setVisibility(View.GONE);
                textView_movestate.setVisibility(View.GONE);
                editText_adname.setVisibility(View.GONE);
                editText_sourposit.setVisibility(View.GONE);
                editText_useposit.setVisibility(View.GONE);
                editText_streetname.setVisibility(View.GONE);
                editText_calendar.setVisibility(View.GONE);

                spinner_feestate.setVisibility(View.GONE);
                spinner_planstate.setVisibility(View.GONE);
                spinner_movestate.setVisibility(View.GONE);

                but_search.setVisibility(View.GONE);

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
        aMap.clear(true);
        adArrayList=arrayList;
        init_markers(adArrayList);
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null&&amapLocation.getErrorCode() == 0) {

                //amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                //amapLocation.getCountry();//国家信息
                //amapLocation.getProvince();//省信息
                //mCity = amapLocation.getCity();//城市信息
                streetname=amapLocation.getDistrict()+amapLocation.getStreet();//城区信息+街道信息

                currentlat = amapLocation.getLatitude();
                currentlon = amapLocation.getLongitude();

                // 设置当前地图显示为当前位置
                //aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentlat, currentlon), 10));
                //*************定义mark
                /*myLocation_markerOptions = new MarkerOptions();
                myLocation_markerOptions.position(new LatLng(currentlat, currentlon));
                myLocation_markerOptions.title("当前位置:"+streetname+"\n经度"+currentlon+"\n纬度"+currentlat);
                myLocation_markerOptions.visible(true);*/

                //****添加Marker
                //aMap.addMarker(myLocation_markerOptions);
//                    mLocationListener.onLocationChanged(amapLocation);

                //fab.setVisibility(View.VISIBLE);
            } else {
                if(amapLocation.getErrorCode()==12){
                    ToastUtil.showToast(getContext(),"请给程序定位权限");
                    //fab.setVisibility(View.GONE);
                }
                else if(amapLocation.getErrorCode()==7){
                    /*String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                    ToastUtil.showToast(getContext(),errText);*/
                    //fab.setVisibility(View.GONE);
                }else if(amapLocation.getErrorCode()==0){
                    //fab.setVisibility(View.VISIBLE);
                }else{
                    String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                    ToastUtil.showToast(getContext(),errText);
                    //fab.setVisibility(View.GONE);
                }
            }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        currentlat=latLng.latitude;
        currentlon=latLng.longitude;
        add_byMyLocation=false;
        //fab.setVisibility(View.VISIBLE);
        MarkerOptions markerOption = new MarkerOptions();
        LatLng latLng1 = new LatLng(currentlat,currentlon);
        markerOption.position(latLng1);
        markerOption.title("此点位置").snippet("经度"+currentlon+"纬度"+currentlat);
        aMap.addMarker(markerOption);
        ToastUtil.showToast(getContext(),"经度"+currentlon+"\n纬度"+currentlat);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        if(null != mLocationClient){
            mLocationClient.onDestroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
        init_ADlist();
        init_markers(adArrayList);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
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