package com.hengtiansoft.tijianba.fragment;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.hengtiansoft.tijianba.activity.CartListActivity;
import com.hengtiansoft.tijianba.activity.CityListActivity;
import com.hengtiansoft.tijianba.activity.LoginActivity;
import com.hengtiansoft.tijianba.activity.MainActivity.OnCallMallFragmentListener;
import com.hengtiansoft.tijianba.activity.MenuDetailActivity;
import com.hengtiansoft.tijianba.activity.MenuListActivity;
import com.hengtiansoft.tijianba.activity.OrgDetailActivity;
import com.hengtiansoft.tijianba.activity.OrgListActivity;
import com.hengtiansoft.tijianba.adapter.AdsViewPagerAdpter;
import com.hengtiansoft.tijianba.adapter.RemOrgAdapter;
import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.HotMenu;
import com.hengtiansoft.tijianba.net.response.HotOrg;
import com.hengtiansoft.tijianba.net.response.MallData;
import com.hengtiansoft.tijianba.net.response.MallDataListener;
import com.hengtiansoft.tijianba.net.response.MallMenuType;
import com.hengtiansoft.tijianba.net.response.ReturnFromServerListener;
import com.hengtiansoft.tijianba.widget.NonScrollGridView;
import com.juanliuinformation.lvningmeng.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class MallFragment extends BaseFragment implements OnClickListener, OnCallMallFragmentListener {
  private LocationClient mLocClient;
  public MyLocationListenner myListener = new MyLocationListenner();
  private View mView;
  private TextView mLocationTextView, mCartCountTextView;
  private NonScrollGridView mInforGridView;
  private RemOrgAdapter mRemOrgAdapter;
  private ArrayList<HotOrg> remOrgs = new ArrayList<HotOrg>();
  private ArrayList<HotMenu> hotMenus = new ArrayList<HotMenu>();
  private PullToRefreshScrollView mScrollView;
  private ImageView mHot1ImageView, mHot2ImageView, mHot3ImageView, mHot4ImageView;
  private List<MallTypeFragment> fragmentList = new ArrayList<MallTypeFragment>();
  private LinearLayout mLinearLayoutTab;
  private String[] advertiseUrl;
  private ImageView[] mImgAdvertise;
  private ViewPager mAdvertiseImageView;
  private ArrayList<View> mViewList;
  protected static final int MSG_UPDATE_IMAGE = 1;
  protected static final int MSG_KEEP_SILENT = 2;
  protected static final int MSG_BREAK_SILENT = 3;
  protected static final int MSG_PAGE_CHANGED = 4;
  protected static final long MSG_DELAY = 3000;
  private int currentItem = 0;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    mView = inflater.inflate(R.layout.main_mall, container, false);
    initView();
    return mView;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // remoteDataManager.cityCode = "3305710000";
  }

  private void initView() {
    options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
        .bitmapConfig(Bitmap.Config.RGB_565).resetViewBeforeLoading(true).displayer(new SimpleBitmapDisplayer())
        .build();
    mLinearLayoutTab = (LinearLayout) mView.findViewById(R.id.ll_taber);
    mLocationTextView = (TextView) mView.findViewById(R.id.tv_location);
    mLocationTextView.setOnClickListener(this);
    mScrollView = (PullToRefreshScrollView) mView.findViewById(R.id.sv_mall);
    mScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

      @Override
      public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
        String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(),
            DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
        onCallMallFragment();
      }
    });
    mInforGridView = (NonScrollGridView) mView.findViewById(R.id.gv_org_rem);
    mInforGridView.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(getActivity(), OrgDetailActivity.class);
        intent.putExtra("orgID", remOrgs.get(position).getOrgId());
        startActivity(intent);
      }
    });
    mRemOrgAdapter = new RemOrgAdapter(getActivity(), remOrgs);
    mInforGridView.setAdapter(mRemOrgAdapter);
    mView.findViewById(R.id.iv_find_more).setOnClickListener(this);
    mView.findViewById(R.id.iv_find_more_org).setOnClickListener(this);
    mAdvertiseImageView = (ViewPager) mView.findViewById(R.id.viewpager_advertise);
    // mAdvertiseImageView.setOnClickListener(this);
    mView.findViewById(R.id.iv_cart).setOnClickListener(this);
    mCartCountTextView = (TextView) mView.findViewById(R.id.tv_cart_num);
    mCartCountTextView.setOnClickListener(this);
    mHot1ImageView = (ImageView) mView.findViewById(R.id.iv_hot_no1);
    mHot2ImageView = (ImageView) mView.findViewById(R.id.iv_hot_no2);
    mHot3ImageView = (ImageView) mView.findViewById(R.id.iv_hot_no3);
    mHot4ImageView = (ImageView) mView.findViewById(R.id.iv_hot_no4);
    if (remoteDataManager.cartCount == 0) {
      mCartCountTextView.setVisibility(View.GONE);
    } else {
      mCartCountTextView.setText(remoteDataManager.cartCount + "");
    }
  }

  /**
   * 定位SDK监听函数
   */
  public class MyLocationListenner implements BDLocationListener {

    @Override
    public void onReceiveLocation(BDLocation location) {
      if (location == null)
        return;
      dismissProgressDialog();
      remoteDataManager.firstOpenApp = false;
      String locationCity = location.getCity();
      if (locationCity != null && !locationCity.equals("")) {
        String locationStr = locationCity.substring(0, locationCity.length() - 1);
        String city = mCityDaoImpl.getCityCode(locationStr);
        if (city.equals("") || mCityDaoImpl.queryForCityName(city) == null) {
          remoteDataManager.cityCode = mCityDaoImpl.getCityCode("杭州");
          remoteDataManager.locatedCity = "杭州";
          mLocationTextView.setText("杭州");
        } else {
          remoteDataManager.cityCode = mCityDaoImpl.getCityCode(locationStr);
          remoteDataManager.locatedCity = locationStr;
          mLocationTextView.setText(locationStr);
        }
        mLocClient.stop();
        getMallData(remoteDataManager.cityCode);
      }
    }

    public void onReceivePoi(BDLocation poiLocation) {
    }
  }

  @Override
  public void onDestroy() {
    // 退出时销毁定位

    super.onDestroy();
  }

  @Override
  public void onClick(View v) {
    // TODO Auto-generated method stub
    Intent intent = new Intent();
    switch (v.getId()) {
      case R.id.tv_location:
        intent.setClass(getActivity(), CityListActivity.class);
        startActivityForResult(intent, 100);
        break;
      case R.id.iv_cart:
        if (remoteDataManager.isLogin) {
          intent.setClass(getActivity(), CartListActivity.class);
          startActivity(intent);
        } else {
          intent.setClass(getActivity(), LoginActivity.class);
          intent.putExtra("isGotoCart", true);
          startActivity(intent);
        }
        break;
      case R.id.tv_cart_num:
        if (remoteDataManager.isLogin) {
          intent.setClass(getActivity(), CartListActivity.class);
          startActivity(intent);
        } else {
          intent.setClass(getActivity(), LoginActivity.class);
          intent.putExtra("isGotoCart", true);
          startActivity(intent);
        }
        break;
      case R.id.iv_find_more:
        intent.setClass(getActivity(), MenuListActivity.class);
        startActivity(intent);
        break;
      case R.id.iv_find_more_org:
        intent.setClass(getActivity(), OrgListActivity.class);
        startActivity(intent);
        break;
      case R.id.iv_hot_no1:
        intent.setClass(getActivity(), MenuDetailActivity.class);
        intent.putExtra("menuID", hotMenus.get(0).getMenuId());
        intent.putExtra("isFamily", false);
        startActivity(intent);
        break;
      case R.id.iv_hot_no2:
        intent.setClass(getActivity(), MenuDetailActivity.class);
        intent.putExtra("menuID", hotMenus.get(1).getMenuId());
        intent.putExtra("isFamily", false);
        startActivity(intent);
        break;
      case R.id.iv_hot_no3:
        intent.setClass(getActivity(), MenuDetailActivity.class);
        intent.putExtra("menuID", hotMenus.get(2).getMenuId());
        intent.putExtra("isFamily", false);
        startActivity(intent);
        break;
      case R.id.iv_hot_no4:
        intent.setClass(getActivity(), MenuDetailActivity.class);
        intent.putExtra("menuID", hotMenus.get(3).getMenuId());
        intent.putExtra("isFamily", false);
        startActivity(intent);
        break;
      case R.id.viewpager_advertise:
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(advertiseUrl[mAdvertiseImageView.getCurrentItem()]);
        intent.setData(content_url);
        startActivity(intent);
        break;
      default:
        break;
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 100 && resultCode == 100) {
      mLocationTextView.setText(data.getStringExtra("city"));
    }
  }

  @Override
  public void onCallMallFragment() {
    // TODO Auto-generated method stub
    if (remoteDataManager.firstOpenApp) {
      mLocClient = new LocationClient(getActivity());
      mLocClient.registerLocationListener(myListener);
      LocationClientOption option = new LocationClientOption();
      option.setOpenGps(true);
      option.setAddrType("all");
      option.setCoorType("bd09ll");
      option.setScanSpan(5000);
      option.disableCache(true);
      option.setPoiNumber(5);
      option.setPoiDistance(1000);
      option.setPoiExtraInfo(true);
      option.setPriority(LocationClientOption.NetWorkFirst);
      mLocClient.setLocOption(option);
      mLocClient.start();
      showProgressDialog("定位中", "请稍候");
    } else {
      remOrgs.clear();
      hotMenus.clear();
      fragmentList.clear();
      mLinearLayoutTab.removeAllViews();
      mLocationTextView.setText(remoteDataManager.locatedCity);
      if (remoteDataManager.cityCode != "")
        getMallData(remoteDataManager.cityCode);
    }
    if (remoteDataManager.isLogin)
      getCartCount();
  }

  @Override
  public void onResume() {
    super.onResume();
    if (remoteDataManager.currentPage == 1)
      onCallMallFragment();
  }

  public void getMallData(String cityCode) {

    remoteDataManager.mallDataListener = new MallDataListener() {
      @Override
      public void onSuccess(final MallData mallData) {
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            mScrollView.onRefreshComplete();
            if (mallData.getAdvertises().size() > 0) {
              mImgAdvertise = new ImageView[mallData.getAdvertises().size()];
              mViewList = new ArrayList<View>();
              advertiseUrl = new String[mallData.getAdvertises().size()];
              for (int i = 0; i < mallData.getAdvertises().size(); i++) {
                mImgAdvertise[i] = new ImageView(getActivity());
                final int num = i;
                ImageLoader.getInstance().loadImage(
                    RemoteDataManager.SERVICE + mallData.getAdvertises().get(i).getImg(), options,
                    new ImageLoadingListener() {

                      @Override
                      public void onLoadingStarted(String arg0, View arg1) {
                      }

                      @Override
                      public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                      }

                      @Override
                      public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                        showImage(arg2, mImgAdvertise[num]);
                      }

                      @Override
                      public void onLoadingCancelled(String arg0, View arg1) {
                      }
                    });
                mViewList.add(mImgAdvertise[i]);
                if (mallData.getAdvertises().get(0).getValue() != null
                    && !mallData.getAdvertises().get(0).getValue().equals("")) {
                  advertiseUrl[i] = mallData.getAdvertises().get(i).getValue();
                }
              }
              AdsViewPagerAdpter adapter = new AdsViewPagerAdpter(mViewList, advertiseUrl, getActivity());
              mAdvertiseImageView.setAdapter(adapter);
              mAdvertiseImageView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {  
                
                @Override  
                public void onPageSelected(int arg0) {  
                    myHandler.sendMessage(Message.obtain(myHandler, MSG_PAGE_CHANGED, arg0, 0));  
                }  
                @Override  
                public void onPageScrolled(int arg0, float arg1, int arg2) {  
                }  
                @Override  
                public void onPageScrollStateChanged(int arg0) {  
                    switch (arg0) {  
                    case ViewPager.SCROLL_STATE_DRAGGING:  
                      myHandler.sendEmptyMessage(MSG_KEEP_SILENT);  
                        break;  
                    case ViewPager.SCROLL_STATE_IDLE:  
                      myHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);  
                        break;  
                    default:  
                        break;  
                    }  
                }  
            });  
            myHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
            }
            hotMenus.clear();
            ArrayList<HotMenu> menus = mallData.getHotMenus();
            for (int i = 0; i < menus.size(); i++) {
              HotMenu menu = new HotMenu();
              menu.setId(menus.get(i).getId());
              menu.setImg(menus.get(i).getImg());
              menu.setMenuId(menus.get(i).getMenuId());
              hotMenus.add(menu);
            }
            if (hotMenus.size() > 0) {
              mHot1ImageView.setVisibility(View.VISIBLE);
              mHot2ImageView.setVisibility(View.VISIBLE);
              mHot3ImageView.setVisibility(View.VISIBLE);
              mHot4ImageView.setVisibility(View.VISIBLE);
              mHot1ImageView.setOnClickListener(MallFragment.this);
              ImageLoader.getInstance().loadImage(RemoteDataManager.SERVICE + hotMenus.get(0).getImg(), options,
                  new ImageLoadingListener() {

                    @Override
                    public void onLoadingStarted(String arg0, View arg1) {
                    }

                    @Override
                    public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                    }

                    @Override
                    public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                      // mHot1ImageView.setImageBitmap(arg2);
                      showImageView(arg2, mHot1ImageView);

                    }

                    @Override
                    public void onLoadingCancelled(String arg0, View arg1) {
                    }
                  });
              // ImageLoader.getInstance().displayImage(RemoteDataManager.SERVICE
              // + hotMenus.get(0).getImg(),
              // mHot1ImageView, options);

            } else {
              mHot1ImageView.setVisibility(View.INVISIBLE);
              mHot2ImageView.setVisibility(View.INVISIBLE);
              mHot3ImageView.setVisibility(View.INVISIBLE);
              mHot4ImageView.setVisibility(View.INVISIBLE);
            }
            if (hotMenus.size() > 1) {
              mHot2ImageView.setOnClickListener(MallFragment.this);
              ImageLoader.getInstance().loadImage(RemoteDataManager.SERVICE + hotMenus.get(1).getImg(), options,
                  new ImageLoadingListener() {

                    @Override
                    public void onLoadingStarted(String arg0, View arg1) {
                    }

                    @Override
                    public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                    }

                    @Override
                    public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                      showImageView(arg2, mHot2ImageView);

                    }

                    @Override
                    public void onLoadingCancelled(String arg0, View arg1) {
                    }
                  });
              // ImageLoader.getInstance().displayImage(RemoteDataManager.SERVICE
              // + hotMenus.get(1).getImg(),
              // mHot2ImageView, options);
            } else {
              mHot2ImageView.setVisibility(View.INVISIBLE);
              mHot3ImageView.setVisibility(View.INVISIBLE);
              mHot4ImageView.setVisibility(View.INVISIBLE);
            }
            if (hotMenus.size() > 2) {
              mHot3ImageView.setOnClickListener(MallFragment.this);
              ImageLoader.getInstance().loadImage(RemoteDataManager.SERVICE + hotMenus.get(2).getImg(), options,
                  new ImageLoadingListener() {

                    @Override
                    public void onLoadingStarted(String arg0, View arg1) {
                    }

                    @Override
                    public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                    }

                    @Override
                    public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                      showImageView(arg2, mHot3ImageView);

                    }

                    @Override
                    public void onLoadingCancelled(String arg0, View arg1) {
                    }
                  });
              // ImageLoader.getInstance().displayImage(RemoteDataManager.SERVICE
              // + hotMenus.get(2).getImg(),
              // mHot3ImageView, options);
            } else {
              mHot3ImageView.setVisibility(View.INVISIBLE);
              mHot4ImageView.setVisibility(View.INVISIBLE);
            }
            if (hotMenus.size() > 3) {
              mHot4ImageView.setOnClickListener(MallFragment.this);
              ImageLoader.getInstance().loadImage(RemoteDataManager.SERVICE + hotMenus.get(3).getImg(), options,
                  new ImageLoadingListener() {

                    @Override
                    public void onLoadingStarted(String arg0, View arg1) {
                    }

                    @Override
                    public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                    }

                    @Override
                    public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                      showImageView(arg2, mHot4ImageView);
                    }

                    @Override
                    public void onLoadingCancelled(String arg0, View arg1) {
                    }
                  });
              // ImageLoader.getInstance().displayImage(RemoteDataManager.SERVICE
              // + hotMenus.get(3).getImg(),
              // mHot4ImageView, options);
            } else {
              mHot4ImageView.setVisibility(View.INVISIBLE);
            }
            remOrgs.clear();
            ArrayList<HotOrg> organizations = mallData.getHotOrgs();
            for (int i = 0; i < organizations.size(); i++) {
              HotOrg org2 = new HotOrg();
              org2.setOrgId(organizations.get(i).getOrgId());
              org2.setName(organizations.get(i).getName());
              org2.setImg(organizations.get(i).getImg());
              org2.setOrgTypeName(RemoteDataManager.mOrderTyNames[organizations.get(i).getOrgType()]);
              remOrgs.add(org2);
            }
            if (mRemOrgAdapter == null) {
              mRemOrgAdapter = new RemOrgAdapter(getActivity(), remOrgs);
              mInforGridView.setAdapter(mRemOrgAdapter);
            } else {
              mRemOrgAdapter.notifyDataSetChanged();
            }
            ArrayList<MallMenuType> menuTypes = mallData.getMenuTypes();
            for (int i = 0; i < menuTypes.size(); i++) {
              FrameLayout frameLayout = new FrameLayout(getActivity());
              frameLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
              frameLayout.setId(i + 1);
              mLinearLayoutTab.addView(frameLayout);
              MallTypeFragment fragment = new MallTypeFragment();
              fragment.setData(menuTypes.get(i));
              fragmentList.add(fragment);
              getChildFragmentManager().beginTransaction().add(i + 1, fragment).commitAllowingStateLoss();
            }
            mLinearLayoutTab.invalidate();
            dismissProgressDialog();
          }
        });
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        dismissProgressDialog();
      }
    };
    if (validateInternet()) {
      showProgressDialog("商城", "加载中");
      remoteDataManager.getMallData(cityCode);
    }
  }

  public void showImageView(Bitmap bitmap, ImageView imageView) {
    int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    imageView.measure(w, h);
    int height = imageView.getMeasuredHeight();
    int width = imageView.getMeasuredWidth();
    float scaleWidth = ((float) width) / bitmap.getWidth();
    float scaleHeight = ((float) height) / bitmap.getHeight();
    Matrix matrix = new Matrix();
    matrix.postScale(scaleWidth, scaleHeight);
    Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    imageView.setImageBitmap(newbm);
  }

  public void showImage(Bitmap bitmap, ImageView imageView) {
    int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    imageView.measure(w, h);
    int height = mAdvertiseImageView.getMeasuredHeight();
    int width = mAdvertiseImageView.getMeasuredWidth();
    float scaleWidth = ((float) width) / bitmap.getWidth();
    float scaleHeight = ((float) height) / bitmap.getHeight();
    Matrix matrix = new Matrix();
    matrix.postScale(scaleWidth, scaleHeight);
    Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    imageView.setImageBitmap(newbm);
  }

  public void getCartCount() {
    remoteDataManager.cartCountListener = new ReturnFromServerListener() {

      @Override
      public void onSuccess(String message) {
        getActivity().runOnUiThread(new Runnable() {

          @Override
          public void run() {
            if (remoteDataManager.cartCount > 0) {
              mCartCountTextView.setVisibility(View.VISIBLE);
              mCartCountTextView.setText(remoteDataManager.cartCount + "");
            } else {
              mCartCountTextView.setVisibility(View.GONE);
            }
          }
        });
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        handleError(errorMessage);
      }
    };
    if (validateInternet() && remoteDataManager.isLogin) {
      remoteDataManager.getCartCount();
    }
  }

  
  Handler myHandler = new Handler() {
    public void handleMessage(Message msg) {
      if (myHandler.hasMessages(MSG_UPDATE_IMAGE)){
         myHandler.removeMessages(MSG_UPDATE_IMAGE);
         }
      switch (msg.what) {
        case MSG_UPDATE_IMAGE:
          currentItem++;
          if(currentItem==advertiseUrl.length){
            currentItem=0;
          }
          mAdvertiseImageView.setCurrentItem(currentItem);
          myHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
          break;
        case MSG_KEEP_SILENT:
          break;
        case MSG_BREAK_SILENT:
          break;
        case MSG_PAGE_CHANGED:
          currentItem = msg.arg1;
          break;
        default:
          break;
      }
      super.handleMessage(msg);
    }
  };
}
