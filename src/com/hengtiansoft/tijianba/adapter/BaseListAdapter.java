package com.hengtiansoft.tijianba.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import com.juanliuinformation.lvningmeng.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * com.hengtiansoft.tijianba.activity.BaseActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 19, 2014 16:42:17 AM
 */
public abstract class BaseListAdapter<T> extends BaseAdapter implements ListAdapter {

  protected Context mContext;
  private List<T> mList;
  private int mLayout;
  protected int startFrom = -1; // use if BaseListAdapter in
  // SeparatedListAdapter

  protected DisplayImageOptions options;

  public BaseListAdapter(Context context, List<T> list, int layout) {
    mContext = context;
    mList = list;
    mLayout = layout;
    options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
        .showImageOnFail(R.drawable.hot4).showImageOnLoading(R.drawable.hot4).bitmapConfig(Bitmap.Config.RGB_565)
        .build();
  }

  @Override
  public int getCount() {
    if (mList != null)
      return mList.size();
    return 0;
  }

  @Override
  public T getItem(int position) {
    return position >= 0 && position < mList.size() ? mList.get(position) : null;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  public Context getContext() {
    return mContext;
  }

  public List<T> getList() {
    return mList;
  }

  public View inflateLayout() {
    LayoutInflater inf = LayoutInflater.from(mContext);
    return inf.inflate(mLayout, null);
  }

  public void setStartFrom(int from) {
    if (startFrom == -1)
      startFrom = from;
  }
}
