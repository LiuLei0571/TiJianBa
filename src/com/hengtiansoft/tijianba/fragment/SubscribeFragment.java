package com.hengtiansoft.tijianba.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hengtiansoft.tijianba.activity.LoginActivity;
import com.hengtiansoft.tijianba.activity.MainActivity.OnCallSubscribeFragmentListener;
import com.hengtiansoft.tijianba.activity.SubInputInfoActivity;
import com.hengtiansoft.tijianba.adapter.SubMenuAdapter;
import com.hengtiansoft.tijianba.adapter.SubMenuAdapter.SubMenuAdapterListener;
import com.hengtiansoft.tijianba.adapter.SubRecordAdapter;
import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.Menu;
import com.hengtiansoft.tijianba.net.response.MyMenusListener;
import com.hengtiansoft.tijianba.net.response.OrgListOfCity;
import com.hengtiansoft.tijianba.net.response.OrgOfSubscribe;
import com.hengtiansoft.tijianba.net.response.SubscribeRecord;
import com.hengtiansoft.tijianba.net.response.SubscribeRecordListListener;
import com.hengtiansoft.tijianba.net.response.SubscribeVerify;
import com.hengtiansoft.tijianba.net.response.SubscribeVerifyListener;
import com.juanliuinformation.lvningmeng.R;

/**
 * 
 * com.hengtiansoft.tijianba.fragment.SubscribeFragment
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 1, 2014 1:06:33 PM
 */
public class SubscribeFragment extends BaseFragment implements OnClickListener,
		OnCallSubscribeFragmentListener {
	private View mView;
	private EditText mCardNumEditText;
	private EditText mCardPwdEditText;
	private RadioButton mMenuRadioButton, mSubRecordRadioButton;
	private ListView mMyMenuListView;
	private ListView mSubRecordListView;
	private View noLoginView;
	private View noBuyView;
	private TextView noRecordTextView;
	private OnSwitchFragmentListener mOnSwitchFragmentListener;
	private boolean isBuy = true;
	private boolean hasRecord = true;
	private SubMenuAdapter mSubMenuAdapter;
	private SubRecordAdapter mSubRecordAdapter;
	private ArrayList<SubscribeRecord> subscribeRecords = new ArrayList<SubscribeRecord>();
	private ArrayList<Menu> myMenus = new ArrayList<Menu>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.main_subscribe, container, false);
		initView();
		return mView;
	}

	private void initView() {
		mMyMenuListView = (ListView) mView.findViewById(R.id.lv_my_menu);
		noRecordTextView = (TextView) mView.findViewById(R.id.tv_no_record);
		mSubRecordListView = (ListView) mView.findViewById(R.id.lv_sub_record);
		noLoginView = (View) mView.findViewById(R.id.ll_no_login);
		noBuyView = (View) mView.findViewById(R.id.ll_no_buy);
		mCardNumEditText = (EditText) mView.findViewById(R.id.edt_card_num);
		mCardPwdEditText = (EditText) mView.findViewById(R.id.edt_card_pwd);
		mMenuRadioButton = (RadioButton) mView.findViewById(R.id.tab_my_menu);
		mMenuRadioButton.setOnClickListener(this);
		mSubRecordRadioButton = (RadioButton) mView
				.findViewById(R.id.tab_my_sub);
		mSubRecordRadioButton.setOnClickListener(this);
		mSubMenuAdapter = new SubMenuAdapter(getActivity(), myMenus);
		mSubMenuAdapter.setListener(new SubMenuAdapterListener() {

			@Override
			public void onItemClickListener(int position) {
				SubscribeVerify(myMenus.get(position).getCardNo(),
						myMenus.get(position).getCardPassword(), 0);
			}
		});
		mMyMenuListView.setAdapter(mSubMenuAdapter);
		mSubRecordAdapter = new SubRecordAdapter(getActivity(),
				subscribeRecords);
		mSubRecordListView.setAdapter(mSubRecordAdapter);
		mSubRecordListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				SubscribeVerify("", "", subscribeRecords.get(position)
						.getBookServerId());
			}
		});
		mView.findViewById(R.id.rl_go_login).setOnClickListener(this);
		mView.findViewById(R.id.rl_go_buy).setOnClickListener(this);
		mView.findViewById(R.id.rl_sub_now).setOnClickListener(this);

	}

	private boolean validation() {
		boolean isValid = true;
		if (mCardNumEditText.length() == 0) {
			mCardNumEditText.setError(getString(R.string.err_msg_card_num));
			isValid = false;
		}
		if (mCardPwdEditText.length() == 0) {
			mCardPwdEditText.setError(getString(R.string.err_msg_card_pwd));
			isValid = false;
		}
		return isValid;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.tab_my_menu:
			if (remoteDataManager.isLogin)
				setMenuData();
			showView(0);
			break;
		case R.id.tab_my_sub:
			if (remoteDataManager.isLogin)
				setRecordData();
			showView(1);
			break;
		case R.id.rl_go_login:
			intent.setClass(getActivity(), LoginActivity.class);
			intent.putExtra("isGotoSub", true);
			getActivity().startActivity(intent);
			break;
		case R.id.rl_sub_now:
			if (validation()) {
				if (remoteDataManager.isLogin) {
					SubscribeVerify(mCardNumEditText.getText().toString(),
							mCardPwdEditText.getText().toString(), 0);
				} else {
					intent.setClass(getActivity(), LoginActivity.class);
					startActivityForResult(intent,
							RemoteDataManager.GO_SUBCRIBE);
					break;
				}
			}
			break;
		case R.id.rl_go_buy:
			mOnSwitchFragmentListener.onSwitchFragmentListener();
			break;
		default:
			break;
		}
	}

	@Override
	public void onAttach(Activity activity) {
		try {
			mOnSwitchFragmentListener = (OnSwitchFragmentListener) activity;
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onAttach(activity);
	}

	public interface OnSwitchFragmentListener {
		public void onSwitchFragmentListener();
	}

	public void showView(int flag) {
		if (!remoteDataManager.isLogin) {
			noLoginView.setVisibility(View.VISIBLE);
			noBuyView.setVisibility(View.GONE);
			mMyMenuListView.setVisibility(View.GONE);
			mSubRecordListView.setVisibility(View.GONE);
			noRecordTextView.setVisibility(View.GONE);
			mMenuRadioButton.setTextColor(getResources().getColor(
					R.color.sub_light_grey));
			mSubRecordRadioButton.setTextColor(getResources().getColor(
					R.color.sub_light_grey));
		} else {
			if (flag == 0) {
				if (!isBuy) {
					noBuyView.setVisibility(View.VISIBLE);
					noLoginView.setVisibility(View.GONE);
					mMyMenuListView.setVisibility(View.GONE);
					mSubRecordListView.setVisibility(View.GONE);
					noRecordTextView.setVisibility(View.GONE);
					mMenuRadioButton.setChecked(true);
					mMenuRadioButton.setTextColor(getResources().getColor(
							R.color.sub_light_grey));
					mSubRecordRadioButton.setTextColor(getResources().getColor(
							R.color.sub_light_grey));
				} else {
					mMyMenuListView.setVisibility(View.VISIBLE);
					noLoginView.setVisibility(View.GONE);
					noBuyView.setVisibility(View.GONE);
					mSubRecordListView.setVisibility(View.GONE);
					noRecordTextView.setVisibility(View.GONE);
					mMenuRadioButton.setChecked(true);
					mMenuRadioButton.setTextColor(getResources().getColor(
							R.color.edt_grey));
					mSubRecordRadioButton.setTextColor(getResources().getColor(
							R.color.sub_light_grey));
				}
			} else {
				if (!hasRecord) {
					noRecordTextView.setVisibility(View.VISIBLE);
					noLoginView.setVisibility(View.GONE);
					noBuyView.setVisibility(View.GONE);
					mSubRecordListView.setVisibility(View.GONE);
					mMyMenuListView.setVisibility(View.GONE);
					mSubRecordRadioButton.setChecked(true);
					mMenuRadioButton.setTextColor(getResources().getColor(
							R.color.sub_light_grey));
					mSubRecordRadioButton.setTextColor(getResources().getColor(
							R.color.sub_light_grey));
				} else {
					mSubRecordListView.setVisibility(View.VISIBLE);
					noLoginView.setVisibility(View.GONE);
					mMyMenuListView.setVisibility(View.GONE);
					noBuyView.setVisibility(View.GONE);
					noRecordTextView.setVisibility(View.GONE);
					mSubRecordRadioButton.setChecked(true);
					mMenuRadioButton.setTextColor(getResources().getColor(
							R.color.sub_light_grey));
					mSubRecordRadioButton.setTextColor(getResources().getColor(
							R.color.edt_grey));
				}
			}
		}
	}

	public void setMenuData() {

		remoteDataManager.myMenusListener = new MyMenusListener() {

			@Override
			public void onSuccess(final ArrayList<Menu> menus) {
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							if (menus != null && menus.size() > 0) {
								myMenus.clear();
								for (int i = 0; i < menus.size(); i++) {
									Menu menu = new Menu();
									menu.setId(menus.get(i).getId());
									menu.setLogo(menus.get(i).getLogo());
									menu.setName(menus.get(i).getName());
									menu.setMenuTypeName(menus.get(i)
											.getMenuTypeName());
									menu.setPrice(menus.get(i).getPrice());
									menu.setCardNo(menus.get(i).getCardNo());
									menu.setCardPassword(menus.get(i)
											.getCardPassword());
									menu.setExamType(menus.get(i).getExamType());
									myMenus.add(menu);
								}
								if (mSubMenuAdapter == null) {
									mSubMenuAdapter = new SubMenuAdapter(
											getActivity(), myMenus);
									mMyMenuListView.setAdapter(mSubMenuAdapter);
								} else {
									mSubMenuAdapter.notifyDataSetChanged();
								}
								isBuy = true;
							} else {
								isBuy = false;
							}
							if (getActivity() != null) {
								showView(0);
							}
							dismissProgressDialog();
						}
					});
				} else {
					dismissProgressDialog();
				}
			}

			@Override
			public void onError(String errorCode, String errorMessage) {
				dismissProgressDialog();
				handleError(errorMessage);
			}
		};
		if (validateInternet()) {
			showProgressDialog("我的套餐", "加载中");
			remoteDataManager.getMyMenus();
		}
	}

	public void setRecordData() {
		subscribeRecords.clear();
		remoteDataManager.subscribeRecordListListener = new SubscribeRecordListListener() {

			@Override
			public void onSuccess(final ArrayList<SubscribeRecord> records) {
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							if (records != null && records.size() > 0) {
								for (int i = 0; i < records.size(); i++) {
									SubscribeRecord subscribeRecord = new SubscribeRecord();
									subscribeRecord.setMenuName(records.get(i)
											.getMenuName());
									subscribeRecord.setTesterName(records
											.get(i).getTesterName());
									subscribeRecord.setOrgName(records.get(i)
											.getOrgName());
									subscribeRecord.setTestDay(records.get(i)
											.getTestDay());
									subscribeRecord.setBookServerId(records
											.get(i).getBookServerId());
									subscribeRecords.add(subscribeRecord);
								}
								if (mSubRecordAdapter == null) {
									mSubRecordAdapter = new SubRecordAdapter(
											getActivity(), subscribeRecords);
									mSubRecordListView
											.setAdapter(mSubRecordAdapter);
								} else {
									mSubRecordAdapter.notifyDataSetChanged();
								}
								hasRecord = true;
							} else {
								hasRecord = false;
							}
							dismissProgressDialog();
						}
					});
				} else {
					dismissProgressDialog();
				}
			}

			@Override
			public void onError(String errorCode, String errorMessage) {
				dismissProgressDialog();
				handleError(errorMessage);
			}
		};
		if (validateInternet()) {
			showProgressDialog("预约记录", "加载中");
			remoteDataManager.getSubscribeRecord();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK
				&& requestCode == RemoteDataManager.GO_SUBCRIBE) {
			SubscribeVerify(mCardNumEditText.getText().toString(),
					mCardPwdEditText.getText().toString(), 0);
		} else if (resultCode == Activity.RESULT_OK
				&& requestCode == RemoteDataManager.SUBCRIBE) {
		}
	}

	public void SubscribeVerify(String cardNo, String cardPassword,
			int bookServerId) {

		remoteDataManager.subscribeVerifyListener = new SubscribeVerifyListener() {

			@Override
			public void onError(String errorCode, String errorMessage) {
				handleError(errorMessage);
			}

			@Override
			public void onSuccess(SubscribeVerify subscribeVerify) {

				final SubscribeVerify subscribeTmp = new SubscribeVerify();
				subscribeTmp.setMenuId(subscribeVerify.getMenuId());
				subscribeTmp.setFamily(subscribeVerify.isFamily());
				subscribeTmp.setLogo(subscribeVerify.getLogo());
				subscribeTmp.setBookServerId(subscribeVerify.getBookServerId());
				subscribeTmp.setExamType(subscribeVerify.getExamType());
				subscribeTmp.setOrgNum(subscribeVerify.getOrgNum());
				subscribeTmp.setTesterName(subscribeVerify.getTesterName());
				subscribeTmp.setTesterGender(subscribeVerify.getTesterGender());
				subscribeTmp
						.setTesterMarried(subscribeVerify.isTesterMarried());
				subscribeTmp.setTesterIDNumber(subscribeVerify
						.getTesterIDNumber());
				subscribeTmp.setTesterMobile(subscribeVerify.getTesterMobile());
				subscribeTmp.setContactName(subscribeVerify.getContactName());
				subscribeTmp.setContactMobile(subscribeVerify
						.getContactMobile());
				subscribeTmp.setContactPostAddr(subscribeVerify
						.getContactPostAddr());
				subscribeTmp.setContactZipCode(subscribeVerify
						.getContactZipCode());
				subscribeTmp.setOrgId(subscribeVerify.getOrgId());
				subscribeTmp.setTestDay(subscribeVerify.getTestDay());
				subscribeTmp.setAgreeUploadReport(subscribeVerify
						.isAgreeUploadReport());
				subscribeTmp.setTestEndDate(subscribeVerify.getTestEndDate());
				subscribeTmp.setCompanyDaysList(subscribeVerify
						.getCompanyDaysList());
				ArrayList<OrgListOfCity> cityOrgList = new ArrayList<OrgListOfCity>();
				int citySize = subscribeVerify.getCityOrgList().size();
				for (int i = 0; i < citySize; i++) {
					OrgListOfCity orgOfCityTmp = subscribeVerify
							.getCityOrgList().get(i);
					OrgListOfCity orgOfCity = new OrgListOfCity();
					orgOfCity.setCityName(orgOfCityTmp.getCityName());
					ArrayList<OrgOfSubscribe> orgOfSubscribeList = new ArrayList<OrgOfSubscribe>();
					int orgSize = subscribeVerify.getCityOrgList().get(i)
							.getOrgList().size();
					for (int j = 0; j < orgSize; j++) {
						OrgOfSubscribe orgTmp = subscribeVerify
								.getCityOrgList().get(i).getOrgList().get(j);
						OrgOfSubscribe org = new OrgOfSubscribe();
						org.setOrgId(orgTmp.getOrgId());
						org.setOrgName(orgTmp.getOrgName());
						org.setOrgType(orgTmp.getOrgType());
						org.setBrandName(orgTmp.getBrandName());
						org.setAddress(orgTmp.getAddress());
						org.setAdvanceDays(orgTmp.getAdvanceDays());
						org.setTestStartDate(orgTmp.getTestStartDate());
						org.setWorkTime(orgTmp.getWorkTime());
						org.setCanUploadReport(orgTmp.isCanUploadReport());
						orgOfSubscribeList.add(org);
					}
					orgOfCity.setOrgList(orgOfSubscribeList);
					cityOrgList.add(orgOfCity);
				}
				subscribeTmp.setCityOrgList(cityOrgList);
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Intent intent = new Intent();
						Bundle bundle = new Bundle();
						bundle.putSerializable("SubscribeVerifyInfo",
								subscribeTmp);
						intent.setClass(getActivity(),
								SubInputInfoActivity.class);
						intent.putExtras(bundle);
						startActivityForResult(intent,
								RemoteDataManager.SUBCRIBE);
					}
				});
			}
		};
		if (validateInternet()) {
			remoteDataManager.SubscribeVerify(cardNo, cardPassword,
					bookServerId);
		}
	}

	@Override
	public void onCallSubscribeFragment() {
		if (remoteDataManager.isLogin) {
			setMenuData();
		} else {
			showView(0);
		}
		mCardNumEditText.setError(null);
		mCardPwdEditText.setError(null);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (remoteDataManager.currentPage == 2)
			onCallSubscribeFragment();
	}
}
