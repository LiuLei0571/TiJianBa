package com.hengtiansoft.tijianba.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.PackedSubscribeInfo;
import com.hengtiansoft.tijianba.net.response.ReturnFromServerListener;
import com.hengtiansoft.tijianba.net.response.SubscribeVerify;
import com.juanliuinformation.lvningmeng.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * com.hengtiansoft.tijianba.activity.SubConfirmInfoActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 3, 2014 3:43:03 PM
 */
public class SubConfirmInfoActivity extends BaseActivity implements OnClickListener {
  private TextView mTesterNameTextView, mTesterIdTextView, mTesterMobileTextView, mContactNameTextView,
      mContactMobileTextView, mContactAddTextView, mContactCodeTextView;
  private RadioButton mFemaleRadioButton, mMaleRadioButton, mNotMarriedRadioButton, mMarriedRadioButton;
  private boolean isTesterExpand, isContactExpand, isUpload;
  private SubscribeVerify mSubscribeVerify = new SubscribeVerify();
  private TextView mMenuNameTextView, mMenuTypeTextView, mMenuSuitPeoTextView, mMenuSuitGenderTextView,
      mOrgNameTextView, mTestTimeTextView;
  private CheckBox mCbIsUpload;
  private RelativeLayout mRlytSent;
  private boolean send;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_subcribe_five);
    isTesterExpand = false;
    isContactExpand = false;
    Bundle bundle = this.getIntent().getExtras();
    if (bundle != null && bundle.containsKey("SubscribeVerifyInfo")) {
      mSubscribeVerify = (SubscribeVerify) bundle.get("SubscribeVerifyInfo");
      send = bundle.getBoolean("send");
    }
    setView();
  }

  private void setView() {
    setStepImageResource();
    mRlytSent = (RelativeLayout) findViewById(R.id.rlyt_send);
    findViewById(R.id.rl_sub_done).setOnClickListener(this);
    findViewById(R.id.rl_contact_expand).setOnClickListener(this);
    findViewById(R.id.rl_tester_expand).setOnClickListener(this);
    mMenuNameTextView = (TextView) findViewById(R.id.tv_menu_name);
    mMenuNameTextView.setText(mSubscribeVerify.getMenuName());
    mMenuTypeTextView = (TextView) findViewById(R.id.tv_menu_type);
    mMenuTypeTextView.setText(mSubscribeVerify.getMenuType());
    mMenuSuitPeoTextView = (TextView) findViewById(R.id.tv_menu_suit_people);
    mMenuSuitPeoTextView.setText(mSubscribeVerify.getMenuSuitPeo());
    mMenuSuitGenderTextView = (TextView) findViewById(R.id.tv_menu_suit_gender);
    mMenuSuitGenderTextView.setText(mSubscribeVerify.getMenuSuitGender());
    mTesterNameTextView = (TextView) findViewById(R.id.tv_tester_name);
    mTesterNameTextView.setText(mSubscribeVerify.getTesterName());
    mTesterIdTextView = (TextView) findViewById(R.id.tv_tester_id);
    mTesterIdTextView.setText(mSubscribeVerify.getTesterIDNumber());
    mTesterMobileTextView = (TextView) findViewById(R.id.tv_tester_mobile);
    mTesterMobileTextView.setText(mSubscribeVerify.getTesterMobile());
    mContactNameTextView = (TextView) findViewById(R.id.tv_contact_name);
    mContactNameTextView.setText(mSubscribeVerify.getContactName());
    mContactMobileTextView = (TextView) findViewById(R.id.tv_contact_mobile);
    mContactMobileTextView.setText(mSubscribeVerify.getContactMobile());
    mContactAddTextView = (TextView) findViewById(R.id.tv_contact_add);
    mContactAddTextView.setText(mSubscribeVerify.getContactPostAddr());
    mContactCodeTextView = (TextView) findViewById(R.id.tv_contact_code);
    mContactCodeTextView.setText(mSubscribeVerify.getContactZipCode());
    mMaleRadioButton = (RadioButton) findViewById(R.id.rb_gender_male);
    mFemaleRadioButton = (RadioButton) findViewById(R.id.rb_gender_female);
    mNotMarriedRadioButton = (RadioButton) findViewById(R.id.rb_not_married);
    mMarriedRadioButton = (RadioButton) findViewById(R.id.rb_married);
    ImageView mMenuImageView = (ImageView) findViewById(R.id.iv_menu);
    String picPath = mSubscribeVerify.getLogo();
    String[] result = new String[100];
    result = picPath.split("\\.");
    picPath = RemoteDataManager.SERVICE + result[0] + "_thumb." + result[1];
    ImageLoader.getInstance().displayImage(picPath, mMenuImageView, options);
    ImageView mFamilyImageView = (ImageView) findViewById(R.id.iv_family);
    if (mSubscribeVerify.isFamily()) {
      mFamilyImageView.setVisibility(View.VISIBLE);
    } else {
      mFamilyImageView.setVisibility(View.GONE);
    }
    if (mSubscribeVerify.getTesterGender() == 1) {
      mFemaleRadioButton.setChecked(true);
      mMaleRadioButton.setVisibility(View.GONE);
    } else {
      mMaleRadioButton.setChecked(true);
      mFemaleRadioButton.setVisibility(View.GONE);
    }
    if (mSubscribeVerify.isTesterMarried()) {
      mMarriedRadioButton.setChecked(true);
      mNotMarriedRadioButton.setVisibility(View.GONE);
    } else {
      mNotMarriedRadioButton.setChecked(true);
      mMarriedRadioButton.setVisibility(View.GONE);
    }
    mOrgNameTextView = (TextView) findViewById(R.id.tv_test_org);
    mOrgNameTextView.setText(mSubscribeVerify.getOrgName());
    mTestTimeTextView = (TextView) findViewById(R.id.tv_time);
    mTestTimeTextView.setText(getTime(mSubscribeVerify.getTestDay()));
    if (send) {
      mCbIsUpload = (CheckBox) findViewById(R.id.cb_is_upload);
      mCbIsUpload.setChecked(mSubscribeVerify.isAgreeUploadReport());
      mCbIsUpload.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
          mCbIsUpload.setChecked(isChecked);
          mSubscribeVerify.setAgreeUploadReport(isChecked);
        }
      });
      mCbIsUpload.setChecked(true);
    } else {
      mRlytSent.setVisibility(View.GONE);
    }
  }

  private String getTime(String str) {
    String[] strTime = str.split("\\s{1,}");
    return strTime[0];
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent();
    switch (v.getId()) {
      case R.id.rl_sub_done:
        doSubscribe();
//     
        // setResult(RESULT_OK);
        // finish();
        break;
      case R.id.rl_tester_expand:
        if (!isTesterExpand) {
          isTesterExpand = true;
          findViewById(R.id.rl_tester_info).setVisibility(View.VISIBLE);
        } else {
          isTesterExpand = false;
          findViewById(R.id.rl_tester_info).setVisibility(View.GONE);
        }
        break;
      case R.id.rl_contact_expand:
        if (!isContactExpand) {
          isContactExpand = true;
          findViewById(R.id.rl_contact_info).setVisibility(View.VISIBLE);
        } else {
          isContactExpand = false;
          findViewById(R.id.rl_contact_info).setVisibility(View.GONE);
        }
        break;
      default:
        break;
    }
  }

  private void doSubscribe() {
    showProgressDialog("预约体检", "预约中..");
    PackedSubscribeInfo packedSubscribeInfo = new PackedSubscribeInfo();
    packedSubscribeInfo.setBookServerId(mSubscribeVerify.getBookServerId());
    packedSubscribeInfo.setOrgId(mSubscribeVerify.getOrgId());
    packedSubscribeInfo.setExamType(mSubscribeVerify.getExamType());
    packedSubscribeInfo.setTesterName(mSubscribeVerify.getTesterName());
    packedSubscribeInfo.setTesterGender(mSubscribeVerify.getTesterGender());
    packedSubscribeInfo.setTesterMarried(mSubscribeVerify.isTesterMarried());
    packedSubscribeInfo.setTesterIDNumber(mSubscribeVerify.getTesterIDNumber());
    packedSubscribeInfo.setTesterMobile(mSubscribeVerify.getTesterMobile());
    packedSubscribeInfo.setContactName(mSubscribeVerify.getContactName());
    packedSubscribeInfo.setContactMobile(mSubscribeVerify.getContactMobile());
    packedSubscribeInfo.setContactPostAddr(mSubscribeVerify.getContactPostAddr());
    packedSubscribeInfo.setContactZipCode(mSubscribeVerify.getContactZipCode());
    packedSubscribeInfo.setTestDay(mSubscribeVerify.getTestDay()+ " 00:00:00");
    if (send) {
      packedSubscribeInfo.setAgreeUploadReport(mSubscribeVerify.isAgreeUploadReport());
    } else {
      packedSubscribeInfo.setAgreeUploadReport(false);
    }
    remoteDataManager.subscribeConfirmListener = new ReturnFromServerListener() {

      @Override
      public void onSuccess(String message) {
        dismissProgressDialog();
        setResult(RESULT_OK);
        SubConfirmInfoActivity.this.runOnUiThread(new Runnable() {
          public void run() {
            new AlertDialog.Builder(SubConfirmInfoActivity.this).setTitle("提示")  
            .setMessage("预约成功!")  
                     .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                         SubConfirmInfoActivity.this.finish();
                       }
                     }).show(); 
          };
        });
      }
      @Override
      public void onError(String errorCode, String errorMessage) {
        handleError(errorMessage);
      }
    };
    if (validateInternet()) {

      remoteDataManager.subscribeConfirm(packedSubscribeInfo);
    }
  }
  public void setStepImageResource() {
    ImageView stepOneImageView = (ImageView) findViewById(R.id.iv_step_one);
    stepOneImageView.setImageResource(R.drawable.ic_lemon_passed);
    ImageView stepTwoImageView = (ImageView) findViewById(R.id.iv_step_two);
    stepTwoImageView.setImageResource(R.drawable.ic_lemon_passed);
    ImageView stepThreeImageView = (ImageView) findViewById(R.id.iv_step_three);
    stepThreeImageView.setImageResource(R.drawable.ic_lemon_passed);
    ImageView stepFourImageView = (ImageView) findViewById(R.id.iv_step_four);
    stepFourImageView.setImageResource(R.drawable.ic_lemon_passed);
    ImageView stepFiveImageView = (ImageView) findViewById(R.id.iv_step_five);
    stepFiveImageView.setImageResource(R.drawable.ic_lemon_selected);
  }
}
