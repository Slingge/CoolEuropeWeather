package slingge.cooleuropeweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import slingge.cooleuropeweather.R;

/**
 * 生活建议
 * Created by Slingge on 2017/3/8 0008.
 */

public class LifeAdviceAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;

    public LifeAdviceAdapter(Context context, List<String> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview, viewGroup, false);
            holder = new ViewHolder();
            holder.text = (TextView) view.findViewById(R.id.text);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.text.setText(list.get(i));
        holder.text.setBackgroundColor(context.getResources().getColor(R.color.colorTransparent));
        holder.text.setTextColor(context.getResources().getColor(R.color.colorWhite));
        return view;
    }


    public class ViewHolder {
        TextView text;

    }

}
