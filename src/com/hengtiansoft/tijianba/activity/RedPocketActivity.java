package com.hengtiansoft.tijianba.activity;

import java.util.ArrayList; 
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import com.hengtiansoft.tijianba.adapter.MyRedPocketAdapter;
import com.hengtiansoft.tijianba.adapter.RedPocketViewPagerAdpter;
import com.hengtiansoft.tijianba.model.Bonus;
import com.hengtiansoft.tijianba.model.BonusList;
import com.hengtiansoft.tijianba.model.RedPocket;
import com.hengtiansoft.tijianba.net.response.BonusListListener;
import com.hengtiansoft.tijianba.net.response.BonusUsedListListener;
import com.hengtiansoft.tijianba.net.response.BonusUsedListResult.BonusUsed;
import com.hengtiansoft.tijianba.widget.NonScrollListView;
import com.juanliuinformation.lvningmeng.R;

public class RedPocketActivity extends BaseActivity implements OnClickListener, OnPageChangeListener {
  private RadioButton mRbPocketGet, mRbPocketUse;
  private ViewPager mViewPager;
  private View mViewGet;
  private View mViewUse;
  private ArrayList<View> mViewList;
  private NonScrollListView mListGet;
  private ListView mListUse;
  private TextView mTvAllMoney;
  private ArrayList<RedPocket> getList = new ArrayList<RedPocket>();
  private ArrayList<RedPocket> usedList = new ArrayList<RedPocket>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_red_pocket);
    initFindView();
    initList();
  }

  private void initFindView() {
    mRbPocketGet = (RadioButton) findViewById(R.id.rb_myred_get);
    mRbPocketUse = (RadioButton) findViewById(R.id.rb_myred_use);
    mRbPocketGet.setOnClickListener(this);
    mRbPocketUse.setOnClickListener(this);
    mViewPager = (ViewPager) findViewById(R.id.myred_viewpager);
    LayoutInflater layoutInfater = getLayoutInflater().from(this);
    mViewGet = layoutInfater.inflate(R.layout.layout_redpocket_get, null);
    mViewUse = layoutInfater.inflate(R.layout.layout_redpocket_use, null);
    mListGet = (NonScrollListView) mViewGet.findViewById(R.id.lv_redpocket_get);
    mListUse = (ListView) mViewUse.findViewById(R.id.lv_redpocket_use);
    mTvAllMoney = (TextView) mViewGet.findViewById(R.id.tv_redpocket_money);
  }

  private void initSetView() {
    MyRedPocketAdapter adapterGet = new MyRedPocketAdapter(this, getList, 0, 0);
    mListGet.setAdapter(adapterGet);
    MyRedPocketAdapter adapterUse = new MyRedPocketAdapter(this, usedList, 0, 1);
    mListUse.setAdapter(adapterUse);
    mViewList = new ArrayList<View>();
    mViewList.add(mViewGet);
    mViewList.add(mViewUse);
    RedPocketViewPagerAdpter mViewPagerAdpter = new RedPocketViewPagerAdpter(mViewList);
    mViewPager.setAdapter(mViewPagerAdpter);
    mViewPager.setOnPageChangeListener(this);
  }
  private void initList() {
    remoteDataManager.bonusUsedListListener = new BonusUsedListListener() {
      @Override
      public void onSuccess(ArrayList<BonusUsed> bonusUsed) {
        if (bonusUsed != null && bonusUsed.size() > 0) {
          for (BonusUsed oneUsed : bonusUsed) {
            RedPocket redPocket;
            if (oneUsed.getUseTime() != null && !oneUsed.getUseTime().equals("")) {
              redPocket = new RedPocket(1, oneUsed.getBonusMoney(), oneUsed.getUseTime());
            } else {
              redPocket = new RedPocket(1, oneUsed.getBonusMoney(), "");
            }
            usedList.add(redPocket);
          }
        }
        dismissProgressDialog();
        runOnUiThread(new Runnable() {

          @Override
          public void run() {
            // TODO Auto-generated method stub
            initSetView();
          }
        });

      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        handleError(errorMessage);
      }
    };
    remoteDataManager.bonusListListener = new BonusListListener() {

      @Override
      public void onSuccess(BonusList bonusList) {
        mTvAllMoney.setText("￥"+bonusList.getBonusTotal());
        if (bonusList.getItemList() != null) {
          for (Bonus oneBonus :bonusList.getItemList()) {
            RedPocket redPocket;
            if (oneBonus.isExpired()) {
              redPocket = new RedPocket(oneBonus.getNumber(), oneBonus.getMoney(), "已过期");
            } else {
              redPocket = new RedPocket(oneBonus.getNumber(), oneBonus.getMoney(), oneBonus.getExpireTime());
            }
            getList.add(redPocket);
          }
        }
        remoteDataManager.getBonusUsedList();
      }
      @Override
      public void onError(String errorCode, String errorMessage) {
        handleError(errorMessage);
      }
    };
    if (validateInternet()) {
      showProgressDialog(getString(R.string.more_my_redpocket), "加载中..");
      remoteDataManager.getBonusList();
    }
  }


  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.rb_myred_get:
        mViewPager.arrowScroll(1);
        mRbPocketUse.setTextColor(getResources().getColor(R.color.sub_light_grey));
        mRbPocketGet.setTextColor(getResources().getColor(R.color.edt_grey));
        mViewPager.setCurrentItem(0);
        break;
      case R.id.rb_myred_use:
        mViewPager.arrowScroll(2);
        mRbPocketGet.setTextColor(getResources().getColor(R.color.sub_light_grey));
        mRbPocketUse.setTextColor(getResources().getColor(R.color.edt_grey));
        mViewPager.setCurrentItem(1);
        break;
      default:
        break;
    }
  }

  @Override
  public void onPageScrollStateChanged(int arg0) {
  }

  @Override
  public void onPageScrolled(int position, float arg1, int arg2) {
    if (position == 0) {
      mRbPocketGet.setChecked(true);
      mRbPocketGet.setTextColor(getResources().getColor(R.color.edt_grey));
      mRbPocketUse.setTextColor(getResources().getColor(R.color.sub_light_grey));
    } else {
      mRbPocketUse.setChecked(true);
      mRbPocketUse.setTextColor(getResources().getColor(R.color.edt_grey));
      mRbPocketGet.setTextColor(getResources().getColor(R.color.sub_light_grey));
    }
  }

  @Override
  public void onPageSelected(int position) {
  }
  
  
}
