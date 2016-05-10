package com.hengtiansoft.tijianba.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hengtiansoft.tijianba.activity.ReportEditActivity;
import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.Report;
import com.juanliuinformation.lvningmeng.R;

/**
 * 
 * com.hengtiansoft.tijianba.adapter.ReportAdapter
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 5, 2014 3:25:29 PM
 */
public class ReportAdapter extends BaseListAdapter<Report> {

  private Context mContext;
  private ArrayList<Report> data;
  private int[] colors = new int[] { R.color.my_orange, R.color.sub_green };
  private static ReportAdapterListener mListener;
  private String[] mWeek = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

  public void setReportListener(ReportAdapterListener listener) {
    this.mListener = listener;
  }

  public ReportAdapter(Context context, ArrayList<Report> list) {
    super(context, list, R.layout.report_item);
    this.mContext = context;
    this.data = list;
  }

  @Override
  public int getCount() {
    return data.size();
  }

  @Override
  public Report getItem(int position) {
    return data.get(position);
  }

  @Override
  public View getView(final int pos, View view, ViewGroup arg2) {
    ViewHolder holder = null;
    final Report item = getItem(pos);
    if (view == null) {
      // view = inflateLayout();
      holder = new ViewHolder();
      view = LayoutInflater.from(mContext).inflate(R.layout.report_item, null);
      holder.mReportNameTextView = (TextView) view.findViewById(R.id.tv_report_name);
      holder.mCreateTimeTextView = (TextView) view.findViewById(R.id.tv_report_create_time);
      holder.mCreateWeekTextView = (TextView) view.findViewById(R.id.tv_report_create_week);
      holder.backView = view.findViewById(R.id.rl_report);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }
    try {
      int colorPos = pos % colors.length;
      holder.backView.setBackgroundColor(mContext.getResources().getColor(colors[colorPos]));
      holder.mReportNameTextView.setText(item.getName());
      holder.mCreateTimeTextView.setText(getTime(item.getCreateTime()));
      holder.mCreateWeekTextView.setText(getWeek(item.getCreateTime()));
      System.out.println(item.getAttachmentType());
      if (item.getAttachmentType() == 0) {
        view.findViewById(R.id.tv_edit).setVisibility(view.GONE);
      } else if (item.getAttachmentType() == 1) {
        view.findViewById(R.id.tv_edit).setVisibility(view.VISIBLE);
        view.findViewById(R.id.tv_edit).setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(mContext, ReportEditActivity.class);
            intent.putExtra("id", item.getId());
            mContext.startActivity(intent);
          }
        });
      }
      view.findViewById(R.id.tv_delete).setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          mListener.onReportAdapterListener(item.getId(), pos);
        }
      });
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    return view;
  }

  class ViewHolder {
    public TextView mReportNameTextView;
    public TextView mCreateTimeTextView;
    public TextView mCreateWeekTextView;
    public View backView;

    void initHolder(View view) {
      mReportNameTextView = (TextView) view.findViewById(R.id.tv_report_name);
      mCreateTimeTextView = (TextView) view.findViewById(R.id.tv_report_create_time);
      backView = view.findViewById(R.id.rl_report);
    }
  }

  private String getWeek(String strTime) {
    Calendar calendar = Calendar.getInstance();
    try {
      calendar.setTime(RemoteDataManager.sdfDate.parse(strTime));
    } catch (ParseException e) {
    }
    return mWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1];
  }

  @SuppressLint("SimpleDateFormat")
  private String getTime(String str) {
    String dateTime = "";
    Date date = null;
    try {
      date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
    } catch (ParseException e) {
    }
    dateTime = new SimpleDateFormat("yyyy-MM-dd").format(date);
    return dateTime;
  }

  public interface ReportAdapterListener {
    public void onReportAdapterListener(int id, int pos);
  }

}
