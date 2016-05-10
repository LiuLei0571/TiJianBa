package com.hengtiansoft.tijianba.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import com.hengtiansoft.tijianba.adapter.FragmentAdapter;
import com.hengtiansoft.tijianba.fragment.MallFragment;
import com.hengtiansoft.tijianba.fragment.MyHealthFragment;
import com.hengtiansoft.tijianba.fragment.SubscribeFragment;
import com.hengtiansoft.tijianba.fragment.SubscribeFragment.OnSwitchFragmentListener;
import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.util.DataUtil;
import com.hengtiansoft.tijianba.widget.CustomViewPager;
import com.juanliuinformation.lvningmeng.R;

public class MainActivity extends FragmentActivity implements OnClickListener,
		OnSwitchFragmentListener {
	public static final int TAB_MALL = 1;
	public static final int TAB_SUBSCRIBE = 2;
	public static final int TAB_MY_HEALTH = 0;
	public static final int TAB_FIND = 3;
	public static final int TAB_MORE = 4;
	private SharedPreferences spSettting;
	private CustomViewPager viewPager;
	private RadioButton mMallRadioButton, mSubscribeRadioButton,
			mFindRadioButton, mMyHealthRadioButton, mMoreRadioButton;
	private OnCallMyhealthFragmentListener mCallMyhealthFragmentListener;
	private OnCallMallFragmentListener mCallMallFragmentListener;
	private OnCallSubscribeFragmentListener mCallSubscribeFragmentListener;
	private List<Fragment> mFragments = new ArrayList<Fragment>();
	private FragmentAdapter adapter;
	private RemoteDataManager rdm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		rdm = RemoteDataManager.getInstance();
		rdm.currentPage = 0;
		initView();
		addListener();
		spSettting = getSharedPreferences("settings", 0);
		boolean hasSDCard = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		if (hasSDCard && spSettting.getBoolean("ISNEEDINPUT", true)) {
			copyDataToPhone();
		} else {
			// showToast("未检测到SDCard");
		}
		// // getOrgs();
		// getMenus();
		// getCities();
	}

	@Override
	protected void onResume() {
		super.onResume();
		switch (rdm.currentPage) {
		case 0:
			viewPager.setCurrentItem(TAB_MY_HEALTH);
			mMyHealthRadioButton.setChecked(true);
			break;
		case 1:
			viewPager.setCurrentItem(TAB_MALL);
			mMallRadioButton.setChecked(true);
			break;
		case 2:
			viewPager.setCurrentItem(TAB_SUBSCRIBE);
			mSubscribeRadioButton.setChecked(true);
			break;
		case 3:
			viewPager.setCurrentItem(TAB_FIND);
			mFindRadioButton.setChecked(true);
			break;
		case 4:
			viewPager.setCurrentItem(TAB_MORE);
			mMoreRadioButton.setChecked(true);
			break;
		default:
			viewPager.setCurrentItem(TAB_MALL);
			mMallRadioButton.setChecked(true);
			break;
		}
	}

	private void copyDataToPhone() {
		DataUtil util = new DataUtil(this);
		boolean dbExist = util.checkDataBase();
		if (dbExist) {
			Log.i("tag", "The database is exist.");
		} else {
			try {
				util.copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
		try {
			util.copySharedPrefs();
		} catch (IOException e) {
			throw new Error("Error copying SharedPrefs");
		}
		Editor editor = spSettting.edit();
		editor.putBoolean("ISNEEDINPUT", false);
		editor.commit();
	}

	private void initView() {
		viewPager = (CustomViewPager) findViewById(R.id.view_pager);
		viewPager.setOffscreenPageLimit(4);
		mMallRadioButton = (RadioButton) findViewById(R.id.main_tab_mall);
		mSubscribeRadioButton = (RadioButton) findViewById(R.id.main_tab_subscribe);
		mFindRadioButton = (RadioButton) findViewById(R.id.main_tab_find);
		mMyHealthRadioButton = (RadioButton) findViewById(R.id.main_tab_my_health);
		mMoreRadioButton = (RadioButton) findViewById(R.id.main_tab_more);
		mMallRadioButton.setOnClickListener(this);
		mSubscribeRadioButton.setOnClickListener(this);
		mFindRadioButton.setOnClickListener(this);
		mMyHealthRadioButton.setOnClickListener(this);
		mMoreRadioButton.setOnClickListener(this);
		adapter = new FragmentAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);
	}

	private void addListener() {
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int id) {
				switch (id) {
				case TAB_MALL:
					mMallRadioButton.setChecked(true);
					if (mCallMallFragmentListener != null) {
						mCallMallFragmentListener.onCallMallFragment();
					}
					break;
				case TAB_SUBSCRIBE:
					mSubscribeRadioButton.setChecked(true);
					if (mCallSubscribeFragmentListener != null) {
						mCallSubscribeFragmentListener
								.onCallSubscribeFragment();
					}
					break;
				case TAB_MY_HEALTH:
					mMyHealthRadioButton.setChecked(true);
					if (mCallMyhealthFragmentListener != null) {
						mCallMyhealthFragmentListener.onCallMyhealthFragment();
					}
					break;
				case TAB_FIND:
					mFindRadioButton.setChecked(true);
					break;
				case TAB_MORE:
					mMoreRadioButton.setChecked(true);
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_tab_mall:
			rdm.currentPage = 1;
			viewPager.setCurrentItem(TAB_MALL);
			break;
		case R.id.main_tab_subscribe:
			rdm.currentPage = 2;
			viewPager.setCurrentItem(TAB_SUBSCRIBE);
			break;
		case R.id.main_tab_find:
			rdm.currentPage = 3;
			viewPager.setCurrentItem(TAB_FIND);
			break;
		case R.id.main_tab_my_health:
			rdm.currentPage = 0;
			viewPager.setCurrentItem(TAB_MY_HEALTH);
			break;
		case R.id.main_tab_more:
			rdm.currentPage = 4;
			viewPager.setCurrentItem(TAB_MORE);
		default:
			break;
		}
	}

	@Override
	public void onAttachFragment(Fragment fragment) {
		super.onAttachFragment(fragment);
		if (fragment instanceof MyHealthFragment) {
			try {
				mCallMyhealthFragmentListener = (OnCallMyhealthFragmentListener) fragment;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (fragment instanceof MallFragment) {
			try {
				mCallMallFragmentListener = (OnCallMallFragmentListener) fragment;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (fragment instanceof SubscribeFragment) {
			try {
				mCallSubscribeFragmentListener = (OnCallSubscribeFragmentListener) fragment;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public interface OnCallMyhealthFragmentListener {
		public void onCallMyhealthFragment();
	}

	public interface OnCallMallFragmentListener {
		public void onCallMallFragment();
	}

	public interface OnCallSubscribeFragmentListener {
		public void onCallSubscribeFragment();
	}

	@Override
	public void onSwitchFragmentListener() {
		viewPager.setCurrentItem(TAB_MALL);
	}

	@Override
	public void onBackPressed() {
		AlertDialog ad = new AlertDialog.Builder(this)
				.setMessage("确定要退出程序？")
				.setPositiveButton(getString(R.string.str_yes),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.dismiss();
								finish();
							}
						})
				.setNegativeButton(getString(R.string.str_no),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.dismiss();
							}
						}).show();
		Button positiveButton = ad.getButton(DialogInterface.BUTTON_POSITIVE);
		positiveButton
				.setTextColor(getResources().getColor(R.color.org_orange));
		positiveButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		Button negativeButton = ad.getButton(DialogInterface.BUTTON_NEGATIVE);
		negativeButton.setTextColor(getResources().getColor(R.color.cart_grey));
		negativeButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
	}
}
