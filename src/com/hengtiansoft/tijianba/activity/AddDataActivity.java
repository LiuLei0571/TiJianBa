package com.hengtiansoft.tijianba.activity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import com.hengtiansoft.tijianba.net.response.BookData;
import com.hengtiansoft.tijianba.net.response.BookletAdd;
import com.hengtiansoft.tijianba.net.response.ReturnFromServerListener;
import com.hengtiansoft.tijianba.widget.DataDatePickerDialog;
import com.juanliuinformation.lvningmeng.R;

@SuppressLint("SimpleDateFormat")
public class AddDataActivity extends BaseActivity implements OnClickListener {
  private TextView mTvDate, mTvTime, mTvUnit, mTvTitle, mTvEdit;
  private EditText mEtData;
  private KeyboardView keyboardView;
  private Keyboard k1;// 字母键盘
  private String mUnit;
  private String mMax, mMin;
  private Intent intent;
  private int state, id;
  private BookletAdd mBookletAdd;
  private BookData bookData;
  private boolean isOk = true;
private int valueType=0;

private int year,month,day;
private DataDatePickerDialog mDatePickerDialog;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_add_data);
    intent = getIntent();
    state = intent.getIntExtra("state", 0);
    id = intent.getIntExtra("id", 0);
    mUnit = intent.getStringExtra("unit");
    mMax = intent.getStringExtra("mMax");
    valueType=intent.getIntExtra("valueType", 0);
    mMin = intent.getStringExtra("mMin");
    initView();
    setView();
  }

  private void initView() {
    mTvTitle = (TextView) findViewById(R.id.tv_title);
    mTvEdit = (TextView) findViewById(R.id.tv_edit);
    mTvDate = (TextView) findViewById(R.id.tv_date);
    mTvTime = (TextView) findViewById(R.id.tv_time);
    mTvUnit = (TextView) findViewById(R.id.tv_unit_name);
    mEtData = (EditText) findViewById(R.id.et_unit);
    keyboardView = (KeyboardView) this.findViewById(R.id.keyboard_view);
    hideSoftInputMethod(mEtData);
    Calendar calendar=Calendar.getInstance();
    year=calendar.get(Calendar.YEAR);
    month=calendar.get(Calendar.MONTH);
    day=calendar.get(Calendar.DAY_OF_MONTH);
  }

  private void setView() {
    mEtData.setOnClickListener(this);
    k1 = new Keyboard(this, R.xml.keybroad);
    keyboardView.setKeyboard(k1);
    keyboardView.setOnKeyboardActionListener(listener);
    mTvUnit.setText(mUnit);
    if (state == 1) {
      mTvTitle.setText("编辑数据");
      mTvEdit.setText("完成");
      bookData = (BookData) intent.getSerializableExtra("bookData");
      mTvDate.setText(setTime(1, bookData.getDataTime()));
      mTvTime.setText(setTime(2, bookData.getDataTime()));
      mEtData.setText(String.valueOf(bookData.getValue()));
    } else if (state == 2) {
//      mTvDate.setOnClickListener(this);
      mTvTitle.setText("添加数据");
      mTvEdit.setText("添加");
      mTvDate.setText(getDate(1,""));
      mTvTime.setText(getDate(2,""));
      mEtData.setHint("范围" + mMin + "~" + mMax);
    }
    mTvEdit.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.tv_date:
        OnDateSetListener dateListener=new OnDateSetListener() {
          
          @Override
          public void onDateSet(DatePicker view, int yea, int monthOfYear, int dayOfMonth) {
            year=yea;
            month=monthOfYear;
            day=dayOfMonth;
            mTvDate.setText(setTime(1, year+"-"+(monthOfYear+1)+"-"+dayOfMonth+" 00:00:00"));
          }
        };
        mDatePickerDialog = new DataDatePickerDialog(this, dateListener,year,month,day );
        mDatePickerDialog.show();
        break;
      case R.id.et_unit:
        if (keyboardView.getVisibility() == View.VISIBLE) {
          keyboardView.setVisibility(View.INVISIBLE);
        } else {
          keyboardView.setVisibility(View.VISIBLE);
        }
        break;
      case R.id.tv_edit:
        if (state == 1) {
          doEdit();

        } else if (state == 2) {
          doAdd();
        }
        break;
      default:
        break;
    }
  }

  private void doEdit() {
    isOk = true;
    if (mEtData.getText().toString().equals("")) {
      mEtData.setError("不能为空");
      isOk = false;
    } else {
      if (valueType==1) {
        if (mEtData.getText().toString().matches("^[0-9]*[0-9]*$") != true) {
          mEtData.setError("不能输入小数");
          isOk = false;
        }
      } else if(valueType==0){
        if (mEtData.getText().toString().matches("^(-?\\d+)(\\.\\d+)?$") != true) {
          mEtData.setError("请输入正确的格式");
          isOk = false;
        }
      }
      if ((mMax.equals("") == true) && (mMin.equals("") == true)) {
      } else if ((mMax.equals("") == true)) {
        if (Float.parseFloat(mEtData.getText().toString().trim()) < Float.parseFloat(mMin)) {
          mEtData.setError("请输入正确的范围" + mMin + "~");
          isOk = false;
        }
      } else if ((mMin.equals("") == true)) {
        if (Float.parseFloat(mEtData.getText().toString().trim()) > Float.parseFloat(mMax)) {
          mEtData.setError("请输入正确的范围" + "~" + mMax);
          isOk = false;
        }
      } else if ((mMax.equals("") != true) && (mMin.equals("") != true)) {
        if ((Float.parseFloat(mEtData.getText().toString().trim()) < Float.parseFloat(mMin))
            || (Float.parseFloat(mEtData.getText().toString().trim()) > Float.parseFloat(mMax))) {
          mEtData.setError("请输入正确的范围" + mMin + "~" + mMax);
          isOk = false;
        }
      }
    }
    if (isOk) {
      updataBooklet(id, bookData.getBookletId(), Float.parseFloat(mEtData.getText().toString()), bookData.getDataTime());
    }

  }

  private void doAdd() {
    isOk = true;
    mBookletAdd = new BookletAdd();
    if (mEtData.getText().toString().equals("")) {
      mEtData.setError("不能为空");
      isOk = false;
    } else {
      if (valueType==1) {
        if (mEtData.getText().toString().matches("^[0-9]*[0-9]*$") != true) {
          mEtData.setError("不能输入小数");
          isOk = false;
        }
      } else if(valueType==0){
        if (mEtData.getText().toString().matches("^(-?\\d+)(\\.\\d+)?$") != true) {
          mEtData.setError("输入格式有误");
          isOk = false;
        }
      }
      if ((mMax.equals("") == true) && (mMin.equals("") == true)) {
      } else if ((mMax.equals("") == true)) {
        if (Float.parseFloat(mEtData.getText().toString().trim()) < Float.parseFloat(mMin)) {
          mEtData.setError("请输入正确的范围" + mMin + "~");
          isOk = false;
        }
      } else if ((mMin.equals("") == true)) {
        if (Float.parseFloat(mEtData.getText().toString().trim()) > Float.parseFloat(mMax)) {
          mEtData.setError("请输入正确的范围" + "~" + mMax);
          isOk = false;
        }
      } else if ((mMax.equals("") != true) && (mMin.equals("") != true)) {
        if ((Float.parseFloat(mEtData.getText().toString().trim()) < Float.parseFloat(mMin))
            || (Float.parseFloat(mEtData.getText().toString().trim()) > Float.parseFloat(mMax))) {
          mEtData.setError("请输入正确的范围" + mMin + "~" + mMax);
          isOk = false;
        }
      }
    }
    if (isOk) {
      mBookletAdd.setId(id);
      mBookletAdd.setValue(Float.parseFloat(mEtData.getText().toString()));
      mBookletAdd.setDataTime(getDate(3,mTvDate.getText().toString()));
      addBooklet(mBookletAdd);
    }
  }

  public void addBooklet(BookletAdd mBookletAdd) {
    remoteDataManager.bookletAddListener = new ReturnFromServerListener() {
      @Override
      public void onSuccess(String message) {
        AddDataActivity.this.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            AddDataActivity.this.finish();
          }
        });

      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        handleError(errorMessage);
      }
    };
    if (validateInternet()) {
      showProgressDialog(getString(R.string.str_booklet_add), getString(R.string.str_wait));
      remoteDataManager.doAddBooklet(mBookletAdd);
    }
  }

  public void updataBooklet(int id, int bookletId, float value, String dataTime) {
    remoteDataManager.bookletUpdateListener = new ReturnFromServerListener() {

      @Override
      public void onSuccess(String message) {
        AddDataActivity.this.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            AddDataActivity.this.finish();
            dismissProgressDialog();
          }
        });
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        dismissProgressDialog();
      }
    };
    if (validateInternet()) {
      showProgressDialog("数据", "上传中...");
      remoteDataManager.updateBooklet(id, bookletId, value, dataTime);
    }
  }

  private OnKeyboardActionListener listener = new OnKeyboardActionListener() {
    @Override
    public void swipeUp() {
    }

    @Override
    public void swipeRight() {
    }

    @Override
    public void swipeLeft() {
    }

    @Override
    public void swipeDown() {
    }

    @Override
    public void onText(CharSequence text) {
    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onPress(int primaryCode) {
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
      Editable editable = mEtData.getText();
      int start = mEtData.getSelectionStart();
      if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
          keyboardView.setVisibility(View.INVISIBLE);
        }
      } else if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
        if (editable != null && editable.length() > 0) {
          if (start > 0) {
            editable.delete(start - 1, start);
          }
        }
      } else if (primaryCode == 222222) {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
          keyboardView.setVisibility(View.INVISIBLE);
        }
      } else {
        editable.insert(start, Character.toString((char) primaryCode));
      }
    }

  };

  private String getDate(int code,String str) {
    if (code == 1) {
      SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd");
      return df.format(new Date());
    } else if (code == 2) {
      SimpleDateFormat df = new SimpleDateFormat("HH:mm");
      return df.format(new Date());
    } else {
      java.util.Date date = null;
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd");
      try {
        date = sdf.parse(str);
      } catch (ParseException e) {
      }
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
      return df.format(date);
    }
  }

  private String setTime(int code, String str) {
    java.util.Date date = null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    try {
      date = sdf.parse(str);
    } catch (ParseException e) {
    }
    if (code == 1) {
      SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd");
      return df.format(date);
    } else if (code == 2) {
      SimpleDateFormat df = new SimpleDateFormat("HH:mm");
      return df.format(date);
    } else {
      return "";
    }
  }

  /**
   * 显示自定义软键盘的的光标
   * */
  public void hideSoftInputMethod(EditText ed) {
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    int currentVersion = android.os.Build.VERSION.SDK_INT;
    String methodName = null;
    if (currentVersion >= 16) {
      methodName = "setShowSoftInputOnFocus";
    } else if (currentVersion >= 14) {
      methodName = "setSoftInputShownOnFocus";
    }

    if (methodName == null) {
      ed.setInputType(InputType.TYPE_NULL);
    } else {
      Class<EditText> cls = EditText.class;
      Method setShowSoftInputOnFocus;
      try {
        setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);
        setShowSoftInputOnFocus.setAccessible(true);
        setShowSoftInputOnFocus.invoke(ed, false);
      } catch (NoSuchMethodException e) {
        ed.setInputType(InputType.TYPE_NULL);
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    dismissProgressDialog();
  }

  @Override
  public void onBackPressed() {
    // super.onBackPressed();
    if (keyboardView.getVisibility() == View.VISIBLE) {
      keyboardView.setVisibility(View.GONE);
    } else {
      this.finish();
    }
  }
}
