package slingge.cooleuropeweather;

import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import slingge.cooleuropeweather.adapter.PredictionAdapter;
import slingge.cooleuropeweather.adapter.RecyclerViewAdapter;
import slingge.cooleuropeweather.bean.WeatherDataBean.AQIBean;
import slingge.cooleuropeweather.bean.WeatherDataBean.Daily_forecastBean;
import slingge.cooleuropeweather.bean.WeatherDataBean.Hourly_forecastBean;
import slingge.cooleuropeweather.bean.WeatherDataBean.NowBean;
import slingge.cooleuropeweather.bean.WeatherDataBean.SuggestionBean;
import slingge.cooleuropeweather.httpRequest.BiYingPicture;
import slingge.cooleuropeweather.httpRequest.WeatherHttp;
import slingge.cooleuropeweather.util.AppJsonFileReader;
import slingge.cooleuropeweather.util.StatusBarUtil;
import slingge.cooleuropeweather.view.MyListView;


/**
 * Created by Slingge on 2017/2/22 0022.
 */
public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.HolderCilck, WeatherHttp.WeatherDataBackCall {

    private RecyclerViewAdapter adapter;
    private List<String> list = new ArrayList<>();

    private DrawerLayout drawerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    private String province = "", city = "";
    private ImageView image_back;
    private TextView text;

    private TextView tv_title, tv_date;//地区，获取天气日期
    private TextView tv_temper, tv_weather;//气温，天气
    private TextView tv_pm25, tv_aqi;//pm2.5指数，AQI指数
    private MyListView list1, list2;//未来几天天气，生活建议

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNavigationView();
        init();

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        StatusBarUtil.setTranslucentForImageView(this, 0, image_back);
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
        WeatherHttp weatherHttp = new WeatherHttp(this);
        weatherHttp.setWeatherDataBackCall(this);
        weatherHttp.weatherHttp("郑州");

        RelativeLayout rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, StatusBarUtil.getStatusBarHeight(this) + 10, 0, 0);
        rl_title.setLayoutParams(lp);

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
        text = (TextView) headerView.findViewById(R.id.text);
        RelativeLayout rl_title2 = (RelativeLayout) headerView.findViewById(R.id.rl_title2);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, StatusBarUtil.getStatusBarHeight(this), 0, 0);
        rl_title2.setLayoutParams(lp);
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
            text.setText(city);
            image_back.setVisibility(View.VISIBLE);
            list.clear();
            list.addAll(AppJsonFileReader.getCityId(province, city, MainActivity.this));
        } else {
            //网络请求天气数据
            tv_title.setText(str);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void weathData(AQIBean aqiBean, List<Daily_forecastBean> dailyList, SuggestionBean suggeBean, NowBean nowBean, Hourly_forecastBean hourlyBean) {
        swipeRefreshLayout.setRefreshing(false);
        PredictionAdapter predictionAdapter = new PredictionAdapter(this, dailyList);
        list1.setAdapter(predictionAdapter);
        tv_weather.setText(nowBean.cond.txt);

        tv_temper.setText(hourlyBean.tmp + "℃");
        tv_date.setText(hourlyBean.date.substring(hourlyBean.date.length() - 5, hourlyBean.date.length()));

        tv_pm25.setText(aqiBean.city.pm25);
        tv_aqi.setText(aqiBean.city.aqi);
    }


}
