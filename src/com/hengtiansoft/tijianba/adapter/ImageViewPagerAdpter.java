package com.hengtiansoft.tijianba.adapter;

import java.util.ArrayList;

import com.hengtiansoft.tijianba.util.TouchImageView;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class ImageViewPagerAdpter extends PagerAdapter {
  ArrayList<TouchImageView> mViewList;
  Context mContext;

  public ImageViewPagerAdpter(ArrayList<TouchImageView> view, Context mContext) {
    this.mViewList = view;
    this.mContext = mContext;
  }

  @Override
  public int getCount() {
    return mViewList.size();
  }

  @Override
  public boolean isViewFromObject(View arg0, Object arg1) {
    return arg0 == arg1;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView(mViewList.get(position));
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    ViewGroup parent = (ViewGroup) mViewList.get(position).getParent();
    if (parent != null) {
   parent.removeAllViews();
    } 
    container.addView(mViewList.get(position), 0);
    return mViewList.get(position);
  }
}
