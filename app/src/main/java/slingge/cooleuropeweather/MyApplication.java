package slingge.cooleuropeweather;

import android.app.Application;

import slingge.cooleuropeweather.util.ImageLoaderUtil;
import slingge.cooleuropeweather.util.abLog;


/**
 * Created by jingbin on 2016/11/22.
 */

public class MyApplication extends Application {

    private static MyApplication cloudReaderApplication;

    public static MyApplication getInstance() {
        // if语句下是不会走的，Application本身已单例
        if (cloudReaderApplication == null) {
            synchronized (MyApplication.class) {
                if (cloudReaderApplication == null) {
                    cloudReaderApplication = new MyApplication();
                }
            }
        }
        return cloudReaderApplication;
    }

    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderUtil.configImageLoader(this);
        cloudReaderApplication = this;
        abLog.E = true;
//        HttpUtils.getInstance().setContext(getApplicationContext());
    }
}
