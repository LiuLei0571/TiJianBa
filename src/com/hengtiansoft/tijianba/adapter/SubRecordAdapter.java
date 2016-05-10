package com.hengtiansoft.tijianba.adapter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.SubscribeRecord;
import com.juanliuinformation.lvningmeng.R;

/**
 * 
 * com.hengtiansoft.tijianba.adapter.SubRecordAdapter
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 1, 2014 3:38:08 PM
 */
public class SubRecordAdapter extends BaseListAdapter<SubscribeRecord> {
  private int[] colors = new int[] { R.color.sub_green, R.color.org_orange };
  private int[] personImageSource = new int[] { R.drawable.ic_person_even, R.drawable.ic_person_odd };
  private int[] arrowImageSource = new int[] { R.drawable.ic_arrow_even, R.drawable.ic_arrow_odd };
  private Context mContext;
  private ArrayList<SubscribeRecord> data;
private String[] mWeek={
    "星期日","星期一", "星期二","星期三","星期四","星期五", "星期六"
};
  public SubRecordAdapter(Context context, ArrayList<SubscribeRecord> list) {
    super(context, list, R.layout.sub_record_item);
    this.mContext = context;
    this.data = list;
  }

  @Override
  public int getCount() {
    return data.size();
  }

  @Override
  public SubscribeRecord getItem(int position) {
    return data.get(position);
  }

  @Override
  public View getView(final int pos, View view, ViewGroup arg2) {
    ViewHolder holder = null;
    final SubscribeRecord item = getItem(pos);
    int colorPos = pos % colors.length;
    if (view == null) {
      // view = inflateLayout();
      holder = new ViewHolder();
      view = LayoutInflater.from(mContext).inflate(R.layout.sub_record_item, null);
      holder.mMenuNameTextView = (TextView) view.findViewById(R.id.tv_menu_name);
      holder.mTesterNameTextView = (TextView) view.findViewById(R.id.tv_tester);
      holder.mOrgNameTextView = (TextView) view.findViewById(R.id.tv_org_name);
      holder.mTestDayTextView = (TextView) view.findViewById(R.id.tv_date);
      holder.mTextWeek = (TextView) view.findViewById(R.id.tv_week);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }
    try {
      view.setBackgroundColor(mContext.getResources().getColor(colors[colorPos]));
      holder.mMenuNameTextView.setText(item.getMenuName());
      holder.mTesterNameTextView.setText(item.getTesterName());
      holder.mOrgNameTextView.setText(item.getOrgName());
      Calendar calendar = Calendar.getInstance();
      try {
        calendar.setTime(RemoteDataManager.sdfDate.parse(item.getTestDay()));
        holder.mTestDayTextView.setText(RemoteDataManager.sdfDate.format(calendar.getTime()));
        holder.mTextWeek.setText(mWeek[calendar.get(Calendar.DAY_OF_WEEK)-1]);
      } catch (ParseException e) {
        e.printStackTrace();
      }
      holder.mTesterNameTextView.setCompoundDrawablesWithIntrinsicBounds(personImageSource[colorPos], 0, 0, 0);
      holder.mOrgNameTextView.setCompoundDrawablesWithIntrinsicBounds(arrowImageSource[colorPos], 0, 0, 0);
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    return view;
  }

  class ViewHolder {
    public TextView mMenuNameTextView;
    public TextView mTesterNameTextView;
    public TextView mOrgNameTextView;
    public TextView mTestDayTextView;
    public TextView mTextWeek;

    void initHolder(View view) {
      mMenuNameTextView = (TextView) view.findViewById(R.id.tv_menu_name);
      mTesterNameTextView = (TextView) view.findViewById(R.id.tv_tester);
      mOrgNameTextView = (TextView) view.findViewById(R.id.tv_org_name);
      mTestDayTextView = (TextView) view.findViewById(R.id.tv_date);
      mTextWeek = (TextView) view.findViewById(R.id.tv_week);
    }
  }
}
