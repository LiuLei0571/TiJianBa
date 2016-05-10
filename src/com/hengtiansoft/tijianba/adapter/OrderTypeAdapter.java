package com.hengtiansoft.tijianba.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengtiansoft.tijianba.model.OrderType;
import com.juanliuinformation.lvningmeng.R;

/**
 * 
 * com.hengtiansoft.tijianba.adapter.OrgListAdapter
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 24, 2014 11:47:56 AM
 */
public class OrderTypeAdapter extends BaseListAdapter<OrderType> {

	private Context mContext;
	private LayoutInflater mInflater;
	private ViewHolder holder;
	private ArrayList<OrderType> data;

	public OrderTypeAdapter(Context context, ArrayList<OrderType> list) {
		super(context, list, R.layout.order_item);
		this.mContext = context;
		this.data = list;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	public boolean select(int position) {
		if (!data.get(position).isSelected()) {
			data.get(position).setSelected(true);
			for (int i = 0; i < data.size(); i++) {
				if (i != position) {
					data.get(i).setSelected(false);
				}
			}
		}
		notifyDataSetChanged();
		return data.get(position).isSelected();
	}

	@Override
	public OrderType getItem(int position) {
		return data.get(position);
	}

	@Override
	public View getView(final int pos, View view, ViewGroup arg2) {
		ViewHolder holder = null;
		final OrderType item = getItem(pos);
		Log.i("GridView", "position" + pos);
		if (view == null) {
			// view = inflateLayout();
			holder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.order_item,
					null);
			holder.mOrderNameTextView = (TextView) view
					.findViewById(R.id.tv_order_name);
			holder.mOrderImageView = (ImageView) view.findViewById(R.id.iv_order);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		try {
			holder.mOrderNameTextView.setText(item.getName());
			if (item.isSelected()) {
				holder.mOrderImageView.setVisibility(View.VISIBLE);
			} else {
				holder.mOrderImageView.setVisibility(View.GONE);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return view;
	}

	class ViewHolder {
		public TextView mOrderNameTextView;
		public ImageView mOrderImageView;

		void initHolder(View view) {
			mOrderNameTextView = (TextView) view
					.findViewById(R.id.tv_order_name);
			mOrderImageView = (ImageView) view.findViewById(R.id.iv_order);
		}
	}

}
