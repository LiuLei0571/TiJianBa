package com.hengtiansoft.tijianba.activity;

import java.util.ArrayList; 
import java.util.List;

import com.hengtiansoft.tijianba.adapter.HealthInquaryAdapter;
import com.hengtiansoft.tijianba.model.HealthInquary;
import com.juanliuinformation.lvningmeng.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

public class HealthInquaryActivity extends Activity implements OnItemClickListener {

  private EditText mEtSearch;
  private ListView mLvInquary;
  private ArrayList<HealthInquary> list;
  private HealthInquaryAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_health_inquary);
    mEtSearch = (EditText) findViewById(R.id.et_health_inquary);
    mLvInquary = (ListView) findViewById(R.id.lv_health_inquary);
    adapter = new HealthInquaryAdapter(this, initList());
    mLvInquary.setAdapter(adapter);
    mLvInquary.setOnItemClickListener(this);
  }

  private ArrayList initList() {
    list = new ArrayList<HealthInquary>();
    HealthInquary healthInquary = new HealthInquary();
    healthInquary.setTitle("北京免费婚检项目已调整");
    healthInquary.setDetial("北京免费婚检项目已调整,有望全市统一为13项，其中新增艾滋病抗体等3项，另有3项调整");
    healthInquary.setImgResource(R.drawable.ic_launcher);
    list.add(healthInquary);
    list.add(healthInquary);
    list.add(healthInquary);
    return list;
  }

  @Override
  public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
    HealthInquary healthInquary = (HealthInquary) adapter.getItem(position);
    Intent intent = new Intent(this, HelathInquaryDetialActivity.class);
    intent.putExtra("healthitem", healthInquary);
    startActivity(intent);
  }
}
