package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class OrderDetail {
  @SerializedName("id")
  private int id;
  @SerializedName("orderNo")
  private String orderNo;
  @SerializedName("createTime")
  private String createTime;
  @SerializedName("totalPrice")
  private int totalPrice;
  @SerializedName("payType")
  private int payType;
  @SerializedName("status")
  private int status;
  @SerializedName("orderUserName")
  private String orderUserName;
  @SerializedName("orderUserMobile")
  private String orderUserMobile;
  @SerializedName("orderUserEmail")
  private String orderUserEmail;
  @SerializedName("orderUserRemark")
  private String orderUserRemark;
  @SerializedName("menuList")
  private ArrayList<Menu> menuList;
  @SerializedName("bonusMoney")
  private int bonusMoney;
  @SerializedName("payMoney")
  private int payMoney;
  @SerializedName("verifyUrl")
  private String verifyUrl;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public int getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(int totalPrice) {
    this.totalPrice = totalPrice;
  }

  public int getPayType() {
    return payType;
  }

  public void setPayType(int payType) {
    this.payType = payType;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getOrderUserName() {
    return orderUserName;
  }

  public void setOrderUserName(String orderUserName) {
    this.orderUserName = orderUserName;
  }

  public String getOrderUserMobile() {
    return orderUserMobile;
  }

  public void setOrderUserMobile(String orderUserMobile) {
    this.orderUserMobile = orderUserMobile;
  }

  public String getOrderUserEmail() {
    return orderUserEmail;
  }

  public void setOrderUserEmail(String orderUserEmail) {
    this.orderUserEmail = orderUserEmail;
  }

  public String getOrderUserRemark() {
    return orderUserRemark;
  }

  public void setOrderUserRemark(String orderUserRemark) {
    this.orderUserRemark = orderUserRemark;
  }

  public ArrayList<Menu> getMenuList() {
    return menuList;
  }

  public void setMenuList(ArrayList<Menu> menuList) {
    this.menuList = menuList;
  }

  public int getBonusMoney() {
    return this.bonusMoney;
  }

  public void setBonusMoney(int bonusMoney) {
    this.bonusMoney = bonusMoney;
  }

  public int getPayMoney() {
    return this.payMoney;
  }

  public void setPayMoney(int payMoney) {
    this.payMoney = payMoney;
  }

  public String getVerifyUrl() {
    return this.verifyUrl;
  }

  public void setVerifyUrl(String verifyUrl) {
    this.verifyUrl = verifyUrl;
  }

}
