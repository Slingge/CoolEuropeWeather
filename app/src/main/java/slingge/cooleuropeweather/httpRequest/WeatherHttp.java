package slingge.cooleuropeweather.httpRequest;

import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import slingge.cooleuropeweather.util.abLog;

/**
 * 获取天气信息
 * Created by Slingge on 2017/3/2 0002.
 */

public class WeatherHttp {

    private Context context;

    public WeatherHttp(Context context) {
        this.context = context;
    }


    public void weatherHttp(String city) {
        OkHttpUtils.get().url("https://api.heweather.com/x3/weather?").addParams("city", city).addParams("key", "a26e3b8650914bc6a429a6e035253cf5").
                build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                abLog.e("天气信息", response);
            }
        });

    }


}
