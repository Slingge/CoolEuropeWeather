package slingge.cooleuropeweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;


/**
 * Created by Slingge on 2017/2/22 0022.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        get();
    }

    private void get(){
        OkHttpUtils.post().url("https://api.heweather.com/x3/weather?").addParams("city","北京").addParams("key","a26e3b8650914bc6a429a6e035253cf5").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(MainActivity.this,"网络错误",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String response, int id) {
                Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
            }
        });
    }

}
