package com.hengtiansoft.tijianba.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hengtiansoft.tijianba.net.response.VersionCheckListener;
import com.juanliuinformation.lvningmeng.R;

public class MoreSettingActivity extends BaseActivity implements OnClickListener {

  private RelativeLayout mChangePassword;
  private RelativeLayout mModifyMobile;
  private RelativeLayout mSecretProtect;
  private RelativeLayout mLegalDisclaimer;
  private RelativeLayout mAbout;
  private ImageView mIvNewVersion;
  private String url;
  private RelativeLayout mUpdate;
  private boolean hasNewVersion;
  private String versionCode;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_more_setting);
    initView();
    checkVersion();
  }
  private void checkVersion() {
   
    PackageInfo info = null;
    try {
      info = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
    } catch (NameNotFoundException e) {
      e.printStackTrace();
    }
    if (info != null) {
      versionCode = info.versionName;
    }
    remoteDataManager.versionCheckListener = new VersionCheckListener() {

      @Override
      public void onSuccess(final String Url) {
        dismissProgressDialog();
        if (Url != null) {
          runOnUiThread(new Runnable() {

            @Override
            public void run() {
              // TODO Auto-generated method stub
              url = Url;
              mIvNewVersion.setVisibility(View.VISIBLE);
              hasNewVersion = true;
            }
          });
        } else {
          hasNewVersion = false;
        }
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        handleError(errorMessage);
        dismissProgressDialog();
      }
    };
    if (validateInternet()) {
      showProgressDialog("检查版本", "请稍等");
      remoteDataManager.checkVersion(versionCode);
    }
  }

  private void initView() {
    mChangePassword = (RelativeLayout) findViewById(R.id.rl_more_setting_password);
    mModifyMobile = (RelativeLayout) findViewById(R.id.rl_more_modify_mobile);
    mSecretProtect = (RelativeLayout) findViewById(R.id.rl_more_setting_secret);
    mLegalDisclaimer = (RelativeLayout) findViewById(R.id.rl_more_setting_service);
    mAbout = (RelativeLayout) findViewById(R.id.rl_more_setting_about);
    mChangePassword.setOnClickListener(this);
    mModifyMobile.setOnClickListener(this);
    mSecretProtect.setOnClickListener(this);
    mLegalDisclaimer.setOnClickListener(this);
    mAbout.setOnClickListener(this);
    mIvNewVersion = (ImageView) findViewById(R.id.iv_setting_newVersion);
    mUpdate = (RelativeLayout) findViewById(R.id.rl_more_setting_update);
    mUpdate.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent();
    switch (v.getId()) {
      case R.id.rl_more_setting_password:

        if (remoteDataManager.isLogin) {
          intent.setClass(MoreSettingActivity.this, ChangePwdActivity.class);
          startActivity(intent);
        } else {
          intent.setClass(MoreSettingActivity.this, LoginActivity.class);
          intent.putExtra("GotoChangePwd", true);
          startActivity(intent);
        }
        break;
      case R.id.rl_more_modify_mobile:
        if (remoteDataManager.isLogin) {
          intent.setClass(MoreSettingActivity.this, ModifyMobileOldActivity.class);
          startActivity(intent);
        } else {
          intent.setClass(MoreSettingActivity.this, LoginActivity.class);
          intent.putExtra("GotoChangePhone", true);
          startActivity(intent);
        }
        break;
      case R.id.rl_more_setting_update:
        if (versionCode == null) {
          Toast.makeText(MoreSettingActivity.this, "无法读取当前版本", Toast.LENGTH_LONG).show();
        } else {
          if (hasNewVersion) {
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            startActivity(intent);
          } else {
            Toast.makeText(MoreSettingActivity.this, "当前版本即为最新版本", Toast.LENGTH_LONG).show();
          }
        }
        break;
      case R.id.rl_more_setting_secret:
        intent.setClass(MoreSettingActivity.this, InformationActivity.class);
        intent.putExtra("title", getResources().getString(R.string.more_setting_secret_protection));
        intent.putExtra("information", getResources().getString(R.string.str_secret_protect));
        startActivity(intent);
        break;
      case R.id.rl_more_setting_service:
        Intent intentLegel = new Intent(this, InformationActivity.class);
        intentLegel.putExtra("title", getResources().getString(R.string.more_setting_service_promise));
        intentLegel.putExtra("information", getResources().getString(R.string.str_legal_disclaimer));
        startActivity(intentLegel);
        break;
      case R.id.rl_more_setting_about:
        Intent intentAbout = new Intent(this, InformationActivity.class);
        intentAbout.putExtra("title", getResources().getString(R.string.more_setting_about));
        intentAbout.putExtra("information", getResources().getString(R.string.str_about));
        startActivity(intentAbout);
        break;
      default:
        break;
    }
  }
}
