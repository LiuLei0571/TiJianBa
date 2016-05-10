package com.hengtiansoft.tijianba.adapter;

import java.util.List; 

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.OrgOfSubscribe;
import com.juanliuinformation.lvningmeng.R;

/**
 * 
 * com.hengtiansoft.tijianba.activity.SubSelectOrgActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 2, 2014 2:12:12 PM
 */
public class ExpandableAdapter extends BaseExpandableListAdapter {
  private Context mContext;
  private List<String> groups;
  private List<List<OrgOfSubscribe>> childs;
  private int selectedOrgID = -1;
  private String selectedOrgName = "";
  private String selectedWorkTime = "";
  private String selectedStartDate = "";
  public ExpandableAdapter(Context context, List<String> groups, List<List<OrgOfSubscribe>> childs) {
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

  public int getSelectOrgId() {
    return selectedOrgID;
  }

  public String getSelectWorkTime() {
    return selectedWorkTime;
  }
  public String getSelectStartDate() {
    return selectedStartDate;
  }
  public String getSelectOrgName() {
    return selectedOrgName;
  }
  public boolean select(int groupPosition, int childPosition) {
    if (!((OrgOfSubscribe) getChild(groupPosition, childPosition)).isSelected()) {
      ((OrgOfSubscribe) getChild(groupPosition, childPosition)).setSelected(true);
      listener.onExpandableAdapterListener(groupPosition, childPosition);
      for (int i = 0; i < getGroupCount(); i++) {
        for (int j = 0; j < getChildrenCount(i); j++)
          if (i != groupPosition || j != childPosition) {
            ((OrgOfSubscribe) getChild(i, j)).setSelected(false);
          }
      }
    } else {
      ((OrgOfSubscribe) getChild(groupPosition, childPosition)).setSelected(false);
    }
    notifyDataSetChanged();
    return ((OrgOfSubscribe) getChild(groupPosition, childPosition)).isSelected();
  }

  @Override
  public long getChildId(int groupPosition, int childPosition) {
    return childPosition;
  }

  @Override
  public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView,
      ViewGroup parent) {
    final OrgOfSubscribe org = ((OrgOfSubscribe) getChild(groupPosition, childPosition));
    LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    RelativeLayout layout = (RelativeLayout) layoutInflater.inflate(R.layout.child, null);
//    layout.setOnClickListener(new OnClickListener() {
//
//      @Override
//      public void onClick(View v) {
//
//        Intent intent = new Intent(mContext, OrgDetailActivity.class);
//        intent.putExtra("orgID", org.getOrgId());
//        mContext.startActivity(intent);
//      }
//    });
    TextView nameTextView = (TextView) layout.findViewById(R.id.tv_org_name);
    TextView typeTextView = (TextView) layout.findViewById(R.id.tv_org_type);
    TextView addTextView = (TextView) layout.findViewById(R.id.tv_org_address);
    nameTextView.setText(org.getOrgName());
    typeTextView.setText(RemoteDataManager.orgType.get(org.getOrgType()));
    addTextView.setText(org.getAddress());
    final CheckBox selectCheckBox = (CheckBox) layout.findViewById(R.id.chb_org);
    if (org.isSelected()) {
      selectedOrgID = org.getOrgId();
      selectedOrgName = org.getOrgName();
      selectedWorkTime= org.getWorkTime();
      selectedStartDate= org.getTestStartDate();
      selectCheckBox.setChecked(true);
    } else {
      selectCheckBox.setChecked(false);
    }
    selectCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (select(groupPosition, childPosition)) {
          selectCheckBox.setChecked(true);
          selectedOrgID = org.getOrgId();
          selectedOrgName = org.getOrgName();
          selectedWorkTime= org.getWorkTime();
          selectedStartDate= org.getTestStartDate();
        } else {
          selectCheckBox.setChecked(false);
        }
      }
    });
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

    LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.group, null);
    TextView textView = (TextView) linearLayout.findViewById(R.id.tv_city);
    textView.setText(text);
    return linearLayout;
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
