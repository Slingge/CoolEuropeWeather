package slingge.cooleuropeweather.db;

import org.litepal.crud.DataSupport;

/**
 * 天气数据
 * Created by Slingge on 2017/3/13 0013.
 */

public class dbResponse extends DataSupport {

    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
