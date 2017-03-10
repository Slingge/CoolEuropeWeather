package slingge.cooleuropeweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Slingge on 2017/3/6 0006.
 */

public class dbData extends DataSupport {

    private String city;//选择的城市
    private String DailyChart;//每日一图

    private String response;//天气数据


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDailyChart() {
        return DailyChart;
    }

    public void setDailyChart(String dailyChart) {
        DailyChart = dailyChart;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
