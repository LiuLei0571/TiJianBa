package com.hengtiansoft.tijianba.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hengtiansoft.tijianba.adapter.OrderDetailAdapter;
import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.Menu;
import com.hengtiansoft.tijianba.net.response.OrderDetail;
import com.hengtiansoft.tijianba.net.response.OrderDetailListener;
import com.hengtiansoft.tijianba.widget.NonScrollListView;
import com.juanliuinformation.lvningmeng.R;

/**
 * 
 * com.hengtiansoft.tijianba.activity.MenuDetailActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 26, 2014 1:12:04 PM
 */
public class OrderDetailActivity extends BaseActivity implements OnClickListener {
  private int orderId;
  private NonScrollListView orderListView;
  private OrderDetailAdapter mOrderDetailAdapter;
  private TextView mStatusView, mPayTypeView, mOrderIdView, mTimeView, mTotalView, mPersonNameView, mContractPhoneView,
      mContractEmailView, mRemarkView;
  private ImageView mPayImageView;
  private TextView mPayTextView;
  private RelativeLayout mTimeLayout;
  private ArrayList<Menu> mMenuData = new ArrayList<Menu>();
  private ScrollView mScrollView;
  // private TextView mBonusTextView;
  // private CheckBox mBonusCheckBox;
  private OrderDetail orderDetail;
  private boolean paySuccess;

  /*
   * private int mShouldPay;
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_order_detail);
    orderId = getIntent().getIntExtra("orderID", 0);
    Log.d("debug", String.valueOf(orderId));
    setView();

  }

  private void setView() {
    // mBonusCheckBox = (CheckBox) findViewById(R.id.chb_bonus);
    // mBonusCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
    //
    // @Override
    // public void onCheckedChanged(CompoundButton buttonView, boolean
    // isChecked) {
    // if (isChecked) {
    // mTotalView.setText((mShouldPay - orderDetail.getBonusMoneyCanUse()) +
    // "");
    // } else {
    // mTotalView.setText(mShouldPay + "");
    // }
    // }
    // });
    mScrollView = (ScrollView) findViewById(R.id.sv_org);
    findViewById(R.id.rl_pay).setOnClickListener(this);
    // mBonusTextView = (TextView) findViewById(R.id.tv_available_bonus);
    mStatusView = (TextView) findViewById(R.id.tv_status);
    mPayTypeView = (TextView) findViewById(R.id.pay_type);
    mPayImageView = (ImageView) findViewById(R.id.iv_pay);
    mPayTextView = (TextView) findViewById(R.id.tv_pay);
    mTimeLayout = (RelativeLayout) findViewById(R.id.rl_time);
    // mOrgAddressTextView = (TextView) findViewById(R.id.tv_org_address);
    mOrderIdView = (TextView) findViewById(R.id.order_id);
    mTimeView = (TextView) findViewById(R.id.time);
    mTotalView = (TextView) findViewById(R.id.total);
    mContractPhoneView = (TextView) findViewById(R.id.contract_phone);
    mContractEmailView = (TextView) findViewById(R.id.contract_email);
    mPersonNameView = (TextView) findViewById(R.id.person_name);
    mRemarkView = (TextView) findViewById(R.id.remark);
    orderListView = (NonScrollListView) findViewById(R.id.list_order);
    orderListView.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(OrderDetailActivity.this, MenuDetailActivity.class);
        intent.putExtra("menuID", (Integer) mMenuData.get(position).getMenuId());
        intent.putExtra("isFamily", (boolean) mMenuData.get(position).isFamily());
        startActivity(intent);
      }
    });
    mOrderDetailAdapter = new OrderDetailAdapter(this, mMenuData);
    orderListView.setAdapter(mOrderDetailAdapter);

  }

  public void updateUi() {

    // mShouldPay = orderDetail.getTotalPrice();
    // if (orderDetail.getBonusMoneyCanUse() != 0) {
    // findViewById(R.id.rl_available_bonus).setVisibility(View.VISIBLE);
    // mBonusTextView.setText(getString(R.string.str_available_bonus) +
    // orderDetail.getBonusMoneyCanUse()
    // + getString(R.string.str_yuan) + getString(R.string.str_bonus));
    // } else {
    // findViewById(R.id.rl_available_bonus).setVisibility(View.GONE);
    // }
    mStatusView.setText(RemoteDataManager.reserveStatus.get(orderDetail.getStatus()));
    mPayTypeView.setText(RemoteDataManager.payType.get(orderDetail.getPayType()));
    mOrderIdView.setText(orderDetail.getOrderNo());
    mTimeView.setText(orderDetail.getCreateTime());
    mTotalView.setText("￥"+orderDetail.getPayMoney());
    mContractPhoneView.setText(orderDetail.getOrderUserMobile());
    mContractEmailView.setText(orderDetail.getOrderUserEmail());
    mRemarkView.setText(orderDetail.getOrderUserRemark());
    mPersonNameView.setText(orderDetail.getOrderUserName());
    if (orderDetail.getStatus() != 0) {
      mPayImageView.setImageResource(R.drawable.btn_order_grey);
      mPayTextView.setText(RemoteDataManager.reserveStatus.get(orderDetail.getStatus()));
      mTimeLayout.setVisibility(View.VISIBLE);
      findViewById(R.id.rl_pay).setEnabled(false);
    } else if (!paySuccess) {
      findViewById(R.id.rl_pay).setEnabled(true);
    }
    mScrollView.smoothScrollTo(0, 0);
    ArrayList<Menu> menuList = orderDetail.getMenuList();
    for (Menu menu : menuList) {
      Menu menuTmp = new Menu();
      menuTmp.setMenuName(menu.getMenuName());
      menuTmp.setMenuId(menu.getMenuId());
      menuTmp.setMenuTypeName(menu.getMenuTypeName());
      menuTmp.setPrice(menu.getPrice());
      menuTmp.setBuyNum(menu.getBuyNum());
      menuTmp.setLogo(menu.getLogo());
      menuTmp.setMarketPrice(menu.getMarketPrice());
      menuTmp.setStatus(menu.getStatus());
      mMenuData.add(menuTmp);
    }

    mOrderDetailAdapter.notifyDataSetChanged();
  }

  public void getOrderDetail(int orderId) {
    remoteDataManager.orderDetailListener = new OrderDetailListener() {

      @Override
      public void onError(String errorCode, String errorMessage) {
        // TODO Auto-generated method stub

        handleError(errorMessage);
      }

      @Override
      public void onSuccess(final OrderDetail orderDetail) {
        // TODO Auto-generated method stub
        dismissProgressDialog();
        runOnUiThread(new Runnable() {

          @Override
          public void run() {
            OrderDetailActivity.this.orderDetail = orderDetail;
            updateUi();
          }
        });
      }
    };

    if (validateInternet()) {
      showProgressDialog(getString(R.string.str_org_detail), "加载中..");
      remoteDataManager.getOrderDetail(orderId);
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.rl_pay:
        findViewById(R.id.rl_pay).setEnabled(false);
        Intent intent = new Intent(OrderDetailActivity.this, PayActivity.class);
        intent.putExtra("url", orderDetail.getVerifyUrl());
        intent.putExtra("description", orderDetail.getMenuList().get(0).getMenuName());
        intent.putExtra("money", orderDetail.getPayMoney());
        intent.putExtra("orderNo", orderDetail.getOrderNo());
        // if (mBonusCheckBox.isChecked() == true) {
        // intent.putExtra("bonusMoney", orderDetail.getBonusMoneyCanUse());
        // } else {
        // intent.putExtra("bonusMoney", 0);
        // }
        startActivity(intent);
        break;
      default:
        break;
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  @Override
  protected void onResume() {
    super.onResume();
    mMenuData.clear();
    paySuccess = getIntent().getBooleanExtra("paySuccess", false);
    getOrderDetail(orderId);
  }
}
