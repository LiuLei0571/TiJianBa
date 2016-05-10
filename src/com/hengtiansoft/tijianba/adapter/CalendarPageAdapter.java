package com.hengtiansoft.tijianba.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hengtiansoft.tijianba.calendarcard.CalendarCard;
import com.hengtiansoft.tijianba.calendarcard.CardGridItem;
import com.hengtiansoft.tijianba.calendarcard.OnCellItemClick;
import com.hengtiansoft.tijianba.calendarcard.OnMonthItemClick;
import com.hengtiansoft.tijianba.model.CalendarDate;
import com.hengtiansoft.tijianba.model.RemoteDataManager;

/**
 * 
 * com.hengtiansoft.tijianba.adapter.CalendarPageAdapter
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 2, 2014 9:13:50 PM
 */
public class CalendarPageAdapter extends PagerAdapter {

  private Context mContext;
  private List<CalendarDate> mDates;
  private OnMonthClick listener;
  private ArrayList<CalendarDate> mFullDates;
  private Calendar startCalendar;
  private Calendar endCalendar;
  private Calendar chooseCalendar;
  public ArrayList<CalendarDate> mCompanyDays;
  private int mExamType;
  public CalendarPageAdapter(int mExamType,ArrayList<CalendarDate> mCompanyDays,Context ctx, List<CalendarDate> mDates, ArrayList<CalendarDate> mFullDates,
      Calendar startCalendar, Calendar endCalendar, Calendar chooseCalendar) {
    mContext = ctx;
    this.mExamType=mExamType;
    this.mCompanyDays=mCompanyDays;
    this.mDates = mDates;
    this.mFullDates = mFullDates;
    this.startCalendar = startCalendar;
    this.endCalendar = endCalendar;
    this.chooseCalendar = chooseCalendar;
  }

  public void setListener(OnMonthClick listener) {
    this.listener = listener;
  }

  @Override
  public Object instantiateItem(View collection, final int position) {
    Calendar cal = Calendar.getInstance();
    CalendarDate date = mDates.get(position);
    cal.set(date.getYear(), date.getMonth(), 1);
    CalendarCard card = new CalendarCard(mContext);
    card.setDateDisplay(mExamType,mCompanyDays,cal, mFullDates, startCalendar, endCalendar, chooseCalendar);
    card.setOnCellItemClick(new OnCellItemClick() {

      @Override
      public void onCellClick(View v, CardGridItem item) {
        String date = RemoteDataManager.sdfDate.format(item.getDate().getTime());
        listener.onDateClick(date);
      }

    });
    card.setmOnMonthItemClick(new OnMonthItemClick() {

      @Override
      public void onMonthItemClick(int flag) {
        listener.onMonthClick(flag);
      }
    });
    card.notifyChanges();

    ((ViewPager) collection).addView(card, 0);

    return card;
  }

  @Override
  public void destroyItem(View collection, int position, Object view) {
    ((ViewPager) collection).removeView((View) view);
  }

  @Override
  public void finishUpdate(View arg0) {
  }

  @Override
  public void restoreState(Parcelable arg0, ClassLoader arg1) {
  }

  @Override
  public Parcelable saveState() {
    return null;
  }

  @Override
  public void startUpdate(View arg0) {
  }

  @Override
  public int getCount() {
    // TODO almoast ifinite ;-)
    return mDates.size();
  }

  public interface OnMonthClick {

    public void onMonthClick(int flag);

    public void onDateClick(String date);

  }

  @Override
  public int getItemPosition(Object object) {
    return POSITION_NONE;
  }

  @Override
  public boolean isViewFromObject(View arg0, Object arg1) {
    return arg0 == arg1;
  }
}
