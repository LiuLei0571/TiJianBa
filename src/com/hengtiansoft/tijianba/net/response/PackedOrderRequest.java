package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

public class PackedOrderRequest {
  @SerializedName("pageNo")
  private int pageNo;
  @SerializedName("pageSize")
  private int pageSize;
  @SerializedName("refreshTime")
  private String refreshTime;
  @SerializedName("status")
  private int status;

  public int getPageNo() {
    return pageNo;
  }

  public void setPageNo(int pageNo) {
    this.pageNo = pageNo;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public String getRefreshTime() {
    return refreshTime;
  }

  public void setRefreshTime(String refreshTime) {
    this.refreshTime = refreshTime;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

}
