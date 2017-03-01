package slingge.cooleuropeweather.httpRequest;

import android.content.Context;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import slingge.cooleuropeweather.util.ToastUtil;
import slingge.cooleuropeweather.util.abLog;

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
                ToastUtil.showToast(e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                abLog.e("..................",response);
                ToastUtil.showToast(response);
                pictureCallBack.Picture(response);

            }
        });


    }


}
