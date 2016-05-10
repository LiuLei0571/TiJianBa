package com.hengtiansoft.tijianba.adapter;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.juanliuinformation.lvningmeng.R;

public class CustomSimpleAdapter extends SimpleAdapter {
	private int[] colors = new int[] { R.color.white, R.color.light_blue };
	private Context mContext;

	public CustomSimpleAdapter(Context context,
			ArrayList<Map<String, Object>> items, int resource, String[] from,
			int[] to) {
		super(context, items, resource, from, to);
		mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		int colorPos = position % colors.length;
		view.setBackgroundColor(mContext.getResources().getColor(
				colors[colorPos]));
		return view;
	}
}