package com.hengtiansoft.tijianba.calendarcard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengtiansoft.tijianba.model.CalendarDate;
import com.juanliuinformation.lvningmeng.R;

public class CalendarCard extends RelativeLayout implements OnClickListener {
  private TextView cardTitleMonth;
  private TextView cardTitlePreMonth;
  private TextView cardTitlePrePreMonth;
  private TextView cardTitleNextMonth;
  private TextView cardTitleNextNextMonth;
  private TextView cardTitlePrePreYear, cardTitlePreYear, cardTitleYear, cardTitleNextYear, cardTitleNextNextYear;
  private View cardTitleView;
  private View cardTitlePreView, cardTitlePrePreView;
  private View cardTitleNextView, cardTitleNextNextView;
  private int itemLayout = R.layout.card_item_simple;
  private OnItemRender mOnItemRender;
  private OnItemRender mOnItemRenderDefault;
  private OnCellItemClick mOnCellItemClick;
  private OnMonthItemClick mOnMonthItemClick;
  private int state;
  private Calendar dateDisplay;
  private ArrayList<CheckableLayout> cells = new ArrayList<CheckableLayout>();
  private LinearLayout cardGrid;
  private ArrayList<CalendarDate> mFullDates;
  private Context mContext;
  private Calendar startCalendar;
  private Calendar endCalendar;
  private ProgressDialog mProgressDialog;
  private Calendar chooseCalendar;
  private int mExamType;
  private ArrayList<CalendarDate> mCompanyDays;

  // private int advanceDay = 3;
  // private int advanceDayTemp;

  public CalendarCard(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    mContext = context;
  }

  public CalendarCard(Context context, AttributeSet attrs) {
    super(context, attrs);
    mContext = context;
  }

  public CalendarCard(Context context) {
    super(context);
    mContext = context;
  }

  private void init(Context ctx) {
    // advanceDayTemp = advanceDay;
    if (isInEditMode())
      return;
    View layout = LayoutInflater.from(ctx).inflate(R.layout.card_view, null, false);
    if (dateDisplay == null)
      dateDisplay = Calendar.getInstance();
    cardTitleYear = (TextView) layout.findViewById(R.id.cardTitleYear);
    cardTitlePreYear = (TextView) layout.findViewById(R.id.cardTitlePreYear);
    cardTitleNextYear = (TextView) layout.findViewById(R.id.cardTitleNextYear);
    cardTitlePrePreYear = (TextView) layout.findViewById(R.id.cardTitlePrePreYear);
    cardTitleNextNextYear = (TextView) layout.findViewById(R.id.cardTitleNextNextYear);
    cardTitleMonth = (TextView) layout.findViewById(R.id.cardTitleMonth);
    cardTitlePreMonth = (TextView) layout.findViewById(R.id.cardTitlePreMonth);
    cardTitleNextMonth = (TextView) layout.findViewById(R.id.cardTitleNextMonth);
    cardTitlePrePreMonth = (TextView) layout.findViewById(R.id.cardTitlePrePreMonth);
    cardTitleNextNextMonth = (TextView) layout.findViewById(R.id.cardTitleNextNextMonth);
    cardTitleView = layout.findViewById(R.id.ll_cardTitle);
    cardTitlePreView = layout.findViewById(R.id.ll_cardTitlePre);
    cardTitleNextView = layout.findViewById(R.id.ll_cardTitleNext);
    cardTitlePrePreView = layout.findViewById(R.id.ll_cardTitlePrePre);
    cardTitleNextNextView = layout.findViewById(R.id.ll_cardTitleNextNext);
    cardTitleView.setOnClickListener(this);
    cardTitlePreView.setOnClickListener(this);
    cardTitleNextView.setOnClickListener(this);
    cardTitlePrePreView.setOnClickListener(this);
    cardTitleNextNextView.setOnClickListener(this);
    cardGrid = (LinearLayout) layout.findViewById(R.id.cardGrid);
    String month = mContext.getString(R.string.str_month);
    String numMonth = new SimpleDateFormat("MM", Locale.getDefault()).format(dateDisplay.getTime());
    cardTitleMonth.setText(numMonth + month);
    if (numMonth.equals("01")) {
      cardTitleYear.setVisibility(VISIBLE);
      cardTitleYear.setText(new SimpleDateFormat("yyyy", Locale.getDefault()).format(dateDisplay.getTime()));
    }
    Calendar preMonth = (Calendar) dateDisplay.clone();
    preMonth.add(Calendar.MONTH, -1);
    numMonth = new SimpleDateFormat("MM", Locale.getDefault()).format(preMonth.getTime());
    Calendar.getInstance().get(Calendar.YEAR);
    if ((preMonth.get(Calendar.YEAR) == startCalendar.get(Calendar.YEAR) && preMonth.get(Calendar.MONTH) < startCalendar
        .get(Calendar.MONTH)) || (preMonth.get(Calendar.YEAR) < startCalendar.get(Calendar.YEAR))) {
      cardTitlePreMonth.setTextColor(mContext.getResources().getColor(R.color.org_gray));
      cardTitlePreYear.setTextColor(mContext.getResources().getColor(R.color.org_gray));
    }
    cardTitlePreMonth.setText(numMonth + month);
    if (numMonth.equals("01")) {
      cardTitlePreYear.setVisibility(VISIBLE);
      cardTitlePreYear.setText(new SimpleDateFormat("yyyy", Locale.getDefault()).format(preMonth.getTime()));
    }
    Calendar prePreMonth = (Calendar) preMonth.clone();
    prePreMonth.add(Calendar.MONTH, -1);
    if ((prePreMonth.get(Calendar.YEAR) == startCalendar.get(Calendar.YEAR) && prePreMonth.get(Calendar.MONTH) < startCalendar
        .get(Calendar.MONTH)) || (prePreMonth.get(Calendar.YEAR) < startCalendar.get(Calendar.YEAR))) {
      cardTitlePrePreMonth.setTextColor(mContext.getResources().getColor(R.color.org_gray));
      cardTitlePrePreYear.setTextColor(mContext.getResources().getColor(R.color.org_gray));
    }
    numMonth = new SimpleDateFormat("MM", Locale.getDefault()).format(prePreMonth.getTime());
    cardTitlePrePreMonth.setText(numMonth + month);
    if (numMonth.equals("01")) {
      cardTitlePrePreYear.setVisibility(VISIBLE);
      cardTitlePrePreYear.setText(new SimpleDateFormat("yyyy", Locale.getDefault()).format(prePreMonth.getTime()));
    }
    Calendar nextMonth = (Calendar) dateDisplay.clone();
    nextMonth.add(Calendar.MONTH, 1);
    if ((nextMonth.get(Calendar.YEAR) == endCalendar.get(Calendar.YEAR) && nextMonth.get(Calendar.MONTH) > endCalendar
        .get(Calendar.MONTH)) || nextMonth.get(Calendar.YEAR) > endCalendar.get(Calendar.YEAR)) {
      cardTitleNextMonth.setTextColor(mContext.getResources().getColor(R.color.org_gray));
      cardTitleNextYear.setTextColor(mContext.getResources().getColor(R.color.org_gray));
    } else {
      cardTitleNextMonth.setTextColor(mContext.getResources().getColor(R.color.edt_grey));
      cardTitleNextYear.setTextColor(mContext.getResources().getColor(R.color.edt_grey));
    }
    numMonth = new SimpleDateFormat("MM", Locale.getDefault()).format(nextMonth.getTime());
    cardTitleNextMonth.setText(numMonth + month);
    if (numMonth.equals("01")) {
      cardTitleNextYear.setVisibility(VISIBLE);
      cardTitleNextYear.setText(new SimpleDateFormat("yyyy", Locale.getDefault()).format(nextMonth.getTime()));
    }
    Calendar nextNextMonth = (Calendar) nextMonth.clone();
    nextNextMonth.add(Calendar.MONTH, 1);
    if ((nextNextMonth.get(Calendar.YEAR) == endCalendar.get(Calendar.YEAR) && nextNextMonth.get(Calendar.MONTH) > endCalendar
        .get(Calendar.MONTH)) || nextNextMonth.get(Calendar.YEAR) > endCalendar.get(Calendar.YEAR)) {
      cardTitleNextNextMonth.setTextColor(mContext.getResources().getColor(R.color.org_gray));
      cardTitleNextNextYear.setTextColor(mContext.getResources().getColor(R.color.org_gray));
    } else {
      cardTitleNextNextMonth.setTextColor(mContext.getResources().getColor(R.color.edt_grey));
      cardTitleNextNextYear.setTextColor(mContext.getResources().getColor(R.color.edt_grey));
    }
    numMonth = new SimpleDateFormat("MM", Locale.getDefault()).format(nextNextMonth.getTime());
    cardTitleNextNextMonth.setText(numMonth + month);
    if (numMonth.equals("01")) {
      cardTitleNextNextYear.setVisibility(VISIBLE);
      cardTitleNextNextYear.setText(new SimpleDateFormat("yyyy", Locale.getDefault()).format(nextNextMonth.getTime()));
    }
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    String sunday = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
    ((TextView) layout.findViewById(R.id.cardDay1)).setText(sunday.substring(sunday.length() - 1));
    cal.add(Calendar.DAY_OF_WEEK, 1);
    String monday = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
    ((TextView) layout.findViewById(R.id.cardDay2)).setText(monday.substring(monday.length() - 1));
    cal.add(Calendar.DAY_OF_WEEK, 1);
    String tuesday = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
    ((TextView) layout.findViewById(R.id.cardDay3)).setText(tuesday.substring(tuesday.length() - 1));
    cal.add(Calendar.DAY_OF_WEEK, 1);
    String wednesday = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
    ((TextView) layout.findViewById(R.id.cardDay4)).setText(wednesday.substring(wednesday.length() - 1));
    cal.add(Calendar.DAY_OF_WEEK, 1);
    String thursday = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
    ((TextView) layout.findViewById(R.id.cardDay5)).setText(thursday.substring(thursday.length() - 1));
    cal.add(Calendar.DAY_OF_WEEK, 1);
    String friday = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
    ((TextView) layout.findViewById(R.id.cardDay6)).setText(friday.substring(friday.length() - 1));
    cal.add(Calendar.DAY_OF_WEEK, 1);
    String saturday = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
    ((TextView) layout.findViewById(R.id.cardDay7)).setText(saturday.substring(saturday.length() - 1));

    LayoutInflater la = LayoutInflater.from(ctx);
    for (int y = 0; y < cardGrid.getChildCount(); y++) {
      LinearLayout row = (LinearLayout) cardGrid.getChildAt(y);
      for (int x = 0; x < row.getChildCount(); x++) {
        CheckableLayout cell = (CheckableLayout) row.getChildAt(x);
        cell.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            for (CheckableLayout c : cells) {
              c.setChecked(false);
              CardGridItem cardGridItem = ((CardGridItem) c.getTag());
              if (cardGridItem != null && cardGridItem.getDate() != null && c.isEnabled())
                if (cardGridItem.getDate().get(Calendar.DAY_OF_WEEK) == 1
                    || cardGridItem.getDate().get(Calendar.DAY_OF_WEEK) == 7) {
                  c.unSelect(true);
                } else {
                  c.unSelect(false);
                }
            }
            ((CheckableLayout) v).setChecked(true);
            if (((CheckableLayout) v).isEnabled())
              ((CheckableLayout) v).select();
            if (getOnCellItemClick() != null)
              getOnCellItemClick().onCellClick(v, ((CardGridItem) v.getTag())); // TODO
            // create
            // item
          }
        });
        View cellContent = la.inflate(itemLayout, cell, false);
        cell.addView(cellContent);
        cells.add(cell);
      }
    }

    addView(layout);

    mOnItemRenderDefault = new OnItemRender() {
      @Override
      public void onRender(CheckableLayout v, CardGridItem item) {
        if (v.isEnabled()) {
          v.setText(item.getDayOfMonth().toString());
          Calendar calendar = item.getDate();
          if (calendar.get(Calendar.DAY_OF_WEEK) == 1 || calendar.get(Calendar.DAY_OF_WEEK) == 7) {
            v.setTextColor(getResources().getColor(R.color.sub_green));
          } else {
            v.setTextColor(getResources().getColor(R.color.cart_grey));
          }
          if (mExamType == 3) {
            if (mCompanyDays != null) {
              for (int i = 0; i < mCompanyDays.size(); i++) {
                if (calendar.get(Calendar.MONTH) == mCompanyDays.get(i).getMonth()
                    && calendar.get(Calendar.DATE) == mCompanyDays.get(i).getDate()) {
                  v.setEnabled(true);
                  v.setImageResource(R.drawable.day_normal);
                  break;
                } else {
                  v.setImageResource(R.drawable.day_full);
                  v.setEnabled(false);
                }
              }
            } else {
              v.setImageResource(R.drawable.day_full);
              v.setEnabled(false);
            }
            if (chooseCalendar != null) {
              if (calendar.get(Calendar.YEAR) == chooseCalendar.get(Calendar.YEAR)
                  && calendar.get(Calendar.MONTH) == chooseCalendar.get(Calendar.MONTH)
                  && calendar.get(Calendar.DAY_OF_MONTH) == chooseCalendar.get(Calendar.DAY_OF_MONTH)) {
                ((CheckableLayout) v).select();
              }
            }
          } else {
            if (chooseCalendar != null) {
              if (calendar.get(Calendar.YEAR) == chooseCalendar.get(Calendar.YEAR)
                  && calendar.get(Calendar.MONTH) == chooseCalendar.get(Calendar.MONTH)
                  && calendar.get(Calendar.DAY_OF_MONTH) == chooseCalendar.get(Calendar.DAY_OF_MONTH)) {
                ((CheckableLayout) v).select();
              }
            }
            if ((startCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
                && startCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) && calendar
                .get(Calendar.DAY_OF_MONTH) < startCalendar.get(Calendar.DAY_OF_MONTH))) {
              v.setImageResource(R.drawable.day_full);
              v.setEnabled(false);
            }
            if ((endCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
                && endCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) && calendar
                .get(Calendar.DAY_OF_MONTH) > endCalendar.get(Calendar.DAY_OF_MONTH))) {
              v.setImageResource(R.drawable.day_full);
              v.setEnabled(false);
            }
          }
          if (mFullDates != null) {
            for (int i = 0; i < mFullDates.size(); i++) {
              if (chooseCalendar.get(Calendar.MONTH) == mFullDates.get(i).getMonth()
                  && chooseCalendar.get(Calendar.DAY_OF_MONTH) == mFullDates.get(i).getDate()) {
                continue;
              } else {
                if (calendar.get(Calendar.MONTH) == mFullDates.get(i).getMonth()
                    && calendar.get(Calendar.DATE) == mFullDates.get(i).getDate()) {
                  v.setImageResource(R.drawable.day_full);
                  v.setStatusVisibility(View.VISIBLE);
                  v.setEnabled(false);
                }
              }
            }
          }
        } else {
          v.setText("");
        }
      }
    };
    mOnMonthItemClick = new OnMonthItemClick() {

      @Override
      public void onMonthItemClick(int flag) {
      }
    };
    updateCells();
  }

  private int getDaySpacing(int dayOfWeek) {
    if (Calendar.SUNDAY == dayOfWeek)
      return 6;
    else
      return dayOfWeek - 2;
  }

  private int getDaySpacingEnd(int dayOfWeek) {
    return 8 - dayOfWeek;
  }

  private void updateCells() {
    Calendar cal;
    Integer counter = 0;
    if (dateDisplay != null)
      cal = (Calendar) dateDisplay.clone();
    else
      cal = Calendar.getInstance();

    cal.set(Calendar.DAY_OF_MONTH, 1);

    int daySpacing = getDaySpacing(cal.get(Calendar.DAY_OF_WEEK) + 1);

    // INFO : wrong calculations of first line - fixed
    if (daySpacing > 0) {
      Calendar prevMonth = (Calendar) cal.clone();
      prevMonth.add(Calendar.MONTH, -1);
      prevMonth.set(Calendar.DAY_OF_MONTH, prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH) - daySpacing + 1);
      for (int i = 0; i < daySpacing; i++) {
        CheckableLayout cell = cells.get(counter);
        cell.setTag(new CardGridItem(Integer.valueOf(prevMonth.get(Calendar.DAY_OF_MONTH))).setEnabled(false));
        cell.setEnabled(false);
        (mOnItemRender == null ? mOnItemRenderDefault : mOnItemRender).onRender(cell, (CardGridItem) cell.getTag());
        counter++;
        prevMonth.add(Calendar.DAY_OF_MONTH, 1);
      }
    }

    int firstDay = cal.get(Calendar.DAY_OF_MONTH);
    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    int lastDay = cal.get(Calendar.DAY_OF_MONTH) + 1;
    for (int i = firstDay; i < lastDay; i++) {
      cal.set(Calendar.DAY_OF_MONTH, i - 1);
      Calendar date = (Calendar) cal.clone();
      date.add(Calendar.DAY_OF_MONTH, 1);
      CheckableLayout cell = cells.get(counter);
      cell.setTag(new CardGridItem(i).setEnabled(true).setDate(date));
      cell.setEnabled(true);
      cell.setVisibility(View.VISIBLE);
      (mOnItemRender == null ? mOnItemRenderDefault : mOnItemRender).onRender(cell, (CardGridItem) cell.getTag());
      counter++;
    }

    if (dateDisplay != null)
      cal = (Calendar) dateDisplay.clone();
    else
      cal = Calendar.getInstance();

    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

    daySpacing = getDaySpacingEnd(cal.get(Calendar.DAY_OF_WEEK) + 1);

    if (daySpacing > 0) {
      for (int i = 0; i < daySpacing; i++) {
        CheckableLayout cell = cells.get(counter);
        cell.setTag(new CardGridItem(i + 1).setEnabled(false)); // .setDate((Calendar)cal.clone())
        cell.setEnabled(false);
        cell.setVisibility(View.VISIBLE);
        (mOnItemRender == null ? mOnItemRenderDefault : mOnItemRender).onRender(cell, (CardGridItem) cell.getTag());
        counter++;
      }
    }

    if (counter < cells.size()) {
      for (int i = counter; i < cells.size(); i++) {
        cells.get(i).setVisibility(View.GONE);
      }
    }
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
    if (changed && cells.size() > 0) {
      int size = (r - l) / 7;
      for (CheckableLayout cell : cells) {
        cell.getLayoutParams().height = size;
      }
    }
  }

  public int getItemLayout() {
    return itemLayout;
  }

  public void setItemLayout(int itemLayout) {
    this.itemLayout = itemLayout;
    // mCardGridAdapter.setItemLayout(itemLayout);
  }

  public OnItemRender getOnItemRender() {
    return mOnItemRender;
  }

  public void setOnItemRender(OnItemRender mOnItemRender) {
    this.mOnItemRender = mOnItemRender;
    // mCardGridAdapter.setOnItemRender(mOnItemRender);
  }

  public Calendar getDateDisplay() {
    return dateDisplay;
  }

  public void setDateDisplay(int mExamType, ArrayList<CalendarDate> mCompanyDays, Calendar dateDisplay,
      ArrayList<CalendarDate> fullDates, Calendar startCalendar, Calendar endCalendar, Calendar chooseCalendar) {
    this.mExamType = mExamType;
    this.mCompanyDays = mCompanyDays;
    this.dateDisplay = dateDisplay;
    this.mFullDates = fullDates;
    this.startCalendar = startCalendar;
    this.endCalendar = endCalendar;
    this.chooseCalendar = chooseCalendar;
    init(mContext);
  }

  public OnCellItemClick getOnCellItemClick() {
    return mOnCellItemClick;
  }

  public void setOnCellItemClick(OnCellItemClick mOnCellItemClick) {
    this.mOnCellItemClick = mOnCellItemClick;
  }

  public OnMonthItemClick getmOnMonthItemClick() {
    return this.mOnMonthItemClick;
  }

  public void setmOnMonthItemClick(OnMonthItemClick mOnMonthItemClick) {
    this.mOnMonthItemClick = mOnMonthItemClick;
  }

  /**
   * call after change any input data - to refresh view
   */
  public void notifyChanges() {
    // mCardGridAdapter.init();
    updateCells();
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.ll_cardTitle) {
      if (mOnMonthItemClick != null)
        mOnMonthItemClick.onMonthItemClick(0);
    } else if (v.getId() == R.id.ll_cardTitlePre) {
      if (mOnMonthItemClick != null)
        mOnMonthItemClick.onMonthItemClick(-1);
    } else if (v.getId() == R.id.ll_cardTitlePrePre) {
      if (mOnMonthItemClick != null)
        mOnMonthItemClick.onMonthItemClick(-2);
    } else if (v.getId() == R.id.ll_cardTitleNext) {
      if (mOnMonthItemClick != null)
        mOnMonthItemClick.onMonthItemClick(1);
    } else if (v.getId() == R.id.ll_cardTitleNextNext) {
      if (mOnMonthItemClick != null)
        mOnMonthItemClick.onMonthItemClick(2);
    }

  }

}
