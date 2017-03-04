package slingge.cooleuropeweather.bean.WeatherDataBean;


/**
 * 未来几天天气
 * Created by Slingge on 2017/3/3 0003.
 */

public class Daily_forecastBean {


        public String date;
        public String code_n;
        public String txt_d;
        public String txt_n;
        public condBean cond;
        public tmpBean tmp;


        public class condBean {
            public String code_d;
            public String code_n;
            public String txt_d;
            public String txt_n;
        }

        public class tmpBean {//气温
            public String max;
            public String min;
        }


}
