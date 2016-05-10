package com.hengtiansoft.tijianba.activity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.LimitLine;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.YLabels;
import com.github.mikephil.charting.utils.XLabels.XLabelPosition;
import com.github.mikephil.charting.utils.YLabels.YLabelPosition;
import com.hengtiansoft.tijianba.net.response.BookletDetailItem;
import com.hengtiansoft.tijianba.net.response.BookletDetailListener;
import com.hengtiansoft.tijianba.net.response.BookletTypeListener;
import com.hengtiansoft.tijianba.net.response.HealthHomeBookletDate;
import com.hengtiansoft.tijianba.net.response.ReturnFromServerListener;
import com.hengtiansoft.tijianba.util.ImageProgressBar;
import com.juanliuinformation.lvningmeng.R;

@SuppressLint("SimpleDateFormat")
public class CubicLineChartActivity extends BaseActivity implements OnClickListener {
  private LineChart mChart;
  private Intent intent;
  private String mTitle;
  private int id;
  private TextView mTvTitle, mTvChartName, mTvMin, mTvMax, mTvNewsDate, mTvToday, mTvUnit, mTvNormal, mTvAdivace,
      mTvWeight, mTvClinic, mTvViewMin, mTvViewMax;
  private BookletDetailItem bookletDetail;
  private CheckBox mCbShow;
  private String mMax, mMin;
  private int type;
  private FrameLayout mFlytEdit;
  private FrameLayout mFlytAdd;
  private RelativeLayout mRlytWeight;
  private float max;
  private float min;
  private boolean isOk = false;
  private int valueType;
  private ImageProgressBar mProgress;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_linechart);
    intent = getIntent();
    mTitle = intent.getStringExtra("name");
    id = intent.getIntExtra("id", 0);
    intiView();
  }

  private void intiView() {
    mProgress = (ImageProgressBar) findViewById(R.id.image_progress);
    mProgress.setProgress(this, 60,mProgress.getLeft());
    mTvViewMin = (TextView) findViewById(R.id.tv_view_min);
    mTvViewMax = (TextView) findViewById(R.id.tv_view_max);
    mTvClinic = (TextView) findViewById(R.id.tv_cicle);
    mFlytAdd = (FrameLayout) findViewById(R.id.flyt_add_data);
    mFlytAdd.setOnClickListener(this);
    mFlytEdit = (FrameLayout) findViewById(R.id.flyt_edit_date);
    mFlytEdit.setOnClickListener(this);
    mCbShow = (CheckBox) findViewById(R.id.cb_show);
    mTvTitle = (TextView) findViewById(R.id.tv_title);
    mTvTitle.setText(mTitle);
    mTvChartName = (TextView) findViewById(R.id.et_chart_name);
    mTvChartName.setText(mTitle);
    mTvMin = (TextView) findViewById(R.id.tv_chart_min);
    mTvMax = (TextView) findViewById(R.id.tv_chart_max);
    mTvNewsDate = (TextView) findViewById(R.id.tv_chart_date);
    mTvToday = (TextView) findViewById(R.id.tv_chart_num);
    mTvUnit = (TextView) findViewById(R.id.tv_chart_unit);
    mTvNormal = (TextView) findViewById(R.id.tv_normal_num);
    mTvAdivace = (TextView) findViewById(R.id.tv_adivace);
    mChart = (LineChart) findViewById(R.id.my_chart);
    mRlytWeight = (RelativeLayout) findViewById(R.id.rlyt_weight);
    mTvWeight = (TextView) findViewById(R.id.tv_normal_num_weight);
    RadioGroup radiogroup = (RadioGroup) findViewById(R.id.rg_peroid);
    radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
          case R.id.rb_week:
            mChart.clear();
            type = 0;
            getBookType(9, 7);
            mChart.animateXY(1000, 1000);
            mFlytAdd.setVisibility(View.VISIBLE);
            mFlytEdit.setVisibility(View.VISIBLE);
            break;
          case R.id.rb_month:
            mChart.clear();
            type = 1;
            getBookType(31, 28);
            mChart.animateXY(1000, 1000);
            mFlytAdd.setVisibility(View.GONE);
            mFlytEdit.setVisibility(View.GONE);
            break;
          case R.id.rb_year:
            mChart.clear();
            type = 2;
            getBookType(15, 12);
            mChart.animateXY(1000, 1000);
            mFlytAdd.setVisibility(View.GONE);
            mFlytEdit.setVisibility(View.GONE);
            break;
          default:
            break;
        }
      }
    });
    mChart.setStartAtZero(true);
    mChart.setDrawYValues(false);
    mChart.setDrawLegend(false);
    mChart.setDescription("");
    mChart.setHighlightEnabled(true);
    mChart.setTouchEnabled(false);
    mChart.setDragEnabled(false);
    mChart.setScaleEnabled(false);
    mChart.setPinchZoom(false);
    mChart.setDrawGridBackground(false);
    mChart.setDrawVerticalGrid(false);
    mChart.setDrawHorizontalGrid(false);
    mChart.setDoubleTapToZoomEnabled(false);
    mChart.setDrawYLabels(false);
    mChart.setGridWidth(2);
    mChart.setScaleMinima(0, 0);
    XLabels x = mChart.getXLabels();
    x.setAvoidFirstLastClipping(true);
    x.setTextSize(9f);
    x.setSpaceBetweenLabels(0);
    x.setTextColor(Color.rgb(255, 255, 255));
    x.setPosition(XLabelPosition.BOTTOM);
    x.setCenterXLabelText(false);
    YLabels y = mChart.getYLabels();
    y.setShowOnlyMinMax(true);
    y.setPosition(YLabelPosition.RIGHT);
    y.setTextColor(Color.rgb(255, 255, 255));
    y.setTextSize(9f);
    mCbShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          setShow(1);
        } else {
          setShow(0);
        }
      }
    });
    getBookle();
  }

  private void setData(int count, int num, String mMax, String mMin, ArrayList<HealthHomeBookletDate> dataList) {
    if (mMax == null || mMax.equals("")) {
      max = 0.0f;
    } else {
      max = Float.parseFloat(mMax);
      // BigDecimal b = new BigDecimal(max);
      // max = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }
    if (mMin == null || mMin.equals("")) {
      min = 0.0f;
    } else {
      min = Float.parseFloat(mMin);
      // BigDecimal b = new BigDecimal(min);
      // min = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }
    float middle = (max + min) / 2;
    float dec = (max - min) / 5;
    HealthHomeBookletDate bookletDate = null;
    ArrayList<String> xVals = new ArrayList<String>();
    ArrayList<Entry> vals1 = new ArrayList<Entry>();
    int start = (count - num) / 2;
    int number = num + start;
    if (type == 0) {
      number -= 1;
    }
    for (int i = 0; i < count; i++) {
      if (i < start || i > number) {
        xVals.add("");
      } else {
        bookletDate = dataList.get(i - start);
        xVals.add(getTime(i, type, bookletDate.getDataTime()));
        if (bookletDate.getValue().equals("") == false) {
          float data = Float.parseFloat(bookletDate.getValue());
          if (data > middle) {
            data -= dec;
          } else {
            data += dec;
          }
          vals1.add(new Entry(data, i));
        }
      }
    }
    LineDataSet set1 = new LineDataSet(vals1, "");
    set1.setCubicIntensity(0.2f);
    set1.setDrawFilled(true);
    set1.setDrawCircles(true);
    set1.setLineWidth(1f);
    set1.setCircleSize(2.5f);
    set1.setCircleColor(Color.rgb(255, 255, 255));
    set1.setFillColor(Color.rgb(202, 240, 239));
    set1.setColor(Color.rgb(255, 255, 255));
    ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
    dataSets.add(set1);

    LineData data = new LineData(xVals, dataSets);
    LimitLine ll1;
    if (max == min && max != 0) {
      float a = max / 10;
      max = max + a;
      min = min - a;
    }
    this.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        if (min == 0 && max == 0) {
          mChart.setYRange(-1, 1, true);
        } else {
          mChart.setYRange(min, max, true);
        }
      }
    });
    ll1 = new LimitLine(middle);
    ll1.setLineWidth(0.2f);
    ll1.setLineColor(Color.rgb(255, 255, 255));
    ll1.enableDashedLine(10f, 5f, 0f);
    ll1.setDrawValue(false);
    data.addLimitLine(ll1);
    mChart.setData(data);
  }

  private void getBookle() {
    remoteDataManager.bookletDetailListener = new BookletDetailListener() {
      @Override
      public void onSuccess(final BookletDetailItem bookletDetailItem) {
        CubicLineChartActivity.this.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            bookletDetail = bookletDetailItem;
            valueType = bookletDetailItem.getValueType();
            setView(bookletDetailItem);
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
      showProgressDialog("图表", "加载中...");
      remoteDataManager.getBookletDetail(id);
    }
  }

  private void setShow(int isShow) {
    remoteDataManager.bookletSwitchListener = new ReturnFromServerListener() {

      @Override
      public void onSuccess(String message) {
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
      }
    };
    if (validateInternet()) {
      remoteDataManager.showBookletSwitch(id, isShow);
    }
  }

  private void setView(BookletDetailItem bookletDetailItem) {
    mTvMin.setText(bookletDetailItem.getValueMin());
    mTvMax.setText(bookletDetailItem.getValueMax());
    mTvViewMax.setText(bookletDetailItem.getValueMax());
    mTvViewMin.setText(bookletDetailItem.getValueMin());
    mTvUnit.setText(bookletDetailItem.getUnit());
    mTvNormal.setText(bookletDetailItem.getNormalMin() + "~" + bookletDetailItem.getNormalMax());
    mTvAdivace.setText(bookletDetailItem.getAdvice());
    if (bookletDetailItem.getIsShow() == 1) {
      mCbShow.setChecked(true);
    } else {
      mCbShow.setChecked(false);
    }
    if (bookletDetailItem.getNewestDataDate() != null) {
      mTvNewsDate.setText(getTime(0, 4, bookletDetailItem.getNewestDataDate()));

    } else {
      // mTvNewsDate.setText("暂无数据~");
    }
    mTvToday.setText(bookletDetailItem.getNewestData() + " ");
    mTvUnit.setText(bookletDetailItem.getUnit());
    mMax = bookletDetailItem.getValueMax();
    mMin = bookletDetailItem.getValueMin();
    mTvClinic.setText(bookletDetailItem.getClinic());
    if (bookletDetailItem.getName().equals("体重") || bookletDetailItem.getName().equals("身高")) {
      mRlytWeight.setVisibility(View.VISIBLE);
      if (bookletDetailItem.getBmiValue().equals("") == false) {
        float weight = Float.parseFloat(bookletDetailItem.getBmiValue());
        BigDecimal b = new BigDecimal(weight);
        weight = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        mTvWeight.setText(String.valueOf(bookletDetailItem.getBmiValue()));
        mTvAdivace.setText(bookletDetailItem.getBmiAdvice());
        mTvClinic.setText(bookletDetailItem.getBmiClinic());
      }
    } else {
      mRlytWeight.setVisibility(View.GONE);
    }
    setData(9, 7, mMax, mMin, bookletDetailItem.getDataList());
  }

  @SuppressLint("SimpleDateFormat")
  private String change(String newestData) {
    Date date = null;
    try {
      date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(newestData);
      return new SimpleDateFormat("yyyy-MM-dd").format(date);
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  private void getBookType(final int count, final int num) {
    remoteDataManager.bookletTypeListener = new BookletTypeListener() {
      @Override
      public void onSuccess(final ArrayList<HealthHomeBookletDate> bookletTypeList) {
        CubicLineChartActivity.this.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            setData(count, num, mMax, mMin, bookletTypeList);
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
      showProgressDialog("图表", "加载中...");
      remoteDataManager.getBookletType(id, type);
    }
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent();
    switch (v.getId()) {
      case R.id.flyt_edit_date:
        isOk = true;
        intent.setClass(CubicLineChartActivity.this, RecordDateActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("unit", bookletDetail.getUnit());
        intent.putExtra("valueType", valueType);
        intent.putExtra("mMax", bookletDetail.getRangeMax());
        intent.putExtra("mMin", bookletDetail.getRangeMin());
        startActivity(intent);
        break;
      case R.id.flyt_add_data:
        isOk = true;
        intent.setClass(CubicLineChartActivity.this, AddDataActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("state", 2);
        intent.putExtra("valueType", valueType);
        intent.putExtra("mMax", bookletDetail.getRangeMax());
        intent.putExtra("mMin", bookletDetail.getRangeMin());
        intent.putExtra("unit", bookletDetail.getUnit());
        startActivity(intent);
        break;
      default:
        break;
    }
  }

  String[] same = null;
  String[] differ = null;

  private String getTime(int i, int type, String str) {
    String dateTime = "";
    Date date = null;
    try {
      date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
    } catch (ParseException e) {
    }
    switch (type) {
      case 0:
        if (i == 1) {
          dateTime = new SimpleDateFormat("M" + "月" + "d" + "日").format(date);
          same = dateTime.split("月");
        } else {
          dateTime = new SimpleDateFormat("M" + "月" + "d" + "日").format(date);
          differ = dateTime.split("月");
          if (same[0].equals(differ[0])) {
            dateTime = new SimpleDateFormat("d").format(date);
          } else {
            same[0] = differ[0];
          }
        }
        break;
      case 1:
        if (i == 1) {
          same = dateTime.split("月");
        }
        if ((i - 1) % 7 == 0) {
          dateTime = new SimpleDateFormat("M" + "月" + "d" + "日").format(date);
          differ = dateTime.split("月");
          if (same[0].equals(differ[0])) {
            dateTime = new SimpleDateFormat("d" + "日").format(date);
          } else {
            same[0] = differ[0];
          }
        }
        break;
      case 2:
        if (i == 1) {
          same = dateTime.split("年");
        }
        if ((i - 1) % 3 == 0) {
          dateTime = new SimpleDateFormat("yy" + "年" + "M" + "月").format(date);
          differ = dateTime.split("年");
          if (same[0].equals(differ[0])) {
            dateTime = new SimpleDateFormat("M" + "月").format(date);
          } else {
            same[0] = differ[0];
          }
        }
        break;
      case 4:
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        dateTime = new SimpleDateFormat("yyyy-MM-dd").format(date);
        if (dateTime.equals(df.format(new Date()))) {
          dateTime = "今天";
        } else {
          dateTime = new SimpleDateFormat("M" + "月" + "d" + "日").format(date);
        }
        break;

      default:
        break;
    }
    return dateTime;
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (isOk) {
      getBookle();
      isOk = false;
    }
    mChart.animateXY(2000, 2000);
    mChart.invalidate();
  }
}
