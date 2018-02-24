package slingge.cooleuropeweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import slingge.cooleuropeweather.R;
import slingge.cooleuropeweather.bean.WeatherDataBean.HeWeather6Model;

/**
 * 未来天气
 * Created by Slingge on 2017/3/6 0006.
 */

public class PredictionAdapter extends BaseAdapter {

    private Context context;
    private List<HeWeather6Model.Daily_forecastModel> list;

    public PredictionAdapter(Context context, List<HeWeather6Model.Daily_forecastModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_prediction, viewGroup, false);
            holder.tv_date = (TextView) view.findViewById(R.id.tv_date);
            holder.tv_max = (TextView) view.findViewById(R.id.tv_max);
            holder.tv_min = (TextView) view.findViewById(R.id.tv_min);
            holder.tv_weather = (TextView) view.findViewById(R.id.tv_weather);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        HeWeather6Model.Daily_forecastModel bean = list.get(i);
        holder.tv_date.setText(bean.date.replace("-", "."));
        holder.tv_weather.setText(bean.cond_txt_d);
        holder.tv_min.setText(bean.tmp_max + "℃");
        holder.tv_max.setText(bean.tmp_min + "℃");
        return view;
    }


    class ViewHolder {
        TextView tv_date, tv_weather, tv_max, tv_min;
    }


}
