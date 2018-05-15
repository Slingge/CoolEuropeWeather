package slingge.cooleuropeweather.util;

import android.util.Log;


/**
 * Created by Slingge on 2017/2/21 0021.
 */

public class abLog {

    public static Boolean E;


    public static void e(String tag, String text) {
        if (E) {
            Log.e(tag, text);
        }
    }


    public static void d(String text){
        Log.d("ftd",text);
    }

}
