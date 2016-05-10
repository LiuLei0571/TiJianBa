package com.hengtiansoft.tijianba.activity;

import com.hengtiansoft.tijianba.model.HealthInquary;
import com.juanliuinformation.lvningmeng.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class HelathInquaryDetialActivity extends Activity {
  private TextView mTvTitle, mTvDetail;
  private ImageView mImgDetial,mImgShare;
  private Intent intent;
  private HealthInquary healthInquary;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_health_inquary_detial);
    mTvTitle = (TextView) findViewById(R.id.helath_inquary_title_tv);
    mImgShare = (ImageView) findViewById(R.id.helath_inquary_title_img);
    mTvDetail = (TextView) findViewById(R.id.helath_inquary_detial_tv);
    mImgDetial = (ImageView) findViewById(R.id.helath_inquary_detial_img);
    intent = getIntent();
    healthInquary = (HealthInquary) intent.getSerializableExtra("healthitem");
    mTvTitle.setText(healthInquary.getTitle());
    mTvDetail.setText(healthInquary.getDetial());
  }
}