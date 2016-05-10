package com.hengtiansoft.tijianba.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.hengtiansoft.tijianba.net.response.ReturnFromServerListener;
import com.juanliuinformation.lvningmeng.R;

public class FindPasswordConfirmActivity extends BaseActivity {

	private EditText mEtNewPwd;
	private EditText mEtConfirmPwd;
	private RelativeLayout mConfirmLayout;
	private boolean isGotoMore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_password_confirm);
		initView();
		isGotoMore = getIntent().getBooleanExtra("isGotoMore", false);
	}

	private void initView() {
		mEtNewPwd = (EditText) findViewById(R.id.edt_new_password);
		mEtConfirmPwd = (EditText) findViewById(R.id.edt_confirm_password);
		mConfirmLayout = (RelativeLayout) findViewById(R.id.rl_find_password_confirm);

		mConfirmLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String newPwd = mEtNewPwd.getText().toString();
				String confirmPwd = mEtConfirmPwd.getText().toString();
				if ("".equals(newPwd)) {
					mEtNewPwd.setError(getString(R.string.err_msg_password));
				} else if ("".equals(confirmPwd)) {
					mEtConfirmPwd
							.setError(getString(R.string.err_msg_password));
				} else if (!newPwd.equals(confirmPwd)) {
					mEtConfirmPwd
							.setError(getString(R.string.err_msg_confirm_pwd));
				} else {
					getMessage(getIntent().getStringExtra("mobile"), confirmPwd);
				}
			}
		});
	}

	private void getMessage(String mobile, String confirmPwd) {
		final String newPwd = confirmPwd;
		final String mobileNew = mobile;
		remoteDataManager.resetPwdListener = new ReturnFromServerListener() {

			@Override
			public void onSuccess(String message) {
				handleSuccess(message);
				SharedPreferences sp = getSharedPreferences("userInfo", 0);
				Editor editor = sp.edit();
				if (remoteDataManager.userType == 1) {
					editor.putBoolean(String.valueOf(remoteDataManager.userId),
							true);
				}
				editor.putString("USER_NAME", mobileNew);
				editor.putString("PASSWORD", newPwd);
				editor.commit();
				Intent intent = new Intent(FindPasswordConfirmActivity.this,
						LoginActivity.class);
				intent.putExtra("isGotoMore", isGotoMore);
				startActivity(intent);
				finish();
			}

			@Override
			public void onError(String errorCode, String errorMessage) {
				handleError(errorMessage + " 请重新找回密码！");
				startActivity(new Intent(FindPasswordConfirmActivity.this,
						FindPasswordActivity.class));
			}
		};
		if (validateInternet())
			remoteDataManager.resetPwd(mobile, confirmPwd);
	}
}
