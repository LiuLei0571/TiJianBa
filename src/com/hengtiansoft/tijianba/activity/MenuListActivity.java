package com.hengtiansoft.tijianba.activity;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hengtiansoft.tijianba.adapter.MenuFamilyAdapter;
import com.hengtiansoft.tijianba.adapter.MenuPlatAdapter;
import com.hengtiansoft.tijianba.adapter.OrderTypeAdapter;
import com.hengtiansoft.tijianba.model.OrderType;
import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.Menu;
import com.hengtiansoft.tijianba.net.response.MenuListListener;
import com.hengtiansoft.tijianba.net.response.MenuTypeListListener;
import com.hengtiansoft.tijianba.net.response.MenuTypeListResult.MenuType;
import com.hengtiansoft.tijianba.net.response.PackedMenuRequest;
import com.juanliuinformation.lvningmeng.R;

/**
 * 
 * com.hengtiansoft.tijianba.activity.OrgListActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 24, 2014 10:38:29 AM
 */
public class MenuListActivity extends BaseActivity implements OnClickListener {
  private ListView mOrderListView;
  private ListView mMenuFamilyListView, mMenuPlatListView;
  private Button menuAllButton, menuRemButton, menuTypeButton;
  // private ScrollView mScrollView;
  private EditText mSearchEditText;
  private TextView mMenuTextView;
  private ArrayList<Menu> mFamilyMenus = new ArrayList<Menu>();
  private ArrayList<Menu> mPlatMenus = new ArrayList<Menu>();
  private ArrayList<OrderType> mOrderTypes = new ArrayList<OrderType>();
  private ArrayList<OrderType> mOrderAlls = new ArrayList<OrderType>();
  private MenuFamilyAdapter mMenuFamilyAdapter;
  private MenuPlatAdapter mMenuPlatAdapter;
  private OrderTypeAdapter mOrderTypeAdapter, mOrderAllAdapter;
  private boolean isMenuAllButton, isMenuRemButton, isMenuTypeButton;
  private boolean isMenu = true;
  private int currentTypeId;
  private boolean isFirst = true;
  private int currentFamilyPageNum = 1;
  private int currentPlatPageNum = 1;
  private int orderType = 0;
  private int orderDesc = 0;
  private RelativeLayout mFindMoreFamilyButton, mFindMorePlatButton;
  private TextView cartCountTextView;
  private boolean isMenuAllFirst, isMenuTypeFirst;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_menu_list);
    setView();
    setDate();
  }

  private void setView() {
    findViewById(R.id.iv_cart).setOnClickListener(this);
    cartCountTextView = (TextView) findViewById(R.id.tv_cart_num);
    cartCountTextView.setOnClickListener(this);
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
        if (s.toString().equals("") && !isMenuRemButton && !isMenuAllButton && !isMenuTypeButton) {
          mFamilyMenus.clear();
          mPlatMenus.clear();
          isFirst = true;
          if (remoteDataManager.loginInfo != null && !"".equals(remoteDataManager.loginInfo)
              && remoteDataManager.userType == 1) {
            getMenus(remoteDataManager.cityCode, 1, "", isMenuRemButton, true, orderType, orderDesc, currentTypeId);
          } else {
            mMenuFamilyListView.setVisibility(View.GONE);
            mFindMoreFamilyButton.setVisibility(View.GONE);
            findViewById(R.id.rl_family).setVisibility(View.GONE);
          }
          getMenus(remoteDataManager.cityCode, 1, "", isMenuRemButton, false, orderType, orderDesc, currentTypeId);
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
            inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
          }
          if (!isMenu) {
            isMenu = true;
            Intent intent = new Intent(MenuListActivity.this, OrgListActivity.class);
            intent.putExtra("search", mSearchEditText.getText() + "");
            startActivity(intent);
            finish();
          }
          if (isFirst) {
            isFirst = false;
            isMenuRemButton = false;
            isMenuAllButton = false;
            isMenuTypeButton = false;
            menuRemButton.setBackgroundResource(R.drawable.list_unselected);
            menuAllButton.setBackgroundResource(R.drawable.list_unselected);
            menuTypeButton.setBackgroundResource(R.drawable.list_unselected);
            if (!isMenu) {
              isMenu = true;
              Intent intent = new Intent(MenuListActivity.this, OrgListActivity.class);
              intent.putExtra("search", mSearchEditText.getText() + "");
              startActivity(intent);
              finish();
            } else if (search != null && !"".equals(search)) {
              mFamilyMenus.clear();
              mPlatMenus.clear();
              if (remoteDataManager.loginInfo != null && !"".equals(remoteDataManager.loginInfo)) {
                getMenus(remoteDataManager.cityCode, 1, search, isMenuRemButton, true, orderType, orderDesc,
                    currentTypeId);
              }
              getMenus(remoteDataManager.cityCode, 1, search, isMenuRemButton, false, orderType, orderDesc,
                  currentTypeId);
            }

            return true;
          }
          return true;
        }
        return false;
      }
    });

    // mScrollView = (ScrollView) findViewById(R.id.sv_menu_list);
    findViewById(R.id.spinnerid).setOnClickListener(this);
    mMenuTextView = (TextView) findViewById(R.id.btn_menu);
    mMenuTextView.setOnClickListener(this);
    findViewById(R.id.tv_org).setOnClickListener(this);
    findViewById(R.id.tv_menu).setOnClickListener(this);
    mMenuFamilyListView = (ListView) findViewById(R.id.lv_family_menu);
    mMenuFamilyListView.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MenuListActivity.this, MenuDetailActivity.class);
        intent.putExtra("menuID", mFamilyMenus.get(position).getId());
        intent.putExtra("isFamily", mFamilyMenus.get(position).isFamily());
        startActivity(intent);
      }
    });
    mMenuPlatListView = (ListView) findViewById(R.id.lv_plat_menu);
    mMenuPlatListView.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MenuListActivity.this, MenuDetailActivity.class);
        intent.putExtra("menuID", mPlatMenus.get(position).getId());
        intent.putExtra("isFamily", mPlatMenus.get(position).isFamily());
        startActivity(intent);
      }
    });
    mOrderListView = (ListView) findViewById(R.id.lv_order_list);
    mFindMoreFamilyButton = (RelativeLayout) findViewById(R.id.btn_find_family_more);
    mFindMoreFamilyButton.setOnClickListener(this);
    mFindMorePlatButton = (RelativeLayout) findViewById(R.id.btn_find_plat_more);
    mFindMorePlatButton.setOnClickListener(this);
    findViewById(R.id.rl_family).setOnClickListener(this);
    findViewById(R.id.rl_plat).setOnClickListener(this);
    mOrderListView.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // mOrderTypes
        mOrderListView.setVisibility(View.GONE);
        findViewById(R.id.half).setVisibility(View.GONE);
        mFamilyMenus.clear();
        mPlatMenus.clear();
        if (isMenuAllButton) {
          mOrderAllAdapter.select(position);
          mOrderAllAdapter.notifyDataSetChanged();
          menuAllButton.setText(mOrderAlls.get(position).getName());
          switch (position) {
            case 0:
              orderType = 0;
              orderDesc = 0;
              break;
            case 1:
              orderType = 2;
              orderDesc = 0;
              break;
            case 2:
              orderType = 2;
              orderDesc = 1;
              break;
            case 3:
              orderType = 3;
              orderDesc = 0;
              break;
            case 4:
              orderType = 1;
              orderDesc = 0;
              break;
            default:
              break;
          }
          mFamilyMenus.clear();
          mPlatMenus.clear();
        } else if (isMenuTypeButton) {
          mOrderTypeAdapter.select(position);
          mOrderTypeAdapter.notifyDataSetChanged();
          menuTypeButton.setText(mOrderTypes.get(position).getName());
          currentTypeId = mOrderTypes.get(position).getId();
          mFamilyMenus.clear();
          mPlatMenus.clear();
        }
        if (remoteDataManager.loginInfo != null && !"".equals(remoteDataManager.loginInfo)) {
          getMenus(remoteDataManager.cityCode, 1, "", false, true, orderType, orderDesc, currentTypeId);
        }
        getMenus(remoteDataManager.cityCode, 1, "", false, false, orderType, orderDesc, currentTypeId);
      }
    });
    menuAllButton = (Button) findViewById(R.id.btn_menu_all);
    menuRemButton = (Button) findViewById(R.id.btn_menu_rem);
    menuRemButton.setBackgroundResource(R.drawable.list_selected);
    menuTypeButton = (Button) findViewById(R.id.btn_menu_type);
    menuAllButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        currentFamilyPageNum = 1;
        currentPlatPageNum = 1;
        menuAllButton.setBackgroundResource(R.drawable.list_selected);
        menuRemButton.setBackgroundResource(R.drawable.list_unselected);
        menuTypeButton.setBackgroundResource(R.drawable.list_unselected);
        isMenuRemButton = false;
        isMenuTypeButton = false;
        isMenuAllButton = true;
        mSearchEditText.setText("");
        mOrderAllAdapter = new OrderTypeAdapter(MenuListActivity.this, mOrderAlls);
        mOrderListView.setAdapter(mOrderAllAdapter);
        mOrderListView.setVisibility(View.VISIBLE);
        findViewById(R.id.half).setVisibility(View.VISIBLE);
      }
    });

    menuRemButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        currentFamilyPageNum = 1;
        currentPlatPageNum = 1;
        isMenuRemButton = true;
        findViewById(R.id.half).setVisibility(View.GONE);
        mOrderListView.setVisibility(View.GONE);
        menuRemButton.setBackgroundResource(R.drawable.list_selected);
        menuAllButton.setBackgroundResource(R.drawable.list_unselected);
        menuTypeButton.setBackgroundResource(R.drawable.list_unselected);
        mSearchEditText.setText("");
        mFamilyMenus.clear();
        mPlatMenus.clear();
        if (remoteDataManager.loginInfo != null && !"".equals(remoteDataManager.loginInfo)) {
          getMenus(remoteDataManager.cityCode, 1, "", isMenuRemButton, true, orderType, orderDesc, currentTypeId);
        }
        getMenus(remoteDataManager.cityCode, 1, "", isMenuRemButton, false, orderType, orderDesc, currentTypeId);
      }
    });
    menuTypeButton.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        currentFamilyPageNum = 1;
        currentPlatPageNum = 1;
        menuRemButton.setBackgroundResource(R.drawable.list_unselected);
        menuTypeButton.setBackgroundResource(R.drawable.list_selected);
        menuAllButton.setBackgroundResource(R.drawable.list_unselected);
        isMenuRemButton = false;
        isMenuTypeButton = true;
        isMenuAllButton = false;
        mSearchEditText.setText("");
        mOrderTypeAdapter = new OrderTypeAdapter(MenuListActivity.this, mOrderTypes);
        mOrderListView.setAdapter(mOrderTypeAdapter);
        mOrderListView.setVisibility(View.VISIBLE);
        findViewById(R.id.half).setVisibility(View.VISIBLE);
      }
    });

    if (mMenuFamilyAdapter == null) {
      mMenuFamilyAdapter = new MenuFamilyAdapter(this, mFamilyMenus);
      mMenuFamilyListView.setAdapter(mMenuFamilyAdapter);
    } 
//    else {
//      mMenuFamilyListView.setAdapter(mMenuFamilyAdapter);
//      mMenuFamilyAdapter.notifyDataSetChanged();
//    }
    if (mMenuPlatAdapter == null) {
      mMenuPlatAdapter = new MenuPlatAdapter(this, mPlatMenus);
      mMenuPlatListView.setAdapter(mMenuPlatAdapter);
    } 
//    else {
//      mMenuPlatListView.setAdapter(mMenuPlatAdapter);
//      mMenuPlatAdapter.notifyDataSetChanged();
//    }
  }

  private void setOrderAll() {
    mOrderAlls.clear();
    OrderType org0 = new OrderType();
    org0.setSelected(true);
    org0.setName(getString(R.string.str_menu_all));
    mOrderAlls.add(org0);
    OrderType org = new OrderType();
    org.setSelected(false);
    org.setName(getString(R.string.str_order_price_down));
    mOrderAlls.add(org);
    OrderType org2 = new OrderType();
    org2.setSelected(false);
    org2.setName(getString(R.string.str_order_price_up));
    mOrderAlls.add(org2);
    OrderType org3 = new OrderType();
    org3.setSelected(false);
    org3.setName(getString(R.string.str_order_num));
    mOrderAlls.add(org3);
    OrderType org4 = new OrderType();
    org4.setSelected(false);
    org4.setName(getString(R.string.str_order_time));
    mOrderAlls.add(org4);
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent();
    View selectorView = findViewById(R.id.selector);
    String search = mSearchEditText.getText().toString();
    switch (v.getId()) {
      case R.id.tv_location:
        intent.setClass(MenuListActivity.this, CityListActivity.class);
        startActivityForResult(intent, 100);
        break;
      case R.id.iv_cart:
        if (remoteDataManager.isLogin) {
          intent.setClass(MenuListActivity.this, CartListActivity.class);
          startActivity(intent);
        } else {
          intent.setClass(MenuListActivity.this, LoginActivity.class);
          intent.putExtra("isGotoCart", true);
          startActivity(intent);
        }
        break;
      case R.id.tv_cart_num:
        if (remoteDataManager.isLogin) {
          intent.setClass(MenuListActivity.this, CartListActivity.class);
          startActivity(intent);
        } else {
          intent.setClass(MenuListActivity.this, LoginActivity.class);
          intent.putExtra("isGotoCart", true);
          startActivity(intent);
        }
        break;
      case R.id.btn_menu:
        if (selectorView.getVisibility() == View.VISIBLE) {
          selectorView.setVisibility(View.GONE);
        } else {
          selectorView.setVisibility(View.VISIBLE);
        }
        break;
      case R.id.tv_org:
        isMenu = false;
        mMenuTextView.setText(getString(R.string.str_org));
        mSearchEditText.setText("");
        selectorView.setVisibility(View.GONE);
        mSearchEditText.setHint("请输入机构名称");
        break;
      case R.id.tv_menu:
        isMenu = true;
        mMenuTextView.setText(getString(R.string.str_menu));
        mSearchEditText.setText("");
        selectorView.setVisibility(View.GONE);
        mSearchEditText.setHint("请输入套餐名称");
        break;
      case R.id.btn_find_family_more:
        currentFamilyPageNum++;
        getMenus(remoteDataManager.cityCode, currentFamilyPageNum, search, isMenuRemButton, true, orderType, orderDesc,
            currentTypeId);
        break;
      case R.id.btn_find_plat_more:
        currentPlatPageNum++;
        getMenus(remoteDataManager.cityCode, currentPlatPageNum, search, isMenuRemButton, false, orderType, orderDesc,
            currentTypeId);
        break;
      case R.id.spinnerid:
        mSearchEditText.requestFocus();
        break;
      case R.id.rl_family:
        if (mMenuFamilyListView.getVisibility() == View.VISIBLE || mFamilyMenus.size() == 0) {
          mMenuFamilyListView.setVisibility(View.GONE);
          mFindMoreFamilyButton.setVisibility(View.GONE);
        } else {
          mMenuFamilyListView.setVisibility(View.VISIBLE);
          mFindMoreFamilyButton.setVisibility(View.VISIBLE);
        }
        break;
      case R.id.rl_plat:
        if (mMenuPlatListView.getVisibility() == View.VISIBLE || mPlatMenus.size() == 0) {
          mMenuPlatListView.setVisibility(View.GONE);
          mFindMorePlatButton.setVisibility(View.GONE);
        } else {
          mMenuPlatListView.setVisibility(View.VISIBLE);
          mFindMorePlatButton.setVisibility(View.VISIBLE);
        }
        break;
      default:
        break;
    }
  }

  public void getMenus(String cityCode, int pageNo, String name, boolean isRecommendList, boolean isFamily,
      int orderType, int orderDesc, int menuTypeId) {
    PackedMenuRequest packedMenuRequest = new PackedMenuRequest();
    packedMenuRequest.setCityCode(cityCode);
    if (isFamily) {
      if (spSettting.getString("REFRESH_TIME_MENU_FAMILY", "") != null
          && !spSettting.getString("REFRESH_TIME_MENU_FAMILY", "").equals("")) {
        packedMenuRequest.setRefreshTime(spSettting.getString("REFRESH_TIME_MENU_FAMILY", ""));
      } else {
        packedMenuRequest.setRefreshTime(RemoteDataManager.sdfTime.format(new java.util.Date()));
      }
    }

    else {
      if (spSettting.getString("REFRESH_TIME_MENU_PLAT", "") != null
          && !spSettting.getString("REFRESH_TIME_MENU_PLAT", "").equals("")) {
        packedMenuRequest.setRefreshTime(spSettting.getString("REFRESH_TIME_MENU_PLAT", ""));
      } else {
        packedMenuRequest.setRefreshTime(RemoteDataManager.sdfTime.format(new java.util.Date()));
      }
    }

    packedMenuRequest.setName(name);
    if (isFamily) {
      packedMenuRequest.setPageSize(2);
    } else {
      packedMenuRequest.setPageSize(10);
    }
    packedMenuRequest.setPageNo(pageNo);
    packedMenuRequest.setRecommendList(isRecommendList);
    packedMenuRequest.setFamily(isFamily);
    packedMenuRequest.setOrderType(orderType);
    packedMenuRequest.setOrderDesc(orderDesc);
    if (menuTypeId != -1)
      packedMenuRequest.setMenuTypeId(menuTypeId);
    remoteDataManager.menuListListener = new MenuListListener() {

      @Override
      public void onSuccess(final ArrayList<Menu> menus, final boolean isFamily) {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            TextView family = (TextView) findViewById(R.id.tv_find_family_more);
            TextView plat = (TextView) findViewById(R.id.tv_find_plat_more);
            // if (menus.size() == 0) {
            // if (isFamily && mFamilyMenus.size() == 0) {
            // mMenuFamilyListView.setVisibility(View.GONE);
            // mFindMoreFamilyButton.setVisibility(View.VISIBLE);
            // family.setText(getString(R.string.str_no_data));
            // ImageView view = (ImageView)
            // findViewById(R.id.rl_seperate_family);
            // view.setVisibility(View.GONE);
            // } else if (mPlatMenus.size() == 0) {
            // mMenuPlatListView.setVisibility(View.GONE);
            // mFindMorePlatButton.setVisibility(View.VISIBLE);
            // plat.setText(getString(R.string.str_no_data));
            // ImageView view = (ImageView)
            // findViewById(R.id.rl_seperate_plat);
            // view.setVisibility(View.GONE);
            // }
            // } else {
            // mFindMorePlatButton.setVisibility(View.VISIBLE);
            // if (isFamily) {
            // mMenuFamilyListView.setVisibility(View.VISIBLE);
            // family.setText(getString(R.string.str_family_more));
            // } else {
            // mMenuPlatListView.setVisibility(View.VISIBLE);
            // plat.setText(getString(R.string.str_plat_more));
            // }
            // }
            for (int i = 0; i < menus.size(); i++) {
              Menu menu = new Menu();
              menu.setId((menus.get(i).getId()));
              menu.setName(menus.get(i).getName());
              menu.setSuitOrgNum(menus.get(i).getSuitOrgNum());
              menu.setFamily(menus.get(i).isFamily());
              menu.setMarketPrice(menus.get(i).getMarketPrice());
              menu.setPlatformPrice(menus.get(i).getPlatformPrice());
              menu.setRebate(menus.get(i).getRebate());
              menu.setLogo(menus.get(i).getLogo());
              if (isFamily) {
                menu.setFamilyPrice(menus.get(i).getFamilyPrice());
                mFamilyMenus.add(menu);
                mMenuFamilyAdapter.notifyDataSetChanged();
              } else {
                mPlatMenus.add(menu);
                mMenuPlatAdapter.notifyDataSetChanged();
              }
            }

            if (isFamily) {
              if (menus.size() < 2) {
                mFindMoreFamilyButton.setVisibility(View.GONE);
              } else if (menus.size() == 2) {
                mFindMoreFamilyButton.setVisibility(View.VISIBLE);
                mMenuFamilyListView.setVisibility(View.VISIBLE);
                family.setText(getString(R.string.str_family_more));
              }
            } else {
              if (menus.size() < 10) {
                mFindMorePlatButton.setVisibility(View.GONE);
              } else if (menus.size() == 10) {
                mFindMorePlatButton.setVisibility(View.VISIBLE);
                mMenuPlatListView.setVisibility(View.VISIBLE);
                plat.setText(getString(R.string.str_plat_more));
              }
            }
            mMenuFamilyAdapter.notifyDataSetChanged();
            mMenuPlatAdapter.notifyDataSetChanged();
            dismissProgressDialog();
          }
        });

      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        handleError(errorMessage);
        dismissProgressDialog();
      }
    };
    if (pageNo == 1 || pageNo == 0) {
      if (isFamily) {
        Editor editor = spSettting.edit();
        editor.putString("REFRESH_TIME_MENU_FAMILY", RemoteDataManager.sdfTime.format(new java.util.Date()));
        editor.commit();
      } else {
        Editor editor = spSettting.edit();
        editor.putString("REFRESH_TIME_MENU_PLAT", RemoteDataManager.sdfTime.format(new java.util.Date()));
        editor.commit();
      }
    }
    if (validateInternet()) {
      showProgressDialog(getString(R.string.str_menu), "加载中..");
      remoteDataManager.getMenuList(packedMenuRequest);
    }
  }

  private void getMenuTypes() {

    remoteDataManager.menuTypeListListener = new MenuTypeListListener() {
      @Override
      public void onSuccess(ArrayList<MenuType> menuTypes) {
      
        mOrderTypes.clear();
        for (int i = 0; i < menuTypes.size(); i++) {
          OrderType orderType = new OrderType();
          if (i == 0) {
            orderType.setName(getString(R.string.str_menu_type));
            orderType.setId(0);
            orderType.setSelected(true);
          } else {
            orderType.setName(menuTypes.get(i - 1).getName());
            orderType.setId(menuTypes.get(i - 1).getId());
            orderType.setSelected(false);
          }
          if (currentTypeId == menuTypes.get(i).getId()) {
            orderType.setSelected(true);
          }
          mOrderTypes.add(orderType);
        }
//        runOnUiThread(new Runnable() {
//
//          @Override
//          public void run() {
//            mOrderTypeAdapter = new OrderTypeAdapter(MenuListActivity.this, mOrderTypes);
//            mOrderListView.setAdapter(mOrderTypeAdapter);
//            dismissProgressDialog();
//            // mOrderListView.setVisibility(View.VISIBLE);
//            // findViewById(R.id.half).setVisibility(View.VISIBLE);
//          }
//        });
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        handleError(errorMessage);
        dismissProgressDialog();
      }

    };
    if (validateInternet()) {
      showProgressDialog("套餐类型", "加载中..");
      remoteDataManager.getMenuType();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  private void setDate() {
    if (remoteDataManager.cartCount == 0) {
      cartCountTextView.setVisibility(View.GONE);
    } else {
      cartCountTextView.setVisibility(View.VISIBLE);
      cartCountTextView.setText(remoteDataManager.cartCount + "");
    }
    menuAllButton.setBackgroundResource(R.drawable.list_unselected);
    menuRemButton.setBackgroundResource(R.drawable.list_selected);
    menuTypeButton.setBackgroundResource(R.drawable.list_unselected);
    currentTypeId = -1;
    setOrderAll();
    mOrderAllAdapter = new OrderTypeAdapter(MenuListActivity.this, mOrderAlls);
    getMenuTypes();
    isMenuRemButton = true;
    String search = getIntent().getStringExtra("search");
    if (search != null) {
      isMenuRemButton = false;
      isMenuAllButton = false;
      isMenuTypeButton = false;
      menuAllButton.setBackgroundResource(R.drawable.list_unselected);
      menuTypeButton.setBackgroundResource(R.drawable.list_unselected);
      menuRemButton.setBackgroundResource(R.drawable.list_unselected);
      orderDesc = 0;
      orderType = 0;
      currentTypeId = -1;
      mSearchEditText.setText(search);
    }
    Boolean fromMall = getIntent().getBooleanExtra("fromMall", false);
    if (fromMall) {
      currentTypeId = getIntent().getIntExtra("menuTypeId", -1);
      String menuTypeName = getIntent().getStringExtra("menuTypeName");
      isMenuRemButton = false;
      isMenuAllButton = false;
      isMenuTypeButton = true;
      orderDesc = 0;
      orderType = 0;
      menuAllButton.setBackgroundResource(R.drawable.list_unselected);
      menuTypeButton.setBackgroundResource(R.drawable.list_selected);
      menuTypeButton.setText(menuTypeName);
      menuRemButton.setBackgroundResource(R.drawable.list_unselected);
    }
    if (remoteDataManager.loginInfo != null && !"".equals(remoteDataManager.loginInfo)
        && remoteDataManager.userType == 1) {
      getMenus(remoteDataManager.cityCode, 1, search, isMenuRemButton, true, orderType, orderDesc, currentTypeId);
    } else {
      mMenuFamilyListView.setVisibility(View.GONE);
      mFindMoreFamilyButton.setVisibility(View.GONE);
      findViewById(R.id.rl_family).setVisibility(View.GONE);
    }
    getMenus(remoteDataManager.cityCode, 1, search, isMenuRemButton, false, orderType, orderDesc, currentTypeId);
  }

}
