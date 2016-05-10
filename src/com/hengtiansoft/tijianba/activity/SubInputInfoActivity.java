package com.hengtiansoft.tijianba.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.SubscribeVerify;
import com.juanliuinformation.lvningmeng.R;

/**
 * 
 * com.hengtiansoft.tijianba.activity.SubInputInfoActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 2, 2014 11:45:21 AM
 */
public class SubInputInfoActivity extends BaseActivity implements OnClickListener {
  private EditText mTesterNameEditText, mTesterIdEditText, mTesterMobileEditText, mContactNameEditText,
      mContactMobileEditText, mContactAddEditText, mContactCodeEditText;
  private RadioButton mFemaleRadioButton, mMaleRadioButton, mNotMarriedRadioButton, mMarriedRadioButton;
  private SubscribeVerify mSubscribeVerify = new SubscribeVerify();
  private RadioGroup mIsmarried, mGender;
  private LinearLayout mLlytLinkMan;
  private CheckBox mCbSame;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_subcribe_one);
    Bundle bundle = this.getIntent().getExtras();
    if (bundle != null && bundle.containsKey("SubscribeVerifyInfo")) {
      mSubscribeVerify = (SubscribeVerify) bundle.get("SubscribeVerifyInfo");
    }
    setView();
    updateUi();
  }

  private void setView() {
    findViewById(R.id.rl_confirm_btn).setOnClickListener(this);
    mTesterNameEditText = (EditText) findViewById(R.id.edt_tester_name);
    mTesterIdEditText = (EditText) findViewById(R.id.edt_tester_id);
    mTesterMobileEditText = (EditText) findViewById(R.id.edt_tester_mobile);
    mContactNameEditText = (EditText) findViewById(R.id.edt_contact_name);
    mContactMobileEditText = (EditText) findViewById(R.id.edt_contact_mobile);
    mContactAddEditText = (EditText) findViewById(R.id.edt_contact_add);
    mContactCodeEditText = (EditText) findViewById(R.id.edt_contact_code);
    mMaleRadioButton = (RadioButton) findViewById(R.id.rb_gender_male);
    mFemaleRadioButton = (RadioButton) findViewById(R.id.rb_gender_female);
    mNotMarriedRadioButton = (RadioButton) findViewById(R.id.rb_not_married);
    mMarriedRadioButton = (RadioButton) findViewById(R.id.rb_married);
    mLlytLinkMan = (LinearLayout) findViewById(R.id.llyt_sub_one_linkman);
    mCbSame = (CheckBox) findViewById(R.id.cb_is_same);
    Intent intent = getIntent();
    Bundle bundle = intent.getExtras();
    if (remoteDataManager.userType == 1 && mSubscribeVerify.getExamType() == 3) {
      mLlytLinkMan.setVisibility(View.GONE);
    }
    setChange();
  }

  private void setChange() {
    mCbSame.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          mContactNameEditText.setText(mTesterNameEditText.getText().toString());
          mContactMobileEditText.setText(mTesterMobileEditText.getText().toString());
        } else {
          mContactNameEditText.setText("");
          mContactMobileEditText.setText("");
        }
      }
    });

    mContactNameEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void afterTextChanged(Editable s) {
        if (mContactNameEditText.isFocused()) {
          if ((s.toString()).equals(mTesterNameEditText.getText().toString())==false) {
            mCbSame.setChecked(false);
          }
        }
      }
    });

    mContactMobileEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void afterTextChanged(Editable s) {
        if (mContactMobileEditText.isFocused()) {
          if ((s.toString()).equals(mTesterMobileEditText.getText().toString())==false) {
            mCbSame.setChecked(false);
          }

        }
      }
    });
    mTesterNameEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void afterTextChanged(Editable s) {
        if (mCbSame.isChecked()) {
          mContactNameEditText.setText(s.toString());
        }
      }
    });
    mTesterMobileEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void afterTextChanged(Editable s) {
        if (mCbSame.isChecked()) {
          mContactMobileEditText.setText(s.toString());
        }
      }
    });
  }

  private void updateUi() {
    if (mSubscribeVerify != null) {
      mTesterNameEditText.setText(mSubscribeVerify.getTesterName());
      mTesterIdEditText.setText(mSubscribeVerify.getTesterIDNumber());
      mTesterMobileEditText.setText(mSubscribeVerify.getTesterMobile());
      mContactNameEditText.setText(mSubscribeVerify.getContactName());
      mContactMobileEditText.setText(mSubscribeVerify.getContactMobile());
      mContactAddEditText.setText(mSubscribeVerify.getContactPostAddr());
      mContactCodeEditText.setText(mSubscribeVerify.getContactZipCode());
      if (mSubscribeVerify.getTesterGender() == 1) {
        mFemaleRadioButton.setChecked(true);
      }
      if (mSubscribeVerify.isTesterMarried()) {
        mMarriedRadioButton.setChecked(true);
      }
    }
  }

  private boolean validation() {
    boolean isValid = true;
    if (mTesterNameEditText.length() == 0) {
      mTesterNameEditText.setError(getString(R.string.err_msg_name));
      isValid = false;
    }
    if (mTesterIdEditText.length() == 0) {
      mTesterIdEditText.setError(getString(R.string.err_msg_idcard));
      isValid = false;
    }
    if (((mTesterIdEditText.getText().toString())
        .matches("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$"))
        || ((mTesterIdEditText.getText().toString())
            .matches("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$"))) {
    } else {
      mTesterIdEditText.setError(getString(R.string.str_badyid_way));
      isValid = false;
    }
    if (mTesterMobileEditText.length() == 0) {
      mTesterMobileEditText.setError(getString(R.string.err_msg_phone));
      isValid = false;
    }
    if ((mTesterMobileEditText.getText().toString()).matches("1[3|5|7|8|][0-9]{9}") != true) {
      mTesterMobileEditText.setError(getString(R.string.str_mobile_way));
      isValid = false;
    }

    if (!(remoteDataManager.userType == 1 && mSubscribeVerify.getExamType() == 3)) {
      if (mContactNameEditText.length() == 0) {
        mContactNameEditText.setError(getString(R.string.err_msg_contractname));
        isValid = false;
      }
      if (mContactMobileEditText.length() == 0) {
        mContactMobileEditText.setError(getString(R.string.err_msg_contractphone));
        isValid = false;
      }
      if ((mContactMobileEditText.getText().toString()).matches("1[3|5|7|8|][0-9]{9}") != true) {
        mContactMobileEditText.setError(getString(R.string.str_mobile_way));
        isValid = false;
      }
      if (mContactAddEditText.length() == 0) {
        mContactAddEditText.setError(getString(R.string.str_email_null));
        isValid = false;
      }
    }
    return isValid;
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent();
    switch (v.getId()) {
      case R.id.rl_menu_view:
        break;
      case R.id.btn_find_more_org:

        intent.setClass(SubInputInfoActivity.this, OrgListActivity.class);
        startActivity(intent);
        finish();
        break;
      case R.id.rl_confirm_btn:
        if (validation()) {
          mSubscribeVerify.setTesterName(mTesterNameEditText.getText().toString());
          mSubscribeVerify.setTesterIDNumber(mTesterIdEditText.getText().toString());
          mSubscribeVerify.setTesterMobile(mTesterMobileEditText.getText().toString());
          mSubscribeVerify.setContactMobile(mContactMobileEditText.getText().toString());
          mSubscribeVerify.setContactName(mContactNameEditText.getText().toString());
          mSubscribeVerify.setContactPostAddr(mContactAddEditText.getText().toString());
          mSubscribeVerify.setContactZipCode(mContactCodeEditText.getText().toString());
          if (mMarriedRadioButton.isChecked()) {
            mSubscribeVerify.setTesterMarried(true);
          } else {
            mSubscribeVerify.setTesterMarried(false);
          }
          if (mFemaleRadioButton.isChecked()) {
            mSubscribeVerify.setTesterGender(1);
          } else {
            mSubscribeVerify.setTesterGender(0);
          }
          Bundle bundle = new Bundle();
          bundle.putSerializable("SubscribeVerifyInfo", mSubscribeVerify);
          intent.putExtras(bundle);
          intent.setClass(SubInputInfoActivity.this, SubSelectOrgActivity.class);
          startActivityForResult(intent, RemoteDataManager.SUBCRIBE);
        }
        break;
      default:
        break;
    }
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
