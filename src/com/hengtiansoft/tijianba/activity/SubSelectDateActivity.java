package com.hengtiansoft.tijianba.activity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hengtiansoft.tijianba.adapter.CalendarPageAdapter;
import com.hengtiansoft.tijianba.adapter.CalendarPageAdapter.OnMonthClick;
import com.hengtiansoft.tijianba.model.CalendarDate;
import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.SubscribeFullDaysListener;
import com.hengtiansoft.tijianba.net.response.SubscribeVerify;
import com.juanliuinformation.lvningmeng.R;

/**
 * 
 * com.hengtiansoft.tijianba.activity.SubSelectDateActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 3, 2014 1:28:05 PM
 */
public class SubSelectDateActivity extends BaseActivity implements OnClickListener {
  private ViewPager mPagerCalendars;
  private ArrayList<CalendarDate> fullDate = new ArrayList<CalendarDate>();
  private ArrayList<CalendarDate> cards = new ArrayList<CalendarDate>();
  private boolean misScrolled;
  private int currentYear;
  private int newYear;
  private int currentMonth;
  private Calendar calendar;
  private boolean isNewYear = false;
  private int beginMonth = 0;
  private int endMonth = 0;
  private boolean isNormalUser = false;
  private int defaultButton = 0;
  private CalendarPageAdapter adapter;
  private SubscribeVerify mSubscribeVerify = new SubscribeVerify();
  private Calendar startCalendar;
  private Calendar endCalendar;
  private Calendar chooseCalendar;
  private RelativeLayout mRlytTime;
  private TextView mTvTime;
  public ArrayList<String> mCompanyDaysList;
  public ArrayList<CalendarDate> mCompanyDays;
  private int mExamType;
  private boolean send;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_subcribe_four);
    calendar = Calendar.getInstance();
    currentYear = calendar.get(Calendar.YEAR);
    newYear = currentYear;
    currentMonth = calendar.get(Calendar.MONTH);
    Bundle bundle = this.getIntent().getExtras();
    if (bundle != null && bundle.containsKey("SubscribeVerifyInfo")) {
      mSubscribeVerify = (SubscribeVerify) bundle.get("SubscribeVerifyInfo");
      send = bundle.getBoolean("send");
    }
    try {
      mExamType = mSubscribeVerify.getExamType();
      mCompanyDaysList = mSubscribeVerify.getCompanyDaysList();
      mCompanyDays = new ArrayList<CalendarDate>();
      String startDate="";
      String endDate="";
      if(mSubscribeVerify.getExamType()==3){
        startDate = mSubscribeVerify.getCompanyDaysList().get(0);
        endDate = mSubscribeVerify.getCompanyDaysList().get( mSubscribeVerify.getCompanyDaysList().size()-1);
      }
      else{
       startDate = mSubscribeVerify.getTestStartDate();
      endDate = mSubscribeVerify.getTestEndDate();}
      startCalendar = Calendar.getInstance();
      startCalendar.setTime(RemoteDataManager.sdfDate.parse(startDate));
      endCalendar = Calendar.getInstance();
      endCalendar.setTime(RemoteDataManager.sdfDate.parse(endDate));
      chooseCalendar = Calendar.getInstance();
      if (mSubscribeVerify.getTestDay() != null && !mSubscribeVerify.getTestDay().equals("")) {
        chooseCalendar.setTime(RemoteDataManager.sdfDate.parse(mSubscribeVerify.getTestDay()));
      } else {
        chooseCalendar = null;
      }
      endMonth = endCalendar.get(Calendar.MONTH);
      beginMonth = startCalendar.get(Calendar.MONTH);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    setView();
  }

  private void setView() {
    mRlytTime = (RelativeLayout) findViewById(R.id.rl_org_title);
    mTvTime = (TextView) findViewById(R.id.tv_org_work_time);
    mTvTime.setText(mSubscribeVerify.getWorkTime());
    setStepImageResource();
    findViewById(R.id.rl_confirm).setOnClickListener(this);
    mPagerCalendars = (ViewPager) findViewById(R.id.vp_calendars);
    adapter = new CalendarPageAdapter(mExamType, getDate(mCompanyDaysList), this, getCalendars(currentYear), fullDate,
        startCalendar, endCalendar, chooseCalendar);
    mPagerCalendars.setAdapter(adapter);
    mPagerCalendars.setOnPageChangeListener(new OnPageChangeListener() {

      @Override
      public void onPageSelected(int arg0) {
        if (!isNewYear) {
          fullDate.clear();
          String startTime = "";
          String endTime = "";
          if (cards.get(arg0).getMonth() != 0) {
            startTime = getFullTime(1, cards.get(arg0).getMonth() - 1, cards.get(arg0).getYear());
          } else {
            startTime = getFullTime(1, 11, cards.get(arg0).getYear() - 1);
          }
          if (cards.get(arg0).getMonth() != 11) {
            endTime = getFullTime(31, cards.get(arg0).getMonth() + 1, cards.get(arg0).getYear());
          } else {
            endTime = getFullTime(31, 0, cards.get(arg0).getYear() + 1);
          }
          getFullDate(startTime, endTime);
        } else {
          isNewYear = false;
        }
      }

      @Override
      public void onPageScrolled(int arg0, float arg1, int arg2) {
      }

      @Override
      public void onPageScrollStateChanged(int arg0) {
        switch (arg0) {
          case ViewPager.SCROLL_STATE_DRAGGING:
            misScrolled = false;
            break;
          case ViewPager.SCROLL_STATE_SETTLING:
            misScrolled = true;
            break;
          case ViewPager.SCROLL_STATE_IDLE:
            if (isNormalUser) {
              int lastPager = mPagerCalendars.getAdapter().getCount() - 1;
              if (mPagerCalendars.getCurrentItem() == lastPager && !misScrolled) {
                isNewYear = true;
                beginMonth = 0;
                endMonth = 11;
                getCalendars(++newYear);
                fullDate.clear();
                String endTime = getFullTime(29, 1, newYear);
                String startTime = getFullTime(1, 2, newYear);
                getFullDate(startTime, endTime);
                mPagerCalendars.setCurrentItem(lastPager + 1);
              }
            }
            misScrolled = true;
            break;
        }
      }
    });
    for (int i = 0; i < cards.size(); i++) {
      if (cards.get(i).getMonth() == currentMonth) {
        defaultButton = i;
        break;
      }
    }
    if (defaultButton == 0) {
      String startTime = "";
      String endTime = "";
      if (cards.get(defaultButton).getMonth() != 0) {
        startTime = getFullTime(1, cards.get(defaultButton).getMonth() - 1, cards.get(defaultButton).getYear());
      } else {
        startTime = getFullTime(1, 11, cards.get(defaultButton).getYear() - 1);
      }
      if (cards.get(defaultButton).getMonth() != 11) {
        endTime = getFullTime(31, cards.get(defaultButton).getMonth() + 1, cards.get(defaultButton).getYear());
      } else {
        endTime = getFullTime(31, 0, cards.get(defaultButton).getYear() + 1);
      }
      getFullDate(startTime, endTime);
    }
    mPagerCalendars.setCurrentItem(defaultButton);
    dismissProgressDialog();
    adapter.setListener(new OnMonthClick() {

      @Override
      public void onMonthClick(int flag) {
        if (flag == -1) {
          mPagerCalendars.setCurrentItem(mPagerCalendars.getCurrentItem() - 1);
        }
        if (flag == -2) {
          mPagerCalendars.setCurrentItem(mPagerCalendars.getCurrentItem() - 2);
        } else if (flag == 1) {
          mPagerCalendars.setCurrentItem(mPagerCalendars.getCurrentItem() + 1);
        } else if (flag == 2) {
          mPagerCalendars.setCurrentItem(mPagerCalendars.getCurrentItem() + 2);
        }
      }

      @Override
      public void onDateClick(String date) {
        mSubscribeVerify.setTestDay(date);
      }
    });
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent();
    switch (v.getId()) {
      case R.id.rl_confirm:
        if (mSubscribeVerify.getTestDay() == null || mSubscribeVerify.getTestDay().equals("")) {
          Toast.makeText(SubSelectDateActivity.this, "请选择日期！", Toast.LENGTH_SHORT).show();
        } else {
          Bundle bundle = new Bundle();
          bundle.putSerializable("SubscribeVerifyInfo", mSubscribeVerify);
          bundle.putBoolean("send", send);
          intent.putExtras(bundle);
          intent.setClass(SubSelectDateActivity.this, SubConfirmInfoActivity.class);
          startActivityForResult(intent, RemoteDataManager.SUBCRIBE);
        }
        break;
      default:
        break;
    }
  }

  private List<CalendarDate> getCalendars(int year) {
    int i = beginMonth;
    if (beginMonth <= endMonth) {
      for (; i <= endMonth; i++) {
        CalendarDate calendarDate = new CalendarDate();
        calendarDate.setYear(year);
        calendarDate.setMonth(i);
        cards.add(calendarDate);
      }
    } else {
      for (; i <= 11; i++) {
        CalendarDate calendarDate = new CalendarDate();
        calendarDate.setYear(year);
        calendarDate.setMonth(i);
        cards.add(calendarDate);
      }
      for (i = 0; i <= endMonth; i++) {
        CalendarDate calendarDate = new CalendarDate();
        calendarDate.setYear(year + 1);
        calendarDate.setMonth(i);
        cards.add(calendarDate);
      }
    }
    return cards;
  }

  public void setStepImageResource() {
    ImageView stepOneImageView = (ImageView) findViewById(R.id.iv_step_one);
    stepOneImageView.setImageResource(R.drawable.ic_lemon_passed);
    ImageView stepTwoImageView = (ImageView) findViewById(R.id.iv_step_two);
    stepTwoImageView.setImageResource(R.drawable.ic_lemon_passed);
    ImageView stepThreeImageView = (ImageView) findViewById(R.id.iv_step_three);
    stepThreeImageView.setImageResource(R.drawable.ic_lemon_passed);
    ImageView stepFourImageView = (ImageView) findViewById(R.id.iv_step_four);
    stepFourImageView.setImageResource(R.drawable.ic_lemon_selected);
    ImageView stepFiveImageView = (ImageView) findViewById(R.id.iv_step_five);
    stepFiveImageView.setImageResource(R.drawable.ic_lemon_unselected);
  }

  public void getFullDate(String startTime, String endTime) {
    Log.i("startTime", startTime);
    Log.i("endTime", endTime);
    remoteDataManager.subscribeFullDaysListener = new SubscribeFullDaysListener() {

      @Override
      public void onSuccess(boolean b, final ArrayList<String> date) {

        runOnUiThread(new Runnable() {

          @Override
          public void run() {
            for (int i = 0; i < date.size(); i++) {
              Calendar calendar = Calendar.getInstance();
              try {
                calendar.setTime(RemoteDataManager.sdfDate.parse(date.get(i)));
              } catch (ParseException e) {
                e.printStackTrace();
              }
              CalendarDate calendarDate = new CalendarDate();
              calendarDate.setDate(calendar.get(Calendar.DATE));
              calendarDate.setMonth(calendar.get(Calendar.MONTH));
              calendarDate.setYear(calendar.get(Calendar.YEAR));
              fullDate.add(calendarDate);
            }
            adapter.notifyDataSetChanged();
          }
        });
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        handleError(errorMessage);
      }
    };
    if (validateInternet()) {
      remoteDataManager.subscribeFullDays(mSubscribeVerify.getExamType(), mSubscribeVerify.getOrgId(), startTime,
          endTime);
    }
  }

  private ArrayList<CalendarDate> getDate(ArrayList<String> mCompanyDaysList) {
    if (mCompanyDaysList != null) {
      for (int i = 0; i < mCompanyDaysList.size(); i++) {
        if (mCompanyDaysList.get(i) != null) {
          Calendar calendar = Calendar.getInstance();
          try {
            calendar.setTime(RemoteDataManager.sdfDate.parse(mCompanyDaysList.get(i)));
          } catch (ParseException e) {
            e.printStackTrace();
          }
          CalendarDate calendarDate = new CalendarDate();
          calendarDate.setDate(calendar.get(Calendar.DATE));
          calendarDate.setMonth(calendar.get(Calendar.MONTH));
          calendarDate.setYear(calendar.get(Calendar.YEAR));
          mCompanyDays.add(calendarDate);
        }
      }
    }
    return mCompanyDays;
  }

  public String getFullTime(int date, int month, int year) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.DATE, date);
    calendar.set(Calendar.MONTH, month);
    calendar.set(Calendar.YEAR, year);
    return RemoteDataManager.sdfTime.format(calendar.getTime());
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (resultCode) {
      case RESULT_OK:
        setResult(RESULT_OK);
        finish();
        break;
      default:
        break;
    }
  }
}
