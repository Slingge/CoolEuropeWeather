package slingge.cooleuropeweather.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 显示所有的子项的ListView
 */
public class MyListView extends ListView {

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 设置不滚动
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
