package slingge.cooleuropeweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Slingge on 2017/3/13 0013.
 */

public class dbCity extends DataSupport {

    private String city;//选择的城市


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
