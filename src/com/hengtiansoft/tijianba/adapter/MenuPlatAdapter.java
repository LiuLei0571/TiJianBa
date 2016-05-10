package com.hengtiansoft.tijianba.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.Menu;
import com.juanliuinformation.lvningmeng.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * com.hengtiansoft.tijianba.activity.BaseActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 19, 2014 16:50:17 AM
 */
public class MenuPlatAdapter extends BaseListAdapter<Menu> {

  private Context mContext;
  private ArrayList<Menu> data;

  public MenuPlatAdapter(Context context, ArrayList<Menu> list) {
    super(context, list, R.layout.menu_plat_item);
    this.mContext = context;
    this.data = list;
  }

  @Override
  public int getCount() {
    return data.size();
  }

  @Override
  public Menu getItem(int position) {
    return data.get(position);
  }

  @Override
  public View getView(final int pos, View view, ViewGroup arg2) {
    ViewHolder holder = null;
    final Menu item = getItem(pos);
    if (view == null) {
      // view = inflateLayout();
      holder = new ViewHolder();
      view = LayoutInflater.from(mContext).inflate(R.layout.menu_plat_item, null);
      holder.mMenuNameTextView = (TextView) view.findViewById(R.id.tv_menu_name);
      holder.mMenuOrgNumTextView = (TextView) view.findViewById(R.id.tv_menu_num);
      holder.mMenuOrgPriceTextView = (TextView) view.findViewById(R.id.tv_menu_price);
      holder.mMenuOrgMarketPriceTextView = (TextView) view.findViewById(R.id.tv_menu_price_market);
      holder.mMenuOrgRebateTextView = (TextView) view.findViewById(R.id.tv_menu_repay_num);
      holder.mMenuPicImageView = (ImageView) view.findViewById(R.id.iv_menu);
      holder.mMenuRebateView = view.findViewById(R.id.ll_menu_repay);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }
    try {
      holder.mMenuNameTextView.setText(item.getName());
      holder.mMenuOrgNumTextView.setText(item.getSuitOrgNum() + "");
      holder.mMenuOrgPriceTextView.setText("￥"+item.getPlatformPrice() );
      holder.mMenuOrgMarketPriceTextView.setText("￥"+item.getMarketPrice());
      if (item.getRebate() != 0) {
        holder.mMenuOrgRebateTextView.setText(item.getRebate() + "");
        holder.mMenuRebateView.setVisibility(View.VISIBLE);
      } else {
        holder.mMenuRebateView.setVisibility(View.GONE);
      }
      String picPath = item.getLogo();
      if (picPath != null && !picPath.equals("")) {
        String[] result = new String[100];
        result = picPath.split("\\.");
        picPath = RemoteDataManager.SERVICE + result[0] + "_thumb." + result[1];
        ImageLoader.getInstance().displayImage(picPath, holder.mMenuPicImageView, options);
      }
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    return view;
  }

  class ViewHolder {
    public TextView mMenuNameTextView;
    public TextView mMenuOrgNumTextView;
    public TextView mMenuOrgPriceTextView;
    public TextView mMenuOrgMarketPriceTextView;
    public TextView mMenuOrgRebateTextView;
    public ImageView mMenuPicImageView;
    public View mMenuRebateView;

    void initHolder(View view) {
      mMenuNameTextView = (TextView) view.findViewById(R.id.tv_menu_name);
      mMenuOrgNumTextView = (TextView) view.findViewById(R.id.tv_menu_num);
      mMenuOrgPriceTextView = (TextView) view.findViewById(R.id.tv_menu_price);
      mMenuOrgMarketPriceTextView = (TextView) view.findViewById(R.id.tv_menu_price_market);
      mMenuOrgRebateTextView = (TextView) view.findViewById(R.id.tv_menu_repay_num);
      mMenuPicImageView = (ImageView) view.findViewById(R.id.iv_menu);
      mMenuRebateView = view.findViewById(R.id.ll_menu_repay);
    }
  }

}
