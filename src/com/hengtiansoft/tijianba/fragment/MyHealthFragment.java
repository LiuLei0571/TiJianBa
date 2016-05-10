package com.hengtiansoft.tijianba.fragment;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.LimitLine;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.XLabels.XLabelPosition;
import com.github.mikephil.charting.utils.YLabels;
import com.github.mikephil.charting.utils.YLabels.YLabelPosition;
import com.hengtiansoft.tijianba.activity.AddHealthReportActivity;
import com.hengtiansoft.tijianba.activity.BasicInforActivity;
import com.hengtiansoft.tijianba.activity.CubicLineChartActivity;
import com.hengtiansoft.tijianba.activity.HealthBookActivity;
import com.hengtiansoft.tijianba.activity.LoginActivity;
import com.hengtiansoft.tijianba.activity.MainActivity.OnCallMyhealthFragmentListener;
import com.hengtiansoft.tijianba.activity.ReportActivity;
import com.hengtiansoft.tijianba.activity.ReportDetailsActivity;
import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.HealthHome;
import com.hengtiansoft.tijianba.net.response.HealthHomeBookletDate;
import com.hengtiansoft.tijianba.net.response.HealthHomeBookletItem;
import com.hengtiansoft.tijianba.net.response.HealthHomeBookletListener;
import com.hengtiansoft.tijianba.net.response.HealthHomeListener;
import com.hengtiansoft.tijianba.net.response.HealthUser;
import com.hengtiansoft.tijianba.net.response.Report;
import com.hengtiansoft.tijianba.util.CircleImageView;
import com.hengtiansoft.tijianba.util.UpdateManage;
import com.juanliuinformation.lvningmeng.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

@SuppressLint("SimpleDateFormat")
public class MyHealthFragment extends BaseFragment implements OnClickListener, OnCallMyhealthFragmentListener {
  private View mView;
  private TextView mTvBill, mTvReport;
  private RelativeLayout mRlytLoginInfo, mRlytLoginBill, mRlytLoginReport;
  private RelativeLayout mRlytUnloginInfo, mRlytUnloginBill, mRlytUnloginReport;
  private RelativeLayout mRlytBtnInfo, mRlytBtnBill, mRlytBtnReport;
  private HealthUser mHealthUser;
  private Report mExamReport;
  private CircleImageView mImgLogo;
  private TextView mTvName, mTvCion, mTvTask;
  private TextView mReportName, mReportTime, mReportWeek;
  private ImageView mImageSex;
  private LinearLayout mLlytChart;
  private int pageNum = 1;
  private ArrayList<HealthHomeBookletItem> mBookletList = new ArrayList<HealthHomeBookletItem>();
  private ArrayList<HealthHomeBookletItem> mBookletItemList = new ArrayList<HealthHomeBookletItem>();
  private boolean isFirst = true;
  private TextView mTvWatchList, mTvMore;
  private RelativeLayout mRlytReport;
  private int id;
  private Float mMin, mMax;
  private String[] mWeek = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
  private static UpdateManage mUp;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    mView = inflater.inflate(R.layout.main_my_health, container, false);
    initView();
    return mView;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  private void initView() {
    mUp = new UpdateManage(getActivity());
    mRlytReport = (RelativeLayout) mView.findViewById(R.id.rl_report);
    mRlytReport.setOnClickListener(this);
    mTvMore = (TextView) mView.findViewById(R.id.tv_bill_addmore);
    mTvMore.setOnClickListener(this);
    mLlytChart = (LinearLayout) mView.findViewById(R.id.llyt_chart);
    mImgLogo = (CircleImageView) mView.findViewById(R.id.img_login_mylogo);
    mImgLogo.setBorderColor(getResources().getColor(R.color.my_green));
    mImgLogo.setBorderWidth(4);
    mTvName = (TextView) mView.findViewById(R.id.tv_login_myname);
    mTvCion = (TextView) mView.findViewById(R.id.tv_cion);
    mTvTask = (TextView) mView.findViewById(R.id.tv_task);
    mImageSex = (ImageView) mView.findViewById(R.id.img_sex);
    mImageSex.setOnClickListener(this);
    mImgLogo.setOnClickListener(this);
    mTvName.setOnClickListener(this);
    mTvCion.setOnClickListener(this);
    mTvTask.setOnClickListener(this);
    mReportName = (TextView) mView.findViewById(R.id.tv_report_name);
    mReportTime = (TextView) mView.findViewById(R.id.tv_report_create_time);
    mReportWeek = (TextView) mView.findViewById(R.id.tv_report_create_week);
    mRlytLoginInfo = (RelativeLayout) mView.findViewById(R.id.rlyt_login_info);
    mRlytLoginBill = (RelativeLayout) mView.findViewById(R.id.rl_login_bill);
    mRlytLoginReport = (RelativeLayout) mView.findViewById(R.id.rl_login_report);
    mRlytUnloginInfo = (RelativeLayout) mView.findViewById(R.id.rlyt_unlogin_info);
    mRlytUnloginBill = (RelativeLayout) mView.findViewById(R.id.rl_unlogin_bill);
    mRlytUnloginReport = (RelativeLayout) mView.findViewById(R.id.rl_unlogin_report);
    mRlytBtnInfo = (RelativeLayout) mView.findViewById(R.id.rlyt_btn_info);
    mRlytBtnBill = (RelativeLayout) mView.findViewById(R.id.rlyt_btn_bill);
    mRlytBtnReport = (RelativeLayout) mView.findViewById(R.id.rlyt_btn_report);
    mRlytBtnInfo.setOnClickListener(this);
    mRlytBtnBill.setOnClickListener(this);
    mRlytBtnReport.setOnClickListener(this);
    mTvBill = (TextView) mView.findViewById(R.id.tv_health_bill);
    mTvReport = (TextView) mView.findViewById(R.id.tv_health_report);
    mTvBill.setOnClickListener(this);
    mTvReport.setOnClickListener(this);
    mTvWatchList = (TextView) mView.findViewById(R.id.tv_watchlist);
    mTvWatchList.setOnClickListener(this);
    options = new DisplayImageOptions.Builder().cacheInMemory(false).cacheOnDisk(false).considerExifParams(true)
        .bitmapConfig(Bitmap.Config.RGB_565).resetViewBeforeLoading(false).displayer(new SimpleBitmapDisplayer())
        .build();
  }

  private void setView() {
    if (remoteDataManager.isLogin) {
      getMyHealth();
      getMyBill();
    }
  }

  @Override
  public void onResume() {
    isFirst = true;
    mTvMore.setVisibility(View.VISIBLE);
    setView();
    super.onResume();
  }

  // TODO Auto-generated method stub
  @Override
  public void onClick(View v) {
    Intent intent = new Intent();
    switch (v.getId()) {
      case R.id.rlyt_btn_info:
        if (remoteDataManager.isLogin) {
          intent.setClass(getActivity(), BasicInforActivity.class);
          startActivity(intent);
        } else {
          intent.setClass(getActivity(), LoginActivity.class);
          intent.putExtra("isGotoBasicInfo", true);
          startActivity(intent);
        }
        break;
      case R.id.rlyt_btn_bill:
        if (remoteDataManager.isLogin) {
          intent.setClass(getActivity(), HealthBookActivity.class);
        } else {
          intent.setClass(getActivity(), LoginActivity.class);
          intent.putExtra("isGotoBill", true);
        }
        startActivity(intent);
        break;
      case R.id.rlyt_btn_report:
        if (remoteDataManager.isLogin) {
          intent.setClass(getActivity(), AddHealthReportActivity.class);
        } else {
          intent.setClass(getActivity(), LoginActivity.class);
          intent.putExtra("isGotoReport", true);
        }
        startActivity(intent);
        break;
      case R.id.tv_health_bill:
        if (remoteDataManager.isLogin) {
          intent.setClass(getActivity(), HealthBookActivity.class);
        } else {
          intent.setClass(getActivity(), LoginActivity.class);
          intent.putExtra("isGotoBill", true);
        }
        startActivity(intent);
        break;
      case R.id.tv_health_report:
        if (remoteDataManager.isLogin) {
          intent.setClass(getActivity(), ReportActivity.class);
        } else {
          intent.setClass(getActivity(), LoginActivity.class);
          intent.putExtra("isGotoReport", true);
        }
        startActivity(intent);
        break;
      case R.id.img_login_mylogo:
        intent.setClass(getActivity(), BasicInforActivity.class);
        intent.putExtra("healthUser", mHealthUser);
        startActivity(intent);
        break;
      case R.id.tv_login_myname:
        intent.setClass(getActivity(), BasicInforActivity.class);
        startActivity(intent);
        break;
      case R.id.img_sex:
        intent.setClass(getActivity(), BasicInforActivity.class);
        startActivity(intent);
        break;
      case R.id.tv_cion:
        // intent.setClass(getActivity(), BasicInforActivity.class);
        // startActivity(intent);
        break;
      case R.id.tv_task:
        // intent.setClass(getActivity(), BasicInforActivity.class);
        // startActivity(intent);
        break;
      case R.id.tv_bill_addmore:
        mLlytChart.removeAllViews();
        if (pageNum == 1) {
          pageNum += 1;
          mTvMore.setText("收回更多");
        } else {
          pageNum = 1;
          mTvMore.setText("加载更多");
        }
        mLlytChart.removeAllViews();
        getMyBill();
        break;
      case R.id.tv_watchlist:
        intent.setClass(getActivity(), ReportActivity.class);
        startActivity(intent);
        break;
      case R.id.rl_report:
        intent.setClass(getActivity(), ReportDetailsActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
        break;
      default:
        break;
    }
  }

  // TODO
  private void getMyBill() {
    remoteDataManager.healthHomeBookletListener = new HealthHomeBookletListener() {

      @Override
      public void onSuccess(final ArrayList<HealthHomeBookletItem> healthHomeBookletItem) {
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            dismissProgressDialog();
            mBookletItemList.clear();
            mBookletList.addAll(healthHomeBookletItem);
            mBookletItemList = healthHomeBookletItem;
            setBill();
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
      showProgressDialog("小账本", "加载中");
      String version = getAppVersionName(getActivity());
      remoteDataManager.getHealthHomeBooklet(pageNum, 3, version, 1);
    }
  }

  public static String getAppVersionName(Context context) {
    String versionName = mUp.getVersionName(context);
    return versionName;
  }

  private void getMyHealth() {
    remoteDataManager.healthHomeListener = new HealthHomeListener() {
      @Override
      public void onSuccess(final HealthHome healthHome) {
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            dismissProgressDialog();
            mHealthUser = healthHome.getHealthUser();
            mExamReport = healthHome.getExamReport();
            setInfo();
            setReport();
          }
        });
      }

      public void onError(String errorCode, String errorMessage) {
        dismissProgressDialog();
      }
    };
    if (validateInternet()) {
      showProgressDialog("我的健康", "加载中");
      remoteDataManager.getHealthHome();
    }
  }

  private void setBill() {
    if (mBookletList == null) {
    } else {
      if (mBookletItemList.size() == 0) {
        mTvMore.setVisibility(View.GONE);
      } else {
        if (isFirst) {
          mLlytChart.removeAllViews();
        }
        isFirst = false;
        mRlytLoginBill.setVisibility(View.VISIBLE);
        mRlytUnloginBill.setVisibility(View.GONE);
        for (int i = 0; i < mBookletItemList.size(); i++) {
          final HealthHomeBookletItem mBookletItem = mBookletItemList.get(i);
          ArrayList<HealthHomeBookletDate> mListDate = mBookletItem.getDataList();
          View mChartView = LinearLayout.inflate(getActivity(), R.layout.layout_chart_main, null);
          TextView mTvChartName = (TextView) mChartView.findViewById(R.id.et_chart_name);
          mTvChartName.setText(mBookletItem.getName());
          TextView mTvViewMax = (TextView) mChartView.findViewById(R.id.tv_view_max);
          mTvViewMax.setText(mBookletItem.getValueMax());
          TextView mTvViewMin = (TextView) mChartView.findViewById(R.id.tv_view_min);
          mTvViewMin.setText(mBookletItem.getValueMin());
          TextView mTvChartNum = (TextView) mChartView.findViewById(R.id.tv_chart_num);
          mTvChartNum.setText(mBookletItem.getNewestData() + " ");
          TextView mTvChartMin = (TextView) mChartView.findViewById(R.id.tv_chart_min);
          mTvChartMin.setText(mBookletItem.getValueMin());
          TextView mTvChartMax = (TextView) mChartView.findViewById(R.id.tv_chart_max);
          mTvChartMax.setText(mBookletItem.getValueMax());
          TextView mTvChartUnit = (TextView) mChartView.findViewById(R.id.tv_chart_unit);
          mTvChartUnit.setText(mBookletItem.getUnit());
          TextView mTvChartDate = (TextView) mChartView.findViewById(R.id.tv_chart_date);
          if (mBookletItem.getNewestDataDate() != null) {
            mTvChartDate.setText(getTime(20, mBookletItem.getNewestDataDate()));
          }
          LineChart mChart = (LineChart) mChartView.findViewById(R.id.my_chart);
          setChart(mChart, mListDate, mBookletItem);
          mLlytChart.addView(mChartView);
          mChartView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(getActivity(), CubicLineChartActivity.class);
              intent.putExtra("id", mBookletItem.getId());
              intent.putExtra("name", mBookletItem.getName());
              startActivity(intent);
            }
          });
        }
      }
    }
  }

  private void setChart(LineChart mChart, ArrayList<HealthHomeBookletDate> mListDate, HealthHomeBookletItem mBookletItem) {
    mChart.setStartAtZero(false);
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
    XLabels x = mChart.getXLabels();
    x.setAvoidFirstLastClipping(true);
    x.setTextSize(9f);
    x.setSpaceBetweenLabels(1);
    x.setTextColor(Color.rgb(255, 255, 255));
    x.setPosition(XLabelPosition.BOTTOM);
    x.setAdjustXLabels(false);
    x.setCenterXLabelText(false);
    YLabels y = mChart.getYLabels();
    y.setShowOnlyMinMax(true);
    y.setPosition(YLabelPosition.RIGHT);
    y.setTextColor(Color.rgb(255, 255, 255));
    y.setTextSize(9f);
    setData(mChart, 8, mListDate, mBookletItem);
    mChart.animateXY(2000, 2000);
    mChart.invalidate();
  }

  private void setData(final LineChart mChart, int count, ArrayList<HealthHomeBookletDate> mListDate,
      HealthHomeBookletItem mBookletItem) {
    if (mBookletItem.getValueMax() == null || mBookletItem.getValueMax().equals("")) {
      mMax = 0.0f;
    } else {
      mMax = Float.parseFloat(mBookletItem.getValueMax());
      BigDecimal b = new BigDecimal(mMax);
      mMax = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }
    if (mBookletItem.getValueMin() == null || mBookletItem.getValueMin().equals("")) {
      mMin = 0.0f;
    } else {
      mMin = Float.parseFloat(mBookletItem.getValueMin());
      BigDecimal b = new BigDecimal(mMax);
      mMax = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }
    float middle = (mMax + mMin) / 2;
    float dec = (mMax - mMin) / 5;
    ArrayList<String> xVals = new ArrayList<String>();
    ArrayList<Entry> vals1 = new ArrayList<Entry>();
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, -6);
    for (int i = 0; i < count; i++) {
      if (i == 0) {
        xVals.add("");
      } else {
        HealthHomeBookletDate item = mListDate.get(i - 1);
        xVals.add(getTime(i, item.getDataTime()));
        if (item.getValue().equals("")) {
        } else {
          float data = Float.parseFloat(item.getValue());
          if (data > middle) {
            data -= dec;
          } else {
            data += dec;
          }
          vals1.add(new Entry(data, i));
        }
        if (i == 7) {
          xVals.add("");
        }
      }
    }
    LineDataSet set1 = new LineDataSet(vals1, "DataSet 1");
    set1.setCubicIntensity(0.2f);
    set1.setDrawFilled(true);
    set1.setLineWidth(1f);
    set1.setDrawCircles(true);
    set1.setCircleSize(2.5f);
    set1.setCircleColor(Color.rgb(255, 255, 255));
    set1.setFillColor(Color.rgb(202, 240, 239));
    set1.setColor(Color.rgb(255, 255, 255));
    ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
    dataSets.add(set1);
    LineData data = new LineData(xVals, dataSets);
    LimitLine ll1;
    if (mMin - mMax == 0 && mMin != 0) {
      float a = mMax / 10;
      float min = mMin - a;
      ;
      float max = mMax + a;
      mChart.setYRange(min, max, true);
    } else {
      mChart.setYRange(mMin, mMax, true);
    }
    if (mMin == 0 && mMax == 0) {
      mChart.setYRange(-1, 1, true);
    }
    ll1 = new LimitLine(middle);
    ll1.setLineWidth(0.2f);
    ll1.setLineColor(Color.rgb(255, 255, 255));
    ll1.enableDashedLine(10f, 5f, 0f);
    ll1.setDrawValue(false);
    data.addLimitLine(ll1);
    mChart.setData(data);
  }

  @SuppressLint("SimpleDateFormat")
  private String getTime(int i, String str) {
    String dateTime = "";
    Date date = null;
    try {
      date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
    } catch (ParseException e) {
    }
    if (i == 1) {
      dateTime = new SimpleDateFormat("M" + "月" + "d" + "日").format(date);
    } else if (i == 20) {
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      dateTime = new SimpleDateFormat("yyyy-MM-dd").format(date);
      if (dateTime.equals(df.format(new Date()))) {
        dateTime = "今天";
      } else {
        dateTime = new SimpleDateFormat("M" + "月" + "d" + "日").format(date);
      }
    } else if (i == 30) {
      dateTime = new SimpleDateFormat("yyyy-MM-dd").format(date);
    } else {
      dateTime = new SimpleDateFormat("d").format(date);
    }
    return dateTime;
  }

  private void setInfo() {
    if (mHealthUser == null) {
    } else {
      mRlytLoginInfo.setVisibility(View.VISIBLE);
      mRlytUnloginInfo.setVisibility(View.GONE);
      mTvName.setText(mHealthUser.getName());
      mImageSex.setImageResource(mHealthUser.getGender() == 0 ? R.drawable.ic_male : R.drawable.ic_female);
      ImageLoader.getInstance().loadImage(RemoteDataManager.SERVICE + mHealthUser.getHeadImg(), options,
          new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String arg0, View arg1) {
            }

            @Override
            public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
            }

            @Override
            public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
              mImgLogo.setImageBitmap(arg2);
            }

            @Override
            public void onLoadingCancelled(String arg0, View arg1) {
            }
          });
    }
  }

  private void setReport() {
    if (mExamReport == null) {
    } else {
      mRlytLoginReport.setVisibility(View.VISIBLE);
      mRlytUnloginReport.setVisibility(View.GONE);
      mReportName.setText(mExamReport.getName());
      mReportTime.setText(getTime(30, mExamReport.getCreateTime()));
      mReportWeek.setText(getWeek(mExamReport.getCreateTime()));
      id = mExamReport.getId();
    }
  }

  private String getWeek(String strTime) {
    Calendar calendar = Calendar.getInstance();
    try {
      calendar.setTime(RemoteDataManager.sdfDate.parse(strTime));
    } catch (ParseException e) {
    }
    return mWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1];
  }

  @Override
  public void onCallMyhealthFragment() {
  }
}
