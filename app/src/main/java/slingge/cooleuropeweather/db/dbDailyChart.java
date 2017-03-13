package slingge.cooleuropeweather.db;

import org.litepal.crud.DataSupport;

import static com.baidu.location.d.j.p;

/**
 * 必应每日一图
 * Created by Slingge on 2017/3/13 0013.
 **/

public class dbDailyChart extends DataSupport {

    private String DailyChart;//每日一图


    public String getDailyChart() {
        return DailyChart;
    }

    public void setDailyChart(String dailyChart) {
        DailyChart = dailyChart;
    }
}
