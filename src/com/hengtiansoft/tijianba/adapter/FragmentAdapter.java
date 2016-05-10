package com.hengtiansoft.tijianba.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hengtiansoft.tijianba.activity.MainActivity;
import com.hengtiansoft.tijianba.fragment.FindFragment;
import com.hengtiansoft.tijianba.fragment.MallFragment;
import com.hengtiansoft.tijianba.fragment.MoreFragment;
import com.hengtiansoft.tijianba.fragment.MyHealthFragment;
import com.hengtiansoft.tijianba.fragment.SubscribeFragment;

public class FragmentAdapter extends FragmentStatePagerAdapter {
  public final static int TAB_COUNT = 5;

  public FragmentAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Fragment getItem(int id) {
    switch (id) {
      case MainActivity.TAB_MALL:
        MallFragment homeFragment = new MallFragment();
        return homeFragment;
      case MainActivity.TAB_SUBSCRIBE:
        SubscribeFragment categoryFragment = new SubscribeFragment();
        return categoryFragment;
      case MainActivity.TAB_FIND:
        FindFragment carFragment = new FindFragment();
        return carFragment;
      case MainActivity.TAB_MY_HEALTH:
        MyHealthFragment myHealthFragment = new MyHealthFragment();
        return myHealthFragment;
      case MainActivity.TAB_MORE:
        MoreFragment moreFragment = new MoreFragment();
        return moreFragment;
    }
    return null;
  }

  @Override
  public int getCount() {
    return TAB_COUNT;
  }

  @Override
  public int getItemPosition(Object object) {
    // TODO Auto-generated method stub
    return POSITION_NONE;
  }
}
