package com.hengtiansoft.tijianba.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.HotOrg;
import com.juanliuinformation.lvningmeng.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * com.hengtiansoft.tijianba.activity.BaseActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 19, 2014 16:50:17 AM
 */
public class RemOrgAdapter extends BaseListAdapter<HotOrg> {

  private Context mContext;
  private LayoutInflater mInflater;
  private ViewHolder holder;
  private ArrayList<HotOrg> data;

  public RemOrgAdapter(Context context, ArrayList<HotOrg> list) {
    super(context, list, R.layout.org_rem_info_item);
    this.mContext = context;
    this.data = list;
  }

  @Override
  public int getCount() {
    return data.size();
  }

  @Override
  public HotOrg getItem(int position) {
    return data.get(position);
  }

  @Override
  public View getView(final int pos, View view, ViewGroup arg2) {
    ViewHolder holder = null;
    final HotOrg item = getItem(pos);
    Log.i("GridView", "position" + pos);
    if (view == null) {
      // view = inflateLayout();
      holder = new ViewHolder();
      view = LayoutInflater.from(mContext).inflate(R.layout.org_rem_info_item, null);
      holder.mOrgNameTextView = (TextView) view.findViewById(R.id.tv_rem_name);
      holder.mOrgCharacterTextView = (TextView) view.findViewById(R.id.tv_rem_character);
      holder.mOrgPicImageView = (ImageView) view.findViewById(R.id.iv_rem);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
      resetView(holder);
    }
    try {
      ImageLoader.getInstance().displayImage(RemoteDataManager.SERVICE + item.getImg(), holder.mOrgPicImageView,
          options);
      holder.mOrgNameTextView.setText(item.getName());
      holder.mOrgCharacterTextView.setText(item.getOrgTypeName());

    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    return view;
  }

  class ViewHolder {
    public TextView mOrgNameTextView;
    public TextView mOrgCharacterTextView;
    public ImageView mOrgPicImageView;

    void initHolder(View view) {
      mOrgNameTextView = (TextView) view.findViewById(R.id.tv_rem_name);
      mOrgCharacterTextView = (TextView) view.findViewById(R.id.tv_rem_character);
      mOrgPicImageView = (ImageView) view.findViewById(R.id.iv_rem);
    }
  }

  private void resetView(ViewHolder holder) {
    holder.mOrgNameTextView.setText(null);
    holder.mOrgCharacterTextView.setText(null);
  }
}
