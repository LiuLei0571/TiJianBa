package com.hengtiansoft.tijianba.activity;

import java.util.Calendar;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.juanliuinformation.lvningmeng.R;

public class EveryDaySignInActivity extends BaseActivity implements OnClickListener {
  private RelativeLayout mRlytBtn;
  private TextView mTvMyRedPocket;
  private TextView mBtnName, mTvTextSecond, mTvTextThrid, mTvDayCount, mTvDay;
  private ImageView mBtnImg;
  private ImageView mBackgroundImg;
  private static final String REDPOCKET = "reaPocketState";
  private static final String TADAY = "mTaday";
  private boolean mRedPocketState = true;
  private boolean mSignState = false;
  private String mTadayData;
  private Editor mEditor;
  private String curDate;
  private Calendar mCalendar;
  private LinearLayout mRedPocketLayout;
  private boolean isFromQuesiton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_every_day_sign_in);
    isFromQuesiton = getIntent().getBooleanExtra("isFromQuestion", false);
    initView();
  }

  private void initView() {
    mRlytBtn = (RelativeLayout) findViewById(R.id.rylt_every_day_signin_btn);
    mTvTextSecond = (TextView) findViewById(R.id.tv_everyday_signin_second);
    mTvTextThrid = (TextView) findViewById(R.id.tv_everyday_signin_thrid);
    mTvDayCount = (TextView) findViewById(R.id.tv_everyday_signin_daycount);
    mTvDay = (TextView) findViewById(R.id.tv_everyday_signin_day);
    mBtnName = (TextView) findViewById(R.id.tv_every_day_signin_btn_text);
    mBtnImg = (ImageView) findViewById(R.id.img_every_day_signin_btn);
    mBackgroundImg = (ImageView) findViewById(R.id.img_every_day_signin_redpocket_big);
    mTvMyRedPocket = (TextView) findViewById(R.id.tv_everyday_signin_my_redpocket);
    mRedPocketLayout = (LinearLayout) findViewById(R.id.ll_red_pocket);
    mTvMyRedPocket.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    mCalendar = Calendar.getInstance();
    curDate = RemoteDataManager.sdfDate.format(mCalendar.getTime());
    getRedPocketState(spSettting);
    mRlytBtn.setOnClickListener(this);
    if (isFromQuesiton) {
      mBackgroundImg.setImageResource(R.drawable.red_pocket_success);
      mBtnImg.setVisibility(View.GONE);
      mBtnName.setVisibility(View.GONE);
      mRedPocketLayout.setVisibility(View.VISIBLE);
      findViewById(R.id.llyt_every_day_signin_notice).setVisibility(View.GONE);
    }
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.rylt_every_day_signin_btn:
        String btnName = mBtnName.getText().toString();
        if (getString(R.string.every_day_signin_btn_text).equals(btnName)) {
          changeView(mRedPocketState);
          mEditor = spSettting.edit();
          mEditor.putString(TADAY, curDate);
          mEditor.putBoolean(REDPOCKET, true);
          mEditor.commit();
        } else if (getString(R.string.red_pocket_end).equals(btnName)) {
          mBackgroundImg.setImageResource(R.drawable.red_pocket_success);
          mBtnImg.setVisibility(View.GONE);
          mBtnName.setVisibility(View.GONE);
          mRedPocketLayout.setVisibility(View.VISIBLE);
          findViewById(R.id.llyt_every_day_signin_notice).setVisibility(View.GONE);
        }
        break;
      default:
        break;
    }
  }

  private void changeView(boolean flag) {
    if (flag) {
      mTvTextSecond.setText(getResources().getString(R.string.tv_everyday_after_signin_second));
      mTvTextThrid.setText(getResources().getString(R.string.str_ge));
      mTvDay.setText(getString(R.string.red_pocket_default_message));
      mBtnName.setText(getResources().getString(R.string.red_pocket_end));
      mBtnImg.setImageResource(R.drawable.btn_upload_enable);
      mBackgroundImg.setImageResource(R.drawable.red_pocket_default);
    } else {
      mTvDayCount.setText("4");
      mTvTextSecond.setText(getResources().getString(R.string.tv_everyday_after_signin_second));
      mBtnName.setText(getResources().getString(R.string.every_day_after_signin_btn_text));
      mBtnImg.setImageResource(R.drawable.btn_upload);
    }
  }

  private void getRedPocketState(SharedPreferences mSp) {
    mTadayData = mSp.getString(TADAY, null);
    mSignState = mSp.getBoolean(REDPOCKET, false);
    if (mSignState && (curDate.equals(mTadayData))) {
      changeView(mRedPocketState);
    }
  }

}
