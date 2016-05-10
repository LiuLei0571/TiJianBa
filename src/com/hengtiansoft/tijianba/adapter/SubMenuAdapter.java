package com.hengtiansoft.tijianba.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.Menu;
import com.juanliuinformation.lvningmeng.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * com.hengtiansoft.tijianba.adapter.SubMenuAdapter
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 1, 2014 1:08:36 PM
 */
public class SubMenuAdapter extends BaseListAdapter<Menu> {

  private Context mContext;
  private ArrayList<Menu> data;
  private SubMenuAdapterListener listener;

  public SubMenuAdapter(Context context, ArrayList<Menu> list) {
    super(context, list, R.layout.sub_menu_item);
    this.mContext = context;
    this.data = list;
  }

  public void setListener(SubMenuAdapterListener listener) {
    this.listener = listener;
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
  public View getView(int pos, View view, ViewGroup arg2) {
    ViewHolder holder = null;
    final Menu item = getItem(pos);
    final int position = pos;
    if (view == null) {
      // view = inflateLayout();
      holder = new ViewHolder();
      view = LayoutInflater.from(mContext).inflate(R.layout.sub_menu_item, null);
      holder.mMenuNameTextView = (TextView) view.findViewById(R.id.tv_name);
      holder.mCardNoTextView = (TextView) view.findViewById(R.id.tv_sub_card_no);
      holder.mMenuPriceTextView = (TextView) view.findViewById(R.id.tv_menu_price);
      holder.mGoSubcribeView = view.findViewById(R.id.rl_go_sub);
      holder.mMenuPicImageView = (ImageView) view.findViewById(R.id.iv_menu);
      holder.mFamilyImageView = (ImageView) view.findViewById(R.id.iv_family);
      holder.mMenuPricePreTextView = (TextView) view.findViewById(R.id.tv_menu_price_pre);
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
      holder.mMenuNameTextView.setText(item.getName());
      holder.mCardNoTextView.setText(item.getCardNo());
      if (item.getExamType()==2) {
        holder.mFamilyImageView.setVisibility(View.VISIBLE);
      } else {
        holder.mFamilyImageView.setVisibility(View.GONE);
      }
      if (item.getExamType()==3) {
        holder.mMenuPriceTextView.setVisibility(View.GONE);
        holder.mMenuPricePreTextView.setVisibility(View.GONE);
      } else {
        holder.mMenuPriceTextView.setText(item.getPrice() + "");
      }
      holder.mGoSubcribeView.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {
          listener.onItemClickListener(position);
        }
      });
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    return view;
  }

  class ViewHolder {
    public TextView mMenuNameTextView;
    public TextView mCardNoTextView;
    public TextView mMenuPriceTextView;
    public View mGoSubcribeView;
    public ImageView mMenuPicImageView;
    public ImageView mFamilyImageView;
    public TextView mMenuPricePreTextView;

    void initHolder(View view) {
      mMenuNameTextView = (TextView) view.findViewById(R.id.tv_name);
      mCardNoTextView = (TextView) view.findViewById(R.id.tv_sub_card_no);
      mMenuPriceTextView = (TextView) view.findViewById(R.id.tv_menu_price);
      mGoSubcribeView = view.findViewById(R.id.rl_go_sub);
      mMenuPicImageView = (ImageView) view.findViewById(R.id.iv_menu);
      mFamilyImageView = (ImageView) view.findViewById(R.id.iv_family);
      mMenuPricePreTextView = (TextView) view.findViewById(R.id.tv_menu_price_pre);
    }
  }

  public interface SubMenuAdapterListener {
    public void onItemClickListener(int position);

  }
}
