package slingge.cooleuropeweather.util;

/**
 * Created by Slingge on 2017/2/25 0025.
 */

import java.io.IOException;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import android.content.Context;
import android.content.res.AssetManager;

import slingge.cooleuropeweather.bean.CityBean;
import slingge.cooleuropeweather.bean.CityListBean;


public class AppJsonFileReader {

    private final static String fileName = "City.json";

    static String jsonStr;


    public static List<String> getCityId(String province, String city, Context context) {
        List<String> strList = new ArrayList<>();
        jsonStr = getJson(context);
        if (jsonStr == null) {
            return strList;
        }
        CityListBean bean = new Gson().fromJson(jsonStr, CityListBean.class);

        for (CityBean cityBean : bean.cityList) {
            if (province.equals("")) {//省级为空获取全部省份
                strList.add(cityBean.provinceZh);
            } else if (cityBean.provinceZh.equals(province) && city.equals("")) {//市级为空获取省级所有市级
                strList.add(cityBean.leaderZh);
            } else if (cityBean.provinceZh.equals(province) && cityBean.leaderZh.equals(city)) {
                strList.add(cityBean.cityZh);
            }
        }
        return DuplicateRemoval(strList);
    }


    public static List<String> DuplicateRemoval(List<String> list) {
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (!list1.contains(list.get(i))) {
                list1.add(list.get(i));
            }
        }
        return list1;
    }


    public static String getJson(Context context) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


}