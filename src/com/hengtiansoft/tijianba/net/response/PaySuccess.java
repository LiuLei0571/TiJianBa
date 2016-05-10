package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class PaySuccess {
  @SerializedName("id")
  private String id;
  @SerializedName("orderNo")
  private String orderNo;
  @SerializedName("detail")
  private String detail;
  @SerializedName("payMoney")
  private int payMoney;
  @SerializedName("totalPrice")
  private int totalPrice;
  @SerializedName("menuList")
  private ArrayList<Menu> menuList;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public int getPayMoney() {
    return payMoney;
  }

  public void setPayMoney(int payMoney) {
    this.payMoney = payMoney;
  }

  public int getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(int totalPrice) {
    this.totalPrice = totalPrice;
  }

  public ArrayList<Menu> getMenuList() {
    return menuList;
  }

  public void setMenuList(ArrayList<Menu> menuList) {
    this.menuList = menuList;
  }

}
