package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

/**
 * com.hengtiansoft.tijianba.net.response.PackedOrgRequest
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 12, 2014 1:03:27 PM
 */
public class PackedOrgRequest {
  @SerializedName("cityCode")
  private String cityCode;
  @SerializedName("pageNo")
  private int pageNo;
  @SerializedName("pageSize")
  private int pageSize;
  @SerializedName("refreshTime")
  private String refreshTime;
  @SerializedName("name")
  private String name;
  @SerializedName("brandId")
  private int brandId;
  @SerializedName("orgType")
  private int orgType;
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

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getBrandId() {
    return this.brandId;
  }

  public void setBrandId(int brandId) {
    this.brandId = brandId;
  }

  public boolean isRecommendList() {
    return this.isRecommendList;
  }

  public void setRecommendList(boolean isRecommendList) {
    this.isRecommendList = isRecommendList;
  }

  public int getOrgType() {
    return this.orgType;
  }

  public void setOrgType(int orgType) {
    this.orgType = orgType;
  }

}
