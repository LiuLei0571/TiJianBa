package com.hengtiansoft.tijianba.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.hengtiansoft.tijianba.activity.LoginActivity;
import com.hengtiansoft.tijianba.activity.MoreSettingActivity;
import com.hengtiansoft.tijianba.activity.OrderActivity;
import com.hengtiansoft.tijianba.activity.RedPocketActivity;
import com.hengtiansoft.tijianba.activity.RegisterActivity;
import com.juanliuinformation.lvningmeng.R;

@SuppressLint("InflateParams")
public class MoreFragment extends BaseFragment implements OnClickListener {
  private RelativeLayout mRlytAfterLogin;
  private RelativeLayout mRlytBeforeLogin;
  private ImageView mImgLogin;
  private ImageView mImgEnter;
  private ImageView mImgLogout;
  private View mView;
  private RelativeLayout mSettingLayout;
  private RelativeLayout mContactLayout;
  private RelativeLayout mMyorderLayout;
  private AlertDialog mAd;
  private TextView mUserPhone;
  private SharedPreferences sp;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    mView = inflater.inflate(R.layout.main_more, container, false);
    initView();
    return mView;
  }

  private void initView() {
    sp = getActivity().getSharedPreferences("userInfo", 0);
    mSettingLayout = (RelativeLayout) mView.findViewById(R.id.rl_more_setting);
    mContactLayout = (RelativeLayout) mView.findViewById(R.id.rl_more_contact);
    mMyorderLayout = (RelativeLayout) mView.findViewById(R.id.rl_more_my_order);
    mSettingLayout.setOnClickListener(this);
    mContactLayout.setOnClickListener(this);
    mMyorderLayout.setOnClickListener(this);
    mView.findViewById(R.id.rl_more_my_redpocket).setOnClickListener(this);
    mRlytAfterLogin = (RelativeLayout) mView.findViewById(R.id.rlyt_more_after);
    mRlytBeforeLogin = (RelativeLayout) mView.findViewById(R.id.rlyt_more_before);
    mImgLogin = (ImageView) mView.findViewById(R.id.iv_more_login);
    mImgLogout = (ImageView) mView.findViewById(R.id.iv_more_logout);
    mImgEnter = (ImageView) mView.findViewById(R.id.iv_more_enter);
    mUserPhone = (TextView) mView.findViewById(R.id.tv_more_phone);
    mImgLogin.setOnClickListener(this);
    mImgLogout.setOnClickListener(this);
    mImgEnter.setOnClickListener(this);
    if (remoteDataManager.isLogin) {
      mRlytAfterLogin.setVisibility(View.VISIBLE);
      mRlytBeforeLogin.setVisibility(View.GONE);
      mUserPhone.setText(remoteDataManager.getLoginInfo());
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    if (remoteDataManager.isLogin) {
      mRlytAfterLogin.setVisibility(View.VISIBLE);
      mRlytBeforeLogin.setVisibility(View.GONE);
      mUserPhone.setText(remoteDataManager.userName);
    } else {
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.iv_more_login:
        Intent intentEnter = new Intent(getActivity(), RegisterActivity.class);
        intentEnter.putExtra("isGotoMore", true);
        getActivity().startActivity(intentEnter);
        break;
      case R.id.iv_more_enter:
        Intent intentLogin = new Intent(getActivity(), LoginActivity.class);
        intentLogin.putExtra("isGotoMore", true);
        getActivity().startActivity(intentLogin);
        break;
      case R.id.iv_more_logout:
        remoteDataManager.isLogin = false;
        remoteDataManager.loginInfo = "";
        remoteDataManager.userId = 0;
        remoteDataManager.userType = 0;
        remoteDataManager.companyId = 0;
        remoteDataManager.userName = "";
        Editor editor = sp.edit();
        editor.putString("USER_NAME", "");
        editor.putString("PASSWORD","");
        editor.commit();
        mRlytAfterLogin.setVisibility(View.GONE);
        mRlytBeforeLogin.setVisibility(View.VISIBLE);
        break;
      case R.id.rl_more_setting:
        startActivity(new Intent(getActivity(), MoreSettingActivity.class));
        break;
      case R.id.rl_more_contact:
        RelativeLayout dialogLayout = (RelativeLayout) getActivity().getLayoutInflater().inflate(
            R.layout.layout_dialog_contact_me, null);
        dialogLayout.findViewById(R.id.rl_contact_call).setOnClickListener(this);
        dialogLayout.findViewById(R.id.rl_contact_cancel).setOnClickListener(this);
        mAd = new AlertDialog.Builder(getActivity()).setView(dialogLayout).show();
        break;
      case R.id.rl_contact_call:
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getString(R.string.str_telephone)));
        startActivity(intent);
        mAd.dismiss();
        break;
      case R.id.rl_contact_cancel:
        mAd.dismiss();
        break;
      case R.id.rl_more_my_order:
        if (remoteDataManager.isLogin) {
          startActivity(new Intent(getActivity(), OrderActivity.class));
        } else {
          Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_LONG).show();
        }
        break;
      case R.id.rl_more_my_redpocket:
        if (remoteDataManager.isLogin) {
          startActivity(new Intent(getActivity(), RedPocketActivity.class));
        } else {
          Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_LONG).show();
        }
        break;
      default:
        break;
    }
  }

}
