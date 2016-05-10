package com.hengtiansoft.tijianba.activity;

import java.util.ArrayList;
import java.util.Map;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.MenuDetail;
import com.hengtiansoft.tijianba.net.response.MenuDetailListener;
import com.hengtiansoft.tijianba.net.response.SubscribeVerify;
import com.hengtiansoft.tijianba.widget.NonScrollListView;
import com.juanliuinformation.lvningmeng.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * com.hengtiansoft.tijianba.activity.SubSelectMenuActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 2, 2014 4:12:53 PM
 */

public class SubSelectMenuActivity extends BaseActivity implements OnClickListener {
  private NonScrollListView mOrgListView;
  private SimpleAdapter mOrgAdapter;
  private TextView mMenuNameTextView, mMenuTypeTextView, mMenuSuitPeoTextView, mMenuSuitGenderTextView,
      mMenuContentTextView;
  private ArrayList<Map<String, Object>> mOrgData = new ArrayList<Map<String, Object>>();
  private SubscribeVerify mSubscribeVerify = new SubscribeVerify();
  private ScrollView mScrollView;
  private int orgId;
  private ImageView seperate_line;
  private RelativeLayout more_line;
  private int currentPageNum = 1;
  private String introduce;
  private int menuID;
  private boolean isFamily;
  private ImageView mMenuImageView;
  private boolean send;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_subcribe_three);
    Bundle bundle = this.getIntent().getExtras();
    if (bundle != null && bundle.containsKey("SubscribeVerifyInfo")) {
      mSubscribeVerify = (SubscribeVerify) bundle.get("SubscribeVerifyInfo");
      send = bundle.getBoolean("send");
    }
    menuID = mSubscribeVerify.getMenuId();
    isFamily = mSubscribeVerify.isFamily();
    setView();
  }

  private void setView() {
    setStepImageResource();
    mScrollView = (ScrollView) findViewById(R.id.sv_menu_detail);
    findViewById(R.id.rl_confirm).setOnClickListener(this);
    findViewById(R.id.rl_menu_view).setOnClickListener(this);
    mMenuNameTextView = (TextView) findViewById(R.id.tv_menu_name);
    mMenuTypeTextView = (TextView) findViewById(R.id.tv_menu_type);
    mMenuSuitPeoTextView = (TextView) findViewById(R.id.tv_menu_suit_people);
    mMenuSuitGenderTextView = (TextView) findViewById(R.id.tv_menu_suit_gender);
    mMenuContentTextView = (TextView) findViewById(R.id.tv_menu_content);
    mMenuImageView = (ImageView) findViewById(R.id.iv_menu_detail);
  }

  public void getMenuDetail(int menuID, boolean isFamily, int pageNo, int pageSize) {
    remoteDataManager.menuDetailListener = new MenuDetailListener() {
      @Override
      public void onError(String errorCode, String errorMessage) {
        // TODO Auto-generated method stub

        handleError(errorMessage);
      }

      @Override
      public void onSuccess(final MenuDetail menuDetail) {
        // TODO Auto-generated method stub
        dismissProgressDialog();
        runOnUiThread(new Runnable() {

          @Override
          public void run() {
            updateUi(menuDetail);
          }
        });
      }
    };
    String refreshTime = "";
    if (spSettting.getString("REFRESH_TIME_MENU_DETAIL", "") != null
        && !spSettting.getString("REFRESH_TIME_MENU_DETAIL", "").equals("")) {
      refreshTime = spSettting.getString("REFRESH_TIME_MENU_DETAIL", "");
    } else {
      refreshTime = RemoteDataManager.sdfTime.format(new java.util.Date());
    }
    if (pageNo == 1 || pageNo == 0) {
      Editor editor = spSettting.edit();
      editor.putString("REFRESH_TIME_MENU_DETAIL", RemoteDataManager.sdfTime.format(new java.util.Date()));
      editor.commit();
    }
    if (validateInternet()) {
      showProgressDialog(getString(R.string.str_menu_detail), "加载中..");
      remoteDataManager.getMenuDetail(menuID, isFamily, pageNo, pageSize, refreshTime);
    }
  }

  public void updateUi(MenuDetail menuDetail) {
    if (currentPageNum == 0 || currentPageNum == 1) {
      Log.d("debug", menuDetail.toString() + "type" + menuDetail.getDetail());
      mMenuNameTextView.setText(menuDetail.getName());
      mMenuTypeTextView.setText(String.valueOf(menuDetail.getMenuTypeName()));
      if (menuDetail.isFamily()) {
        findViewById(R.id.iv_family).setVisibility(View.VISIBLE);
      } else {
        findViewById(R.id.iv_family).setVisibility(View.GONE);
      }
      switch (menuDetail.getSuitGender()) {
        case 0:
          mMenuSuitGenderTextView.setText(getString(R.string.str_both));
          break;
        case 1:
          mMenuSuitGenderTextView.setText(getString(R.string.str_male));
          break;
        case 2:
          mMenuSuitGenderTextView.setText(getString(R.string.str_female));
          break;
        default:
          break;
      }
      ImageLoader.getInstance().displayImage(RemoteDataManager.SERVICE + menuDetail.getLogo(), mMenuImageView, options);
      mMenuContentTextView.setText(menuDetail.getNameStrong());
      introduce = menuDetail.getDetail();
      mMenuSuitPeoTextView.setText(menuDetail.getSuitObject().toString());
      mSubscribeVerify.setMenuName(mMenuNameTextView.getText().toString());
      mSubscribeVerify.setMenuType(mMenuTypeTextView.getText().toString());
      mSubscribeVerify.setMenuSuitPeo(mMenuSuitPeoTextView.getText().toString());
      mSubscribeVerify.setMenuSuitGender(mMenuSuitGenderTextView.getText().toString());
      mScrollView.smoothScrollTo(0, 0);
    }

  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent();
    switch (v.getId()) {
      case R.id.rl_menu_view:
        intent.setClass(SubSelectMenuActivity.this, WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("introduce", introduce);
        intent.putExtras(bundle);
        startActivity(intent);
        break;
      case R.id.rl_confirm:
        showProgressDialog("日历", "加载中");
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("SubscribeVerifyInfo", mSubscribeVerify);
        bundle2.putBoolean("send", send);
        intent.putExtras(bundle2);
        intent.setClass(SubSelectMenuActivity.this, SubSelectDateActivity.class);
        startActivityForResult(intent, RemoteDataManager.SUBCRIBE);
        break;
      default:
        break;
    }
  }

  public void setStepImageResource() {
    ImageView stepOneImageView = (ImageView) findViewById(R.id.iv_step_one);
    stepOneImageView.setImageResource(R.drawable.ic_lemon_passed);
    ImageView stepTwoImageView = (ImageView) findViewById(R.id.iv_step_two);
    stepTwoImageView.setImageResource(R.drawable.ic_lemon_passed);
    ImageView stepThreeImageView = (ImageView) findViewById(R.id.iv_step_three);
    stepThreeImageView.setImageResource(R.drawable.ic_lemon_selected);
    ImageView stepFourImageView = (ImageView) findViewById(R.id.iv_step_four);
    stepFourImageView.setImageResource(R.drawable.ic_lemon_unselected);
    ImageView stepFiveImageView = (ImageView) findViewById(R.id.iv_step_five);
    stepFiveImageView.setImageResource(R.drawable.ic_lemon_unselected);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (resultCode) {
      case RESULT_OK:
        dismissProgressDialog();
        setResult(RESULT_OK);
        finish();
        break;
      default:
        break;
    }
  }

  @Override
  protected void onResume() {
    // TODO Auto-generated method stub
    super.onResume();
    currentPageNum = 1;
    mOrgData.clear();
    getMenuDetail(menuID, isFamily, currentPageNum, 5);
  }
}
