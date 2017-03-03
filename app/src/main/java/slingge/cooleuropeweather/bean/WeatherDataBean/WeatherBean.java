package slingge.cooleuropeweather.bean.WeatherDataBean;


import java.util.List;

/**
 * 天气数据
 * Created by Slingge on 2017/3/2 0002.
 */

public class WeatherBean {

    public List<HeWeatherData> HeWeatherList;

    public class HeWeatherData{
        public String status;
        public AQIBean aqi;
        public Daily_forecastBean daily_forecast;



    }





}
