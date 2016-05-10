package com.hengtiansoft.tijianba.util;

import com.juanliuinformation.lvningmeng.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ImageProgressBar extends RelativeLayout {
  private ProgressBar mProgressBar = null;
  private TextView mProgressTxt = null;
  private ImageView mArrowImg = null;
  private static final float PROGRESS_MAX = 100.0f;

  public ImageProgressBar(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    initArrowProgressBar(context);
  }

  public ImageProgressBar(Context context, AttributeSet attrs) {
    super(context, attrs);
    initArrowProgressBar(context);
  }

  public ImageProgressBar(Context context) {
    super(context);
    initArrowProgressBar(context);

  }

  private void initArrowProgressBar(Context context) {
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View view = layoutInflater.inflate(R.layout.layout_progress, null);
    mProgressBar = (ProgressBar) view.findViewById(R.id.progress);
    mProgressTxt = (TextView) view.findViewById(R.id.tv_pro);
    mArrowImg = (ImageView) view.findViewById(R.id.img_pro_logo);
    addView(view);
  }

  public void setProgress(Context context, int progress,int left) {
    if (progress < PROGRESS_MAX) {
      WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
      DisplayMetrics dm = new DisplayMetrics();
      wm.getDefaultDisplay().getMetrics(dm);
      int screenWidth = dm.widthPixels;
//      int imageWith=mArrowImg.getWidth()/2;
//      LayoutParams progressParams = (LayoutParams) mProgressBar.getLayoutParams();
//      progressParams.leftMargin = (int) Math.ceil(imageWith);
//      mProgressBar.setLayoutParams(progressParams);  
      LayoutParams arrowParams = (LayoutParams) mArrowImg.getLayoutParams();
      LayoutParams textParams = (LayoutParams) mProgressTxt.getLayoutParams();
      float leftPosition = (((screenWidth) / PROGRESS_MAX) * (progress - 2));
      arrowParams.leftMargin = (int) Math.ceil(leftPosition)-15;
      textParams.leftMargin = (int) Math.ceil(leftPosition)-15;
      mArrowImg.setLayoutParams(arrowParams);
      mProgressTxt.setLayoutParams(textParams);
    } 
    mProgressBar.setProgress(progress);
    mProgressTxt.setText(progress + "%");
  }
}
