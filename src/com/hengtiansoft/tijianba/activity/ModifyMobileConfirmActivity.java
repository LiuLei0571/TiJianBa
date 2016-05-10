package com.hengtiansoft.tijianba.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengtiansoft.tijianba.net.response.ReturnFromServerListener;
import com.juanliuinformation.lvningmeng.R;

public class ModifyMobileConfirmActivity extends BaseActivity implements OnClickListener {

  private EditText mPhoneText;
  private EditText mCodeText;
  private TextView mSendBtn;
  private RelativeLayout mConfirmBtn;
  private ImageView mProgressImg;
  private LinearLayout mBodyLayout;
  private RelativeLayout mSuccessLayout;
  private String mNewPhone;
  public final static int MSG_UPDATE_TIME = 1;
  public final static int MSG_UPDATE_ALERT = 2;

  private Handler mTimeHandler = new Handler(new Handler.Callback() {
    @Override
    public boolean handleMessage(Message msg) {
      switch (msg.what) {
        case MSG_UPDATE_TIME:
          int time = (Integer) msg.obj;
          mSendBtn.setText(getString(R.string.str_send_code_again) + "(" + time + ")");
          break;
        case MSG_UPDATE_ALERT:
          mSendBtn.setBackgroundResource(R.drawable.ic_modify_send_green);
          mSendBtn.setEnabled(true);
          mSendBtn.setText(getString(R.string.str_send_code_again));
          break;
      }
      return false;
    }
  });

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_modify_mobile_confirm);
    initView();
  }

  private void initView() {
    mPhoneText = (EditText) findViewById(R.id.et_confirm_mobile);
    mNewPhone = getIntent().getStringExtra("phone");
    // mNewPhone = mNewPhone.substring(0, 3) + "*****" +
    // mNewPhone.substring(8);
    mPhoneText.setText(mNewPhone);
    mCodeText = (EditText) findViewById(R.id.et_confirm_code);
    mSendBtn = (TextView) findViewById(R.id.tv_confirm_send_code);
    mConfirmBtn = (RelativeLayout) findViewById(R.id.rl_modify_confirm);
    mProgressImg = (ImageView) findViewById(R.id.iv_confirm_progress);
    mBodyLayout = (LinearLayout) findViewById(R.id.ll_confirm_body);
    mSuccessLayout = (RelativeLayout) findViewById(R.id.ll_confirm_success);
    mSendBtn.setOnClickListener(this);
    mConfirmBtn.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.tv_confirm_send_code:
        mSendBtn.setBackgroundResource(R.drawable.ic_modify_send_grey);
        mSendBtn.setEnabled(false);
        countTime();
        getVerifyCode(mNewPhone);
        break;
      case R.id.rl_modify_confirm:
        if ("".equals(mCodeText.getText().toString())) {
          mCodeText.setError(getString(R.string.err_msg_verify_code));
        } else {
          modifyPhoneNum(mNewPhone, mCodeText.getText().toString());

        }
        break;
      default:
        break;
    }
  }

  private void modifyPhoneNum(final String mobile, final String verifyCode) {
    remoteDataManager.loginListener = new ReturnFromServerListener() {

      @Override
      public void onSuccess(String message) {
        dismissProgressDialog();
        handleSuccess(message);
        startActivity(new Intent(ModifyMobileConfirmActivity.this, MoreSettingActivity.class));
        finish();
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        dismissProgressDialog();
        handleError(errorMessage + "" + getString(R.string.str_login_again));
        startActivity(new Intent(ModifyMobileConfirmActivity.this, LoginActivity.class));
        finish();
      }
    };
    remoteDataManager.modifyPhoneNumListener = new ReturnFromServerListener() {

      @Override
      public void onSuccess(String message) {
        handleSuccess(message);
        runOnUiThread(new Runnable() {
          public void run() {
            mProgressImg.setVisibility(View.GONE);
            mBodyLayout.setVisibility(View.GONE);
            mConfirmBtn.setVisibility(View.GONE);
            mSuccessLayout.setVisibility(View.VISIBLE);
            SharedPreferences sp = getSharedPreferences("userInfo", 0);
            Editor editor = sp.edit();
            editor.putBoolean("ISCHECK", true);
            editor.putString("USER_NAME", mobile);
            editor.commit();
          }
        });

      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        handleError(errorMessage);

      }
    };
    if (validateInternet()) {
      showProgressDialog("修改绑定手机号", "加载中..");
      remoteDataManager.modifyPhoneNum(mobile, verifyCode);
    }
  }

  private void getVerifyCode(String mobile) {

    remoteDataManager.getVerifyCodeListener = new ReturnFromServerListener() {

      @Override
      public void onSuccess(String message) {
        dismissProgressDialog();
        handleSuccess(message);
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        dismissProgressDialog();
        handleError(errorMessage);
      }
    };
    if (validateInternet()) {
      showProgressDialog("获取手机验证码", "获取中..");
      remoteDataManager.getVerifyCode(mobile);
    }
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    startActivity(new Intent(ModifyMobileConfirmActivity.this, LoginActivity.class));
    finish();
  }

  private void countTime() {
    new Thread() {
      public void run() {
        for (int i = 60; i > 0; i--) {
          Message msgTime = new Message();
          msgTime.what = MSG_UPDATE_TIME;
          msgTime.obj = i;
          mTimeHandler.sendMessage(msgTime);
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        Message msgAlery = new Message();
        msgAlery.what = MSG_UPDATE_ALERT;
        mTimeHandler.sendMessage(msgAlery);
      };
    }.start();
  }
}
