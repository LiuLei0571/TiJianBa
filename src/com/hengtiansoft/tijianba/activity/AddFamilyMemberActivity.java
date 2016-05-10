package com.hengtiansoft.tijianba.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juanliuinformation.lvningmeng.R;

/**
 * 
 * com.hengtiansoft.tijianba.activity.AddFamilyMemberActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 16, 2014 3:15:06 PM
 */
public class AddFamilyMemberActivity extends Activity implements OnClickListener {
  private EditText mSearchEditText;
  private int status = 2;
  private TextView mStatusTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_add_contact);
    setView();
  }

  private void setView() {
    mStatusTextView = (TextView) findViewById(R.id.tv_remind);
    mStatusTextView.setTextColor(getResources().getColor(R.color.white));
    mStatusTextView.setOnClickListener(this);
    mSearchEditText = (EditText) findViewById(R.id.et_search);
    mSearchEditText.setOnKeyListener(new OnKeyListener() {

      @Override
      public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
          InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
          if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
          }
          return true;
        }
        return false;
      }
    });
    if (status == 0) {
      mStatusTextView.setText(getString(R.string.str_added));
      mStatusTextView.setBackgroundColor(getResources().getColor(R.color.transparent));
    } else if (status == 1) {
      mStatusTextView.setText(getString(R.string.str_add));
      mStatusTextView.setBackgroundResource(R.drawable.btn_add_member);
    } else {
      mStatusTextView.setText(getString(R.string.str_invite));
      mStatusTextView.setBackgroundResource(R.drawable.btn_invite_member);
    }

  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.tv_remind:
        if (status == 1) {
          RelativeLayout dialogLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.layout_dialog_family_add,
              null);
          TextView dialogTitle = (TextView) dialogLayout.findViewById(R.id.tv_dialog_title);
          dialogTitle.setText(getString(R.string.str_nick_name));
          AlertDialog ad = new AlertDialog.Builder(this).setView(dialogLayout)
              .setPositiveButton(getString(R.string.str_finish), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                  dialog.dismiss();
                  mStatusTextView.setText(getString(R.string.str_added));
                  mStatusTextView.setTextColor(getResources().getColor(R.color.cart_grey));
                  mStatusTextView.setBackgroundColor(getResources().getColor(R.color.transparent));
                }
              }).setNegativeButton(getString(R.string.str_cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                  dialog.dismiss();

                }
              }).show();
          Button positiveButton = ad.getButton(DialogInterface.BUTTON_POSITIVE);
          positiveButton.setTextColor(getResources().getColor(R.color.org_orange));
          positiveButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
          Button negativeButton = ad.getButton(DialogInterface.BUTTON_NEGATIVE);
          negativeButton.setTextColor(getResources().getColor(R.color.cart_grey));
          negativeButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);

        } else if (status == 2) {
          RelativeLayout dialogLayout = (RelativeLayout) getLayoutInflater().inflate(
              R.layout.layout_dialog_family_other, null);
          TextView dialogTitle = (TextView) dialogLayout.findViewById(R.id.tv_dialog_title);
          dialogTitle.setText(getString(R.string.str_is_send_invite));
          AlertDialog ad = new AlertDialog.Builder(this).setView(dialogLayout)
              .setPositiveButton(getString(R.string.str_yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                  dialog.dismiss();
                  mStatusTextView.setText(getString(R.string.str_invited));
                  mStatusTextView.setTextColor(getResources().getColor(R.color.cart_grey));
                  mStatusTextView.setBackgroundColor(getResources().getColor(R.color.transparent));
                }
              }).setNegativeButton(getString(R.string.str_no), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                  dialog.dismiss();
                }
              }).show();
          Button positiveButton = ad.getButton(DialogInterface.BUTTON_POSITIVE);
          positiveButton.setTextColor(getResources().getColor(R.color.org_orange));
          positiveButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
          Button negativeButton = ad.getButton(DialogInterface.BUTTON_NEGATIVE);
          negativeButton.setTextColor(getResources().getColor(R.color.cart_grey));
          negativeButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        }
        break;
      default:
        break;
    }
  }
}
