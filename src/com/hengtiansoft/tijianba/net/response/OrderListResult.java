package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class OrderListResult extends ResponseResult {
  @SerializedName("data")
  private ArrayList<Order> orderList = new ArrayList<Order>();

  public ArrayList<Order> getOrderList() {
    return orderList;
  }

  public void setOrderList(ArrayList<Order> orderList) {
    this.orderList = orderList;
  }

}
