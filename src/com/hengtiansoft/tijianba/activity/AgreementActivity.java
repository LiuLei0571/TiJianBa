package com.hengtiansoft.tijianba.activity;

import com.juanliuinformation.lvningmeng.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class AgreementActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_agreement);
  }
}
