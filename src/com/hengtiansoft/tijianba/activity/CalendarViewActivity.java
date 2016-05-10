package com.hengtiansoft.tijianba.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hengtiansoft.tijianba.model.CalendarView;
import com.hengtiansoft.tijianba.model.CalendarView.OnItemClickListener;
import com.juanliuinformation.lvningmeng.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

import android.widget.TextView;

public class CalendarViewActivity extends Activity {
  private CalendarView calendar;
  private TextView calendarLeft;
  private TextView calendarCenter;
  private TextView calendarRight;
  private SimpleDateFormat format;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_calendar_view);

    format = new SimpleDateFormat("yyyy-MM-dd");
    // 获取日历控件对象
    calendar = (CalendarView) findViewById(R.id.calendar);
    calendar.setSelectMore(false); // 单选

    calendarLeft = (TextView) findViewById(R.id.calendarLeft);
    calendarCenter = (TextView) findViewById(R.id.calendarCenter);
    calendarRight = (TextView) findViewById(R.id.calendarRight);
    try {
      // 设置日历日期
      Date date = format.parse("2015-01-01");
      calendar.setCalendarData(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    // 获取日历中年月 ya[0]为年，ya[1]为月（格式大家可以自行在日历控件中改）
    String[] ya = calendar.getYearAndmonth().split("-");
    calendarCenter.setText(ya[0] + "年" + ya[1] + "月");
    calendarLeft.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        // 点击上一月 同样返回年月
        String leftYearAndmonth = calendar.clickLeftMonth();
        String[] ya = leftYearAndmonth.split("-");
        calendarCenter.setText(ya[0] + "年" + ya[1] + "月");
      }
    });

    calendarRight.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        // 点击下一月
        String rightYearAndmonth = calendar.clickRightMonth();
        String[] ya = rightYearAndmonth.split("-");
        calendarCenter.setText(ya[0] + "年" + ya[1] + "月");
      }
    });

    // 设置控件监听，可以监听到点击的每一天
    calendar.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void OnItemClick(Date selectedStartDate, Date selectedEndDate, Date downDate) {
        getIntent().putExtra("currentTime", "" + format.format(downDate));
        setResult(1, getIntent());
        finish();

      }
    });

  }
}
