package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * com.hengtiansoft.tijianba.net.response.PackedBuyNowRequest
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 26, 2014 6:34:50 PM
 */
public class PackedBuyNowRequest {
  @SerializedName("payType")
  private int payType;
  @SerializedName("payChannel")
  private int payChannel;
  @SerializedName("orderUserName")
  private String orderUserName;
  @SerializedName("orderUserMobile")
  private String orderUserMobile;
  @SerializedName("orderUserEmail")
  private String orderUserEmail;
  @SerializedName("orderUserRemark")
  private String orderUserRemark;
  @SerializedName("bonusMoney")
  private int bonusMoney;
  @SerializedName("menuList")
  private ArrayList<BuyMenu> menus = new ArrayList<BuyMenu>();

  public int getPayType() {
    return this.payType;
  }

  public void setPayType(int payType) {
    this.payType = payType;
  }

  public int getPayChannel() {
    return this.payChannel;
  }

  public void setPayChannel(int payChannel) {
    this.payChannel = payChannel;
  }

  public String getOrderUserName() {
    return this.orderUserName;
  }

  public void setOrderUserName(String orderUserName) {
    this.orderUserName = orderUserName;
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

  public String getOrderUserRemark() {
    return this.orderUserRemark;
  }

  public void setOrderUserRemark(String orderUserRemark) {
    this.orderUserRemark = orderUserRemark;
  }

  public ArrayList<BuyMenu> getMenus() {
    return this.menus;
  }

  public void setMenus(ArrayList<BuyMenu> menus) {
    this.menus = menus;
  }

  public int getBonusMoney() {
    return this.bonusMoney;
  }

  public void setBonusMoney(int bonusMoney) {
    this.bonusMoney = bonusMoney;
  }

}
