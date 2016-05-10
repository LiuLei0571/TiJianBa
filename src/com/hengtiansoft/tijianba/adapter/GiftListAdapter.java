package com.hengtiansoft.tijianba.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class GiftListAdapter extends BaseAdapter {

	private  Context mContent;
	private  List list;
	
	public GiftListAdapter(Context mContent, List list) {
		super();
		this.mContent = mContent;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 5  ;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}
