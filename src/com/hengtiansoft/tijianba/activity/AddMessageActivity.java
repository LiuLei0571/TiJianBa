package com.hengtiansoft.tijianba.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.juanliuinformation.lvningmeng.R;

public class AddMessageActivity extends Activity implements OnClickListener, OnDateChangedListener,
    OnTimeChangedListener {
  private EditText mMessageEditText;
  private DatePicker datePicker;
  private TimePicker timePicker;
  private AlertDialog ad;
  private String dateTime;
  private Calendar calendar;
  private TextView mTimeTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_add_message);
    setView();
  }

  private void setView() {
    mMessageEditText = (EditText) findViewById(R.id.edt_meaasge);
    mTimeTextView = (TextView) findViewById(R.id.tv_remind_time);
    findViewById(R.id.iv_calender).setOnClickListener(this);
    findViewById(R.id.tv_cancel).setOnClickListener(this);
    findViewById(R.id.tv_confirm).setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.iv_calender:
        dateTimePicKDialog();
        break;
      case R.id.tv_cancel:
        finish();
        break;
      case R.id.tv_confirm:
        break;
      default:
        break;
    }
  }

  public void init(DatePicker datePicker, TimePicker timePicker) {
    calendar = Calendar.getInstance();
    datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
        this);
    timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
    timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
  }

  public AlertDialog dateTimePicKDialog() {
    LinearLayout dateTimeLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.layout_date_time_picker, null);
    datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
    timePicker = (TimePicker) dateTimeLayout.findViewById(R.id.timepicker);
    init(datePicker, timePicker);
    timePicker.setIs24HourView(true);
    timePicker.setOnTimeChangedListener(this);
    ad = new AlertDialog.Builder(this).setTitle(getString(R.string.title_start_time)).setView(dateTimeLayout)
        .setPositiveButton(getString(R.string.str_confirmed), new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
            mTimeTextView.setText(dateTime);
          }
        }).setNegativeButton(getString(R.string.str_cancel), new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
            mTimeTextView.setText("");
          }
        }).show();
    Button positiveButton = ad.getButton(DialogInterface.BUTTON_POSITIVE);
    positiveButton.setTextColor(getResources().getColor(R.color.org_orange));
    Button negativeButton = ad.getButton(DialogInterface.BUTTON_NEGATIVE);
    negativeButton.setTextColor(getResources().getColor(R.color.cart_grey));
    onDateChanged(null, 0, 0, 0);
    return ad;
  }

  public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
    onDateChanged(null, 0, 0, 0);
  }

  public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(),
        timePicker.getCurrentMinute());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.getDefault());
    dateTime = sdf.format(calendar.getTime());
  }
}
