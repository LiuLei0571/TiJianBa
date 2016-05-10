package com.hengtiansoft.tijianba.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import android.app.Fragment;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hengtiansoft.tijianba.adapter.AddAdapter;
import com.hengtiansoft.tijianba.adapter.AddAdapter.AddAdapterListener;
import com.hengtiansoft.tijianba.net.response.ReturnFromServerListener;
import com.hengtiansoft.tijianba.util.ImageAdd;
import com.hengtiansoft.tijianba.util.ImageUtil;
import com.juanliuinformation.lvningmeng.R;

public class AddHealthReportActivity extends BaseActivity implements OnClickListener, OnItemClickListener {
  private static final int TIME = 0;
  private RelativeLayout mRlytBtn;
  private ImageView mImg;
  private TextView mTvTime;
  private EditText mReportName, mOrganization;
  private GridView mGridView;
  private AddAdapter mGridAdapter;
  private ArrayList<byte[]> bitmapByteList = new ArrayList<byte[]>();
  private File file;
  private Bitmap bitmapAdd;
  private PopupWindow pop;
  private LinearLayout ll_popup;
  private View parentView;
  private static final int IMAGE_REQUEST_CODE = 1;
  private static final int CAMERA_REQUEST_CODE = 2;
  private static final int RESULT_REQUEST_CODE = 3;
  private static final int android = 2;
  private int pos;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    parentView = getLayoutInflater().inflate(R.layout.activity_add_health_report, null);
    setContentView(parentView);
    init();
    initPopWindow();
  }

  private void init() {
    mRlytBtn = (RelativeLayout) findViewById(R.id.health_management_btn);
    mRlytBtn.setOnClickListener(this);
    mReportName = (EditText) findViewById(R.id.health_management_report_name);
    mOrganization = (EditText) findViewById(R.id.health_management_organization);
    mImg = (ImageView) findViewById(R.id.health_management_time);
    mTvTime = (TextView) findViewById(R.id.health_management_tv);
    mTvTime.setOnClickListener(this);
    mImg.setOnClickListener(this);
    bitmapAdd = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_add_reportphoto);
    ImageAdd.bitmap = bitmapAdd;
    ImageAdd.AddList.add(bitmapAdd);
    mGridView = (GridView) findViewById(R.id.gridview);
  }

  public void initPopWindow() {
    pop = new PopupWindow(AddHealthReportActivity.this);
    View view = getLayoutInflater().inflate(R.layout.layout_popupwindows, null);
    ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
    pop.setWidth(LayoutParams.MATCH_PARENT);
    pop.setHeight(LayoutParams.WRAP_CONTENT);
    pop.setBackgroundDrawable(new BitmapDrawable());
    pop.setFocusable(true);
    pop.setOutsideTouchable(true);
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
  public void onClick(View view) {
    Intent intent = new Intent();
    switch (view.getId()) {
      case R.id.health_management_time:
        intent.setClass(this, CalendarViewActivity.class);
        startActivityForResult(intent, TIME);
        break;
      case R.id.health_management_tv:
        intent.setClass(this, CalendarViewActivity.class);
        startActivityForResult(intent, TIME);
        break;
      case R.id.health_management_btn:
        submmit();
        break;
      case R.id.parent:
        pop.dismiss();
        ll_popup.clearAnimation();
        break;
      case R.id.item_popupwindows_camera:
        takePicture(pos);
        pop.dismiss();
        ll_popup.clearAnimation();
        break;
      case R.id.item_popupwindows_Photo:
        intent.setClass(AddHealthReportActivity.this, PhotoAlbumChoiceActivity.class);
        intent.putExtra("num", (9 - ImageAdd.AddList.size() + 1));
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

  // TODO
  private void submmit() {
    boolean ok = false;
    String reportName = mReportName.getText().toString();
    String organization = mOrganization.getText().toString();
    if (reportName.equals("")) {
      mReportName.setError("不能为空！");
      ok = true;
    }
    if (organization.equals("")) {
      mOrganization.setError("不能为空！");
      ok = true;
    }
    if (mTvTime.getText().toString().equals("")) {
      mTvTime.setError("不能为空！");
      ok = true;
    }
    if (ok) {
      return;
    }
    String creatTime = mTvTime.getText().toString() + " 00:00:00";
    getBitmap();
    doAddreport(reportName, organization, creatTime, android, bitmapByteList);
  }

  private void getBitmap() {
    ImageAdd.AddList.remove(bitmapAdd);
    for (Bitmap bitmap : ImageAdd.AddList) {
      bitmapByteList.add(ImageUtil.getBitmapByte(bitmap));
    }
  }

  private void doAddreport(String name, String orgName, String creatTime, int srv, ArrayList<byte[]> attachments) {
    remoteDataManager.reportAddListener = new ReturnFromServerListener() {

      @Override
      public void onSuccess(String message) {
        dismissProgressDialog();
        AddHealthReportActivity.this.finish();
        ImageAdd.AddList.clear();
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        handleError(errorMessage);
        ImageAdd.AddList.clear();
        AddHealthReportActivity.this.finish();
        dismissProgressDialog();
      }
    };
    if (validateInternet()) {
      showProgressDialog(getString(R.string.str_report_add), getString(R.string.str_wait));
      remoteDataManager.doAddReport(name, orgName, creatTime, srv, attachments);
    }
  }

  @Override
  public void onAttachFragment(Fragment fragment) {
    super.onAttachFragment(fragment);
  }

  @Override
  protected void onResume() {
    super.onResume();
    mGridAdapter = new AddAdapter(ImageAdd.AddList, this);
    mGridAdapter.setListener(new AddAdapterListener() {
      @Override
      public void onAddAdapterListener(int position) {
        ImageAdd.AddList.remove(position);
        if (ImageAdd.AddList.contains(bitmapAdd) == false) {
          ImageAdd.AddList.add(bitmapAdd);
        }
        mGridAdapter = new AddAdapter(ImageAdd.AddList, AddHealthReportActivity.this);
        mGridView.setAdapter(mGridAdapter);
      }
    });
    mGridView.setAdapter(mGridAdapter);
    mGridView.setOnItemClickListener(this);
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    if (ImageAdd.AddList.get(position) == bitmapAdd) {
      // showDio(items, position);
      pos = position;
      pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
    } else {
      Intent intent = new Intent(AddHealthReportActivity.this, ShowImageActivity.class);
      intent.putExtra("num", position);
      intent.putExtra("state", 2);
      startActivity(intent);
    }
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
          mImg.setVisibility(View.INVISIBLE);
          mTvTime.setText(currentTime);
          mTvTime.setError(null);
        }
        break;
      case IMAGE_REQUEST_CODE:
        if (data != null) {
          ImageAdd.AddList.remove(bitmapAdd);
          for (String str : data.getStringArrayListExtra("idList")) {
            Integer mId = Integer.parseInt(str);
            bitmap = MediaStore.Images.Thumbnails.getThumbnail(this.getContentResolver(), mId,
                Images.Thumbnails.MINI_KIND, options);
            ImageAdd.AddList.add(bitmap);
          }
          if (ImageAdd.AddList.size() < 9) {
            ImageAdd.AddList.add(bitmapAdd);
          }
          mGridAdapter = new AddAdapter(ImageAdd.AddList, AddHealthReportActivity.this);
          mGridView.setAdapter(mGridAdapter);
        }
        break;
      case CAMERA_REQUEST_CODE:
        if (resultCode == RESULT_OK) {
          ImageAdd.AddList.remove(bitmapAdd);
          try {
            bitmap = BitmapFactory.decodeFile(file.getPath(), options);
          } catch (NullPointerException e) {
          }
          ImageAdd.AddList.add(bitmap);
          if (ImageAdd.AddList.size() < 9) {
            ImageAdd.AddList.add(bitmapAdd);
          }
          mGridAdapter = new AddAdapter(ImageAdd.AddList, AddHealthReportActivity.this);
          mGridView.setAdapter(mGridAdapter);
        } else {
          file.delete();
        }
        break;
      case RESULT_REQUEST_CODE:
        break;
    // case SHOW_BIG_IMAGE:
    // ImageAdd.AddList.remove(bitmapAdd);
    // int position = data.getIntExtra("position", 10);
    // if (position < 10) {
    // ImageAdd.AddList.remove(position);
    // ImageAdd.AddList.add(bitmapAdd);
    // }
    // mGridAdapter = new AddAdapter(ImageAdd.AddList,
    // AddHealthReportActivity.this);
    // mGridView.setAdapter(mGridAdapter);
    // break;
    }
    super.onActivityResult(requestCode, resultCode, data);
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    ImageAdd.AddList.clear();
  }
}
