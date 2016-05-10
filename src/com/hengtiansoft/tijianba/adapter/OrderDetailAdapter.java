package com.hengtiansoft.tijianba.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.Menu;
import com.juanliuinformation.lvningmeng.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class OrderDetailAdapter extends BaseListAdapter<Menu> {
  private Context mContext;
  private ArrayList<Menu> mMenuList;
  private String[] mState = { "未预约", "已预约", "已体检", "退款中", "已退款" };

  public OrderDetailAdapter(Context context, ArrayList<Menu> list) {
    super(context, list, R.layout.layout_order_detail_item);
    this.mContext = context;
    this.mMenuList = list;
  }

  @Override
  public int getCount() {
    int count = mMenuList.size();
    return count;
  }

  @Override
  public View getView(int position, View view, ViewGroup parent) {
    Menu menu = mMenuList.get(position);
    ViewHolder holder = null;
    if (view == null) {
      holder = new ViewHolder();
      view = LayoutInflater.from(mContext).inflate(R.layout.layout_order_detail_item, null);
      holder.tvMenuName = (TextView) view.findViewById(R.id.tv_menu_item_name);
      holder.tvMenuTypeName = (TextView) view.findViewById(R.id.tv_menu_item_type);
      holder.tvPrice = (TextView) view.findViewById(R.id.tv_price);
      holder.tvBuyNum = (TextView) view.findViewById(R.id.tv_buynum);
      holder.tvMarketPrice = (TextView) view.findViewById(R.id.tv_market_price);
      holder.tvState = (TextView) view.findViewById(R.id.tv_state);
      holder.mMenuImageView = (ImageView) view.findViewById(R.id.img_menu_item);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }
    holder.tvMenuName.setText(menu.getMenuName());
    holder.tvMenuTypeName.setText(menu.getMenuTypeName());
    holder.tvPrice.setText("￥"+menu.getPrice());
    holder.tvBuyNum.setText("×" + menu.getBuyNum());
    holder.tvMarketPrice.setText("￥"+menu.getMarketPrice());
    holder.tvMarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    holder.tvState.setText(mState[menu.getStatus()]);
    ImageLoader.getInstance().displayImage(RemoteDataManager.SERVICE + menu.getLogo(), holder.mMenuImageView, options);
    return view;
  }

  class ViewHolder {
    public TextView tvMenuName;
    public TextView tvMenuTypeName;
    public TextView tvPrice;
    public TextView tvBuyNum;
    public TextView tvMarketPrice;
    public TextView tvState;
    public ImageView mMenuImageView;
  }
}
