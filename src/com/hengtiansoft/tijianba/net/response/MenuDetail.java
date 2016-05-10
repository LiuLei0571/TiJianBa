package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * com.hengtiansoft.tijianba.activity.BaseActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 19, 2014 16:52:17 AM
 */
public class MenuDetail extends Menu {
  // @SerializedName("id")
  // private int id;
  @SerializedName("suitGender")
  private int suitGender;
  @SerializedName("feature")
  private String feature;
  @SerializedName("detail")
  private String detail;
  @SerializedName("customerNeedKnow")
  private String customerNeedKnow;
  @SerializedName("purchaseUserNum")
  private int purchaseUserNum;
  @SerializedName("purchaseNum")
  private int purchaseNum;
  @SerializedName("orgNum")
  private int orgNum;
  @SerializedName("nameStrong")
  private String nameStrong;
  @SerializedName("suitMarried")
  private int suitMarried;
  @SerializedName("menuTypeId")
  private int menuTypeId;
  @SerializedName("totalNum")
  private int totalNum;
  @SerializedName("orgList")
  private ArrayList<OrgOfMenu> orgList;
  @SerializedName("contactPhone")
  private String contactPhone;

  //
  // public int getOrgId() {
  // return this.id;
  // }
  //
  // public void setOrgId(int id) {
  // this.id = id;
  // }
  //
  // public int getId() {
  // return this.id;
  // }
  //
  // public void setId(int id) {
  // this.id = id;
  // }

  public int getSuitGender() {
    return this.suitGender;
  }

  public void setSuitGender(int suitGender) {
    this.suitGender = suitGender;
  }

  public String getFeature() {
    return this.feature;
  }

  public void setFeature(String feature) {
    this.feature = feature;
  }

  public String getDetail() {
    return this.detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public String getCustomerNeedKnow() {
    return this.customerNeedKnow;
  }

  public void setCustomerNeedKnow(String customerNeedKnow) {
    this.customerNeedKnow = customerNeedKnow;
  }

  public int getPurchaseUserNum() {
    return this.purchaseUserNum;
  }

  public void setPurchaseUserNum(int purchaseUserNum) {
    this.purchaseUserNum = purchaseUserNum;
  }

  public int getPurchaseNum() {
    return this.purchaseNum;
  }

  public void setPurchaseNum(int purchaseNum) {
    this.purchaseNum = purchaseNum;
  }

  public int getOrgNum() {
    return orgNum;
  }

  public void setOrgNum(int orgNum) {
    this.orgNum = orgNum;
  }

  public ArrayList<OrgOfMenu> getOrgList() {
    return this.orgList;
  }

  public void setOrgList(ArrayList<OrgOfMenu> orgList) {
    this.orgList = orgList;
  }

  public String getNameStrong() {
    return this.nameStrong;
  }

  public void setNameStrong(String nameStrong) {
    this.nameStrong = nameStrong;
  }

  public int getSuitMarried() {
    return this.suitMarried;
  }

  public void setSuitMarried(int suitMarried) {
    this.suitMarried = suitMarried;
  }

  public int getMenuTypeId() {
    return this.menuTypeId;
  }

  public void setMenuTypeId(int menuTypeId) {
    this.menuTypeId = menuTypeId;
  }

  public int getTotalNum() {
    return this.totalNum;
  }

  public void setTotalNum(int totalNum) {
    this.totalNum = totalNum;
  }

  public String getContactPhone() {
    return this.contactPhone;
  }

  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
  }

}
