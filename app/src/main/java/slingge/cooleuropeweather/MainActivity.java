package slingge.cooleuropeweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import slingge.cooleuropeweather.adapter.RecyclerViewAdapter;


/**
 *
 * Created by Slingge on 2017/2/22 0022.
 */
public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.HolderCilck{

    private RecyclerViewAdapter adapter;
    private List<String> list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNavigationView();
    }

    private void initNavigationView(){
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        adapter=new RecyclerViewAdapter(this,list);
        adapter.setHolderCilck(this);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void click() {

    }
}
