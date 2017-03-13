package slingge.cooleuropeweather.httpRequest;

import android.content.Context;
import android.text.TextUtils;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.litepal.crud.DataSupport;

import okhttp3.Call;
import slingge.cooleuropeweather.db.dbDailyChart;

/**
 * 获取bingying每日一图
 * Created by Slingge on 2017/3/1 0001.
 */

public class BiYingPicture {


    private Context context;

    public BiYingPicture(Context context) {
        this.context = context;
    }

    public interface PictureCallBack {
        void Picture(String url);
    }

    public PictureCallBack pictureCallBack;

    public void setPictureCallBack(PictureCallBack pictureCallBack) {
        this.pictureCallBack = pictureCallBack;
    }


    public void getPicture() {
        OkHttpUtils.get().url("http://guolin.tech/api/bing_pic").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dbDailyChart db = DataSupport.findFirst(dbDailyChart.class);
                if(db.isSaved()){
                    if (TextUtils.isEmpty(db.getDailyChart())) {
                        return;
                    }
                }
                pictureCallBack.Picture(db.getDailyChart());
            }

            @Override
            public void onResponse(String response, int id) {
                pictureCallBack.Picture(response);
                dbDailyChart db = new dbDailyChart();
                db.setDailyChart(response);
                db.save();
            }
        });


    }


}
