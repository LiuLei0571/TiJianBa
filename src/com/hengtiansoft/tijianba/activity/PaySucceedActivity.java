package com.hengtiansoft.tijianba.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengtiansoft.tijianba.adapter.ClosingAdapter;
import com.hengtiansoft.tijianba.net.response.Menu;
import com.hengtiansoft.tijianba.net.response.PaySuccess;
import com.hengtiansoft.tijianba.net.response.PaySuccessListener;
import com.hengtiansoft.tijianba.widget.NonScrollListView;
import com.juanliuinformation.lvningmeng.R;

/**
 * 
 * com.hengtiansoft.tijianba.activity.ClosingActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 27, 2014 11:15:48 PM
 */
public class PaySucceedActivity extends BaseActivity implements OnClickListener {
  private NonScrollListView mClosingListView;
  private ArrayList<Menu> mClosingMenus = new ArrayList<Menu>();
  private ClosingAdapter mClosingAdapter;
  private TextView mClosingNumTextView, mSummationPriceTextView, mOrderIdTextView;
  private String orderNo;
  // private int bonusMoney;
  RelativeLayout mrlConfirm;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_pay_succeed);
    orderNo = this.getIntent().getStringExtra("orderNo");
    // bonusMoney = this.getIntent().getIntExtra("bonusMoney", 0);
    setView();
    getPaySuccess(orderNo, 0, 1);
  }

  private void setView() {
    mClosingNumTextView = (TextView) findViewById(R.id.tv_num);
    mOrderIdTextView = (TextView) findViewById(R.id.tv_order_id);
    mSummationPriceTextView = (TextView) findViewById(R.id.tv_summation);
    mClosingListView = (NonScrollListView) findViewById(R.id.lv_cart);
    mrlConfirm = (RelativeLayout) this.findViewById(R.id.rl_start_subscribe);
    mrlConfirm.setOnClickListener(this);

    mClosingAdapter = new ClosingAdapter(this, mClosingMenus, false);
    mClosingListView.setAdapter(mClosingAdapter);

  }

  public void getPaySuccess(String orderNo, int payType, int payChannel) {
    remoteDataManager.paySuccessListener = new PaySuccessListener() {
      @Override
      public void onError(String errorCode, String errorMessage) {
        // TODO Auto-generated method stub

        handleError(errorMessage);
      }

      @Override
      public void onSuccess(final PaySuccess paySuccess) {
        // TODO Auto-generated method stub
        runOnUiThread(new Runnable() {

          @Override
          public void run() {
            updateUi(paySuccess);
          }
        });
      }
    };

    if (validateInternet()) {
      remoteDataManager.paySuccess(orderNo, payType, payChannel);
    }
  }

  public void updateUi(PaySuccess paySuccess) {
    mClosingMenus.clear();
    ArrayList<Menu> menuListTmp = paySuccess.getMenuList();
    Log.d("susu", menuListTmp.size() + "");
    for (int i = 0; i < menuListTmp.size(); i++) {
      Menu menu = new Menu();
      menu.setMenuName(menuListTmp.get(i).getMenuName());
      menu.setMenuTypeName(menuListTmp.get(i).getMenuTypeName());
      menu.setBuyNum(menuListTmp.get(i).getBuyNum());
      menu.setNum(menuListTmp.get(i).getNum());
      menu.setFamily(menuListTmp.get(i).isFamily());
      menu.setPlatformPrice(menuListTmp.get(i).getPlatformPrice());
      menu.setPrice(menuListTmp.get(i).getPrice());
      menu.setMarketPrice(menuListTmp.get(i).getMarketPrice());
      menu.setLogo(menuListTmp.get(i).getLogo());

      mClosingMenus.add(menu);
    }
    mClosingAdapter.notifyDataSetChanged();
    mClosingNumTextView.setText(mClosingMenus.size() + "");
    int totalPrice = paySuccess.getTotalPrice();
    mSummationPriceTextView.setText(totalPrice + "");
    mOrderIdTextView.setText(paySuccess.getOrderNo());
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.rl_start_subscribe:
        Intent intent = new Intent();
        remoteDataManager.currentPage=2;
        intent.setClass(PaySucceedActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        break;
      default:
        break;
    }
  }
}
