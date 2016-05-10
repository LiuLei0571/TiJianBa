package com.hengtiansoft.tijianba.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.BookData;
import com.hengtiansoft.tijianba.net.response.ReturnFromServerListener;
import com.juanliuinformation.lvningmeng.R;

public class RecordItemAdapter extends BaseListAdapter<BookData> {
  private Context mContext;
  private static RecordItemAdapterListener listener;
  private RemoteDataManager mRemoteDataManager;
  private ArrayList<BookData> data;
  private boolean isConteced;
  private String mUnit;

  public void setListener(RecordItemAdapterListener listener) {
    this.listener = listener;
  }

  public RecordItemAdapter(Context context, ArrayList<BookData> list, RemoteDataManager remoteDataManage,
      boolean isConteced, String mUnit) {
    super(context, list, R.layout.record_item);
    this.mContext = context;
    this.data = list;
    this.mRemoteDataManager = remoteDataManage;
    this.isConteced = isConteced;
    this.mUnit = mUnit;
  }

  @Override
  public int getCount() {
    return data.size();
  }

  @Override
  public BookData getItem(int position) {
    return data.get(position);
  }

  @Override
  public View getView(final int pos, View view, ViewGroup arg2) {
    ViewHolder holder = null;
    final BookData item = getItem(pos);
    if (view == null) {
      holder = new ViewHolder();
      view = LayoutInflater.from(mContext).inflate(R.layout.record_item, null);
      holder.mTvNum = (TextView) view.findViewById(R.id.tv_num);
      holder.mTvUnit = (TextView) view.findViewById(R.id.tv_unit_record);
      holder.mTvTime = (TextView) view.findViewById(R.id.tv_time_record);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }
    try {
      holder.mTvNum.setText(item.getValue() + "");
      holder.mTvUnit.setText(mUnit);
      holder.mTvTime.setText(getDate(item.getDataTime()));
      view.findViewById(R.id.tv_delete).setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          deleteRecordData(item.getBookletId(), pos);
          listener.onRecordItemAdapterListener();
          notifyDataSetChanged();
        }
      });
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    return view;
  }

  class ViewHolder {
    public TextView mTvNum;
    public TextView mTvUnit;
    public TextView mTvTime;

    void initHolder(View view) {
      mTvNum = (TextView) view.findViewById(R.id.tv_num);
      mTvUnit = (TextView) view.findViewById(R.id.tv_unit_record);
      mTvTime = (TextView) view.findViewById(R.id.tv_time_record);
    }
  }

  public interface RecordItemAdapterListener {
    public void onRecordItemAdapterListener();
  }

  private void deleteRecordData(int bookId, final int pos) {
    mRemoteDataManager.bookletDeleteListener = new ReturnFromServerListener() {
      @Override
      public void onSuccess(String message) {
        data.remove(pos);
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
      }
    };
    if (isConteced) {
      mRemoteDataManager.deleteBooklet(bookId);
    }
  }
  
  private String  getDate(String str){
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
    Date date = null;
    try {
      date = ((java.text.DateFormat) format).parse(str);
    } catch (ParseException e) {
    }   
    SimpleDateFormat sdf=new SimpleDateFormat("MM月dd日 hh:mm"); 
    String time=sdf.format(date); 
    return time;
  }
}
