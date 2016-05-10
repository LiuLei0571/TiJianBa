package com.hengtiansoft.tijianba.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.juanliuinformation.lvningmeng.R;

public class FindPasswordActivity extends BaseGetVerifyCodeActivity implements OnClickListener {

  private RelativeLayout mNextLayout;
  private boolean isGotoMore;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_find_password);
    isRegister = false;
    mNextLayout = (RelativeLayout) findViewById(R.id.rl_find_password_next);
    mNextLayout.setOnClickListener(this);
    isGotoMore = getIntent().getBooleanExtra("isGotoMore", false);
  }

  private boolean validation() {
    boolean isValid = true;
    if (mUserNameEditText.length() == 0) {
      mUserNameEditText.setError(getString(R.string.err_msg_user_name));
      isValid = false;
    }
    if (mVerifyCodeEditText.length() == 0) {
      mVerifyCodeEditText.setError(getString(R.string.err_msg_verify_code));
      isValid = false;
    }
    return isValid;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.rl_find_password_next:
        String mobile = mUserNameEditText.getText().toString();
        if (validation()) {
          Intent intent = new Intent(FindPasswordActivity.this, FindPasswordConfirmActivity.class);
          intent.putExtra("mobile", mobile);
          intent.putExtra("isGotoMore", isGotoMore);
          startActivity(intent);
          finish();
        }
        break;
      default:
        break;
    }
  }
}
