package com.hengtiansoft.tijianba.adapter;

import java.util.ArrayList; 

import com.hengtiansoft.tijianba.model.HealthInquary;
import com.juanliuinformation.lvningmeng.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HealthInquaryAdapter extends BaseListAdapter<HealthInquary> {

  private ArrayList<HealthInquary> mList;
  private Context mContext;

  public HealthInquaryAdapter(Context context, ArrayList<HealthInquary> list) {
    super(context, list, R.layout.layout_health_inquary_list);
    this.mContext = context;
    this.mList = list;
  }

  @Override
  public int getCount() {
    return mList.size();
  }

  @Override
  public HealthInquary getItem(int position) {
    return mList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    HealthInquary healthInquary = mList.get(position);
    ViewHolder holder = null;
    if (convertView == null) {
      holder = new ViewHolder();
      convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_health_inquary_list, null);
      holder.img = (ImageView) convertView.findViewById(R.id.health_inquary_list_img);
      holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_health_inquary_list_title);
      holder.tvDetial = (TextView) convertView.findViewById(R.id.tv_health_inquary_list_detial);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    try {
      String title = healthInquary.getTitle();
      String detail = healthInquary.getDetial();
      int imgId = healthInquary.getImgResource();
      holder.tvTitle.setText(title);
      holder.tvDetial.setText(detail);
      holder.img.setImageResource(imgId);
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    return convertView;

  }

  class ViewHolder {
    public ImageView img;
    public TextView tvTitle;
    public TextView tvDetial;
  }
}
