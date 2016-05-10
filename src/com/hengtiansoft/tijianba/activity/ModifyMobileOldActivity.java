package com.hengtiansoft.tijianba.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.ReturnFromServerListener;
import com.juanliuinformation.lvningmeng.R;

public class ModifyMobileOldActivity extends BaseActivity implements OnClickListener {

  private EditText mMobileText;
  private EditText mCodeText;
  private TextView mSendBtn;
  private RelativeLayout mModifyBtn;
  private boolean isChanged = false;
  private StringBuffer mMobile = new StringBuffer();
  public final static int MSG_UPDATE_TIME = 1;
  public final static int MSG_UPDATE_ALERT = 2;
  public final static int MSG_SEND_CODE = 3;
  public final static int MSG_CODE_ERROR = 4;
  private String all;
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
        case MSG_SEND_CODE:
          mSendBtn.setBackgroundResource(R.drawable.ic_modify_send_grey);
          mSendBtn.setEnabled(false);
          countTime();
          break;
        case MSG_CODE_ERROR:
          mCodeText.setError(getString(R.string.err_msg_verify_code));
          break;
      }
      return false;
    }
  });

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_modify_mobile_old);
    initView();
  }

  private void initView() {
    mMobileText = (EditText) findViewById(R.id.et_modify_mobile);
    mCodeText = (EditText) findViewById(R.id.et_modify_code);
    mSendBtn = (TextView) findViewById(R.id.tv_modify_code);
    mModifyBtn = (RelativeLayout) findViewById(R.id.rl_modify_modify);
    mSendBtn.setOnClickListener(this);
    mModifyBtn.setOnClickListener(this);
    all = getSharedPreferences("userInfo", 0).getString("USER_NAME", "").toString();
    String top = all.substring(0, 3);
    String middle = all.substring(3, 8).replaceAll("\\d", "*");
    String tail = all.substring(8, 11);
    mMobileText.setText(top + middle + tail);
    mMobileText.setFocusable(false);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.tv_modify_code:
        if ("".equals(mMobileText.getText().toString())) {
          mMobileText.setError(getString(R.string.error_mobile_is_null));
        } else if (mMobileText.length() != 11) {
          mMobileText.setError(getString(R.string.error_mobile_not_standard));
        } else {

          Message msgSendCode = new Message();
          msgSendCode.what = MSG_SEND_CODE;
          mTimeHandler.sendMessage(msgSendCode);
          RemoteDataManager rdmGetModifyCode = RemoteDataManager.getInstance();
          rdmGetModifyCode.getModifyVerifyCodeListener = new ReturnFromServerListener() {

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
            showProgressDialog("获取验证码", "获取中..");
            rdmGetModifyCode.getModifyVerifyCode(all);
          }
        }
        break;

      case R.id.rl_modify_modify:
        if ("".equals(mMobileText.getText().toString()) || "".equals(mCodeText.getText().toString())) {
          Toast.makeText(ModifyMobileOldActivity.this, getString(R.string.err_information_absence), Toast.LENGTH_LONG)
              .show();
        } else {
          String mobile = all;
          String verifyCode = mCodeText.getText().toString();
          verifyOldMobile(mobile, verifyCode);
        }
        break;
      default:
        break;
    }
  }

  private void verifyOldMobile(String mobile, String verifyCode) {

    remoteDataManager.verifyOldMobileListener = new ReturnFromServerListener() {

      @Override
      public void onSuccess(String message) {
        dismissProgressDialog();
        startActivity(new Intent(ModifyMobileOldActivity.this, ModifyMobileNewActivity.class));
        finish();
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        handleError(errorMessage);

      }
    };
    if (validateInternet()) {
      showProgressDialog("修改绑定手机号", "加载中..");
      remoteDataManager.verifyOldMobile(mobile, verifyCode);
    }

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
