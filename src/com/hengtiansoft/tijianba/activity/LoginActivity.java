package com.hengtiansoft.tijianba.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.hengtiansoft.tijianba.net.response.ReturnFromServerListener;
import com.juanliuinformation.lvningmeng.R;

public class LoginActivity extends BaseActivity {
  private EditText mUserNameEditText;
  private EditText mPasswordEditText;
  private SharedPreferences sp;
  private boolean isGotoCart, isGotoSub, addToCart, isGotoMore, isGotoChangePwd, isGotoChangePhone, isGotoFind,isGotoBasicInfo,isGotoBill,isGotoReport;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_login);
    sp = getSharedPreferences("userInfo", 0);
    setView();
    isGotoCart = getIntent().getBooleanExtra("isGotoCart", false);
    addToCart = getIntent().getBooleanExtra("addToCart", false);
    isGotoSub = getIntent().getBooleanExtra("isGotoSub", false);
    isGotoMore = getIntent().getBooleanExtra("isGotoMore", false);
    isGotoChangePwd = getIntent().getBooleanExtra("GotoChangePwd", false);
    isGotoChangePhone = getIntent().getBooleanExtra("GotoChangePhone", false);
    isGotoFind = getIntent().getBooleanExtra("isGotoFind", false);
    isGotoBasicInfo = getIntent().getBooleanExtra("isGotoBasicInfo", false);
    isGotoBill = getIntent().getBooleanExtra("isGotoBill", false);
    isGotoReport = getIntent().getBooleanExtra("isGotoReport", false);
  }

  private void setView() {
    mUserNameEditText = (EditText) findViewById(R.id.edt_username);
    mPasswordEditText = (EditText) findViewById(R.id.edt_password);
    mUserNameEditText.setText(sp.getString("USER_NAME", ""));
    mPasswordEditText.setText(sp.getString("PASSWORD", ""));
    findViewById(R.id.btn_login).setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        // TODO Auto-generated method stub
        if (validation()) {
          Editor editor = sp.edit();
          editor.putBoolean("ISCHECK", true);
          editor.putString("USER_NAME", mUserNameEditText.getText().toString());
          editor.putString("PASSWORD", mPasswordEditText.getText().toString());
          editor.commit();
          remoteDataManager.loginListener = new ReturnFromServerListener() {

            @Override
            public void onSuccess(String message) {
              dismissProgressDialog();
              // TODO Auto-generated method stub
              if (remoteDataManager.userType == 1 && !sp.getBoolean(String.valueOf(remoteDataManager.userId), false)) {
                jump();
                return;
              }
              jump();
            }

            @Override
            public void onError(String errorCode, String errorMessage) {
              // TODO Auto-generated method stub
              handleError(errorMessage);
              dismissProgressDialog();
            }

          };
          if (validateInternet())
            showProgressDialog(getString(R.string.titel_login), getString(R.string.str_wait));
            remoteDataManager.login(mUserNameEditText.getText().toString(), mPasswordEditText.getText().toString(), "");
        }
      }
    });
    TextView textView = (TextView) findViewById(R.id.tv_register);
    textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    textView.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        intent.putExtra("isGotoMore", isGotoMore);
        startActivity(intent);
        // finish();
      }
    });
    TextView textViewFind = (TextView) findViewById(R.id.tv_find_pwd);
    textViewFind.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    textViewFind.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        Intent intent = new Intent(LoginActivity.this, FindPasswordActivity.class);
        intent.putExtra("isGotoMore", isGotoMore);
        startActivity(intent);
        // finish();
      }
    });
  }

  private boolean validation() {
    boolean isValid = true;
    if (mUserNameEditText.length() == 0) {
      mUserNameEditText.setError(getString(R.string.err_msg_user_name));
      isValid = false;
    }
    if (mPasswordEditText.length() == 0) {
      mPasswordEditText.setError(getString(R.string.err_msg_password));
      isValid = false;
    }
    return isValid;
  }

  @Override
  protected void onResume() {
    super.onResume();
    mUserNameEditText.setError(null);
    mPasswordEditText.setError(null);
  }

  public void jump() {
    Intent intent = new Intent();
    if (addToCart) {
      setResult(RESULT_OK);
      finish();
    } else if (isGotoSub) {
      LoginActivity.this.finish();
    } else if (isGotoMore) {
      remoteDataManager.currentPage = 4;
      intent.setClass(LoginActivity.this, MainActivity.class);
      startActivity(intent);
      finish();
    } else if (isGotoFind) {
      remoteDataManager.currentPage = 3;
      intent.setClass(LoginActivity.this, InteractionActivity.class);
      startActivity(intent);
      finish();
    } else if (isGotoCart) {
      intent.setClass(LoginActivity.this, CartListActivity.class);
      startActivity(intent);
      LoginActivity.this.finish();
    } else if (isGotoChangePwd) {
      intent.setClass(LoginActivity.this, ChangePwdActivity.class);
      startActivity(intent);
      LoginActivity.this.finish();
    } else if (isGotoChangePhone) {
      intent.setClass(LoginActivity.this, ModifyMobileOldActivity.class);
      startActivity(intent);
      LoginActivity.this.finish();
    } else if (isGotoBasicInfo) {
      remoteDataManager.currentPage = 0;
      intent.setClass(LoginActivity.this, BasicInforActivity.class);
      startActivity(intent);
      finish();
    } 
    else if (isGotoBill) {
      remoteDataManager.currentPage = 0;
      intent.setClass(LoginActivity.this, HealthBookActivity.class);
      startActivity(intent);
      finish();
    } 
    else if (isGotoReport) {
      remoteDataManager.currentPage = 0;
      intent.setClass(LoginActivity.this,ReportActivity.class);
      startActivity(intent);
      finish();
    } 
    else {
      setResult(RESULT_OK);
      finish();
    }
  }
  
  @Override
  protected void onDestroy() {
    super.onDestroy();
    dismissProgressDialog();
  }
}
