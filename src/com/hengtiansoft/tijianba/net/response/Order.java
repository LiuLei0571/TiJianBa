package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class Order {
  @SerializedName("id")
  private int id;
  @SerializedName("orderNo")
  private String orderNo;
  @SerializedName("payType")
  private int payType;
  @SerializedName("status")
  private int status;
  @SerializedName("menuList")
  private ArrayList<Menu> menuList;
  @SerializedName("verifyUrl")
  private String verifyUrl;

  public Order() {
    super();
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getOrderNo() {
    return this.orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public int getPayType() {
    return this.payType;
  }

  public void setPayType(int payType) {
    this.payType = payType;
  }

  public int getStatus() {
    return this.status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public ArrayList<Menu> getMenuList() {
    return this.menuList;
  }

  public void setMenuList(ArrayList<Menu> menuList) {
    this.menuList = menuList;
  }

}
