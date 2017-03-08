package slingge.cooleuropeweather.bean.WeatherDataBean;

/**
 * 天气地址信息
 * Created by Slingge on 2017/3/8 0008.
 */

public class BaseicBean {

    public String city;
    public String cnty;
    public String id;
    public String lat;
    public String lon;
    public updateBean update;

    public class updateBean {//更新时间
        public String loc;
        public String utc;
    }
}
