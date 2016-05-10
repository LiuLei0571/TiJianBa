package com.hengtiansoft.tijianba.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.hengtiansoft.tijianba.net.response.ReturnFromServerListener;
import com.juanliuinformation.lvningmeng.R;

public class ChangePwdActivity extends BaseActivity implements OnClickListener {
  private RelativeLayout rlChangePwd;
  private EditText etOldPwd;
  private EditText etNewPwd;
  private EditText et_confirm_newpwd;
  private String mOldPwd;
  private String mNewPwd;
  private String mConfirmNewPwd;
  private SharedPreferences sp;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_changepwd);
    rlChangePwd = (RelativeLayout) this.findViewById(R.id.rl_changepwd);
    etOldPwd = (EditText) this.findViewById(R.id.edt_oldpwd);
    etNewPwd = (EditText) this.findViewById(R.id.edt_newpwd);
    et_confirm_newpwd = (EditText) this.findViewById(R.id.edt_confirm_newpwd);
    rlChangePwd.setOnClickListener(this);
    sp = this.getSharedPreferences("userInfo", 0);
  }

  @Override
  public void onClick(View v) {
    if (validation()) {
      mOldPwd = etOldPwd.getText().toString();
      mNewPwd = etNewPwd.getText().toString();
      mConfirmNewPwd = et_confirm_newpwd.getText().toString();
      changePwd(mOldPwd, mNewPwd);
      Log.d("debug", mOldPwd + "new " + mNewPwd);
    }

  }

  public void changePwd(String oldPwd, String newPwd) {

    remoteDataManager.modifyPwdListener = new ReturnFromServerListener() {

      @Override
      public void onError(String errorCode, String errorMessage) {
        handleError(errorMessage);
      }

      @Override
      public void onSuccess(String message) {
        handleSuccess(message);
        // TODO Auto-generated method stub
        if (remoteDataManager.userType == 1) {
          Editor editor = sp.edit();
          editor.putBoolean(String.valueOf(remoteDataManager.userId), true);
          editor.commit();
        }
        if (!sp.getString("PASSWORD", "").equals("")) {
          Editor editor = sp.edit();
          editor.putBoolean("ISCHECK", true);
          // editor.putString("USER_NAME",
          // mUserNameEditText.getText().toString());
          editor.putString("PASSWORD", mNewPwd);
          editor.commit();
        }
        startActivity(new Intent(ChangePwdActivity.this, LoginActivity.class));
        finish();
      }
    };
    if (validateInternet()) {
      remoteDataManager.modifyPwd(newPwd, oldPwd);
    }
  }

  // public void showMsg(final String successMsg) {
  // this.runOnUiThread(new Runnable() {
  //
  // @Override
  // public void run() {
  // Log.e("errorMsg", "errorMessage");
  // Toast.makeText(this, successMsg, Toast.LENGTH_LONG).show();
  // }
  // });
  // }
  private boolean validation() {
    boolean isValid = true;
    if (etOldPwd.length() == 0) {
      etOldPwd.setError(getString(R.string.err_msg_oldpassword));
      isValid = false;
    }
    if (etNewPwd.length() == 0) {
      etNewPwd.setError(getString(R.string.err_msg_newpassword));
      isValid = false;
    }
    if (et_confirm_newpwd.length() == 0) {
      et_confirm_newpwd.setError(getString(R.string.err_msg_confirmpwd));
      isValid = false;
    }
    if (!et_confirm_newpwd.getText().toString().equals(etNewPwd.getText().toString())) {
      et_confirm_newpwd.setError(getString(R.string.err_confirm_password));
      isValid = false;
    }
    return isValid;
  }
}