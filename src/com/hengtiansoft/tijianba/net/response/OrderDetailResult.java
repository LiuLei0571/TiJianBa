package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

public class OrderDetailResult extends ResponseResult {
  @SerializedName("data")
  private OrderDetail orderDetail = new OrderDetail();

  public OrderDetail getOrderDetail() {
    return orderDetail;
  }

  public void setOrderDetail(OrderDetail orderDetail) {
    this.orderDetail = orderDetail;
  }

}
