package com.hengtiansoft.tijianba.activity;

import java.util.Calendar;

import com.juanliuinformation.lvningmeng.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TodayTaskActivity extends Activity {
private TextView mTvDate;
private Calendar mCalendar;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_today_task);
    intView();
  }
  
  private void intView(){
    mTvDate=(TextView) findViewById(R.id.tv_today_date);
    mCalendar=Calendar.getInstance();
    mTvDate.setText(mCalendar.MONTH+"月"+mCalendar.DAY_OF_MONTH+"日");
  }
}
