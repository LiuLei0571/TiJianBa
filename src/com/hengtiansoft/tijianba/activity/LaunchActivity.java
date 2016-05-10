package com.hengtiansoft.tijianba.activity;

import java.net.SocketTimeoutException;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.hengtiansoft.tijianba.net.response.ReturnFromServerListener;
import com.hengtiansoft.tijianba.net.response.VersionCheckListener;
import com.hengtiansoft.tijianba.util.UpdateManage;
import com.juanliuinformation.lvningmeng.R;

public class LaunchActivity extends BaseActivity {
  private TimeCount time;
  private UpdateManage mUp;
  private AlertDialog ad;

  @SuppressLint("NewApi")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    super.onCreate(savedInstanceState);
    ImageView imageView = new ImageView(this);
    imageView.setImageResource(R.drawable.launch);
    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    setContentView(imageView);
    mUp = new UpdateManage(LaunchActivity.this);
    if (validateInternet()) {
        doCheckVersion();
    } else {
      time = new TimeCount(2000, 1000);
      time.start();
    }
  }

  private void doCheckVersion() {
    final String version = mUp.getVersionName(LaunchActivity.this);
    remoteDataManager.versionCheckListener = new VersionCheckListener() {
      @Override
      public void onSuccess(final String Url) {
        runOnUiThread(new Runnable() {
          public void run() {
            if (Url != null && !Url.equals("")) {
              showDio(Url);
            } else {
              time = new TimeCount(2000, 1000);
              time.start();
              doLogin();
            }
          }
        });
      }
      @Override
      public void onError(String errorCode, String errorMessage) {
        startActivity(new Intent(getApplication(), MainActivity.class));
        LaunchActivity.this.finish();
      }
    };
    remoteDataManager.checkVersion(version);
  }

  private void doLogin() {
    final SharedPreferences sp = getSharedPreferences("userInfo", 0);
    String username = sp.getString("USER_NAME", "");
    String password = sp.getString("PASSWORD", "");
    if ((username.equals("") == false) && (password.equals("") == false)) {
      remoteDataManager.loginListener = new ReturnFromServerListener() {
        @Override
        public void onSuccess(String message) {
          if (remoteDataManager.userType == 1 && !sp.getBoolean(String.valueOf(remoteDataManager.userId), false)) {
            return;
          }
        }

        @Override
        public void onError(String errorCode, String errorMessage) {
          handleError(errorMessage);
        }
      };
      if (validateInternet())
      remoteDataManager.login(username, password, "");
    }
  }

  class TimeCount extends CountDownTimer {

    public TimeCount(long millisInFuture, long countDownInterval) {
      super(millisInFuture, countDownInterval);
    }

    @Override
    public void onFinish() {
      startActivity(new Intent(getApplication(), MainActivity.class));
      LaunchActivity.this.finish();
    }

    @Override
    public void onTick(long millisUntilFinished) {
    }
  }

  private void showDio(final String Url) {
    ad = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT).setMessage("检测到新版本是否更新？").setCancelable(false)
        .setPositiveButton(getString(R.string.str_yes), new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
            mUp.updateAppByUrl(Url, "lnmjk_android");
          }
        }).setNegativeButton(getString(R.string.str_no), new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
            time = new TimeCount(2000, 1000);
            time.start();
            doLogin();
          }
        }).show();
  }
}
