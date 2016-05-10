package com.hengtiansoft.tijianba.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class AdsViewPagerAdpter extends PagerAdapter {
  ArrayList<View> mViewList;
  String[] mUrl;
  Context mContext;

  public AdsViewPagerAdpter(ArrayList<View> view, String[] mUrl, Context mContext) {
    this.mViewList = view;
    this.mUrl = mUrl;
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
    container.addView(mViewList.get(position), 0);
    final int pos = position;
    mViewList.get(position).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(mUrl[pos]);
        intent.setData(content_url);
        mContext.startActivity(intent);
      }
    });
    return mViewList.get(position);
  }
}
