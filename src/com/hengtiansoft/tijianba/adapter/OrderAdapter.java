package com.hengtiansoft.tijianba.adapter;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengtiansoft.tijianba.activity.OrderActivity;
import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.Menu;
import com.hengtiansoft.tijianba.net.response.Order;
import com.juanliuinformation.lvningmeng.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class OrderAdapter extends BaseListAdapter<Order> {
  private Context mContext;
  private ArrayList<Order> mOrderList;

  public OrderAdapter(Context context, ArrayList<Order> list) {
    super(context, list, R.layout.layout_order_list);
    this.mContext = context;
    this.mOrderList = list;
  }

  @Override
  public int getCount() {
    int count = mOrderList.size();
    return count;
  }

  @Override
  public View getView(final int position, View view, ViewGroup parent) {
    Order order = mOrderList.get(position);
    ViewHolder holder = null;
    if (view == null) {
      holder = new ViewHolder();
      view = LayoutInflater.from(mContext).inflate(R.layout.layout_order_list, null);
      holder.tvNumber = (TextView) view.findViewById(R.id.tv_order_nummber);
      holder.tvState = (TextView) view.findViewById(R.id.tv_order_state);
      holder.tvPayWay = (TextView) view.findViewById(R.id.tv_order_way);
      // holder.btnPay = (RelativeLayout)
      // view.findViewById(R.id.rlyt_order_pay);
      holder.btnCancelOrder = (RelativeLayout) view.findViewById(R.id.rl_cancel_order);
      holder.llytView = (LinearLayout) view.findViewById(R.id.llyt_order);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }

    try {
      holder.tvNumber.setText(order.getOrderNo());
      int statu = order.getStatus();
      holder.tvState.setText(RemoteDataManager.reserveStatus.get(statu));
      if (statu == 0) {
        // holder.btnPay.setVisibility(View.VISIBLE);
        holder.btnCancelOrder.setVisibility(View.VISIBLE);
      } else {
        // holder.btnPay.setVisibility(View.INVISIBLE);
        holder.btnCancelOrder.setVisibility(View.INVISIBLE);
      }
      int type = order.getPayType();
      if (type == 0) {
        holder.tvPayWay.setText("支付宝");
      } else {
      }
      ArrayList<Menu> menuList = new ArrayList<Menu>();
      menuList = order.getMenuList();
      holder.llytView.removeAllViews();
      for (int i = 0; i < menuList.size(); i++) {
        Menu item = menuList.get(i);
        View menuView = LayoutInflater.from(mContext).inflate(R.layout.layout_order_item_list, null);
        TextView tvName = (TextView) menuView.findViewById(R.id.tv_order_item_name);
        TextView tvType = (TextView) menuView.findViewById(R.id.tv_order_item_type);
        TextView tvPrice = (TextView) menuView.findViewById(R.id.tv_order_item_price);
        TextView tvNum = (TextView) menuView.findViewById(R.id.tv_order_item_number);
        ImageView imgLogo = (ImageView) menuView.findViewById(R.id.img_order_item);
        String picPath = item.getLogo();
        String[] result = new String[100];
        result = picPath.split("\\.");
        picPath = RemoteDataManager.SERVICE + result[0] + "_thumb." + result[1];
        ImageLoader.getInstance().displayImage(picPath, imgLogo, options);
        tvName.setText(item.getName());
        tvType.setText(item.getMenuTypeName());
        tvPrice.setText("￥"+item.getPrice());
        tvNum.setText("×" + item.getBuyNum());
        holder.llytView.addView(menuView);
      }
      holder.btnCancelOrder.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {
          // TODO Auto-generated method stub
          onBack(position);
        }

      });

    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    return view;
  }

  class ViewHolder {
    public TextView tvNumber;
    public TextView tvState;
    public TextView tvPayWay;
    public LinearLayout llytView;
    // public RelativeLayout btnPay;
    public RelativeLayout btnCancelOrder;
  }


  public void onBack(final int position) {
    AlertDialog ad = new AlertDialog.Builder(mContext).setMessage("是否確定取消订单？")
        .setPositiveButton(mContext.getString(R.string.str_yes), new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
            int orderId = mOrderList.get(position).getId();
            ((OrderActivity) mContext).cancelOrder(orderId, position);
            dialog.dismiss();
          }
        }).setNegativeButton(mContext.getString(R.string.str_no), new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
            dialog.dismiss();
          }
        }).show();
    Button positiveButton = ad.getButton(DialogInterface.BUTTON_POSITIVE);
    positiveButton.setTextColor(mContext.getResources().getColor(R.color.org_orange));
    positiveButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
    Button negativeButton = ad.getButton(DialogInterface.BUTTON_NEGATIVE);
    negativeButton.setTextColor(mContext.getResources().getColor(R.color.cart_grey));
    negativeButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
  }

}
