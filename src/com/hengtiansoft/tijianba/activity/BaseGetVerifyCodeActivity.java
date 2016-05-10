
package com.hengtiansoft.tijianba.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.hengtiansoft.tijianba.net.response.ReturnFromServerListener;
import com.juanliuinformation.lvningmeng.R;

/**
 * com.hengtiansoft.tijianba.activity.BaseActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 3, 2014 10:42:17 AM
 */
public class BaseGetVerifyCodeActivity extends BaseActivity {

    public TextView mVerifyTextView;
    public EditText mUserNameEditText;
    public EditText mVerifyCodeEditText;
    public final static int MSG_UPDATE_TIME = 1;
    public final static int MSG_UPDATE_ALERT = 2;
    public boolean isRegister = true;
    public Handler mTimeHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_TIME:
                    int time = (Integer) msg.obj;
                    mVerifyTextView.setText(getString(R.string.str_get_verify_code) + "(" + time + ")");
                    break;

                case MSG_UPDATE_ALERT:
                    mVerifyTextView.setBackgroundResource(R.drawable.btn_orange_enable);
                    mVerifyTextView.setEnabled(true);
                    mVerifyTextView.setText(getString(R.string.str_get_verify_code_again));
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setView();
    }

    private void setView() {
        mVerifyTextView = (TextView) findViewById(R.id.tv_verify_code);
        mVerifyCodeEditText = (EditText) findViewById(R.id.edt_verify_code);
        mVerifyTextView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                countTime();
                remoteDataManager.getVerifyCodeListener = new ReturnFromServerListener() {

                    @Override
                    public void onSuccess(String message) {
                        dismissProgressDialog();
                        handleSuccess(message);
                    }

                    @Override
                    public void onError(String errorCode, String errorMessage) {
                        handleError(errorMessage);
                    }
                };
                if (validateInternet()) {
                    showProgressDialog(getString(R.string.str_get_verify_code), getString(R.string.str_wait));
                    remoteDataManager.getVerifyCode(mUserNameEditText.getText() + "");
                }
            }
        });
        mUserNameEditText = (EditText) findViewById(R.id.edt_username);
        if (mUserNameEditText.length() == 0 || mUserNameEditText.equals("")) {
            mVerifyTextView.setBackgroundResource(R.drawable.btn_orange);
            mVerifyTextView.setEnabled(false);
        } else {
            mVerifyTextView.setBackgroundResource(R.drawable.btn_orange_enable);
            mVerifyTextView.setEnabled(true);
        }
        mUserNameEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
              if (mUserNameEditText.length() == 11) {
                if (isRegister) {
                    remoteDataManager.isMobileExitListener = new ReturnFromServerListener() {

                        @Override
                        public void onSuccess(String messgae) {
                            // TODO Auto-generated method stub
                            handleIsMobileExit(1);
                        }

                        @Override
                        public void onError(String errorCode, String errorMessage) {
                            // TODO Auto-generated method stub
                            if ("1".equals(errorCode)) {
                                handleIsMobileExit(0);
                            } else {
                                handleError(errorMessage);
                            }
                        }

                    };
                    if (validateInternet())
                        remoteDataManager.isMobileExit(mUserNameEditText.getText().toString());
                } else {
                    mVerifyTextView.setBackgroundResource(R.drawable.btn_orange_enable);
                    mVerifyTextView.setEnabled(true);
                }
            }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (mUserNameEditText.length() != 11 || mUserNameEditText.equals("")) {
                    mVerifyTextView.setBackgroundResource(R.drawable.btn_orange);
                    mVerifyTextView.setEnabled(false);
                }

            }
        });
        mUserNameEditText.requestFocus();
    }

    public void handleIsMobileExit(final int flag) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                dismissProgressDialog();
                if (flag == 0) {
                    mUserNameEditText.setError(null);
                    mVerifyTextView.setBackgroundResource(R.drawable.btn_orange_enable);
                    mVerifyTextView.setEnabled(true);
                } else {
                    Toast.makeText(BaseGetVerifyCodeActivity.this, getString(R.string.err_msg_user_name_exit),
                            Toast.LENGTH_LONG).show();
                    mVerifyTextView.setBackgroundResource(R.drawable.btn_orange);
                    mVerifyTextView.setEnabled(false);
                }
            }
        });
    }

    public void countTime() {
        mVerifyTextView.setBackgroundResource(R.drawable.btn_orange);
        mVerifyTextView.setEnabled(false);
        new Thread() {
            public void run() {
                for (int i = 60; i > 0; i--) {
                    Message msgTime = new Message();
                    msgTime.what = MSG_UPDATE_TIME;
                    msgTime.obj = i;
                    mTimeHandler.sendMessage(msgTime);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                Message msgAlery = new Message();
                msgAlery.what = MSG_UPDATE_ALERT;
                mTimeHandler.sendMessage(msgAlery);

            };
        }.start();

    }

}
