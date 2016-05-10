package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * com.hengtiansoft.tijianba.net.response.CartDetail
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 23, 2014 11:21:00 AM
 */
public class CartDetail {
  @SerializedName("id")
  private int id;
  @SerializedName("cartDetailId")
  private int cartDetailId;
  @SerializedName("logo")
  private String logo;
  @SerializedName("name")
  private String name;
  @SerializedName("menuTypeName")
  private String menuTypeName;
  @SerializedName("suitOrgNum")
  private int suitOrgNum;
  @SerializedName("suitObject")
  private int suitObject;
  @SerializedName("marketPrice")
  private int marketPrice;
  @SerializedName("familyPrice")
  private int familyPrice;
  @SerializedName("platformPrice")
  private int platformPrice;
  @SerializedName("rebate")
  private int rebate;
  @SerializedName("isFamily")
  private boolean isFamily;
  @SerializedName("number")
  private int number;
  @SerializedName("totalNum")
  private int totalNum;
  @SerializedName("purchaseNum")
  private int purchaseNum;
  private boolean isSelect = false;
  private boolean isEdit = false;

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCartDetailId() {
    return this.cartDetailId;
  }

  public void setCartDetailId(int cartDetailId) {
    this.cartDetailId = cartDetailId;
  }

  public String getLogo() {
    return this.logo;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMenuTypeName() {
    return this.menuTypeName;
  }

  public void setMenuTypeName(String menuTypeName) {
    this.menuTypeName = menuTypeName;
  }

  public int getSuitOrgNum() {
    return this.suitOrgNum;
  }

  public void setSuitOrgNum(int suitOrgNum) {
    this.suitOrgNum = suitOrgNum;
  }

  public int getSuitObject() {
    return this.suitObject;
  }

  public void setSuitObject(int suitObject) {
    this.suitObject = suitObject;
  }

  public int getMarketPrice() {
    return this.marketPrice;
  }

  public void setMarketPrice(int marketPrice) {
    this.marketPrice = marketPrice;
  }

  public int getFamilyPrice() {
    return this.familyPrice;
  }

  public void setFamilyPrice(int familyPrice) {
    this.familyPrice = familyPrice;
  }

  public int getPlatformPrice() {
    return this.platformPrice;
  }

  public void setPlatformPrice(int platformPrice) {
    this.platformPrice = platformPrice;
  }

  public int getRebate() {
    return this.rebate;
  }

  public void setRebate(int rebate) {
    this.rebate = rebate;
  }

  public boolean isFamily() {
    return this.isFamily;
  }

  public void setFamily(boolean isFamily) {
    this.isFamily = isFamily;
  }

  public int getNumber() {
    return this.number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public boolean isSelect() {
    return this.isSelect;
  }

  public void setSelect(boolean isSelect) {
    this.isSelect = isSelect;
  }

  public boolean isEdit() {
    return this.isEdit;
  }

  public void setEdit(boolean isEdit) {
    this.isEdit = isEdit;
  }

  public int getTotalNum() {
    return this.totalNum;
  }

  public void setTotalNum(int totalNum) {
    this.totalNum = totalNum;
  }

  public int getPurchaseNum() {
    return this.purchaseNum;
  }

  public void setPurchaseNum(int purchaseNum) {
    this.purchaseNum = purchaseNum;
  }

}