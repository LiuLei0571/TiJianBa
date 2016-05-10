package com.hengtiansoft.tijianba.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.hengtiansoft.tijianba.model.Ranking;
import com.juanliuinformation.lvningmeng.R;

public class RankingAdapter extends BaseAdapter{
	
	private List<Ranking> mList;
	private LayoutInflater mInflater;
	
	public RankingAdapter(Context context,List<Ranking> list) {
		 mList = list;
		 mInflater = LayoutInflater.from(context);
	    }

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		 ViewHolder viewHolder = null;
		 if(arg1 == null){
			 viewHolder = new ViewHolder();
			 arg1 = mInflater.inflate(R.layout.ranking_item, null);
			 viewHolder.ranking_item_order_iv =(ImageView) arg1.findViewById(R.id.ranking_item_order_iv);
			 viewHolder.ranking_item_img_iv =(ImageView) arg1.findViewById(R.id.ranking_item_img_iv);
			 viewHolder.ranking_item_name =(TextView) arg1.findViewById(R.id.ranking_item_name);
			 viewHolder.ranking_item_img_star =(ImageView) arg1.findViewById(R.id.ranking_item_img_star);
			 viewHolder.ranking_item_long =(TextView) arg1.findViewById(R.id.ranking_item_long);
			 arg1.setTag(viewHolder);
		 }else{
			 arg1.getTag();
		 }
		 
		Ranking mRanking = mList.get(arg0);
//		 viewHolder.ranking_item_order_iv.setBackgroundResource(mRanking.getRanking_item_order_iv());
//		 viewHolder.ranking_item_img_iv.setBackgroundResource(mRanking.getRanking_item_img_iv());
//		 viewHolder.ranking_item_name.setText(mRanking.getRanking_item_name());
//		 viewHolder.ranking_item_img_star.setBackgroundResource(mRanking.getRanking_item_img_star());
//		 viewHolder.ranking_item_long.setText(mRanking.getRanking_item_long());
		 
//		 viewHolder.ranking_item_order_iv.setBackgroundResource(R.drawable.paihang_one);
//		 viewHolder.ranking_item_img_iv.setBackgroundResource(R.drawable.paihang_baby);
//		 viewHolder.ranking_item_name.setText("name");
//		 viewHolder.ranking_item_img_star.setBackgroundResource(R.drawable.paihang_one_star);
//		 viewHolder.ranking_item_long.setText("long");
		 
		return arg1;
	}
	
    class ViewHolder{
    	public ImageView ranking_item_order_iv;
    	public ImageView ranking_item_img_iv;
    	public TextView ranking_item_name;
    	public ImageView ranking_item_img_star;
    	public TextView ranking_item_long;
    }

}
