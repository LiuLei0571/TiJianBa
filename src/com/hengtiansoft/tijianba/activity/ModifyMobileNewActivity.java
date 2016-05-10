package com.hengtiansoft.tijianba.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.hengtiansoft.tijianba.net.response.ReturnFromServerListener;
import com.juanliuinformation.lvningmeng.R;

public class ModifyMobileNewActivity extends BaseActivity {

  private EditText mMobile;
  private String mobile;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_modify_mobile_new);
    mMobile = (EditText) findViewById(R.id.et_new_mobile);
    RelativeLayout button = (RelativeLayout) findViewById(R.id.rl_new_confirm);
    button.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        mobile = mMobile.getText().toString();
        if ("".equals(mobile)) {
          mMobile.setError(getString(R.string.error_mobile_is_null));
        } else if (mMobile.length() != 11) {
          mMobile.setError(getString(R.string.error_mobile_not_standard));
        } else {
          isMobileExit();

        }
      }
    });
  }

  private void isMobileExit() {

    remoteDataManager.isMobileExitListener = new ReturnFromServerListener() {

      @Override
      public void onSuccess(String messgae) {
        dismissProgressDialog();
        handleSuccess(messgae + getString(R.string.str_input_new_mobile));
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        dismissProgressDialog();
        if (getString(R.string.str_mobile_not_exist).equals(errorMessage)) {
          Intent intent = new Intent(ModifyMobileNewActivity.this, ModifyMobileConfirmActivity.class);
          intent.putExtra("phone", mobile);
          startActivity(intent);
          finish();
        } else {
          handleError(errorMessage);
        }
      }
    };
    if (validateInternet()) {
      showProgressDialog("修改绑定手机号", "加载中..");
      remoteDataManager.isMobileExit(mobile);
    }
  }
}
