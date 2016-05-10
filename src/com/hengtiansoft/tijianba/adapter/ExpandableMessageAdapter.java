package com.hengtiansoft.tijianba.adapter;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengtiansoft.tijianba.model.Contact;
import com.juanliuinformation.lvningmeng.R;

/**
 * 
 * com.hengtiansoft.tijianba.adapter.ExpandableMeassgeAdapter
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 16, 2014 4:51:03 PM
 */
public class ExpandableMessageAdapter extends BaseExpandableListAdapter {
  private Context mContext;
  private List<String> groups;
  private List<List<Contact>> childs;

  public ExpandableMessageAdapter(Context context, List<String> groups, List<List<Contact>> childs) {
    this.groups = groups;
    this.childs = childs;
    this.mContext = context;
  }

  private ExpandableAdapterListener listener;

  public void setListener(ExpandableAdapterListener listener) {
    this.listener = listener;
  }

  @Override
  public Object getChild(int groupPosition, int childPosition) {
    return childs.get(groupPosition).get(childPosition);
  }

  @Override
  public long getChildId(int groupPosition, int childPosition) {
    return childPosition;
  }

  @Override
  public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView,
      ViewGroup parent) {
    Contact contact = ((Contact) getChild(groupPosition, childPosition));
    LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout.contact_item, null);
    TextView nameTextView = (TextView) layout.findViewById(R.id.tv_nick_name);
    TextView mobileTextView = (TextView) layout.findViewById(R.id.tv_mobile);
    final TextView statusTextView = (TextView) layout.findViewById(R.id.tv_remind);
    nameTextView.setText(contact.getName());
    if (contact.getType() == 0) {
      mobileTextView.setText(contact.getMobile());
      statusTextView.setText(mContext.getString(R.string.str_added));
      statusTextView.setTextColor(mContext.getResources().getColor(R.color.cart_grey));
      statusTextView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
    } else if (contact.getType() == 1) {
      mobileTextView.setText(contact.getMobile());
      statusTextView.setText(mContext.getString(R.string.str_add));
      statusTextView.setTextColor(mContext.getResources().getColor(R.color.white));
      statusTextView.setBackgroundResource(R.drawable.btn_add_member);
      statusTextView.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {
          RelativeLayout dialogLayout = (RelativeLayout) ((Activity) mContext).getLayoutInflater().inflate(R.layout.layout_dialog_family_add,
              null);
          TextView dialogTitle = (TextView) dialogLayout.findViewById(R.id.tv_dialog_title);
          dialogTitle.setText(mContext.getString(R.string.str_nick_name));
          AlertDialog ad = new AlertDialog.Builder(mContext).setView(dialogLayout)
              .setPositiveButton(mContext.getString(R.string.str_finish), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                  dialog.dismiss();
                  statusTextView.setText(mContext.getString(R.string.str_added));
                  statusTextView.setTextColor(mContext.getResources().getColor(R.color.cart_grey));
                  statusTextView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
                }
              }).setNegativeButton(mContext.getString(R.string.str_cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                  dialog.dismiss();

                }
              }).show();
          Button positiveButton = ad.getButton(DialogInterface.BUTTON_POSITIVE);
          positiveButton.setTextColor(mContext.getResources().getColor(R.color.org_orange));
          positiveButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
          Button negativeButton = ad.getButton(DialogInterface.BUTTON_NEGATIVE);
          negativeButton.setTextColor(mContext.getResources().getColor(R.color.cart_grey));
          negativeButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        }
      });
    } else if (contact.getType() == 2) {
      mobileTextView.setText(contact.getMobile());
      statusTextView.setText(mContext.getString(R.string.str_deleted));
      statusTextView.setTextColor(mContext.getResources().getColor(R.color.cart_grey));
      statusTextView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
    } else {
      mobileTextView.setText(contact.getContent());
      statusTextView.setText(contact.getTime());
      statusTextView.setTextColor(mContext.getResources().getColor(R.color.family_grey));
      statusTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
      statusTextView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
    }
    return layout;
  }

  @Override
  public int getChildrenCount(int groupPosition) {
    return childs.get(groupPosition).size();
  }

  @Override
  public Object getGroup(int groupPosition) {
    return groups.get(groupPosition);
  }

  @Override
  public int getGroupCount() {
    return groups.size();
  }

  @Override
  public long getGroupId(int groupPosition) {
    return groupPosition;
  }

  @Override
  public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    String text = groups.get(groupPosition);
    LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(R.layout.group_abnormal, null);
    TextView textView = (TextView) relativeLayout.findViewById(R.id.tv_group_name);
    textView.setText(text);
    return relativeLayout;
  }

  @Override
  public boolean hasStableIds() {
    return false;
  }

  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return false;
  }

  public interface ExpandableAdapterListener {
    public void onExpandableAdapterListener(int groupPosition, int childPosition);

  }
}
