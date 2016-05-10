package com.hengtiansoft.tijianba.activity;

import com.juanliuinformation.lvningmeng.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;

public class AboutActivity extends Activity implements OnClickListener {
  private RelativeLayout mRlytGiveGood, mRlytWelcom, mRlytHelp;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_about);
    findView();
  }

  
  
  
  private void findView() {
    mRlytGiveGood = (RelativeLayout) findViewById(R.id.rlyt_about_good);
    mRlytWelcom = (RelativeLayout) findViewById(R.id.rlyt_about_welcom);
    mRlytHelp = (RelativeLayout) findViewById(R.id.rlyt_about_help);
    mRlytGiveGood.setOnClickListener(this);
    mRlytWelcom.setOnClickListener(this);
    mRlytHelp.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.rlyt_about_good:
        break;
      case R.id.rlyt_about_welcom:
        break;
      case R.id.rlyt_about_help:
        break;
      default:
        break;
    }
  }
}
