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

/**
 * 
 * com.hengtiansoft.tijianba.adapter.ClosingAdapter
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 27, 2014 11:18:22 PM
 */
public class ClosingAdapter extends BaseListAdapter<Menu> {

  private Context mContext;
  private ArrayList<Menu> data;
  private boolean isClosing;

  public ClosingAdapter(Context context, ArrayList<Menu> list, boolean isClosing) {
    super(context, list, R.layout.closing_item);
    this.mContext = context;
    this.data = list;
    this.isClosing = isClosing;
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
      view = LayoutInflater.from(mContext).inflate(R.layout.closing_item, null);
      holder.mMenuNameTextView = (TextView) view.findViewById(R.id.tv_name);
      holder.mMenuTypeTextView = (TextView) view.findViewById(R.id.tv_type);
      holder.mMenuBuyNumTextView = (TextView) view.findViewById(R.id.tv_cart_num);
      holder.mMenuPriceTextView = (TextView) view.findViewById(R.id.tv_menu_price);
      holder.mMenuMarketPriceTextView = (TextView) view.findViewById(R.id.tv_menu_price_market);
      holder.mMenuPicImageView = (ImageView) view.findViewById(R.id.iv_menu);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }
    try {
      String picPath = item.getLogo();
      String[] result = new String[100];
      result = picPath.split("\\.");
      picPath = RemoteDataManager.SERVICE + result[0] + "_thumb." + result[1];
      ImageLoader.getInstance().displayImage(picPath, holder.mMenuPicImageView, options);
      holder.mMenuNameTextView.setText(item.getMenuName());
      holder.mMenuTypeTextView.setText(item.getMenuTypeName());
      holder.mMenuMarketPriceTextView.setText("￥"+item.getMarketPrice());
      holder.mMenuPriceTextView.setText("￥"+item.getPrice());
      holder.mMenuMarketPriceTextView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
      holder.mMenuBuyNumTextView.setText("X" + item.getBuyNum());
      if (!isClosing) {
        holder.mMenuPriceTextView.setTextColor(mContext.getResources().getColor(R.color.edt_grey));
      }
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    return view;
  }


  class ViewHolder {
    public TextView mMenuNameTextView;
    public TextView mMenuTypeTextView;
    public TextView mMenuBuyNumTextView;
    public TextView mMenuPriceTextView;
    public TextView mMenuMarketPriceTextView;
    public ImageView mMenuPicImageView;

    void initHolder(View view) {
      mMenuNameTextView = (TextView) view.findViewById(R.id.tv_name);
      mMenuTypeTextView = (TextView) view.findViewById(R.id.tv_type);
      mMenuBuyNumTextView = (TextView) view.findViewById(R.id.tv_cart_num);
      mMenuPriceTextView = (TextView) view.findViewById(R.id.tv_menu_price);
      mMenuMarketPriceTextView = (TextView) view.findViewById(R.id.tv_menu_price_market);
      mMenuPicImageView = (ImageView) view.findViewById(R.id.iv_menu);
    }
  }
}
