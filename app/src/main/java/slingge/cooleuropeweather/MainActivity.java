package slingge.cooleuropeweather;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import slingge.cooleuropeweather.adapter.RecyclerViewAdapter;
import slingge.cooleuropeweather.util.AppJsonFileReader;
import slingge.cooleuropeweather.util.ToastUtil;


/**
 * Created by Slingge on 2017/2/22 0022.
 */
public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.HolderCilck {

    private RecyclerViewAdapter adapter;
    private List<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNavigationView();
    }

    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        RecyclerView recyclerView = (RecyclerView) headerView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list.clear();
        list.addAll(AppJsonFileReader.getCityId("", "", MainActivity.this));
        ToastUtil.showToast(list.size() + "");
        adapter = new RecyclerViewAdapter(this, list);
        adapter.setHolderCilck(this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void click(int position) {
        list.get(position);
    }


}
