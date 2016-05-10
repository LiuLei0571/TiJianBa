package com.hengtiansoft.tijianba.widget;

import java.lang.reflect.Field;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;

/**
 * 
 * com.hengtiansoft.tijianba.widget.CustomDatePickerDialog
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 15, 2014 10:54:54 AM
 */

public class DataDatePickerDialog extends DatePickerDialog {
  private int year;
  private int monthOfYear;
  private int dayOfMonth;
  public DataDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
    super(context, callBack, year, monthOfYear, dayOfMonth);
    this.setTitle(year + "年" + (monthOfYear+1) + "月" + dayOfMonth + "日");
    this.year = year;
    this.monthOfYear = monthOfYear;
    this.dayOfMonth = dayOfMonth;
  }

  @Override
  public void onDateChanged(DatePicker view, int year, int month, int day) {
    super.onDateChanged(view, year, month, day);
    this.setTitle(year + "年" + (month+1) + "月" + day + "日");
  }

  @Override
  public void show() {
    // TODO Auto-generated method stub
    super.show();
    DatePicker dp = findDatePicker((ViewGroup) this.getWindow().getDecorView());
    setMaxDate( dp);
    if (dp != null) {
      Class c = dp.getClass();
      Field f;
      try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
          f = c.getDeclaredField("mDaySpinner");
        } else {
          f = c.getDeclaredField("mDayPicker");
        }
        f.setAccessible(true);
        LinearLayout l = (LinearLayout) f.get(dp);
        // l.setVisibility(View.GONE);
      } catch (SecurityException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (NoSuchFieldException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IllegalArgumentException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    }
  }

  private DatePicker findDatePicker(ViewGroup group) {
    if (group != null) {
      for (int i = 0, j = group.getChildCount(); i < j; i++) {
        View child = group.getChildAt(i);
        if (child instanceof DatePicker) {
          return (DatePicker) child;
        } else if (child instanceof ViewGroup) {
          DatePicker result = findDatePicker((ViewGroup) child);
          if (result != null)
            return result;
        }
      }
    }
    return null;
  }

  private void setMaxDate(DatePicker dp) {
    dp.init(year, monthOfYear, dayOfMonth, new OnDateChangedListener() {

      @Override
      public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        if (isDateAfter(view)) {
          Calendar mCalendar = Calendar.getInstance();
          view.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH),
              this);
        }
      }

      private boolean isDateAfter(DatePicker tempView) {
        Calendar mCalendar = Calendar.getInstance();
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.set(tempView.getYear(), tempView.getMonth(), tempView.getDayOfMonth(), 0, 0, 0);
        if (tempCalendar.after(mCalendar))
          return true;
        else
          return false;
      }
    });
  }
}