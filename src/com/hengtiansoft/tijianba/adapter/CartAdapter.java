package com.hengtiansoft.tijianba.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.CartDetail;
import com.juanliuinformation.lvningmeng.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * com.hengtiansoft.tijianba.adapter.CartAdapter
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 27, 2014 12:49:19 PM
 */
public class CartAdapter extends BaseListAdapter<CartDetail> {

  private Context mContext;
  private ArrayList<CartDetail> data;
  private CartAdapterListener listener;

  public void setListener(CartAdapterListener listener) {
    this.listener = listener;
  }

  public CartAdapter(Context context, ArrayList<CartDetail> list) {
    super(context, list, R.layout.cart_item);
    this.mContext = context;
    this.data = list;
  }

  @Override
  public int getCount() {
    return data.size();
  }

  @Override
  public CartDetail getItem(int position) {
    return data.get(position);
  }

  @Override
  public View getView(final int pos, View view, ViewGroup arg2) {
    ViewHolder holder = null;
    final CartDetail item = getItem(pos);
    final int maxBuy;
    final boolean isRestrict;

    if (view == null) {
      // view = inflateLayout();
      holder = new ViewHolder();
      view = LayoutInflater.from(mContext).inflate(R.layout.cart_item, null);
      holder.mMenuNameTextView = (TextView) view.findViewById(R.id.tv_name);
      holder.mMenuTypeTextView = (TextView) view.findViewById(R.id.tv_type);
      holder.mMenuBuyNumTextView = (TextView) view.findViewById(R.id.tv_cart_num);
      holder.mEditBuyNumTextView = (TextView) view.findViewById(R.id.tv_edit_num);
      holder.mMenuPriceTextView = (TextView) view.findViewById(R.id.tv_menu_price);
      holder.mMenuMarketPriceTextView = (TextView) view.findViewById(R.id.tv_menu_price_market);
      holder.mMenuPicImageView = (ImageView) view.findViewById(R.id.iv_menu);
      holder.mMenuFamilyImageView = (ImageView) view.findViewById(R.id.iv_cart_family);
      holder.mEditNum = view.findViewById(R.id.ll_edit_num);
      holder.mNumAddCheckBox = (CheckBox) view.findViewById(R.id.chb_cart_add);
      holder.mNumDelCheckBox = (CheckBox) view.findViewById(R.id.chb_cart_del);
      holder.mItemCheckBox = (CheckBox) view.findViewById(R.id.chb_cart);
      holder.mMenuDeleteTextView = (TextView) view.findViewById(R.id.tv_delete);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }
    final ViewHolder viewHolder = holder;
    try {
      String picPath = item.getLogo();
      String[] result = new String[100];
      result = picPath.split("\\.");
      picPath = RemoteDataManager.SERVICE + result[0] + "_thumb." + result[1];
      ImageLoader.getInstance().displayImage(picPath, holder.mMenuPicImageView, options);
      maxBuy = item.getTotalNum() - item.getPurchaseNum();
      if (item.getTotalNum() != 0) {
        isRestrict = true;
      } else {
        isRestrict = false;
      }
      holder.mItemCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
          if (isChecked) {
            item.setSelect(true);
          } else {
            item.setSelect(false);
          }
          notifyDataSetChanged();
          listener.onCartAdapterListener(0, pos);
        }
      });
      holder.mNumDelCheckBox.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {
          if (item.getNumber() != 0) {
            item.setNumber(item.getNumber() - 1);
          } else {
            viewHolder.mNumDelCheckBox.setChecked(false);
          }
          notifyDataSetChanged();
          listener.onCartAdapterListener(0, pos);
        }
      });
      holder.mNumAddCheckBox.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {
          if ((item.getNumber() != maxBuy && isRestrict) || !isRestrict) {
            item.setNumber(item.getNumber() + 1);
          } else {
            viewHolder.mNumDelCheckBox.setChecked(false);
          }
          notifyDataSetChanged();
          listener.onCartAdapterListener(0, pos);
        }

      });
      holder.mMenuDeleteTextView.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {
          listener.onCartAdapterListener(1, pos);
        }
      });
      if (item.isEdit()) {
        holder.mEditNum.setVisibility(View.VISIBLE);
        holder.mEditBuyNumTextView.setText(item.getNumber() + "");
        holder.mMenuNameTextView.setVisibility(View.GONE);
        holder.mMenuTypeTextView.setVisibility(View.GONE);
        if (item.isSelect()) {
          holder.mItemCheckBox.setChecked(true);
        } else {
          holder.mItemCheckBox.setChecked(false);
        }
        if (item.getNumber() == 0) {
          holder.mNumDelCheckBox.setChecked(false);
          holder.mNumDelCheckBox.setEnabled(false);
        } else {
          holder.mNumDelCheckBox.setEnabled(true);
          holder.mNumDelCheckBox.setChecked(true);
        }
        if (item.getNumber() == maxBuy && isRestrict) {
          holder.mNumAddCheckBox.setChecked(false);
          holder.mNumAddCheckBox.setEnabled(false);
        } else {
          holder.mNumAddCheckBox.setEnabled(true);
          holder.mNumAddCheckBox.setChecked(true);
        }
      } else {
        if (item.isSelect()) {
          holder.mItemCheckBox.setChecked(true);
        } else {
          holder.mItemCheckBox.setChecked(false);
        }
        holder.mMenuNameTextView.setVisibility(View.VISIBLE);
        holder.mMenuTypeTextView.setVisibility(View.VISIBLE);
        holder.mEditNum.setVisibility(View.GONE);
        holder.mMenuNameTextView.setText(item.getName());
        holder.mMenuTypeTextView.setText(item.getMenuTypeName());
        holder.mMenuMarketPriceTextView.setText("￥"+item.getMarketPrice());
        holder.mMenuBuyNumTextView.setText("X" + item.getNumber());
        if (item.isFamily()) {
          holder.mMenuFamilyImageView.setVisibility(View.VISIBLE);
          holder.mMenuPriceTextView.setText("￥"+item.getFamilyPrice());
        } else {
          holder.mMenuFamilyImageView.setVisibility(View.GONE);
          holder.mMenuPriceTextView.setText("￥"+item.getPlatformPrice());
        }
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
    public TextView mEditBuyNumTextView;
    public TextView mMenuPriceTextView;
    public TextView mMenuMarketPriceTextView;
    public TextView mMenuDeleteTextView;
    public ImageView mMenuPicImageView;
    public ImageView mMenuFamilyImageView;
    public View mEditNum;
    public CheckBox mNumAddCheckBox;
    public CheckBox mNumDelCheckBox;
    public CheckBox mItemCheckBox;

    void initHolder(View view) {
      mMenuNameTextView = (TextView) view.findViewById(R.id.tv_name);
      mMenuTypeTextView = (TextView) view.findViewById(R.id.tv_type);
      mMenuBuyNumTextView = (TextView) view.findViewById(R.id.tv_cart_num);
      mEditBuyNumTextView = (TextView) view.findViewById(R.id.tv_edit_num);
      mMenuPriceTextView = (TextView) view.findViewById(R.id.tv_menu_price);
      mMenuMarketPriceTextView = (TextView) view.findViewById(R.id.tv_menu_price_market);
      mMenuPicImageView = (ImageView) view.findViewById(R.id.iv_menu);
      mMenuFamilyImageView = (ImageView) view.findViewById(R.id.iv_cart_family);
      mEditNum = view.findViewById(R.id.ll_edit_num);
      mNumAddCheckBox = (CheckBox) view.findViewById(R.id.chb_cart_add);
      mNumDelCheckBox = (CheckBox) view.findViewById(R.id.chb_cart_del);
      mItemCheckBox = (CheckBox) view.findViewById(R.id.chb_cart);
    }
  }

  public interface CartAdapterListener {
    public void onCartAdapterListener(int type, int position);

  }
}
