package com.hengtiansoft.tijianba.activity;

import java.util.ArrayList;
import com.hengtiansoft.tijianba.adapter.ImageViewPagerAdpter;
import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.util.ImageAdd;
import com.hengtiansoft.tijianba.util.TouchImageView;
import com.juanliuinformation.lvningmeng.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ShowImageActivity extends BaseActivity implements OnClickListener, OnPageChangeListener {
  private ViewPager mViewPager;
  private Intent intent;
  private ArrayList<String> arrayList = new ArrayList<String>();
  private ArrayList<Integer> deleteIdList = new ArrayList<Integer>();
  private ArrayList<TouchImageView> mViewList = new ArrayList<TouchImageView>();
  private TouchImageView mPhotoView;
  private ImageViewPagerAdpter adapter;
  private int num;
  private int state;
  private DisplayImageOptions options;
  private TextView mTvTitle;
  private ImageView mTvDelete;
  private int number;
  private View view;
  private ProgressBar mProgress;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_show_image);
    intent = getIntent();
    num = intent.getIntExtra("num", 0);
    state = intent.getIntExtra("state", 0);
    options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
        .bitmapConfig(Bitmap.Config.ARGB_8888).resetViewBeforeLoading(false).displayer(new SimpleBitmapDisplayer())
        .build();
    init();
  }

  @SuppressLint("NewApi")
  private void init() {
    mTvTitle = (TextView) findViewById(R.id.tv_title);
    mTvDelete = (ImageView) findViewById(R.id.image_edit);
    mViewPager = (ViewPager) findViewById(R.id.viewpager_img);
    mProgress = (ProgressBar) findViewById(R.id.progress);
    if (state == 1) {
      mTvDelete.setVisibility(View.GONE);
      arrayList = intent.getStringArrayListExtra("arrayList");
      for (int i = 0; i < arrayList.size(); i++) {
        final int a = i;
        view = LinearLayout.inflate(this, R.layout.layout_image_item, null);
        mPhotoView = (TouchImageView) view.findViewById(R.id.webview);
        number = arrayList.size();
        Bitmap bitmap = ImageAdd.detailMap.get(i);
        mPhotoView.setImageBitmap(bitmap);
        mViewList.add(mPhotoView);
      }
    } else if (state == 2) {
      int size = 0;
      if (ImageAdd.AddList.contains(ImageAdd.bitmap)) {
        size = ImageAdd.AddList.size() - 1;
      } else {
        size = ImageAdd.AddList.size();
      }
      number = size;
      mTvDelete.setVisibility(View.VISIBLE);
      mTvDelete.setOnClickListener(this);
      for (int i = 0; i < size; i++) {
        view = LinearLayout.inflate(this, R.layout.layout_image_item, null);
        mPhotoView = (TouchImageView) view.findViewById(R.id.webview);
        Bitmap bitmap = ImageAdd.AddList.get(i);
        if (bitmap != null) {
          mPhotoView.setImageBitmap(bitmap);
          mViewList.add(mPhotoView);
        }
      }
    }

    else if (state == 3) {
      mTvDelete.setVisibility(View.VISIBLE);
      mTvDelete.setOnClickListener(this);
      arrayList = intent.getStringArrayListExtra("arrayList");
      arrayList.remove("add");
      number = ImageAdd.smallList.size();
      for (int i = 0; i < number; i++) {
        view = LinearLayout.inflate(this, R.layout.layout_image_item, null);
        mPhotoView = (TouchImageView) view.findViewById(R.id.webview);
        Bitmap bitmap = ImageAdd.smallList.get(i);
        if (bitmap == ImageAdd.bitmap) {
          number = ImageAdd.smallList.size() - 1;
          continue;
        }
        mPhotoView.setImageBitmap(bitmap);
        mViewList.add(mPhotoView);
      }
    }
    
    
    adapter = new ImageViewPagerAdpter(mViewList, ShowImageActivity.this);
    mViewPager.setAdapter(adapter);
    mViewPager.setCurrentItem(num);
    if (arrayList != null && arrayList.size() > 0) {
      try {
        String url = arrayList.get(num);
        loadImage(url, num);
      } catch (IndexOutOfBoundsException e) {
      }
    }
    mTvTitle.setText((num + 1) + "/" + number);
    mViewPager.setOnPageChangeListener(this);
    dismissProgressDialog();
  }

  @Override
  public void onClick(View v) {
    int position = mViewPager.getCurrentItem();
    if (state == 2) {
      ImageAdd.AddList.remove(position);
      mViewList.remove(position);
      adapter.notifyDataSetChanged();
    } else if (state == 3) {
      deleteIdList.add(position);
      mViewList.remove(position);
      adapter.notifyDataSetChanged();
      Intent aintent = new Intent(this, ReportEditActivity.class);
      aintent.putIntegerArrayListExtra("deleteIdList", deleteIdList);
      ShowImageActivity.this.setResult(RESULT_OK, aintent);
    }
    this.finish();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  @Override
  public void onPageScrollStateChanged(int arg0) {
  }

  @Override
  public void onPageScrolled(int arg0, float arg1, int arg2) {
  }

  @Override
  public void onPageSelected(int arg0) {
    mTvTitle.setText((arg0 + 1) + "/" + number);
    if (arrayList != null && arrayList.size() > 0) {
      try {
        String url = arrayList.get(arg0);
        final int a = arg0;
        loadImage(url, a);
      } catch (IndexOutOfBoundsException e) {
      }
    }
  }

  private void loadImage(String url, final int a) {
    ImageLoader.getInstance().loadImage(RemoteDataManager.SERVICE + url, options, new ImageLoadingListener() {

      @Override
      public void onLoadingStarted(String arg0, View arg1) {
        mProgress.setVisibility(View.VISIBLE);
      }

      @Override
      public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
        mProgress.setVisibility(View.GONE);
      }

      @Override
      public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
        mProgress.setVisibility(View.GONE);
        mViewList.get(a).setImageBitmap(arg2);
        adapter.notifyDataSetChanged();
      }

      @Override
      public void onLoadingCancelled(String arg0, View arg1) {
      }
    });
  }
}
