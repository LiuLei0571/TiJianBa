package com.hengtiansoft.tijianba.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hengtiansoft.tijianba.adapter.DetailGridAdapter;
import com.hengtiansoft.tijianba.adapter.DetailGridAdapter.DetailAdapterListener;
import com.hengtiansoft.tijianba.net.response.ReportDetail;
import com.hengtiansoft.tijianba.net.response.ReportDetailListener;
import com.hengtiansoft.tijianba.net.response.ReportItem;
import com.hengtiansoft.tijianba.util.ImageAdd;
import com.juanliuinformation.lvningmeng.R;

public class ReportDetailsActivity extends BaseActivity implements OnItemClickListener, OnClickListener {
  private TextView mTvName, mTvTime, mTvOrganization;
  private int id;
  private int type;
  private ReportDetail reportDetail;
  private GridView mGridView;
  private ImageView mImageView;
  private DetailGridAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_report_details);
    id = getIntent().getIntExtra("id", 0);
    setView();
    getReportDetail(id);
  }

  private void setView() {
    mTvName = (TextView) findViewById(R.id.tv_reportdetail_name);
    mTvTime = (TextView) findViewById(R.id.tv_reportdetail_time);
    mTvOrganization = (TextView) findViewById(R.id.tv_reportdetail_organization);
    mGridView = (GridView) findViewById(R.id.gridview);
    mImageView = (ImageView) findViewById(R.id.img_pdf);
  }

  @Override
  public void onAttachFragment(Fragment fragment) {
    super.onAttachFragment(fragment);
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  private void getReportDetail(int id) {
    remoteDataManager.reportDetailListener = new ReportDetailListener() {
      @Override
      public void onSuccess(final ReportDetail examReportDetail) {
        ReportDetailsActivity.this.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            reportDetail = examReportDetail;
            mTvName.setText(reportDetail.getName());
            mTvTime.setText(getTime(reportDetail.getExamTime()));
            mTvOrganization.setText(reportDetail.getOrgName());
            type = reportDetail.getAttachmentType();
            if (type == 1) {
              mGridView.setOnItemClickListener(ReportDetailsActivity.this);
              adapter = new DetailGridAdapter(reportDetail.getAttachmentList(), ReportDetailsActivity.this);
              mGridView.setAdapter(adapter);
              adapter.setListener(new DetailAdapterListener() {
                @Override
                public void onFinish(boolean success) {
                  if (success) {
                    dismissProgressDialog();
                  } else {
                    dismissProgressDialog();
                  }
                }
              });
              if (reportDetail.getAttachmentList().size() == 0) {
                dismissProgressDialog();
              }
            } else if (type == 0) {
              mGridView.setVisibility(View.GONE);
              mImageView.setVisibility(View.VISIBLE);
              mImageView.setOnClickListener(ReportDetailsActivity.this);
              dismissProgressDialog();
            }
          }
        });
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        dismissProgressDialog();
      }
    };
    if (validateInternet()) {
      showProgressDialog("报告详情", "加载中...");
      remoteDataManager.getExamReportDetail(id);
    }
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    ArrayList<ReportItem> report = reportDetail.getAttachmentList();
    ArrayList<String> arrayList = new ArrayList<String>();
    for (int i = 0; i < report.size(); i++) {
      arrayList.add(report.get(i).getUrl());
    }
    Intent intent = new Intent(this, ShowImageActivity.class);
    intent.putExtra("state", 1);
    intent.putExtra("arrayList", arrayList);
    intent.putExtra("num", position);
    startActivity(intent);
  }

  private String getTime(String str) {
    String[] time = str.split("\\s");
    return time[0];
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.img_pdf:
        showProgressDialog("pdf文档", "加载中...");
        final String url = remoteDataManager.SERVICE + reportDetail.getAttachmentList().get(0).getUrl();
        new Thread(new Runnable() {
          @Override
          public void run() {
            // showPdf(downloadFile(url));
            dismissProgressDialog();
            try {
              showpdf(downloadFile(url));
            } catch (ActivityNotFoundException e) {
              Looper.prepare();
              Toast.makeText(ReportDetailsActivity.this, "没有打开pdf工具", Toast.LENGTH_SHORT).show();
              Looper.loop();
            }
          }
        }).start();
        break;
      default:
        break;
    }
  }

  // public void showPdf(File file) {
  // Uri uri = Uri.parse(file.getPath());
  // // Intent intent = new Intent(this,MuPDFActivity.class);
  // intent.setAction(Intent.ACTION_VIEW);
  // intent.setData(uri);
  // startActivity(intent);
  // }

  public File downloadFile(String fileUrl) {
    File apkFile = null;
    String fileName = fileUrl.substring(fileUrl.lastIndexOf("/"));
    try {
      if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
        String savedir = Environment.getExternalStorageDirectory() + "/lnmjk_android";
        File dir = new File(savedir);
        if (!dir.exists()) {
          dir.mkdir();
        }
        String save = Environment.getExternalStorageDirectory() + "/lnmjk_android/pdf";
        File direr = new File(save);
        if (!direr.exists()) {
          direr.mkdir();
        }
        String saveDir = Environment.getExternalStorageDirectory() + "/lnmjk_android/pdf";
        URL url = new URL(fileUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        InputStream is = conn.getInputStream();
        File file = new File(saveDir);
        if (!file.exists()) {
          file.mkdir();
        }
        apkFile = new File(saveDir, fileName);
        if (apkFile.exists()) {
          return apkFile;
        }
        FileOutputStream fos = new FileOutputStream(apkFile);
        int count = 0;
        int numread = 0;
        byte buf[] = new byte[1024];
        while ((numread = is.read(buf)) != -1) {
          fos.write(buf, 0, numread);
        }
        fos.flush();
        fos.close();
        is.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return apkFile;
  }

  public void showpdf(File file) {
    PackageManager packageManager = getPackageManager();
    Intent testIntent = new Intent(Intent.ACTION_VIEW);
    testIntent.setType("application/pdf");
    List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_VIEW);
    Uri uri = Uri.fromFile(file);
    intent.setDataAndType(uri, "application/pdf");
    startActivity(intent);
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    ImageAdd.detailMap.clear();
  }
}
