package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * com.hengtiansoft.tijianba.net.response.BuyResult
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 26, 2014 6:54:00 PM
 */
public class BuyDetail {
  @SerializedName("id")
  private int id;
  @SerializedName("orderNo")
  private String orderNo;
  @SerializedName("payMoney")
  private int payMoney;
  @SerializedName("bonusMoney")
  private int bonusMoneyCanUse;
  @SerializedName("menuList")
  private ArrayList<Menu> menuList;
  @SerializedName("orderUserName")
  private String orderUserName;
  @SerializedName("rebateTotal")
  private int rebateTotal;
  @SerializedName("orderUserRemark")
  private String orderUserRemark;
  @SerializedName("orderUserMobile")
  private String orderUserMobile;
  @SerializedName("orderUserEmail")
  private String orderUserEmail;
  @SerializedName("verifyUrl")
  private String verifyUrl;

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

  public int getPayMoney() {
    return this.payMoney;
  }

  public void setPayMoney(int payMoney) {
    this.payMoney = payMoney;
  }

  public int getBonusMoneyCanUse() {
    return this.bonusMoneyCanUse;
  }

  public void setBonusMoneyCanUse(int bonusMoneyCanUse) {
    this.bonusMoneyCanUse = bonusMoneyCanUse;
  }

  public ArrayList<Menu> getMenuList() {
    return this.menuList;
  }

  public void setMenuList(ArrayList<Menu> menuList) {
    this.menuList = menuList;
  }

  public String getOrderUserName() {
    return this.orderUserName;
  }

  public void setOrderUserName(String orderUserName) {
    this.orderUserName = orderUserName;
  }

  public int getRebateTotal() {
    return this.rebateTotal;
  }

  public void setRebateTotal(int rebateTotal) {
    this.rebateTotal = rebateTotal;
  }

  public String getOrderUserRemark() {
    return this.orderUserRemark;
  }

  public void setOrderUserRemark(String orderUserRemark) {
    this.orderUserRemark = orderUserRemark;
  }

  public String getOrderUserMobile() {
    return this.orderUserMobile;
  }

  public void setOrderUserMobile(String orderUserMobile) {
    this.orderUserMobile = orderUserMobile;
  }

  public String getOrderUserEmail() {
    return this.orderUserEmail;
  }

  public void setOrderUserEmail(String orderUserEmail) {
    this.orderUserEmail = orderUserEmail;
  }

  public String getVerifyUrl() {
    return this.verifyUrl;
  }

  public void setVerifyUrl(String verifyUrl) {
    this.verifyUrl = verifyUrl;
  }

}