package com.hengtiansoft.tijianba.adapter;

import android.content.Context; 
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hengtiansoft.tijianba.model.RedPocket;
import com.juanliuinformation.lvningmeng.R;
import java.util.List;

public class MyRedPocketAdapter extends BaseListAdapter<RedPocket> {
	private Context mContext;
	private List<RedPocket> mList;
	private int mState;
	private String[] mStrType = { "张", "次" };
	private String[] mStrName = { "元优惠券", "元红包" };
	private String[] mStrTime = { "过期时间 ", "使用时间 " };
	private int[] mImgBac = { R.drawable.redpocket_get_background,
			R.drawable.redpocket_use_background };

	public MyRedPocketAdapter(Context context, List<RedPocket> list, int layout) {
		super(context, list, layout);

	}

	public MyRedPocketAdapter(Context context, List<RedPocket> list,
			int layout, int state) {
		this(context, list, layout);
		this.mContext = context;
		this.mList = list;
		this.mState = state;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RedPocket redPocket = mList.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.layout_redpocket_list, null);
			holder.tvNumber = (TextView) convertView
					.findViewById(R.id.tv_list_red_num);
			holder.tvName = (TextView) convertView
					.findViewById(R.id.tv_list_red_name);
			holder.tvTime = (TextView) convertView
					.findViewById(R.id.tv_list_red_time);
			holder.TvMoney = (TextView) convertView
					.findViewById(R.id.tv_list_red_money);
			holder.imgBac = (RelativeLayout) convertView
					.findViewById(R.id.rlyt_redpocket_list);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		try {
			holder.tvNumber.setText(redPocket.getNum() + mStrType[mState]);
			holder.tvName.setText(redPocket.getMoney() + mStrName[mState]);
			if (redPocket.getTime() == null) {
				redPocket.setTime("");
			}
			holder.tvTime.setText(mStrTime[mState] + redPocket.getTime());
			holder.TvMoney.setText("￥"+redPocket.getMoney());
			holder.imgBac.setBackgroundResource(mImgBac[mState]);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return convertView;
	}

	class ViewHolder {
		public TextView tvNumber;
		public TextView tvName;
		public TextView tvTime;
		public TextView TvMoney;
		public RelativeLayout imgBac;
	}
}
