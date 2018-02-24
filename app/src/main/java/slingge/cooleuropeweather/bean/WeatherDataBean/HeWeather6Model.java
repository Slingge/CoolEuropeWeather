package slingge.cooleuropeweather.bean.WeatherDataBean;


import java.util.List;

/**
 * 未来几天天气
 * Created by Slingge on 2017/3/3 0003.
 */

public class HeWeather6Model {

    public String status;

    public Basic basic;
    public UpdateModel update;

    public List<Daily_forecastModel> daily_forecast;


    public class UpdateModel {
        public String loc;
        public String utc;
    }

    public class Basic {
        public String cid;
        public String location;
        public String parent_city;
        public String admin_area;
        public String cnty;
        public String lat;
        public String lon;
        public String tz;
    }

    public class Daily_forecastModel {
        public String cond_code_d;
        public String cond_code_n;
        public String cond_txt_d;
        public String cond_txt_n;

        public String date;
        public String hum;
        public String mr;
        public String ms;

        public String pcpn;
        public String pop;
        public String pres;
        public String sr;

        public String ss;
        public String tmp_max;
        public String tmp_min;
        public String uv_index;

        public String vis;
        public String wind_deg;
        public String wind_dir;
        public String wind_sc;
        public String wind_spd;
    }


}
