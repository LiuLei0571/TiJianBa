package com.hengtiansoft.tijianba.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.hengtiansoft.tijianba.net.response.PackedNewAccount;
import com.hengtiansoft.tijianba.net.response.ReturnFromServerListener;
import com.juanliuinformation.lvningmeng.R;

public class RegisterActivity extends BaseGetVerifyCodeActivity {

  private EditText mPasswordEditText;

  private CheckBox mCheckBoxAgreement;
  private boolean isGotoMore;
  private boolean isGotoFind;
  private TextView mTvUser;
  private SharedPreferences sp;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_register);
    sp = getSharedPreferences("userInfo", 0);
    isGotoMore = getIntent().getBooleanExtra("isGotoMore", false);
    isGotoFind = getIntent().getBooleanExtra("isGotoFind", false);
  }
  @Override
  protected void onResume() {
    super.onResume();
    setView();
    mUserNameEditText.setError(null);
    mPasswordEditText.setError(null);
    mVerifyCodeEditText.setError(null);
  }

  private void setView() {
    mPasswordEditText = (EditText) findViewById(R.id.edt_password);
    mPasswordEditText.setText("");
    mCheckBoxAgreement = (CheckBox) findViewById(R.id.chb_agreement);
    mCheckBoxAgreement.setChecked(true);
    mTvUser = (TextView) findViewById(R.id.tv_agreement);
    mTvUser.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(RegisterActivity.this, AgreementActivity.class);
        startActivity(intent);
      }
    });
    findViewById(R.id.btn_register).setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        if (validation()) {
          register();
        }

      }
    });

    TextView textView = (TextView) findViewById(R.id.tv_exite_account);
    textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    textView.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.putExtra("isGotoMore", isGotoMore);
        startActivity(intent);
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
    if (mVerifyCodeEditText.length() == 0) {
      mVerifyCodeEditText.setError(getString(R.string.err_msg_verify_code));
      isValid = false;
    }
    return isValid;
  }
  public void register() {
    final PackedNewAccount packedNewAccount = new PackedNewAccount();
    packedNewAccount.setMobile(mUserNameEditText.getText().toString());
    packedNewAccount.setPassword(mPasswordEditText.getText().toString());
    packedNewAccount.setVerifyCode(mVerifyCodeEditText.getText().toString());
    remoteDataManager.loginListener = new ReturnFromServerListener() {
      @Override
      public void onSuccess(String message) {
        Intent intent = new Intent();
        if (isGotoMore) {
          intent.setClass(RegisterActivity.this, MainActivity.class);
          remoteDataManager.currentPage = 4;
          startActivity(intent);
          finish();
        } else if (isGotoFind) {
          intent.setClass(RegisterActivity.this, MainActivity.class);
          remoteDataManager.currentPage = 3;
          startActivity(intent);
          finish();
        }
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        // TODO Auto-generated method stub
        handleError(errorMessage);
      }

    };

    remoteDataManager.registerListener = new ReturnFromServerListener() {
      @Override
      public void onSuccess(String message) {
        dismissProgressDialog();
        handleSuccess(message);
        remoteDataManager.login(mUserNameEditText.getText().toString(), mPasswordEditText.getText().toString(), "");
        Editor editor = sp.edit();
        editor.putBoolean("ISCHECK", true);
        editor.putString("USER_NAME", mUserNameEditText.getText().toString());
        editor.putString("PASSWORD", mPasswordEditText.getText().toString());
        editor.commit();
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        handleError(errorMessage);
      }
    };
    if (validateInternet()) {
      showProgressDialog(getString(R.string.titel_register), getString(R.string.str_wait));
      remoteDataManager.register(packedNewAccount);
    }
  }
}
