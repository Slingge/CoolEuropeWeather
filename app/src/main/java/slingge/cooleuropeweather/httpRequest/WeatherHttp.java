package slingge.cooleuropeweather.httpRequest;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.List;

import okhttp3.Call;
import slingge.cooleuropeweather.bean.WeatherDataBean.HeWeather6Model;

import slingge.cooleuropeweather.db.dbResponse;
import slingge.cooleuropeweather.util.ToastUtil;

/**
 * 获取天气信息
 * Created by Slingge on 2017/3/2 0002.
 */

public class WeatherHttp {

    private Context context;

    public WeatherHttp(Context context) {
        this.context = context;
    }


    public interface WeatherDataBackCall {
        void weathData(List<HeWeather6Model.Daily_forecastModel> dailyList, String upTime);
    }

    public WeatherDataBackCall weatherData;

    public void setWeatherDataBackCall(WeatherDataBackCall weatherData) {
        this.weatherData = weatherData;
    }


    public void weatherHttp(String city) {
        if(TextUtils.isEmpty(city)){
            ToastUtil.showToast("city is null");
            return;
        }

        Log.e("获取天气信息.........", city);
        OkHttpUtils.get().url("https://free-api.heweather.com/s6/weather/forecast?parameters")
                .addParams("location", city).addParams("key", "a26e3b8650914bc6a429a6e035253cf5").
                build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                   okhttp3.OkHttpClient OkHttpClient= OkHttpUtils.getInstance().getOkHttpClient();
                dbResponse db = DataSupport.findFirst(dbResponse.class);

                if (!TextUtils.isEmpty(db.getResponse())) {
                    analysisJson(db.getResponse());
                    return;
                }
                ToastUtil.showToast("网络错误");
            }

            @Override
            public void onResponse(String response, int id) {
                analysisJson(response);
                Log.e("天气信息.........", response);
//                dbResponse db = new dbResponse();//保存
//                db.setResponse(response);
//                db.save();
            }
        });

    }

    /**
     * 解析
     */
    public void analysisJson(String response) {

        try {
            JSONObject obj = new JSONObject(response);
            Gson gson = new Gson();
            List<HeWeather6Model> list = gson.fromJson(obj.getString("HeWeather6"), new TypeToken<List<HeWeather6Model>>() {
            }.getType());
            HeWeather6Model model = list.get(0);
            if (model.status.equals("ok")) {
                slingge.cooleuropeweather.util.abLog.d("1");
                weatherData.weathData(model.daily_forecast, model.update.loc);
            } else {
                 slingge.cooleuropeweather.util.abLog.d("2");
                ToastUtil.showToast("天气信息获取错误");
            }
        } catch (JSONException e) {
             slingge.cooleuropeweather.util.abLog.d("3");
            e.printStackTrace();
        }
    }

}
