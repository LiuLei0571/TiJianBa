package com.hengtiansoft.tijianba.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.BuyMenu;
import com.hengtiansoft.tijianba.net.response.Menu;
import com.hengtiansoft.tijianba.net.response.MenuDetail;
import com.hengtiansoft.tijianba.net.response.MenuDetailListener;
import com.hengtiansoft.tijianba.net.response.OrgOfMenu;
import com.hengtiansoft.tijianba.net.response.ReturnFromServerListener;
import com.hengtiansoft.tijianba.widget.NonScrollListView;
import com.juanliuinformation.lvningmeng.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 
 * com.hengtiansoft.tijianba.activity.MenuDetailActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 26, 2014 1:12:04 PM
 */
public class MenuDetailActivity extends BaseActivity implements OnClickListener {
	private NonScrollListView mOrgListView;
	private SimpleAdapter mOrgAdapter;
	private TextView mMenuNameTextView, mMenuTypeTextView,
			mMenuSuitPeoTextView, mMenuSuitGenderTextView, mMenuPriceTextView,
			mMenuRebateTextView, mMenuMarketPriceTextView, mMenuSoldTextView,
			mMenuLocationTextView, mMenuContentTextView, mOrgNumTextView,
			mMenuCustomerTextView;
	private ArrayList<Map<String, Object>> mOrgData = new ArrayList<Map<String, Object>>();
	private ImageView mNumAddButton;
	private ImageView mNumDelButton;
	private TextView mEditBuyNumTextView;
	private ScrollView mScrollView;
	private int num = 1;
	private boolean isBuyNow;
	private ImageView mMenuImageView;
	private ImageView seperate_line;
	private Menu menu;
	private int menuID;
	private boolean isFamily;
	private int mMenuDetailId;
	private TextView mCartNumTextView;
	private int currentPageNum = 1;
	private RelativeLayout more_line;
	private int maxBuy = 0;
	private boolean isRestrict = false;
	private String phoneNum;
	private String introduce;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_menu_detail);
		Intent intent = this.getIntent();
		menuID = intent.getIntExtra("menuID", 0);
		isFamily = intent.getBooleanExtra("isFamily", false);
		setView();
	}

	private void setView() {
		findViewById(R.id.iv_cart).setOnClickListener(this);
		more_line = (RelativeLayout) findViewById(R.id.more_line);
		mScrollView = (ScrollView) findViewById(R.id.sv_menu_detail);
		mNumAddButton = (ImageView) findViewById(R.id.btn_cart_add);
		mNumDelButton = (ImageView) findViewById(R.id.btn_cart_del);
		mNumAddButton.setOnClickListener(this);
		mNumDelButton.setOnClickListener(this);
		findViewById(R.id.btn_menu_call).setOnClickListener(this);
		findViewById(R.id.rl_confirm_num).setOnClickListener(this);
		findViewById(R.id.iv_close).setOnClickListener(this);
		mEditBuyNumTextView = (TextView) findViewById(R.id.tv_edit_num);
		findViewById(R.id.btn_find_more_org).setOnClickListener(this);
		findViewById(R.id.rl_add_cart).setOnClickListener(this);
		findViewById(R.id.rl_buy_now).setOnClickListener(this);
		findViewById(R.id.rl_menu_view).setOnClickListener(this);
		mCartNumTextView = (TextView) findViewById(R.id.tv_cart_num);
		mCartNumTextView.setOnClickListener(this);
		seperate_line = (ImageView) findViewById(R.id.seperate_line);
		mMenuNameTextView = (TextView) findViewById(R.id.tv_menu_name);
		mMenuTypeTextView = (TextView) findViewById(R.id.tv_menu_type);
		mMenuSuitPeoTextView = (TextView) findViewById(R.id.tv_menu_suit_people);
		mMenuSuitGenderTextView = (TextView) findViewById(R.id.tv_menu_suit_gender);
		mMenuPriceTextView = (TextView) findViewById(R.id.tv_menu_price);
		mMenuRebateTextView = (TextView) findViewById(R.id.tv_menu_repay_num);
		mMenuMarketPriceTextView = (TextView) findViewById(R.id.tv_menu_market_price);
		mMenuSoldTextView = (TextView) findViewById(R.id.tv_menu_sold);
		mMenuLocationTextView = (TextView) findViewById(R.id.tv_menu_location);
		mMenuContentTextView = (TextView) findViewById(R.id.tv_menu_content);
		mOrgNumTextView = (TextView) findViewById(R.id.tv_org_num);
		mMenuCustomerTextView = (TextView) findViewById(R.id.tv_menu_custom);
		mMenuImageView = (ImageView) findViewById(R.id.iv_menu_detail);
		mOrgListView = (NonScrollListView) findViewById(R.id.lv_menu_org);
		mOrgListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(MenuDetailActivity.this,
						OrgDetailActivity.class);
				intent.putExtra("orgID",
						(Integer) mOrgData.get(position).get("orgID"));
				startActivity(intent);
			}
		});
	}

	public void getMenuDetail(int menuID, boolean isFamily, int pageNo,
			int pageSize) {
		remoteDataManager.menuDetailListener = new MenuDetailListener() {
			@Override
			public void onError(String errorCode, String errorMessage) {
				// TODO Auto-generated method stub

				handleError(errorMessage);
			}

			@Override
			public void onSuccess(final MenuDetail menuDetail) {
				// TODO Auto-generated method stub
				dismissProgressDialog();
				mMenuDetailId = menuDetail.getId();
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						maxBuy = menuDetail.getTotalNum()
								- menuDetail.getPurchaseNum();
						if (maxBuy <= 0) {
							mEditBuyNumTextView.setText("0");
							mNumAddButton
									.setImageResource(R.drawable.ic_add_unenable);
							mNumAddButton.setEnabled(false);
							mNumDelButton
									.setImageResource(R.drawable.ic_del_unenable);
							mNumDelButton.setEnabled(false);
						} else {
							mEditBuyNumTextView.setText("1");
							mNumAddButton
									.setImageResource(R.drawable.ic_add_enable);
							mNumAddButton.setEnabled(true);
							mNumDelButton
									.setImageResource(R.drawable.ic_del_enable);
							mNumDelButton.setEnabled(true);
						}
						if (menuDetail.getTotalNum() != 0) {
							isRestrict = true;
						}
						updateUi(menuDetail);
						if (menuDetail.getOrgList().size() < 5) {
							more_line.setVisibility(View.GONE);
						} else {
							more_line.setVisibility(View.VISIBLE);
						}
					}
				});
			}
		};
		String refreshTime = "";
		if (spSettting.getString("REFRESH_TIME_MENU_DETAIL", "") != null
				&& !spSettting.getString("REFRESH_TIME_MENU_DETAIL", "")
						.equals("")) {
			refreshTime = spSettting.getString("REFRESH_TIME_MENU_DETAIL", "");
		} else {
			refreshTime = RemoteDataManager.sdfTime
					.format(new java.util.Date());
		}
		if (pageNo == 1 || pageNo == 0) {
			Editor editor = spSettting.edit();
			editor.putString("REFRESH_TIME_MENU_DETAIL",
					RemoteDataManager.sdfTime.format(new java.util.Date()));
			editor.commit();
		}
		if (validateInternet()) {
			showProgressDialog(getString(R.string.str_menu_detail), "加载中..");
			remoteDataManager.getMenuDetail(menuID, isFamily, pageNo, pageSize,
					refreshTime);
		}
	}

	public void updateUi(MenuDetail menuDetail) {
		phoneNum = menuDetail.getContactPhone();
		if (currentPageNum == 0 || currentPageNum == 1) {
			Log.d("debug",
					menuDetail.toString() + "type" + menuDetail.getDetail());
			mMenuNameTextView.setText(menuDetail.getName());
			mMenuTypeTextView.setText(String.valueOf(menuDetail
					.getMenuTypeName()));
			isFamily = menuDetail.isFamily();
			if (menuDetail.isFamily()) {
				findViewById(R.id.iv_family).setVisibility(View.VISIBLE);
				mMenuPriceTextView.setText("￥"+menuDetail
						.getFamilyPrice());
			} else {
				findViewById(R.id.iv_family).setVisibility(View.GONE);
				mMenuPriceTextView.setText("￥"+menuDetail
						.getPlatformPrice());
			}
			switch (menuDetail.getSuitGender()) {
			case 0:
				mMenuSuitGenderTextView.setText(getString(R.string.str_both));
				break;
			case 1:
				mMenuSuitGenderTextView.setText(getString(R.string.str_male));
				break;
			case 2:
				mMenuSuitGenderTextView.setText(getString(R.string.str_female));
				break;
			default:
				break;
			}

			if (menuDetail.getRebate() > 0) {
				mMenuRebateTextView.setText(String.valueOf(menuDetail
						.getRebate()));
			} else {
				findViewById(R.id.ll_menu_repay).setVisibility(View.GONE);
			}
			mMenuMarketPriceTextView.setText("￥"+menuDetail
					.getMarketPrice());
			mMenuSoldTextView.setText(String.valueOf(menuDetail
					.getPurchaseNum()));
			mMenuContentTextView.setText(menuDetail.getNameStrong());
			introduce = menuDetail.getDetail();
			mMenuSuitPeoTextView.setText(menuDetail.getSuitObject().toString());
			mOrgNumTextView.setText(String.valueOf(menuDetail.getOrgNum()));
			mMenuCustomerTextView.setText(menuDetail.getCustomerNeedKnow());
			mMenuLocationTextView.setText(remoteDataManager.locatedCity);
			TextView menuNameTextView = (TextView) findViewById(R.id.tv_menu_name_num);
			menuNameTextView.setText(menuDetail.getName());
//			ImageLoader.getInstance().displayImage(
//					RemoteDataManager.SERVICE + menuDetail.getLogo(),
//					mMenuImageView, options);
			showDetailIamge(menuDetail.getLogo());
			mScrollView.smoothScrollTo(0, 0);
		}
		mOrgAdapter = new SimpleAdapter(this, mOrgData, R.layout.menu_org_item,
				new String[] { "orgName", "orgType" }, new int[] {
						R.id.tv_name, R.id.tv_type });
		ArrayList<OrgOfMenu> orgList = menuDetail.getOrgList();
		if (orgList.size() > 0) {
			seperate_line.setVisibility(View.VISIBLE);
			more_line.setVisibility(View.VISIBLE);
		} else {
			more_line.setVisibility(View.GONE);
		}
		for (OrgOfMenu org : orgList) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("orgName", org.getOrgName());
			item.put("orgType", remoteDataManager.orgType.get(org.getOrgType()));
			item.put("orgID", org.getOrgId());
			mOrgData.add(item);
		}
		mOrgListView.setAdapter(mOrgAdapter);
	}
	private void showDetailIamge(String iamgeUrl){

        ImageLoader.getInstance().loadImage(RemoteDataManager.SERVICE + iamgeUrl, options, new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String arg0, View arg1) {
			}
			
			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
			}

			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				showImageView(arg2,mMenuImageView);
				
			}

			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
				
			}
		});
	}
	  public void showImageView(Bitmap bitmap,ImageView imageView){
		  
			int width =getWindowManager().getDefaultDisplay().getWidth() ;
			int height = width /8 * 5;
			float scaleWidth = ((float) width) / bitmap.getWidth();
			float scaleHeight = ((float) height) / bitmap.getHeight();
			Matrix matrix = new Matrix();
			matrix.postScale(scaleWidth, scaleHeight);
			Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix,true);
			imageView.setImageBitmap(newbm);
	  }
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.iv_cart:
			if (remoteDataManager.isLogin) {
				intent.setClass(MenuDetailActivity.this, CartListActivity.class);
				startActivity(intent);
			} else {
				intent.setClass(MenuDetailActivity.this, LoginActivity.class);
				intent.putExtra("isGotoCart", true);
				startActivity(intent);
			}
			break;
		case R.id.tv_cart_num:
			if (remoteDataManager.isLogin) {
				intent.setClass(MenuDetailActivity.this, CartListActivity.class);
				startActivity(intent);
			} else {
				intent.setClass(MenuDetailActivity.this, LoginActivity.class);
				intent.putExtra("isGotoCart", true);
				startActivity(intent);
			}
			break;
		case R.id.rl_add_cart:
			if (maxBuy <= 0) {
				Toast.makeText(MenuDetailActivity.this, "此套餐已售完",
						Toast.LENGTH_LONG).show();
			} else {
				findViewById(R.id.ll_confirm_num).setVisibility(View.VISIBLE);
				mScrollView.setEnabled(false);
				isBuyNow = false;
				mEditBuyNumTextView.setText("1");
				mNumAddButton.setImageResource(R.drawable.ic_add_enable);
				mNumAddButton.setEnabled(true);
				mNumDelButton.setImageResource(R.drawable.ic_del_enable);
				mNumDelButton.setEnabled(true);
			}
			break;
		case R.id.rl_buy_now:
			if (maxBuy <= 0) {
				Toast.makeText(MenuDetailActivity.this, "此套餐已售完",
						Toast.LENGTH_SHORT).show();
			} else {
				findViewById(R.id.ll_confirm_num).setVisibility(View.VISIBLE);
				isBuyNow = true;
				mEditBuyNumTextView.setText("1");
				mNumAddButton.setImageResource(R.drawable.ic_add_enable);
				mNumAddButton.setEnabled(true);
				mNumDelButton.setImageResource(R.drawable.ic_del_enable);
				mNumDelButton.setEnabled(true);
			}
			break;
		case R.id.btn_find_more_org:
			currentPageNum++;
			getMenuDetail(menuID, isFamily, currentPageNum, 5);
			break;
		case R.id.rl_menu_view:
			intent.setClass(MenuDetailActivity.this, WebViewActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("introduce", introduce);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		case R.id.btn_cart_add:
			num++;
			mEditBuyNumTextView.setText(num + "");
			if (num == maxBuy && isRestrict) {
				mNumAddButton.setImageResource(R.drawable.ic_add_unenable);
				mNumAddButton.setEnabled(false);
			} else if (num != 0) {
				mNumDelButton.setImageResource(R.drawable.ic_del_enable);
				mNumDelButton.setEnabled(true);
			}
			break;
		case R.id.btn_cart_del:
			num--;
			if (num <= 0) {
				mNumDelButton.setImageResource(R.drawable.ic_del_unenable);
				mNumDelButton.setEnabled(false);
			} else if (num != maxBuy && isRestrict) {
				mEditBuyNumTextView.setText(num + "");
				mNumAddButton.setImageResource(R.drawable.ic_add_enable);
				mNumAddButton.setEnabled(true);
			} else if (!isRestrict) {
				mEditBuyNumTextView.setText(num + "");
				mNumAddButton.setImageResource(R.drawable.ic_add_enable);
				mNumAddButton.setEnabled(true);
			}
			break;
		case R.id.rl_confirm_num:
			num = 1;
			if (!remoteDataManager.isLogin) {
				intent.setClass(MenuDetailActivity.this, LoginActivity.class);
				intent.putExtra("addToCart", !isBuyNow);
				startActivityForResult(intent, RemoteDataManager.ADD_CART);
			} else {
				if (isBuyNow) {
					intent.setClass(MenuDetailActivity.this,
							AddContactActivity.class);
					ArrayList<BuyMenu> buyMenus = new ArrayList<BuyMenu>();
					BuyMenu menu = new BuyMenu();
					menu.setBuyNum(Integer.parseInt(mEditBuyNumTextView
							.getText().toString()));
					menu.setFamily(isFamily);
					menu.setMenuId(menuID);
					buyMenus.add(menu);
					int totalPrice = menu.getBuyNum()
							* (Integer.parseInt(getMoney(mMenuPriceTextView.getText()
									.toString())));
					intent.putExtra("totalPrice", totalPrice);
					intent.putExtra("buyMenus", buyMenus);
					startActivity(intent);
				} else {
					addToCart();
				}
			}
			break;
		case R.id.iv_close:
			num = 1;
			mEditBuyNumTextView.setText(num + "");
			findViewById(R.id.ll_confirm_num).setVisibility(View.GONE);
			break;
		case R.id.btn_menu_call:
			Intent intentCall = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
					+ phoneNum));
			startActivity(intentCall);
			startActivity(intentCall);
			break;
		default:
			break;
		}
	}

	private void addToCart() {
		remoteDataManager.cartCountListener = new ReturnFromServerListener() {

			@Override
			public void onSuccess(String message) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						findViewById(R.id.ll_confirm_num).setVisibility(
								View.GONE);
					}
				});
				getCartCount(mCartNumTextView);
			}

			@Override
			public void onError(String errorCode, String errorMessage) {
				handleError(errorMessage);
			}
		};
		if (validateInternet()) {
			showProgressDialog("加入" + getString(R.string.str_cart), "进行中..");
			remoteDataManager.addToCart(mMenuDetailId, isFamily,
					Integer.parseInt(mEditBuyNumTextView.getText().toString()));
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case Activity.RESULT_OK:
			Intent intent = new Intent();
			if (isBuyNow) {
				intent.setClass(MenuDetailActivity.this,
						AddContactActivity.class);
				ArrayList<BuyMenu> buyMenus = new ArrayList<BuyMenu>();
				BuyMenu menu = new BuyMenu();
				menu.setBuyNum(Integer.parseInt(mEditBuyNumTextView.getText()
						.toString()));
				menu.setFamily(isFamily);
				menu.setMenuId(menuID);
				buyMenus.add(menu);
				intent.putExtra("buyMenus", buyMenus);
				int totalPrice = menu.getBuyNum()
						* (Integer.parseInt(getMoney(mMenuPriceTextView.getText().toString())
								));
				intent.putExtra("totalPrice", totalPrice);
				startActivity(intent);
			} else {
				addToCart();
			}
			break;
		default:
			break;
		}
	}

	
	private String getMoney(String str){
	 String[] money=str.split("￥");
	 return money[1];
	}
	@Override
	protected void onResume() {
		super.onResume();
		num = 1;
		currentPageNum = 1;
		mOrgData.clear();
		findViewById(R.id.ll_confirm_num).setVisibility(View.GONE);
		getMenuDetail(menuID, isFamily, currentPageNum, 5);
		if (remoteDataManager.cartCount == 0) {
			mCartNumTextView.setVisibility(View.GONE);
		} else {
			mCartNumTextView.setVisibility(View.VISIBLE);
			mCartNumTextView.setText(remoteDataManager.cartCount + "");
		}
	}
}
