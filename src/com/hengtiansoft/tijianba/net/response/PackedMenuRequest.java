package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

/**
 * com.hengtiansoft.tijianba.net.response.PackedMenuRequest
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 7, 2014 2:29:19 PM
 */
public class PackedMenuRequest {
  @SerializedName("pageNo")
  private int pageNo;
  @SerializedName("pageSize")
  private int pageSize;
  @SerializedName("refreshTime")
  private String refreshTime;
  @SerializedName("isFamily")
  private boolean isFamily;
  @SerializedName("cityCode")
  private String cityCode;
  @SerializedName("orderType")
  private int orderType;
  @SerializedName("orderDesc")
  private int orderDesc;
  @SerializedName("menuTypeId")
  private int menuTypeId;
  @SerializedName("name")
  private String name;
  @SerializedName("isRecommendList")
  private boolean isRecommendList;

  public int getPageNo() {
    return this.pageNo;
  }

  public void setPageNo(int pageNo) {
    this.pageNo = pageNo;
  }

  public int getPageSize() {
    return this.pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public String getRefreshTime() {
    return this.refreshTime;
  }

  public void setRefreshTime(String refreshTime) {
    this.refreshTime = refreshTime;
  }

  public String getCityCode() {
    return this.cityCode;
  }

  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  public int getOrderType() {
    return this.orderType;
  }

  public void setOrderType(int orderType) {
    this.orderType = orderType;
  }

  public int getOrderDesc() {
    return this.orderDesc;
  }

  public void setOrderDesc(int orderDesc) {
    this.orderDesc = orderDesc;
  }

  public int getMenuTypeId() {
    return this.menuTypeId;
  }

  public void setMenuTypeId(int menuTypeId) {
    this.menuTypeId = menuTypeId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isFamily() {
    return this.isFamily;
  }

  public void setFamily(boolean isFamily) {
    this.isFamily = isFamily;
  }

  public boolean isRecommendList() {
    return this.isRecommendList;
  }

  public void setRecommendList(boolean isRecommendList) {
    this.isRecommendList = isRecommendList;
  }

}
