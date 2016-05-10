package com.hengtiansoft.tijianba.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hengtiansoft.tijianba.net.response.CheckInteractionListener;
import com.hengtiansoft.tijianba.net.response.CheckInteractionResult.InteractionStatus;
import com.juanliuinformation.lvningmeng.R;

public class InteractionActivity extends BaseActivity implements OnClickListener {
  private RelativeLayout mShareRelativeLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.acitivity_interaction);
    findViewById(R.id.rl_sign).setOnClickListener(this);
    mShareRelativeLayout = (RelativeLayout) findViewById(R.id.rl_share);
    mShareRelativeLayout.setOnClickListener(this);
    findViewById(R.id.rl_question).setOnClickListener(this);
    checkInteraction();
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent();
    switch (v.getId()) {
      case R.id.rl_sign:
        intent.setClass(this, EveryDaySignInActivity.class);
        startActivity(intent);
        break;
      case R.id.rl_share:
        if (remoteDataManager.isLogin) {
          intent.setClass(this, ShareAndGetPocketActivity.class);
          startActivity(intent);
        } else {
          Toast.makeText(this, "请先登录", Toast.LENGTH_LONG).show();
        }
        break;
      case R.id.rl_question:
        intent.setClass(this, QuestionListActivity.class);
        startActivity(intent);
        break;
      default:
        break;
    }

  }

  public void checkInteraction() {
    remoteDataManager.checkInteractionListener = new CheckInteractionListener() {

      @Override
      public void onSuccess(final InteractionStatus interactionStatus) {
        runOnUiThread(new Runnable() {

          @Override
          public void run() {
            if (interactionStatus.getVal() != null && !interactionStatus.getVal().equals("")) {
              if (interactionStatus.getVal().equals("1")) {
                mShareRelativeLayout.setVisibility(View.VISIBLE);
              } else {
                mShareRelativeLayout.setVisibility(View.GONE);
              }
            }
          }
        });

      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        handleError(errorMessage);
      }
    };
    if (validateInternet()) {
      remoteDataManager.checkInteraction();
    }
  }
}
