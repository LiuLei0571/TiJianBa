package com.hengtiansoft.tijianba.activity;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hengtiansoft.tijianba.adapter.OrderTypeAdapter;
import com.hengtiansoft.tijianba.adapter.OrgAdapter;
import com.hengtiansoft.tijianba.model.OrderType;
import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.OrgBrandListResult.Brand;
import com.hengtiansoft.tijianba.net.response.OrgBrandListener;
import com.hengtiansoft.tijianba.net.response.Organization;
import com.hengtiansoft.tijianba.net.response.OrganizationListListener;
import com.hengtiansoft.tijianba.net.response.PackedOrgRequest;
import com.juanliuinformation.lvningmeng.R;

//import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 
 * com.hengtiansoft.tijianba.activity.OrgListActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 24, 2014 10:38:29 AM
 */
public class OrgListActivity extends BaseActivity implements OnClickListener {
	private ListView mOrderListView;
	private PullToRefreshListView mOrgListView;
	private Button orgBrandButton, orgRemButton, orgTypeButton;
	private EditText mSearchEditText;
	private TextView mTypeTextView;
	private ArrayList<Organization> mOrgs = new ArrayList<Organization>();
	private ArrayList<OrderType> mOrderTypes = new ArrayList<OrderType>();
	private ArrayList<OrderType> mOrderBrands = new ArrayList<OrderType>();
	private OrgAdapter mOrgAdapter;
	private OrderTypeAdapter mOrderTypeAdapter, mOrderBrandAdapter;
	private int currentPageNum = 1;
	private boolean isOrgBrandButton, isOrgRemButton, isOrgTypeButton;
	private boolean isType = true;
	private int currentBrandId=-1;
	private int currentTypeId=-1;
	private boolean isFirst = true;
	private TextView cartCountTextView;
//	private TextView location;
	private boolean isOrgBrandFirst, isOrgTypeFirst;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_org_list);
		setView();
		setDate();
	}

	private void setView() {
//		location = (TextView) findViewById(R.id.tv_location);
//		location.setOnClickListener(this);
//		if (remoteDataManager.locatedCity != null
//				&& !"".equals(remoteDataManager.locatedCity))
//			location.setText(remoteDataManager.locatedCity);
		findViewById(R.id.iv_cart).setOnClickListener(this);
		cartCountTextView = (TextView) findViewById(R.id.tv_cart_num);
		cartCountTextView.setOnClickListener(this);
		mTypeTextView = (TextView) findViewById(R.id.btn_org);
		mTypeTextView.setOnClickListener(this);
		findViewById(R.id.tv_org).setOnClickListener(this);
		findViewById(R.id.tv_menu).setOnClickListener(this);
		mSearchEditText = (EditText) findViewById(R.id.searchView);
		 mSearchEditText.addTextChangedListener(new TextWatcher() {
	      
	      @Override
	      public void onTextChanged(CharSequence s, int start, int before, int count) {
	      }
	      
	      @Override
	      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	      }
	      
	      @Override
	      public void afterTextChanged(Editable s) {
	        if(s.toString().equals("")&&!isOrgRemButton 
	        &&!isOrgTypeButton&&!isOrgBrandButton){
	          isFirst=true;
	          mOrgs.clear();
	          getOrgs(remoteDataManager.cityCode, 1, "", isOrgRemButton,
	              -1, -1);
	        }
	      }
	    });
		
		mSearchEditText.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					String search = mSearchEditText.getText() + "";
					InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					if (inputMethodManager.isActive()) {
						inputMethodManager.hideSoftInputFromWindow(
								v.getApplicationWindowToken(), 0);
					}
//					if (isFirst) {
//						isFirst = false;
//						isOrgRemButton = false;
//						isOrgBrandButton = false;
//						isOrgTypeButton = false;
//						orgRemButton
//								.setBackgroundResource(R.drawable.list_unselected);
//						orgBrandButton
//								.setBackgroundResource(R.drawable.list_unselected);
//						orgTypeButton
//								.setBackgroundResource(R.drawable.list_unselected);
//						isOrgBrandFirst = true;
//						isOrgTypeFirst = true;
//						if (!isType) {
//							isType = true;
							Intent intent = new Intent(OrgListActivity.this,
									MenuListActivity.class);
							intent.putExtra("search", mSearchEditText.getText()
									+ "");
							startActivity(intent);
							finish();
//						} else if (search != null && !"".equals(search)) {
//							mOrgs.clear();
//							getOrgs(remoteDataManager.cityCode, 1, search,
//									false, -1, -1);
//						}
//						return true;
//					}
				}
				return false;
			}
		});
		mOrderListView = (ListView) findViewById(R.id.lv_order_list);
		mOrderListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (isOrgBrandButton) {
					mOrderBrandAdapter.select(position);
					orgBrandButton
							.setText(mOrderBrands.get(position).getName());
					currentBrandId = mOrderBrands.get(position).getId();
					mOrderListView.setVisibility(View.GONE);
					findViewById(R.id.half).setVisibility(View.GONE);
					mOrgs.clear();
				} else if (isOrgTypeButton) {
					mOrderTypeAdapter.select(position);
					orgTypeButton.setText(mOrderTypes.get(position).getName());
					currentTypeId = mOrderTypes.get(position).getId();
					mOrderListView.setVisibility(View.GONE);
					findViewById(R.id.half).setVisibility(View.GONE);
					mOrgs.clear();
				}
				getOrgs(remoteDataManager.cityCode, 1, "", false, currentBrandId,
				    currentTypeId);
			}
		});
		orgBrandButton = (Button) findViewById(R.id.btn_org_brand);
		orgRemButton = (Button) findViewById(R.id.btn_org_rem);
		orgRemButton.setBackgroundResource(R.drawable.list_selected);
		orgTypeButton = (Button) findViewById(R.id.btn_org_type);
		orgBrandButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			  currentPageNum=1;
			  isFirst = true;
				orgBrandButton.setBackgroundResource(R.drawable.list_selected);
				orgTypeButton.setBackgroundResource(R.drawable.list_unselected);
				orgRemButton.setBackgroundResource(R.drawable.list_unselected);
				isOrgRemButton = false;
				isOrgTypeButton = false;
				isOrgTypeFirst = true;
				isOrgBrandButton= true;
				mOrderBrandAdapter = new OrderTypeAdapter(
            OrgListActivity.this, mOrderBrands);
        mOrderListView.setAdapter(mOrderBrandAdapter);
        mOrderListView.setVisibility(View.VISIBLE);
        findViewById(R.id.half).setVisibility(View.VISIBLE);
//				if (isOrgBrandButton) {
//					if (mOrderListView.getVisibility() == View.VISIBLE) {
//						findViewById(R.id.half).setVisibility(View.GONE);
//						mOrderListView.setVisibility(View.GONE);
//					} else {
//						if (mOrderBrands.size() == 0) {
//							getOrgBrand();
//						} else {
//							mOrderBrandAdapter = new OrderTypeAdapter(
//									OrgListActivity.this, mOrderBrands);
//							mOrderListView.setAdapter(mOrderBrandAdapter);
//							mOrderListView.setVisibility(View.VISIBLE);
//							findViewById(R.id.half).setVisibility(View.VISIBLE);
//						}
//					}
//				} else {
//					isOrgBrandButton = true;
//					mSearchEditText.setText("");
//					if (orgBrandButton.getText().toString()
//							.equals(getString(R.string.str_org_brand))
//							&& isOrgBrandFirst==false) {
//						mOrgs.clear();
//						getOrgs(remoteDataManager.cityCode, 1, "", false,
//								currentBrandId, -1);
//						isOrgBrandFirst = false;
//					} else {
//						if (mOrderBrands.size() == 0) {
//							getOrgBrand();
//						} else {
//							mOrderBrandAdapter = new OrderTypeAdapter(
//									OrgListActivity.this, mOrderBrands);
//							mOrderListView.setAdapter(mOrderBrandAdapter);
//							mOrderListView.setVisibility(View.VISIBLE);
//							findViewById(R.id.half).setVisibility(View.VISIBLE);
//						}
//					}
//				}
			}
		});
		orgRemButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			  currentPageNum=1;
				isOrgBrandFirst = true;
				isOrgTypeFirst = true;
				orgRemButton.setBackgroundResource(R.drawable.list_selected);
				orgTypeButton.setBackgroundResource(R.drawable.list_unselected);
				orgBrandButton
						.setBackgroundResource(R.drawable.list_unselected);
				isOrgRemButton = true;
				isOrgBrandButton = false;
				isOrgTypeButton = false;
				mSearchEditText.setText("");
				isFirst = true;
				findViewById(R.id.half).setVisibility(View.GONE);
				mOrderListView.setVisibility(View.GONE);
				mOrgs.clear();
				getOrgs(remoteDataManager.cityCode, 1, "", true,currentBrandId,
            currentTypeId);
			}
		});
		orgTypeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			  currentPageNum=1;
			  isFirst = true;
				isOrgBrandFirst = true;
				orgTypeButton.setBackgroundResource(R.drawable.list_selected);
				orgRemButton.setBackgroundResource(R.drawable.list_unselected);
				orgBrandButton
						.setBackgroundResource(R.drawable.list_unselected);
				isOrgBrandButton = false;
				isOrgRemButton = false;
				isOrgTypeButton=true;
				mOrderTypeAdapter = new OrderTypeAdapter(
            OrgListActivity.this, mOrderTypes);
        mOrderListView.setAdapter(mOrderTypeAdapter);
        mOrderListView.setVisibility(View.VISIBLE);
        findViewById(R.id.half).setVisibility(View.VISIBLE);
//				if (isOrgTypeButton) {
//					if (mOrderListView.getVisibility() == View.VISIBLE) {
//						findViewById(R.id.half).setVisibility(View.GONE);
//						mOrderListView.setVisibility(View.GONE);
//					} else {
//						mOrderTypeAdapter = new OrderTypeAdapter(
//								OrgListActivity.this, mOrderTypes);
//						mOrderListView.setAdapter(mOrderTypeAdapter);
//						mOrderListView.setVisibility(View.VISIBLE);
//						findViewById(R.id.half).setVisibility(View.VISIBLE);
//					}
//				} else {
//					isOrgTypeButton = true;
//					mSearchEditText.setText("");
//				
//					if (orgTypeButton.getText().toString()
//							.equals(getString(R.string.str_org_type))
//							&& isOrgTypeFirst==false) {
//						mOrgs.clear();
//						getOrgs(remoteDataManager.cityCode, 1, "", false, -1,
//		            currentTypeId);
//						isOrgTypeFirst = false;
//					} else {
//						mOrderTypeAdapter = new OrderTypeAdapter(
//								OrgListActivity.this, mOrderTypes);
//						mOrderListView.setAdapter(mOrderTypeAdapter);
//						mOrderListView.setVisibility(View.VISIBLE);
//						findViewById(R.id.half).setVisibility(View.VISIBLE);
//					}
//				}
			}
		});
		mOrgListView = (PullToRefreshListView) findViewById(R.id.lv_org);
		setListListener();
		mOrgAdapter = new OrgAdapter(this, mOrgs);
		mOrgListView.setAdapter(mOrgAdapter);
//		mOrgListView.setOnPullEventListener(new OnPullEventListener<ListView>() {
//      @Override
//      public void onPullEvent(PullToRefreshBase<ListView> refreshView, State state, Mode direction) {
//        currentPageNum=1;
//      }
//    });
	}

	
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		View selectorView = findViewById(R.id.selector);
		switch (v.getId()) {
//		case R.id.tv_location:
//			intent.setClass(OrgListActivity.this, CityListActivity.class);
//			startActivityForResult(intent, 100);
//			break;
		case R.id.btn_org:
			if (selectorView.getVisibility() == View.VISIBLE) {
				selectorView.setVisibility(View.GONE);
			} else {
				selectorView.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.tv_org:
			isType = true;
			mTypeTextView.setText(getString(R.string.str_org));
			selectorView.setVisibility(View.GONE);
			mSearchEditText.setText("");
			mSearchEditText.setHint("请输入机构名称");
			break;
		case R.id.tv_menu:
			isType = false;
			mTypeTextView.setText(getString(R.string.str_menu));
			selectorView.setVisibility(View.GONE);
			mSearchEditText.setText("");
			mSearchEditText.setHint("请输入套餐名称");
			break;
		case R.id.iv_cart:
			if (remoteDataManager.isLogin) {
				intent.setClass(OrgListActivity.this, CartListActivity.class);
				startActivity(intent);
			} else {
				intent.setClass(OrgListActivity.this, LoginActivity.class);
				intent.putExtra("isGotoCart", true);
				startActivity(intent);
			}
			break;
		case R.id.tv_cart_num:
			if (remoteDataManager.isLogin) {
				intent.setClass(OrgListActivity.this, CartListActivity.class);
				startActivity(intent);
			} else {
				intent.setClass(OrgListActivity.this, LoginActivity.class);
				intent.putExtra("isGotoCart", true);
				startActivity(intent);
			}
			break;
		default:
			break;
		}

	}

	private void setListListener() {

		mOrgListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent intent = new Intent(OrgListActivity.this,
						OrgDetailActivity.class);
				intent.putExtra("orgID", mOrgs.get(position - 1).getId());
				startActivity(intent);
			}
		});
		mOrgListView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						currentPageNum++;
						getOrgs(remoteDataManager.cityCode, currentPageNum, "",
								isOrgRemButton, currentBrandId, currentTypeId);
					}
				});
		
		
		mOrgListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			  currentPageNum=1;
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				mOrgs.clear();
				getOrgs(remoteDataManager.cityCode, 1, "", isOrgRemButton,
						currentBrandId, currentTypeId);
			}
		});
	}

	
	
	public void getOrgs(String cityCode, int pageNo, String name,
			boolean isRecommendList, int brandId, int typeId) {
		PackedOrgRequest packedOrgRequest = new PackedOrgRequest();
		packedOrgRequest.setCityCode(cityCode);
		if (spSettting.getString("REFRESH_TIME_ORG", "") != null
				&& !spSettting.getString("REFRESH_TIME_ORG", "").equals("")) {
			packedOrgRequest.setRefreshTime(spSettting.getString(
					"REFRESH_TIME_ORG", ""));
		} else {
			packedOrgRequest.setRefreshTime(RemoteDataManager.sdfTime
					.format(new java.util.Date()));
		}
		packedOrgRequest.setName(name);
		packedOrgRequest.setPageSize(4);
		packedOrgRequest.setPageNo(pageNo);
		packedOrgRequest.setRecommendList(isRecommendList);
		packedOrgRequest.setBrandId(brandId);
		packedOrgRequest.setOrgType(typeId);
		remoteDataManager.organizationListListener = new OrganizationListListener() {

			@Override
			public void onError(String errorCode, String errorMessage) {
				handleError(errorMessage);
			}

			@Override
			public void onSuccess(final ArrayList<Organization> organizations) {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						dismissProgressDialog();
						mOrgListView.onRefreshComplete();
						TextView noDataTextView = (TextView) findViewById(R.id.tv_no_data);
						if (organizations.size() == 0 && mOrgs.size() == 0) {
							noDataTextView.setVisibility(View.VISIBLE);
						} else {
							noDataTextView.setVisibility(View.GONE);
						}
						for (int i = 0; i < organizations.size(); i++) {
							Organization org2 = new Organization();
							org2.setId(organizations.get(i).getId());
							org2.setName(organizations.get(i).getName());
							org2.setBrandName(organizations.get(i)
									.getBrandName());
							org2.setAddress(organizations.get(i).getAddress());
							org2.setPic(organizations.get(i).getPic());
							org2.setOrgTypeName(remoteDataManager.mOrderTyNames[organizations
									.get(i).getOrgType()]);
							mOrgs.add(org2);
						}
						mOrgAdapter.notifyDataSetChanged();
					}
				});
			}
		};
		if (pageNo == 1 || pageNo == 0) {
			Editor editor = spSettting.edit();
			editor.putString("REFRESH_TIME_ORG",
					RemoteDataManager.sdfTime.format(new java.util.Date()));
			editor.commit();
		}
		if (validateInternet()) {
			showProgressDialog(getString(R.string.str_org), "加载中..");
			remoteDataManager.getOrgList(packedOrgRequest);
		}
	}

	private void setOrderType() {
		mOrderTypes.clear();
		OrderType org0 = new OrderType();
    org0.setSelected(true);
    org0.setName("机构类型");
    org0.setId(-1);
    mOrderTypes.add(org0);
		OrderType org = new OrderType();
		org.setSelected(false);
		org.setName("专业体检中心");
		org.setId(0);
		mOrderTypes.add(org);
		OrderType org2 = new OrderType();
		org2.setSelected(false);
		org2.setName("疗养院");
		org2.setId(1);
		mOrderTypes.add(org2);
		OrderType org3 = new OrderType();
		org3.setSelected(false);
		org3.setName("公立医院");
		org3.setId(2);
		mOrderTypes.add(org3);
	}

	private void getOrgBrand() {
		remoteDataManager.orgBrandListener = new OrgBrandListener() {
			@Override
			public void onSuccess(ArrayList<Brand> brands) {
				dismissProgressDialog();
				mOrderBrands.clear();
				for (int i = 0; i < brands.size(); i++) {
				  OrderType orderType = new OrderType();
				  if(i==0){
				    orderType.setName("机构品牌");
	          orderType.setId(0);
	          orderType.setSelected(true);
				  }else{
					orderType.setName(brands.get(i-1).getName());
					orderType.setId(brands.get(i-1).getId());
				  orderType.setSelected(false);}
					mOrderBrands.add(orderType);
				}
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//						mOrderBrandAdapter = new OrderTypeAdapter(
//								OrgListActivity.this, mOrderBrands);
//						mOrderListView.setAdapter(mOrderBrandAdapter);
//						mOrderListView.setVisibility(View.VISIBLE);
//						findViewById(R.id.half).setVisibility(View.VISIBLE);
//					}
//				});
			}

			@Override
			public void onError(String errorCode, String errorMessage) {
				handleError(errorMessage);
				dismissProgressDialog();
			}
		};
		if (validateInternet()) {
			showProgressDialog("机构品牌", "加载中..");
			remoteDataManager.getOrgBrand();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}
	
private void setDate(){
  if (remoteDataManager.cartCount == 0) {
    cartCountTextView.setVisibility(View.GONE);
  } else {
    cartCountTextView.setVisibility(View.VISIBLE);
    cartCountTextView.setText(remoteDataManager.cartCount + "");
  }
  currentPageNum = 1;
    isOrgRemButton = true;
    isOrgTypeButton = false;
    isOrgBrandButton = false;
    currentBrandId = -1;
    currentTypeId = -1;
    orgRemButton.setBackgroundResource(R.drawable.list_selected);
    orgBrandButton.setBackgroundResource(R.drawable.list_unselected);
    orgTypeButton.setBackgroundResource(R.drawable.list_unselected);
  setOrderType();
  getOrgBrand();
  mOrgs.clear();
  String search = getIntent().getStringExtra("search");
  if (search != null && !"".equals(search)) {
    isOrgRemButton = false;
    isOrgTypeButton = false;
    isOrgBrandButton = false;
    orgRemButton.setBackgroundResource(R.drawable.list_unselected);
    orgTypeButton.setBackgroundResource(R.drawable.list_unselected);
    orgBrandButton.setBackgroundResource(R.drawable.list_unselected);
    getOrgs(remoteDataManager.cityCode, 1, search, isOrgRemButton,
        currentBrandId, currentTypeId);
  } else {
    getOrgs(remoteDataManager.cityCode, 1, "", isOrgRemButton,
        currentBrandId, currentTypeId);
  }
  mSearchEditText.setText(search);
}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 100 && resultCode == 100) {
			String cityName = data.getStringExtra("city");
		}
	}
}
