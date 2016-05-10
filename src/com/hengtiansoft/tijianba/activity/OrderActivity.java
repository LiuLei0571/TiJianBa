package com.hengtiansoft.tijianba.activity;

import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hengtiansoft.tijianba.adapter.OrderAdapter;
import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.Menu;
import com.hengtiansoft.tijianba.net.response.Order;
import com.hengtiansoft.tijianba.net.response.OrderListListener;
import com.hengtiansoft.tijianba.net.response.PackedOrderRequest;
import com.hengtiansoft.tijianba.net.response.ReturnFromServerListener;
import com.juanliuinformation.lvningmeng.R;

public class OrderActivity extends BaseActivity {
  private PullToRefreshListView mLvOrderView;
  private ArrayList<Order> mListOrder = new ArrayList<Order>();
  private OrderAdapter adapter;
  private int currentPageNum = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_order);
    setView();
  }

  @Override
  protected void onResume() {
    // TODO Auto-generated method stub
    mListOrder.clear();
    currentPageNum=1;
    getMyOrder(currentPageNum, 0);
    super.onResume();
  }

  private void setView() {
    mLvOrderView = (PullToRefreshListView) findViewById(R.id.list_order);
    setListListener();
    adapter = new OrderAdapter(this, mListOrder);
    mLvOrderView.setAdapter(adapter);
  }

  public void getMyOrder(int pageNo, int status) {

    PackedOrderRequest packedOrderRequest = new PackedOrderRequest();
    if (spSettting.getString("REFRESH_TIME_ORDER", "") != null
        && !spSettting.getString("REFRESH_TIME_ORDER", "").equals("")) {
      packedOrderRequest.setRefreshTime(spSettting.getString("REFRESH_TIME_ORDER", ""));
    } else {
      Date curDate = new Date(System.currentTimeMillis());
      packedOrderRequest.setRefreshTime(RemoteDataManager.sdfTime.format(curDate));
    }

    packedOrderRequest.setPageSize(10);
    packedOrderRequest.setStatus(status);
    packedOrderRequest.setPageNo(pageNo);
    remoteDataManager.orderlistListener = new OrderListListener() {

      @Override
      public void onSuccess(final ArrayList<Order> orderList) {
        // TODO Auto-generated method stub
        if (currentPageNum != 1 && orderList.size() == 0) {
          currentPageNum--;
        }
        for (int i = 0; i < orderList.size(); i++) {
          Order order = new Order();
          order.setId(orderList.get(i).getId());
          order.setOrderNo(orderList.get(i).getOrderNo());
          order.setPayType(orderList.get(i).getPayType());
          order.setStatus(orderList.get(i).getStatus());
          ArrayList<Menu> menuList = new ArrayList<Menu>();
          int menuSize = orderList.get(i).getMenuList().size();
          for (int j = 0; j < menuSize; j++) {
            Menu menuTmp = orderList.get(i).getMenuList().get(j);
            Menu menu = new Menu();
            menu.setId(menuTmp.getId());
            menu.setName(menuTmp.getName());
            menu.setMenuTypeName(menuTmp.getMenuTypeName());
            menu.setPrice(menuTmp.getPrice());
            menu.setNum(menuTmp.getNum());
            menu.setBuyNum(menuTmp.getBuyNum());
            menu.setLogo(menuTmp.getLogo());
            menuList.add(menu);
          }
          order.setMenuList(menuList);
          mListOrder.add(order);
        }
        runOnUiThread(new Runnable() {

          @Override
          public void run() {
            dismissProgressDialog();
            mLvOrderView.onRefreshComplete();
            TextView noDataTextView = (TextView) findViewById(R.id.tv_no_data);
            if (orderList.size() == 0 && mListOrder.size() == 0) {
              noDataTextView.setVisibility(View.VISIBLE);
            } else {
              noDataTextView.setVisibility(View.GONE);
            }
            adapter.notifyDataSetChanged();
          }
        });

      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        // TODO Auto-generated method stub
        handleError(errorMessage);
      }

    };
    if (pageNo == 1 || pageNo == 0) {
      Editor editor = spSettting.edit();
      editor.putString("REFRESH_TIME_ORDER", RemoteDataManager.sdfTime.format(new java.util.Date()));
      editor.commit();
    }
    if (validateInternet()) {
      showProgressDialog("订单", "加载中..");
      remoteDataManager.getOrderList(packedOrderRequest);
    }
  }

  private void setListListener() {
    mLvOrderView.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        Intent intent = new Intent(OrderActivity.this, OrderDetailActivity.class);
        intent.putExtra("orderID", mListOrder.get(position - 1).getId());
        startActivity(intent);
      }
    });
    mLvOrderView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

      @Override
      public void onLastItemVisible() {
        currentPageNum++;
        getMyOrder(currentPageNum, 0);
      }
    });
    mLvOrderView.setOnRefreshListener(new OnRefreshListener<ListView>() {
      @Override
      public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        Log.i("mLvOrderView", "onRefresh");
        mListOrder.clear();
        currentPageNum=1;
        String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
            DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
        getMyOrder(currentPageNum, 0);
      }
    });
  }

  public void cancelOrder(int orderId, final int position) {
    remoteDataManager.cancelOrderListener = new ReturnFromServerListener() {

      @Override
      public void onSuccess(String message) {
        // TODO Auto-generated method stub
        handleSuccess(message);
        mListOrder.remove(position);
        runOnUiThread(new Runnable() {

          @Override
          public void run() {
            adapter.notifyDataSetChanged();
          }
        });
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        // TODO Auto-generated method stub
        handleError(errorMessage);
      }

    };
    if (validateInternet()) {
      remoteDataManager.cancelOrder(orderId);
    }
  }
}
