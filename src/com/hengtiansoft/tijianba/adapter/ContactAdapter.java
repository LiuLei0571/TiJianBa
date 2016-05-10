package com.hengtiansoft.tijianba.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hengtiansoft.tijianba.activity.AddMessageActivity;
import com.hengtiansoft.tijianba.model.Contact;
import com.juanliuinformation.lvningmeng.R;

/**
 * 
 * com.hengtiansoft.tijianba.adapter.ContactAdapter
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 16, 2014 1:24:26 PM
 */
public class ContactAdapter extends BaseListAdapter<Contact> {

  private Context mContext;
  private LayoutInflater mInflater;
  private ViewHolder holder;
  private ArrayList<Contact> data;
  private ContactAdapterListener listener;

  public void setListener(ContactAdapterListener listener) {
    this.listener = listener;
  }

  public ContactAdapter(Context context, ArrayList<Contact> list) {
    super(context, list, R.layout.contact_item);
    this.mContext = context;
    this.data = list;
  }

  @Override
  public int getCount() {
    return data.size();
  }

  @Override
  public Contact getItem(int position) {
    return data.get(position);
  }

  @Override
  public View getView(final int pos, View view, ViewGroup arg2) {
    ViewHolder holder = null;
    final Contact item = getItem(pos);
    if (view == null) {
      // view = inflateLayout();
      holder = new ViewHolder();
      view = LayoutInflater.from(mContext).inflate(R.layout.contact_item, null);
      holder.mNickTimeTextView = (TextView) view.findViewById(R.id.tv_nick_name);
      holder.mMobileTextView = (TextView) view.findViewById(R.id.tv_mobile);
      holder.mReminderTextView = (TextView) view.findViewById(R.id.tv_remind);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }
    try {
      holder.mNickTimeTextView.setText(item.getNickName() + "(" + item.getName() + ")");
      holder.mMobileTextView.setText(item.getMobile());
      holder.mReminderTextView.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {
          Intent intent = new Intent(mContext, AddMessageActivity.class);
          mContext.startActivity(intent);
        }
      });
      view.findViewById(R.id.tv_delete).setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {
          listener.onContactAdapterListener(pos);
        }
      });
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    return view;
  }

  class ViewHolder {
    public TextView mNickTimeTextView;
    public TextView mMobileTextView;
    public TextView mReminderTextView;

    void initHolder(View view) {
      mNickTimeTextView = (TextView) view.findViewById(R.id.tv_nick_name);
      mMobileTextView = (TextView) view.findViewById(R.id.tv_mobile);
      mReminderTextView = (TextView) view.findViewById(R.id.tv_remind);
    }
  }

  public interface ContactAdapterListener {
    public void onContactAdapterListener(int pos);
  }
}
