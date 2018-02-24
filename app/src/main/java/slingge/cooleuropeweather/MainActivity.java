package slingge.cooleuropeweather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

import slingge.cooleuropeweather.adapter.LifeAdviceAdapter;
import slingge.cooleuropeweather.adapter.PredictionAdapter;
import slingge.cooleuropeweather.adapter.RecyclerViewAdapter;
import slingge.cooleuropeweather.bean.WeatherDataBean.AQIBean;
import slingge.cooleuropeweather.bean.WeatherDataBean.HeWeather6Model;
import slingge.cooleuropeweather.bean.WeatherDataBean.Hourly_forecastBean;
import slingge.cooleuropeweather.bean.WeatherDataBean.NowBean;
import slingge.cooleuropeweather.bean.WeatherDataBean.SuggestionBean;
import slingge.cooleuropeweather.db.dbCity;
import slingge.cooleuropeweather.db.dbResponse;
import slingge.cooleuropeweather.httpRequest.BiYingPicture;
import slingge.cooleuropeweather.httpRequest.WeatherHttp;
import slingge.cooleuropeweather.util.AppJsonFileReader;
import slingge.cooleuropeweather.util.PermissionDialog;
import slingge.cooleuropeweather.util.StatusBarUtil;
import slingge.cooleuropeweather.util.ToastUtil;
import slingge.cooleuropeweather.view.MyListView;


/**
 * Created by Slingge on 2017/2/22 0022.
 */
public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.HolderCilck, WeatherHttp.WeatherDataBackCall {

    private RecyclerViewAdapter adapter;
    private List<String> list = new ArrayList<>();

    private WeatherHttp weatherHttp;

    private DrawerLayout drawerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    private String province = "", city = "";
    private ImageView image_back;
    private TextView text;

    private TextView tv_title, tv_date;//地区，获取天气日期
    private TextView tv_temper, tv_weather;//气温，天气
    private TextView tv_pm25, tv_aqi;//pm2.5指数，AQI指数
    private MyListView list1, list2;//未来几天天气，生活建议

    private String City;//当前城市
    private MyBroadcastReciver myBroadcastReciver;

    private View main_titleview, navi_titleview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Connector.getDatabase();
        initNavigationView();
        init();
        if (Build.VERSION.SDK_INT >= 23) {
            StatusBarUtil.setTranslucentForImageView(this, 0, image_back);
            main_titleview.setVisibility(View.VISIBLE);
            navi_titleview.setVisibility(View.VISIBLE);
        }
        Intent intent = new Intent(MainActivity.this, LocationService.class);
        startService(intent);
       /* swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                //定位权限判断申请
                if (Permission.JudgePermission(MainActivity.this)) {
                    if (!TextUtils.isEmpty(City)) {
                        weatherHttp.weatherHttp(City);
                        tv_title.setText(City);
                    } else {
                        Intent intent = new Intent(MainActivity.this, LocationService.class);
                        startService(intent);
                    }
                }
            }
        });*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        myBroadcastReciver = new MyBroadcastReciver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("LocationCity");
        registerReceiver(myBroadcastReciver, filter);
    }

    private void init() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        final ImageView image_bg = (ImageView) findViewById(R.id.image_bg);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        weatherHttp.weatherHttp(City);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });

        BiYingPicture biYingPicture = new BiYingPicture(this);
        biYingPicture.getPicture();
        biYingPicture.setPictureCallBack(new BiYingPicture.PictureCallBack() {
            @Override
            public void Picture(String url) {
                ImageLoader.getInstance().displayImage(url, image_bg);
            }
        });
        weatherHttp = new WeatherHttp(this);
        weatherHttp.setWeatherDataBackCall(this);

        main_titleview = findViewById(R.id.main_titleview);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_date = (TextView) findViewById(R.id.tv_date);
        ImageView image_menu = (ImageView) findViewById(R.id.image_menu);
        image_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        tv_temper = (TextView) findViewById(R.id.tv_temper);
        tv_weather = (TextView) findViewById(R.id.tv_weather);
        tv_pm25 = (TextView) findViewById(R.id.tv_pm25);
        tv_aqi = (TextView) findViewById(R.id.tv_aqi);
        list1 = (MyListView) findViewById(R.id.list1);
        list2 = (MyListView) findViewById(R.id.list2);
    }

    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navi_titleview = headerView.findViewById(R.id.navi_titleview);
        text = (TextView) headerView.findViewById(R.id.text);
        image_back = (ImageView) headerView.findViewById(R.id.image_back);
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!city.equals("")) {
                    image_back.setVisibility(View.VISIBLE);
                    list.clear();
                    city = "";
                    text.setText(province);
                    list.addAll(AppJsonFileReader.getCityId(province, "", MainActivity.this));
                } else if (!province.equals("")) {
                    image_back.setVisibility(View.INVISIBLE);
                    list.clear();
                    text.setText("中国");
                    province = "";
                    list.addAll(AppJsonFileReader.getCityId("", "", MainActivity.this));
                }
                adapter.notifyDataSetChanged();

            }
        });
        RecyclerView recyclerView = (RecyclerView) headerView.findViewById(R.id.recyclerView);
        recyclerView.setFocusable(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list.clear();
        list.addAll(AppJsonFileReader.getCityId("", "", MainActivity.this));
        adapter = new RecyclerViewAdapter(this, list);
        adapter.setHolderCilck(this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void click(int position) {
        String str = list.get(position);
        if (province.equals("")) {
            province = str;
            text.setText(province);
            image_back.setVisibility(View.VISIBLE);
            list.clear();
            list.addAll(AppJsonFileReader.getCityId(province, "", MainActivity.this));
        } else if (city.equals("")) {
            city = str;
            City = city;
            text.setText(city);
            image_back.setVisibility(View.VISIBLE);
            list.clear();
            list.addAll(AppJsonFileReader.getCityId(province, city, MainActivity.this));
        } else {
            //网络请求天气数据
            swipeRefreshLayout.setRefreshing(true);
            weatherHttp.weatherHttp(str);
            tv_title.setText(str);
            drawerLayout.closeDrawer(GravityCompat.START);
            City = str;
//            dbCity db = new dbCity();
//            db.setCity(City);
//            db.save();
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void weathData(List<HeWeather6Model.Daily_forecastModel> dailyList, String upTime) {
        swipeRefreshLayout.setRefreshing(false);
        PredictionAdapter predictionAdapter = new PredictionAdapter(this, dailyList);
        list1.setAdapter(predictionAdapter);

//        tv_weather.setText(nowBean.cond.txt);
//        tv_temper.setText(hourlyBean.tmp + "℃");
        tv_date.setText(upTime);

//        tv_pm25.setText(aqiBean.city.pm25);
//        tv_aqi.setText(aqiBean.city.aqi);

      /*  List<String> strList = new ArrayList<>();
        strList.add(suggeBean.air.txt);
        strList.add(suggeBean.comf.txt);
        strList.add(suggeBean.cw.txt);
        strList.add(suggeBean.drsg.txt);
        strList.add(suggeBean.flu.txt);
        strList.add(suggeBean.sport.txt);
        strList.add(suggeBean.trav.txt);
        strList.add(suggeBean.uv.txt);
        LifeAdviceAdapter lifeAdapter = new LifeAdviceAdapter(this, strList);
        list2.setAdapter(lifeAdapter);*/
    }


    private class MyBroadcastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String city = "";
            if (TextUtils.isEmpty(intent.getStringExtra("city"))) {
                dbResponse db = DataSupport.findFirst(dbResponse.class);
                if (!TextUtils.isEmpty(db.getResponse())) {
                    weatherHttp.analysisJson(db.getResponse());
                }
                ToastUtil.showToast("定位失败，请手动选择所在城市");
                swipeRefreshLayout.setRefreshing(false);
                return;
            }
            if (intent.getStringExtra("city").contains("市")) {
                city = intent.getStringExtra("city").replace("市", "");//接收参数
            } else {
                city = intent.getStringExtra("city");
            }
            String action = intent.getAction();//接收广播识别
            if (action.equals("LocationCity")) {
                weatherHttp.weatherHttp(city);
                tv_title.setText(city);
                City = city;
            }
        }
    }


    /**
     * 申请权限结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MainActivity.this, LocationService.class);
                startService(intent);
            } else {
                Toast.makeText(this, "你拒绝了权限申请", Toast.LENGTH_SHORT).show();
                PermissionDialog.dialog(this);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReciver);
        PermissionDialog.disDialog();
    }


}
