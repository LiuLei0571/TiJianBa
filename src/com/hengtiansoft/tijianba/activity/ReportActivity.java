package com.hengtiansoft.tijianba.activity;

import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.hengtiansoft.tijianba.adapter.ReportAdapter;
import com.hengtiansoft.tijianba.adapter.ReportAdapter.ReportAdapterListener;
import com.hengtiansoft.tijianba.net.response.Report;
import com.hengtiansoft.tijianba.net.response.ReportListListener;
import com.hengtiansoft.tijianba.net.response.ReturnFromServerListener;
import com.juanliuinformation.lvningmeng.R;

/**
 * 
 * com.hengtiansoft.tijianba.activity.ReportActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 5, 2014 3:51:50 PM
 */
public class ReportActivity extends BaseActivity implements OnClickListener {
  private ReportAdapter mReportAdapter;
  private SwipeListView mReportListView;
  private ArrayList<Report> testReports = new ArrayList<Report>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_health_report);
    setView();
  }

  private void setView() {
    findViewById(R.id.rl_add_report).setOnClickListener(this);
    mReportAdapter = new ReportAdapter(ReportActivity.this, testReports);
    mReportAdapter.setReportListener(new ReportAdapterListener() {
      @Override
      public void onReportAdapterListener(int id, int pos) {
        deleteTestReport(id);
        testReports.remove(pos);
        mReportAdapter.notifyDataSetChanged();
        mReportListView.closeOpenedItems();
      }
    });
    mReportListView = (SwipeListView) findViewById(R.id.lv_test_report);
    mReportListView.setAdapter(mReportAdapter);
    mReportListView.setSwipeListViewListener(new BaseSwipeListViewListener() {
      @Override
      public void onClickFrontView(int position) {
        super.onClickFrontView(position);
        Intent intent = new Intent(ReportActivity.this, ReportDetailsActivity.class);
        intent.putExtra("id", testReports.get(position).getId());
        startActivity(intent);
      }
    });

  }

  @Override
  protected void onResume() {
    super.onResume();
    getReport();
    mReportListView.closeOpenedItems();
    // mTestReportAdapter.notifyDataSetChanged();
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.rl_add_report:
        ReportActivity.this.startActivity(new Intent(ReportActivity.this, AddHealthReportActivity.class));
        break;
      default:
        break;
    }
  }

  private void getReport() {
    remoteDataManager.reportListListener = new ReportListListener() {
      @Override
      public void onSuccess(final ArrayList<Report> reportList) {
        ReportActivity.this.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            testReports = reportList;
            mReportAdapter = new ReportAdapter(ReportActivity.this, testReports);
            mReportListView.setAdapter(mReportAdapter);
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
      showProgressDialog("体检报告", "加载中");
      remoteDataManager.getReportList();
    }
  }

  private void deleteTestReport(int id) {
    remoteDataManager.reportDeleteListener = new ReturnFromServerListener() {
      @Override
      public void onSuccess(String message) {
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
      }
    };
    if (validateInternet()) {
      remoteDataManager.doReportDelete(id);
    }
  }
}
