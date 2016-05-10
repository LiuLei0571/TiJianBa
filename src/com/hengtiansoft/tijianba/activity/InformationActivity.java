package com.hengtiansoft.tijianba.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class InformationActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ScrollView scrollView = new ScrollView(this);
    setContentView(scrollView);
    LinearLayout llytContent = new LinearLayout(this);
    llytContent.setOrientation(LinearLayout.VERTICAL);
    scrollView.addView(llytContent);
    TextView textTitle = new TextView(this);
    textTitle.setGravity(Gravity.CENTER_HORIZONTAL);
    TextView textView = new TextView(this);
    llytContent.addView(textTitle);
    llytContent.addView(textView);
    Intent intent = getIntent();
    String title = intent.getStringExtra("title");
    String text = intent.getStringExtra("information");
    textTitle.setText(title);
    textView.setText(text);
  }
}
