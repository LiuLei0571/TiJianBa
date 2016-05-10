package com.hengtiansoft.tijianba.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import com.hengtiansoft.tijianba.adapter.AddAdapter;
import com.hengtiansoft.tijianba.adapter.AddAdapter.AddAdapterListener;
import com.hengtiansoft.tijianba.adapter.UpdateGridAdapter;
import com.hengtiansoft.tijianba.adapter.UpdateGridAdapter.UpdataGridAdapterListener;
import com.hengtiansoft.tijianba.net.response.ReportItem;
import com.hengtiansoft.tijianba.net.response.ReportDetail;
import com.hengtiansoft.tijianba.net.response.ReportDetailListener;
import com.hengtiansoft.tijianba.net.response.ExamReportUpdate;
import com.hengtiansoft.tijianba.net.response.ReturnFromServerListener;
import com.hengtiansoft.tijianba.util.ImageAdd;
import com.hengtiansoft.tijianba.util.ImageUtil;
import com.juanliuinformation.lvningmeng.R;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ReportEditActivity extends BaseActivity implements OnClickListener, OnItemClickListener {
  private static final int TIME = 0;
  private RelativeLayout mRlytBtn;
  private int id;
  private TextView mTvTime;
  private EditText mReportName, mOrganization;
  private ReportDetail reportDetail;
  private GridView mGridView;
  private ArrayList<byte[]> attachments = new ArrayList<byte[]>();
  private ArrayList<Integer> attachmentIdList = new ArrayList<Integer>();
  private UpdateGridAdapter adapter;
  private ArrayList<ReportItem> reportDetailList = new ArrayList<ReportItem>();
  ArrayList<String> arrayList = new ArrayList<String>();
  private File file;
  private static final int IMAGE_REQUEST_CODE = 1;
  private static final int CAMERA_REQUEST_CODE = 2;
  private static final int SHOW_BIG_IMAGE = 4;
  private static final int ANDROID = 2;
  private boolean isFirst = true;
  private AddAdapter adapterUpdate;
  private Bitmap bitmapAdd;
  private ReportItem item;
  private PopupWindow pop;
  private LinearLayout ll_popup;
  private View parentView;
  private int pos;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    parentView = getLayoutInflater().inflate(R.layout.activity_report_edit, null);
    setContentView(parentView);
    findView();
    id = getIntent().getIntExtra("id", 0);
    getReportDetail(id);
    initPopWindow();
  }

  private void findView() {
    mRlytBtn = (RelativeLayout) findViewById(R.id.edit_management_btn);
    mRlytBtn.setOnClickListener(this);
    mReportName = (EditText) findViewById(R.id.et_report_name);
    mOrganization = (EditText) findViewById(R.id.et_report_organization);
    mTvTime = (TextView) findViewById(R.id.tv_report_time);
    mTvTime.setOnClickListener(this);
    mGridView = (GridView) findViewById(R.id.gridview);
    mGridView.setOnItemClickListener(this);
    bitmapAdd = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_add_reportphoto);
    ImageAdd.bitmap = bitmapAdd;
    item = new ReportItem();
    item.setSmallUrl("add");
    item.setUrl("add");
    adapterUpdate = new AddAdapter(ImageAdd.smallList, ReportEditActivity.this);
    adapterUpdate.setListener(new AddAdapterListener() {
      @Override
      public void onAddAdapterListener(int position) {
        ImageAdd.smallList.remove(position);
        if (ImageAdd.smallList.contains(bitmapAdd) == false) {
          ImageAdd.smallList.add(bitmapAdd);
        }
        try {
          if (reportDetailList.get(position).getAttachmentId() != 0) {
            attachmentIdList.add(reportDetailList.get(position).getAttachmentId());
            reportDetailList.remove(position);
          }
        } catch (Exception e) {
        }
        adapterUpdate = new AddAdapter(ImageAdd.smallList, ReportEditActivity.this);
        mGridView.setAdapter(adapterUpdate);
      }
    });
  }

  public void initPopWindow() {
    pop = new PopupWindow(ReportEditActivity.this);
    View view = getLayoutInflater().inflate(R.layout.layout_popupwindows, null);
    ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
    pop.setWidth(LayoutParams.MATCH_PARENT);
    pop.setHeight(LayoutParams.WRAP_CONTENT);
    pop.setFocusable(true);
    pop.setOutsideTouchable(true);
    pop.setBackgroundDrawable(new BitmapDrawable());
    pop.setContentView(view);
    RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
    Button btnTakePhoto = (Button) view.findViewById(R.id.item_popupwindows_camera);
    Button btnAblum = (Button) view.findViewById(R.id.item_popupwindows_Photo);
    Button btnCancle = (Button) view.findViewById(R.id.item_popupwindows_cancel);
    parent.setOnClickListener(this);
    btnTakePhoto.setOnClickListener(this);
    btnAblum.setOnClickListener(this);
    btnCancle.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent();
    switch (v.getId()) {
      case R.id.edit_management_btn:
        doUpdateReport();
        break;
      case R.id.tv_report_time:
        intent.setClass(this, CalendarViewActivity.class);
        startActivityForResult(intent, TIME);
        break;
      case R.id.item_popupwindows_camera:
        takePicture(pos);
        pop.dismiss();
        ll_popup.clearAnimation();
        break;
      case R.id.item_popupwindows_Photo:
        intent.setClass(ReportEditActivity.this, PhotoAlbumChoiceActivity.class);
        if (ImageAdd.smallList.contains(bitmapAdd)) {
          intent.putExtra("num", (9 - ImageAdd.smallList.size() + 1));
        } else {
          intent.putExtra("num", (9 - ImageAdd.smallList.size()));
        }
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
        pop.dismiss();
        ll_popup.clearAnimation();
        break;
      case R.id.item_popupwindows_cancel:
        pop.dismiss();
        ll_popup.clearAnimation();
        break;
      default:
        break;
    }
  }

  private void doUpdateReport() {
    ExamReportUpdate examReportUpdate = new ExamReportUpdate();
    examReportUpdate.setId(id);
    examReportUpdate.setExamTime(mTvTime.getText().toString() + " 00:00:00");
    examReportUpdate.setName(mReportName.getText().toString());
    examReportUpdate.setOrgName(mOrganization.getText().toString());
    examReportUpdate.setAttachmentIdList(attachmentIdList);
    examReportUpdate.setSrc(ANDROID);
    ImageAdd.smallList.remove(bitmapAdd);
    int size = 0;
    if (reportDetailList.contains(item)) {
      size = reportDetailList.size() - 1;
    } else {
      size = reportDetailList.size();
    }
    for (int i = size; i < ImageAdd.smallList.size(); i++) {
      Bitmap bitmap = ImageAdd.smallList.get(i);
      attachments.add(ImageUtil.getBitmapByte(bitmap));
    }
    doUpdate(examReportUpdate, attachments);
  }

  private void getReportDetail(int id) {
    remoteDataManager.reportDetailListener = new ReportDetailListener() {
      @Override
      public void onSuccess(final ReportDetail examReportDetail) {
        ReportEditActivity.this.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            reportDetail = examReportDetail;
            mReportName.setText(reportDetail.getName());
            mTvTime.setText(getTime(reportDetail.getExamTime()));
            mOrganization.setText(reportDetail.getOrgName());
            reportDetailList = reportDetail.getAttachmentList();
            // importNum=reportDetailList.size();
            if (reportDetailList.size() < 9) {
              reportDetailList.add(item);
            }
            adapter = new UpdateGridAdapter(reportDetailList, item, ReportEditActivity.this);
            adapter.setListener(new UpdataGridAdapterListener() {
              @Override
              public void onUpdataGridAdapterListener(int pos) {
                ImageAdd.smallList.remove(pos);
                if (ImageAdd.smallList.contains(bitmapAdd)) {
                } else {
                  ImageAdd.smallList.add(bitmapAdd);
                }
                adapterUpdate = new AddAdapter(ImageAdd.smallList, ReportEditActivity.this);
                mGridView.setAdapter(adapterUpdate);
                reportDetailList.remove(item);
                try {
                  attachmentIdList.add(reportDetailList.get(pos).getAttachmentId());
                  reportDetailList.remove(pos);
                } catch (Exception e) {
                }
                isFirst = false;
              }

              @Override
              public void onFinish(boolean success) {
                if (success) {
                  ImageAdd.smallList.clear();
                  for (int i = 0; i < ImageAdd.smallMap.size(); i++) {
                    ImageAdd.smallList.add(ImageAdd.smallMap.get(i));
                  }
                  ImageAdd.smallMap.clear();
                  dismissProgressDialog();
                } else {
                  dismissProgressDialog();
                }
              }
            });
            mGridView.setAdapter(adapter);
          }
        });
        if (ImageAdd.smallList.size() == 0) {
          dismissProgressDialog();
        }
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        handleError(errorMessage);
        dismissProgressDialog();
      }
    };
    if (validateInternet()) {
      showProgressDialog("报告", "加载中...");
      remoteDataManager.getExamReportDetail(id);
    }
  }

  private void doUpdate(ExamReportUpdate examReportUpdate, ArrayList<byte[]> attachments) {
    remoteDataManager.reportUpdateListener = new ReturnFromServerListener() {
      @Override
      public void onSuccess(String message) {
        dismissProgressDialog();
        ReportEditActivity.this.finish();
        ImageAdd.smallList.clear();
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        dismissProgressDialog();
        handleError(errorMessage);
        ReportEditActivity.this.finish();
        ImageAdd.smallList.clear();
      }
    };
    if (validateInternet()) {
      showProgressDialog("报告", "上传中...");
      remoteDataManager.doUpdateExamReport(examReportUpdate, attachments);
    }
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    arrayList.clear();
    for (ReportItem item : reportDetailList)
      arrayList.add(item.getUrl());
    if (isFirst) {
      ReportItem report = reportDetailList.get(position);
      if (report.getSmallUrl().equals("add")) {
        pos = position;
        pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
      } else {
        Intent intent = new Intent(this, ShowImageActivity.class);
        intent.putExtra("state", 3);
        intent.putExtra("num", position);
        intent.putExtra("arrayList", arrayList);
        startActivityForResult(intent, SHOW_BIG_IMAGE);
      }
      isFirst = false;
    } else {
      if (ImageAdd.smallList.get(position) == bitmapAdd) {
        pos = position;
        pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
      } else {
        Intent intent = new Intent(this, ShowImageActivity.class);
        intent.putExtra("num", position);
        intent.putExtra("state", 3);
        intent.putExtra("arrayList", arrayList);
        startActivityForResult(intent, SHOW_BIG_IMAGE);
      }
    }
  }

  @Override
  protected void onResume() {
    if (isFirst == false) {
      reportDetailList.remove(item);
      if (ImageAdd.smallList.contains(bitmapAdd)) {
      } else {
        if (ImageAdd.smallList.size() < 9)
          ImageAdd.smallList.add(bitmapAdd);
      }
      adapterUpdate = new AddAdapter(ImageAdd.smallList, ReportEditActivity.this);
      mGridView.setAdapter(adapterUpdate);
    }
    super.onResume();
  }

  private void takePicture(int pos) {
    String state = Environment.getExternalStorageState();
    if (state.equals(Environment.MEDIA_MOUNTED)) {
      String saveDir = Environment.getExternalStorageDirectory() + "/lnmjk_android";
      File dir = new File(saveDir);
      if (!dir.exists()) {
        dir.mkdir();
      }
      String save = Environment.getExternalStorageDirectory() + "/lnmjk_android/report";
      File direr = new File(save);
      if (!direr.exists()) {
        direr.mkdir();
      }
      file = new File(save, "report" + pos + ".jpg");
      file.delete();
      if (!file.exists()) {
        try {
          file.createNewFile();
        } catch (IOException e) {
          e.printStackTrace();
          return;
        }
      }
    }
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
    startActivityForResult(intent, CAMERA_REQUEST_CODE);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inDither = false;
    options.inPreferredConfig = Bitmap.Config.RGB_565;
    Bitmap bitmap = null;
    switch (requestCode) {
      case TIME:
        if (data != null) {
          String currentTime = data.getStringExtra("currentTime");
          mTvTime.setText(currentTime);
        }
        break;
      case IMAGE_REQUEST_CODE:
        if (data != null) {
          ImageAdd.smallList.remove(bitmapAdd);
          for (String str : data.getStringArrayListExtra("idList")) {
            Integer mId = Integer.parseInt(str);
            bitmap = MediaStore.Images.Thumbnails.getThumbnail(this.getContentResolver(), mId,
                Images.Thumbnails.MINI_KIND, options);
            ImageAdd.smallList.add(bitmap);
          }
          if (ImageAdd.smallList.size() < 9) {
            ImageAdd.smallList.add(bitmapAdd);
          }
          adapterUpdate = new AddAdapter(ImageAdd.smallList, this);
          mGridView.setAdapter(adapterUpdate);
          reportDetailList.remove(item);
        }
        break;
      case CAMERA_REQUEST_CODE:
        if (resultCode == RESULT_OK) {
          ImageAdd.smallList.remove(bitmapAdd);
          bitmap = BitmapFactory.decodeFile(file.getPath(), options);
          ImageAdd.smallList.add(bitmap);
          if (ImageAdd.smallList.size() < 9) {
            ImageAdd.smallList.add(bitmapAdd);
          }
          adapterUpdate = new AddAdapter(ImageAdd.smallList, this);
          mGridView.setAdapter(adapterUpdate);
          reportDetailList.remove(item);
        } else {
          file.delete();
        }
        break;
      case SHOW_BIG_IMAGE:
        if (data != null) {
          reportDetailList.remove(item);
          for (int i : data.getIntegerArrayListExtra("deleteIdList")) {
            try {
              ImageAdd.smallList.remove(i);
              if (reportDetailList.get(i).getAttachmentId() != 0) {
                attachmentIdList.add(reportDetailList.get(i).getAttachmentId());
                reportDetailList.remove(i);
              }
            } catch (IndexOutOfBoundsException e) {
            }
          }
          adapterUpdate = new AddAdapter(ImageAdd.smallList, this);
          mGridView.setAdapter(adapterUpdate);
        }
        break;
    }
    super.onActivityResult(requestCode, resultCode, data);
  }

  private String getTime(String str) {
    String[] time = str.split("\\s");
    return time[0];
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    ImageAdd.smallList.clear();
  }
}
