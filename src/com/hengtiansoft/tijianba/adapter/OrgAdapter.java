package com.hengtiansoft.tijianba.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.Organization;
import com.juanliuinformation.lvningmeng.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * com.hengtiansoft.tijianba.adapter.OrgListAdapter
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 24, 2014 11:47:56 AM
 */
public class OrgAdapter extends BaseListAdapter<Organization> {

  private Context mContext;
  private LayoutInflater mInflater;
  private ViewHolder holder;
  private ArrayList<Organization> data;

  public OrgAdapter(Context context, ArrayList<Organization> list) {
    super(context, list, R.layout.org_item);
    this.mContext = context;
    this.data = list;
  }

  @Override
  public int getCount() {
    return data.size();
  }

  @Override
  public Organization getItem(int position) {
    return data.get(position);
  }

  @Override
  public View getView(final int pos, View view, ViewGroup arg2) {
    ViewHolder holder = null;
    final Organization item = getItem(pos);
    if (view == null) {
      // view = inflateLayout();
      holder = new ViewHolder();
      view = LayoutInflater.from(mContext).inflate(R.layout.org_item, null);
      holder.mOrgNameTextView = (TextView) view.findViewById(R.id.tv_org_name);
      holder.mOrgCharacterTextView = (TextView) view.findViewById(R.id.tv_org_type);
      holder.mOrgBrandTextView = (TextView) view.findViewById(R.id.tv_org_brand);
      holder.mOrgAddressTextView = (TextView) view.findViewById(R.id.tv_org_address);
      holder.mOrgPicImageView = (ImageView) view.findViewById(R.id.iv_org);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }
    try {
      holder.mOrgNameTextView.setText(item.getName());
      holder.mOrgCharacterTextView.setText(item.getOrgTypeName());
      holder.mOrgBrandTextView.setText(item.getBrandName());
      holder.mOrgAddressTextView.setText(item.getAddress());
      String picPath = item.getPic();
      String[] result = new String[100];
      result = picPath.split("\\.");
      picPath = RemoteDataManager.SERVICE + result[0] + "_thumb." + result[1];
      ImageLoader.getInstance().displayImage(picPath, holder.mOrgPicImageView, options);
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    return view;
  }

  class ViewHolder {
    public TextView mOrgNameTextView;
    public TextView mOrgCharacterTextView;
    public TextView mOrgAddressTextView;
    public TextView mOrgBrandTextView;
    public ImageView mOrgPicImageView;

    void initHolder(View view) {
      mOrgNameTextView = (TextView) view.findViewById(R.id.tv_org_name);
      mOrgCharacterTextView = (TextView) view.findViewById(R.id.tv_org_type);
      mOrgBrandTextView = (TextView) view.findViewById(R.id.tv_org_brand);
      mOrgAddressTextView = (TextView) view.findViewById(R.id.tv_org_address);
      mOrgPicImageView = (ImageView) view.findViewById(R.id.iv_org);
    }
  }

}
