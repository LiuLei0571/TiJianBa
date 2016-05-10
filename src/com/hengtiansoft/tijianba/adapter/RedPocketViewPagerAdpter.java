
package com.hengtiansoft.tijianba.adapter;

import java.util.ArrayList;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class RedPocketViewPagerAdpter extends PagerAdapter {
  ArrayList<View> mViewList;

  public RedPocketViewPagerAdpter(ArrayList<View> view) {
    this.mViewList = view;
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
  public void destroyItem(ViewGroup container, int position, Object object) 
  {
    container.removeView(mViewList.get(position));
  }
  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    container.addView(mViewList.get(position), 0);
    return mViewList.get(position);
  }
}
