package com.hengtiansoft.tijianba.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hengtiansoft.tijianba.net.response.BonusAvailableListener;
import com.hengtiansoft.tijianba.net.response.BuyMenu;
import com.juanliuinformation.lvningmeng.R;

public class AddContactActivity extends BaseActivity implements OnClickListener {

  private EditText mNameText;
  private EditText mPhoneText;
  private EditText mMailText;
  private EditText mRemarkText;
  private RelativeLayout mButton;
  private ArrayList<BuyMenu> buyMenus = new ArrayList<BuyMenu>();
  private CheckBox mBonusCheckBox;
  private int bonusMoney = 0;
  private TextView mBonusTextView;
  private int totalPrice = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_a_contact);
    buyMenus = (ArrayList<BuyMenu>) getIntent().getSerializableExtra("buyMenus");
    mCartDeleted = (ArrayList<Integer>) getIntent().getSerializableExtra("cartDeleted");
    totalPrice = getIntent().getIntExtra("totalPrice", 0);
    initView();
    getAvailableBonus(totalPrice);
  }

  private void initView() {
    mBonusCheckBox = (CheckBox) findViewById(R.id.ck_chb_bonus);
    mBonusCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked) {
          bonusMoney = 0;
        }
      }
    });
    mNameText = (EditText) findViewById(R.id.et_contact_name);
    mPhoneText = (EditText) findViewById(R.id.et_contact_phone);
    mMailText = (EditText) findViewById(R.id.et_contact_mail);
    mRemarkText = (EditText) findViewById(R.id.et_contact_remark);
    mButton = (RelativeLayout) findViewById(R.id.rl_contact_confirm);
    mBonusTextView = (TextView) findViewById(R.id.tv_available_bonus);
    mButton.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    //
    if (validation()) {
      buyNow(buyMenus, mPhoneText.getText().toString(), mNameText.getText().toString(), mMailText.getText().toString(),
          mRemarkText.getText().toString(), bonusMoney);
    }
  }

  private boolean validation() {
    boolean isValid = true;
    if (mNameText.length() == 0) {
      mNameText.setError(getString(R.string.err_msg_name));
      isValid = false;
    }

    if (mPhoneText.length() == 0) {
      mPhoneText.setError(getString(R.string.err_msg_phone));
      isValid = false;
    }
    if ((mPhoneText.getText().toString()).matches("1[3|5|7|8|][0-9]{9}") != true) {
      mPhoneText.setError(getString(R.string.str_mobile_way));
      isValid = false;
    }

    if (mMailText.length() != 0) {
      if ((mMailText.getText().toString())
          .matches("^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$") != true) {
        mMailText.setError(getString(R.string.str_email_way));
        isValid = false;
      }
    }
    return isValid;
  }

  @Override
  protected void onResume() {
    super.onResume();
    mBonusCheckBox.setChecked(false);
    bonusMoney = 0;
  }

  public void getAvailableBonus(int totalPrice) {
    remoteDataManager.bonusAvailableListener = new BonusAvailableListener() {

      @Override
      public void onSuccess(final int bonusAvailable) {
        runOnUiThread(new Runnable() {

          @Override
          public void run() {
            if (bonusAvailable != 0) {
              findViewById(R.id.rl_available_bonus).setVisibility(View.VISIBLE);
              bonusMoney = bonusAvailable;
              mBonusTextView.setText(getString(R.string.str_available_bonus) + bonusAvailable
                  + getString(R.string.str_yuan) + getString(R.string.str_bonus));
            } else {
              findViewById(R.id.rl_available_bonus).setVisibility(View.GONE);
            }
          }
        });
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        // handleError(errorMessage);
        final String error = errorMessage;
        runOnUiThread(new Runnable() {

          @Override
          public void run() {
            findViewById(R.id.rl_available_bonus).setVisibility(View.VISIBLE);
            mBonusTextView.setText(error.toString());
            mBonusCheckBox.setChecked(false);
            mBonusCheckBox.setVisibility(View.GONE);
          }
        });
      }
    };
    if (validateInternet()) {
      remoteDataManager.getAvailableBonus(totalPrice);
    }
  }
}
