package com.hengtiansoft.tijianba.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
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

import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.Menu;
import com.hengtiansoft.tijianba.net.response.OrganizationDetail;
import com.hengtiansoft.tijianba.net.response.OrganizationDetailListener;
import com.hengtiansoft.tijianba.widget.NonScrollListView;
import com.juanliuinformation.lvningmeng.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * com.hengtiansoft.tijianba.activity.MenuDetailActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 26, 2014 1:12:04 PM
 */
public class OrgDetailActivity extends BaseActivity implements OnClickListener {
  private int orgId;
  private NonScrollListView mMenuListView;
  private SimpleAdapter mMenuAdapter;
  private TextView mOrgNameTextView, mOrgTypeTextView, mOrgLocationTextView, mOrgWorkTimeTextView, mOrgAddressTextView,
      mOrgAheadTextView, mMenuNumTextView, mOrgCustomerTextView, mOrgPhoneNumTextView;
  private ImageView seperate_line;
  private RelativeLayout more_line;
  private int currentPageNum = 1;
  private String introduce;
  private ArrayList<Map<String, Object>> mMenuData = new ArrayList<Map<String, Object>>();
  private ScrollView mScrollView;
  private TextView cartNumTextView;
  private ImageView mOrgImageView;
  private DisplayImageOptions options;
  private String address,region;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_org_detail);
    orgId = getIntent().getIntExtra("orgID", 0);
    setView();
  }

  private void setView() {
    mScrollView = (ScrollView) findViewById(R.id.sv_org);
    findViewById(R.id.iv_cart).setOnClickListener(this);
    findViewById(R.id.btn_find_more_menu).setOnClickListener(this);
    findViewById(R.id.rl_call_org).setOnClickListener(this);
    findViewById(R.id.tv_org_view).setOnClickListener(this);
    findViewById(R.id.tv_address_view).setOnClickListener(this);
    cartNumTextView = (TextView) findViewById(R.id.tv_cart_num);
    cartNumTextView.setOnClickListener(this);
    seperate_line = (ImageView) findViewById(R.id.seperate_line);
    more_line = (RelativeLayout) findViewById(R.id.more_line);
    mOrgPhoneNumTextView = (TextView) findViewById(R.id.tv_phone_num);
    mOrgNameTextView = (TextView) findViewById(R.id.tv_org_name);
    mOrgTypeTextView = (TextView) findViewById(R.id.tv_org_type);
    mOrgWorkTimeTextView = (TextView) findViewById(R.id.tv_org_work_time);
    // mOrgAddressTextView = (TextView) findViewById(R.id.tv_org_address);
    mOrgAheadTextView = (TextView) findViewById(R.id.tv_org_ahead);
    mOrgLocationTextView = (TextView) findViewById(R.id.tv_org_location);
    mMenuNumTextView = (TextView) findViewById(R.id.tv_menu_num);
    mMenuListView = (NonScrollListView) findViewById(R.id.lv_org_menu);
    mOrgImageView = (ImageView) findViewById(R.id.iv_org_detail);
    mMenuListView.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(OrgDetailActivity.this, MenuDetailActivity.class);
        intent.putExtra("menuID", (Integer) mMenuData.get(position).get("menuID"));
        intent.putExtra("isFamily", (Boolean) mMenuData.get(position).get("isFamily"));
        startActivity(intent);
      }
    });

  }

  public void updateUi(OrganizationDetail organizationDetail) {
    if (currentPageNum == 0 || currentPageNum == 1) {
      mOrgPhoneNumTextView.setText(organizationDetail.getContactPhone());
      mOrgNameTextView.setText(organizationDetail.getName());
      mOrgTypeTextView.setText(remoteDataManager.mOrderTyNames[organizationDetail.getOrgType()]);
      mOrgWorkTimeTextView.setText(organizationDetail.getWorkTime());
      // mOrgAddressTextView.setText(organizationDetail.getAddress());
      mOrgAheadTextView.setText(String.valueOf(organizationDetail.getAdvanceDays()));
      mOrgLocationTextView.setText(organizationDetail.getRegion());
      mMenuNumTextView.setText(String.valueOf(organizationDetail.getMenuNum()));
      mScrollView.smoothScrollTo(0, 0);
    }
    // mOrgCustomerTextView.setText(text);
    mMenuAdapter = new SimpleAdapter(this, mMenuData, R.layout.menu_org_item, new String[] { "name", "type" },
        new int[] { R.id.tv_name, R.id.tv_type });
    ArrayList<Menu> menuList = organizationDetail.getMenuList();
    if (menuList.size() > 0) {
      seperate_line.setVisibility(View.VISIBLE);
      more_line.setVisibility(View.VISIBLE);
    } else {
      more_line.setVisibility(View.GONE);
    }
    for (Menu menu : menuList) {
      Map<String, Object> item = new HashMap<String, Object>();
      item.put("name", menu.getName());
      item.put("type", menu.getMenuTypeName());
      item.put("menuID", menu.getId());
      item.put("isFamily", menu.isFamily());
      mMenuData.add(item);
    }
    introduce = organizationDetail.getIntroduce();
    address = organizationDetail.getAddress();
    region=organizationDetail.getRegion();
//    ImageLoader.getInstance().displayImage(RemoteDataManager.SERVICE + organizationDetail.getPic(), mOrgImageView,
//        options);
    showDetailIamge(organizationDetail.getPic());
    mMenuListView.setAdapter(mMenuAdapter);
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
				showImageView(arg2,mOrgImageView);
				
			}

			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
				
			}
		});
	}
	  public void showImageView(Bitmap bitmap,ImageView imageView){
		  
			int width =getWindowManager().getDefaultDisplay().getWidth() ; 
			int height = width/8*5;
			
			float scaleWidth = ((float) width) / bitmap.getWidth();
			float scaleHeight = ((float) height) / bitmap.getHeight();
			Matrix matrix = new Matrix();
			matrix.postScale(scaleWidth, scaleHeight);
			Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix,true);
			imageView.setImageBitmap(newbm);
	  }
  public void getOrgDetail(int orgID, int pageNo, int pageSize) {
    remoteDataManager.organizationDetailListener = new OrganizationDetailListener() {

      @Override
      public void onSuccess(final OrganizationDetail organizationDetail) {
        // TODO Auto-generated method stub
        dismissProgressDialog();
        runOnUiThread(new Runnable() {

          @Override
          public void run() {
            updateUi(organizationDetail);
            if (organizationDetail.getMenuList().size() < 5) {
              more_line.setVisibility(View.GONE);
            } else {
              more_line.setVisibility(View.VISIBLE);
            }

          }
        });
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        // TODO Auto-generated method stub

        handleError(errorMessage);
      }
    };
    String refreshTime = "";
    if (spSettting.getString("REFRESH_TIME_ORG_DETAIL", "") != null
        && !spSettting.getString("REFRESH_TIME_ORG_DETAIL", "").equals("")) {
      refreshTime = spSettting.getString("REFRESH_TIME_ORG_DETAIL", "");
    } else {
      refreshTime = RemoteDataManager.sdfTime.format(new java.util.Date());
    }
    if (pageNo == 1 || pageNo == 0) {
      Editor editor = spSettting.edit();
      editor.putString("REFRESH_TIME_ORG_DETAIL", RemoteDataManager.sdfTime.format(new java.util.Date()));
      editor.commit();
    }
    if (validateInternet()) {
      showProgressDialog(getString(R.string.str_org_detail), "加载中..");
      remoteDataManager.getOrganizationDetail(orgID, pageNo, pageSize, refreshTime);
    }
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent();
    switch (v.getId()) {
      case R.id.rl_call_org:
        String phoneNum = mOrgPhoneNumTextView.getText().toString();
        Intent intentCall = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNum));
        startActivity(intentCall);
        break;
      case R.id.tv_org_view:
        intent.setClass(OrgDetailActivity.this, LocationActivity.class);
        intent.putExtra("address", address);
        intent.putExtra("region", region);
        startActivity(intent);
        break;
      case R.id.tv_address_view:
        intent.setClass(OrgDetailActivity.this, WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("introduce", introduce);
        intent.putExtras(bundle);
        startActivity(intent);
        break;
      case R.id.btn_find_more_menu:
        currentPageNum++;
        getOrgDetail(orgId, currentPageNum, 5);
        break;
      case R.id.iv_cart:
        if (remoteDataManager.isLogin) {
          intent.setClass(OrgDetailActivity.this, CartListActivity.class);
          startActivity(intent);
        } else {
          intent.setClass(OrgDetailActivity.this, LoginActivity.class);
          intent.putExtra("isGotoCart", true);
          startActivity(intent);
        }
        break;
      case R.id.tv_cart_num:
        if (remoteDataManager.isLogin) {
          intent.setClass(OrgDetailActivity.this, CartListActivity.class);
          startActivity(intent);
        } else {
          intent.setClass(OrgDetailActivity.this, LoginActivity.class);
          intent.putExtra("isGotoCart", true);
          startActivity(intent);
        }
        break;
      default:
        break;
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Editor editor = spSettting.edit();
    editor.putString("REFRESH_TIME_ORG_DETAIL", "");
    editor.commit();
  }

  @Override
  protected void onResume() {
    super.onResume();
    currentPageNum = 1;
    mMenuData.clear();
    if (remoteDataManager.cartCount == 0) {
      cartNumTextView.setVisibility(View.GONE);
    } else {
      cartNumTextView.setVisibility(View.VISIBLE);
      cartNumTextView.setText(remoteDataManager.cartCount + "");
    }
    getOrgDetail(orgId, currentPageNum, 5);
  }
}
