package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

/**
 * com.hengtiansoft.tijianba.activity.BaseActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 19, 2014 16:52:17 AM
 */
public class Menu {

  @SerializedName("id")
  private int id;
  @SerializedName("logo")
  private String logo;
  @SerializedName("name")
  private String name;
  @SerializedName("menuTypeName")
  private String menuTypeName;
  @SerializedName("suitOrgNum")
  private int suitOrgNum;
  @SerializedName("suitObject")
  private String suitObject;
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
  @SerializedName("menuId")
  private int menuId;
  @SerializedName("num")
  private int num;
  @SerializedName("price")
  private int price;
  @SerializedName("menuName")
  private String menuName;
  @SerializedName("cardNo")
  private String cardNo; // for paySuccess interface and my menu interface only
  @SerializedName("cardPassword")
  private String cardPassword; // for paySuccess interface and my menu interface
                               // only
  @SerializedName("examType")
  // for my menu interface only
  private int examType;
  @SerializedName("status")
  private int status;

  public int getStatus() {
    return this.status;
  }

  public void setStatus(int status) {
 this.status = status;}
  

  
  public int getPrice() {
    return this.price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  private boolean isEdit = false;
  private int buyNum = 0;
  private boolean isSelect = false;

  public int getMenuId() {
    return this.menuId;
  }

  public void setMenuId(int menuId) {
    this.menuId = menuId;
  }

  public int getNum() {
    return this.num;
  }

  public void setNum(int num) {
    this.num = num;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
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

  public String getSuitObject() {
    return this.suitObject;
  }

  public void setSuitObject(String suitObject) {
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

  public boolean isEdit() {
    return this.isEdit;
  }

  public void setEdit(boolean isEdit) {
    this.isEdit = isEdit;
  }

  public int getBuyNum() {
    return this.buyNum;
  }

  public void setBuyNum(int buyNum) {
    this.buyNum = buyNum;
  }

  public boolean isFamily() {
    return this.isFamily;
  }

  public void setFamily(boolean isFamily) {
    this.isFamily = isFamily;
  }

  public boolean isSelect() {
    return this.isSelect;
  }

  public void setSelect(boolean isSelect) {
    this.isSelect = isSelect;
  }

  public String getMenuName() {
    return this.menuName;
  }

  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }

  public String getCardNo() {
    return cardNo;
  }

  public void setCardNo(String cardNo) {
    this.cardNo = cardNo;
  }

  public String getCardPassword() {
    return cardPassword;
  }

  public void setCardPassword(String cardPassword) {
    this.cardPassword = cardPassword;
  }

  public int getExamType() {
    return this.examType;
  }

  public void setExamType(int examType) {
    this.examType = examType;
  }

}