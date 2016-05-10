package com.hengtiansoft.tijianba.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengtiansoft.tijianba.adapter.ExpandableAdapter;
import com.hengtiansoft.tijianba.adapter.ExpandableAdapter.ExpandableAdapterListener;
import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.OrgListOfCity;
import com.hengtiansoft.tijianba.net.response.OrgOfSubscribe;
import com.hengtiansoft.tijianba.net.response.SubscribeVerify;
import com.juanliuinformation.lvningmeng.R;

/**
 * 
 * com.hengtiansoft.tijianba.activity.SubSelectOrgActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 2, 2014 2:12:12 PM
 */
public class SubSelectOrgActivity extends BaseActivity implements OnClickListener {
  private ExpandableListView mOrgExpandableListView;
  private ExpandableAdapter mOrgExpandableAdapter;
  private TextView mMenuNameTextView, mMenuTypeTextView, mMenuSuitPeoTextView, mMenuSuitGenderTextView,
      mMenuLocationTextView, mMenuContentTextView, mOrgNumTextView, mMenuCustomerTextView;
  private List<String> groups = new ArrayList<String>();
  private List<List<OrgOfSubscribe>> childs = new ArrayList<List<OrgOfSubscribe>>();
  private ArrayList<OrgListOfCity> mOrgOfCityList = new ArrayList<OrgListOfCity>();
  private SubscribeVerify mSubscribeVerify = new SubscribeVerify();
  private boolean send;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_subcribe_two);
    Bundle bundle = this.getIntent().getExtras();
    if (bundle != null && bundle.containsKey("SubscribeVerifyInfo")) {
      mSubscribeVerify = (SubscribeVerify) bundle.get("SubscribeVerifyInfo");
      setOrgData();
    }
    setView();

  }

  private void setView() {
    setStepImageResource();
    findViewById(R.id.rl_confirm).setOnClickListener(this);
    TextView orgNumTextView = (TextView) findViewById(R.id.tv_org_num);
    orgNumTextView.setText(mSubscribeVerify.getOrgNum() + "");
    mOrgExpandableListView = (ExpandableListView) findViewById(R.id.elv_org);
    mOrgExpandableListView.setOnGroupClickListener(new OnGroupClickListener() {

      @Override
      public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        // TODO Auto-generated method stub
        return true;
      }
    });
    mOrgExpandableAdapter = new ExpandableAdapter(this, groups, childs);
    mOrgExpandableAdapter.setListener(new ExpandableAdapterListener() {

      @Override
      public void onExpandableAdapterListener(int groupPosition, int childPosition) {
        send = mSubscribeVerify.getCityOrgList().get(groupPosition).getOrgList().get(childPosition).isCanUploadReport();
      }
    });
    mOrgExpandableListView.setAdapter(mOrgExpandableAdapter);

    mOrgExpandableListView.setGroupIndicator(null);
    setData();
    mOrgExpandableAdapter.notifyDataSetChanged();
    int groupCount = mOrgExpandableListView.getCount();
    for (int i = 0; i < groupCount; i++) {
      mOrgExpandableListView.expandGroup(i);
    }

  }

  private void setOrgData() {
    if (mSubscribeVerify != null && mSubscribeVerify.getCityOrgList() != null) {
      int citySize = mSubscribeVerify.getCityOrgList().size();
      for (int i = 0; i < citySize; i++) {
        OrgListOfCity orgOfCityTmp = mSubscribeVerify.getCityOrgList().get(i);
        OrgListOfCity orgOfCity = new OrgListOfCity();
        orgOfCity.setCityName(orgOfCityTmp.getCityName());
        ArrayList<OrgOfSubscribe> orgOfSubscribeList = new ArrayList<OrgOfSubscribe>();
        int orgSize = mSubscribeVerify.getCityOrgList().get(i).getOrgList().size();
        for (int j = 0; j < orgSize; j++) {
          OrgOfSubscribe orgTmp = mSubscribeVerify.getCityOrgList().get(i).getOrgList().get(j);
          OrgOfSubscribe org = new OrgOfSubscribe();
          org.setOrgId(orgTmp.getOrgId());
          if (mSubscribeVerify.getOrgId() == org.getOrgId()) {
            org.setSelected(true);
           
          } else {
            org.setSelected(false);
          }
          org.setAddress(orgTmp.getAddress());
          org.setAdvanceDays(orgTmp.getAdvanceDays());
          org.setBrandName(orgTmp.getBrandName());
          org.setOrgType(orgTmp.getOrgType());
          org.setOrgName(orgTmp.getOrgName());
          org.setWorkTime(orgTmp.getWorkTime());
          org.setTestStartDate(orgTmp.getTestStartDate());
          org.setCanUploadReport(orgTmp.isCanUploadReport());
          orgOfSubscribeList.add(org);
        }
        orgOfCity.setOrgList(orgOfSubscribeList);
        mOrgOfCityList.add(orgOfCity);
      }

    }

  }

  private void setData() {
    for (int i = 0; i < mOrgOfCityList.size(); i++) {
      groups.add(mOrgOfCityList.get(i).getCityName());
      List<OrgOfSubscribe> orgCityList = new ArrayList<OrgOfSubscribe>();
      int orgSize = mOrgOfCityList.get(i).getOrgList().size();
      for (int j = 0; j < orgSize; j++) {
        OrgOfSubscribe orgTmp = mOrgOfCityList.get(i).getOrgList().get(j);
        OrgOfSubscribe org = new OrgOfSubscribe();
        org.setAddress(orgTmp.getAddress());
        org.setAdvanceDays(orgTmp.getAdvanceDays());
        org.setBrandName(orgTmp.getBrandName());
        org.setOrgId(orgTmp.getOrgId());
        org.setOrgName(orgTmp.getOrgName());
        org.setOrgType(orgTmp.getOrgType());
        org.setWorkTime(orgTmp.getWorkTime());
        org.setTestStartDate(orgTmp.getTestStartDate());
        org.setCanUploadReport(orgTmp.isCanUploadReport());
        if (mSubscribeVerify.getOrgId() == org.getOrgId()) {
          org.setSelected(true);
          send =org.isCanUploadReport();
        } else {
          org.setSelected(false);
        }
        orgCityList.add(org);
      }
      childs.add(orgCityList);
    }
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent();
    switch (v.getId()) {
      case R.id.rl_confirm:
        if (mOrgExpandableAdapter.getSelectOrgId() != -1) {
          mSubscribeVerify.setOrgId(mOrgExpandableAdapter.getSelectOrgId());
          mSubscribeVerify.setOrgName(mOrgExpandableAdapter.getSelectOrgName());
          mSubscribeVerify.setWorkTime(mOrgExpandableAdapter.getSelectWorkTime());
          mSubscribeVerify.setTestStartDate(mOrgExpandableAdapter.getSelectStartDate());
          mSubscribeVerify.setOrgName(mOrgExpandableAdapter.getSelectOrgName());
          Bundle bundle = new Bundle();
          bundle.putSerializable("SubscribeVerifyInfo", mSubscribeVerify);
          bundle.putBoolean("send", send);
          intent.setClass(SubSelectOrgActivity.this, SubSelectMenuActivity.class);
          intent.putExtras(bundle);
          startActivityForResult(intent, RemoteDataManager.SUBCRIBE);
        } else {
          complain(R.string.error_select_oneorg);
        }
        break;
      default:
        break;
    }
  }

  public void setStepImageResource() {
    ImageView stepOneImageView = (ImageView) findViewById(R.id.iv_step_one);
    stepOneImageView.setImageResource(R.drawable.ic_lemon_passed);
    ImageView stepTwoImageView = (ImageView) findViewById(R.id.iv_step_two);
    stepTwoImageView.setImageResource(R.drawable.ic_lemon_selected);
    ImageView stepThreeImageView = (ImageView) findViewById(R.id.iv_step_three);
    stepThreeImageView.setImageResource(R.drawable.ic_lemon_unselected);
    ImageView stepFourImageView = (ImageView) findViewById(R.id.iv_step_four);
    stepFourImageView.setImageResource(R.drawable.ic_lemon_unselected);
    ImageView stepFiveImageView = (ImageView) findViewById(R.id.iv_step_five);
    stepFiveImageView.setImageResource(R.drawable.ic_lemon_unselected);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (resultCode) {
      case RESULT_OK:
        setResult(RESULT_OK);
        finish();
        break;
      default:
        break;
    }
  }
}
