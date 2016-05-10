package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

public class SubscribeRecord {
  @SerializedName("menuName")
  private String menuName;
  @SerializedName("orgName")
  private String orgName;
  @SerializedName("testerName")
  private String testerName;
  @SerializedName("testDay")
  private String testDay;
  @SerializedName("bookServerId")
  private int bookServerId;

  public String getMenuName() {
    return this.menuName;
  }

  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }

  public String getOrgName() {
    return this.orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }

  public String getTesterName() {
    return this.testerName;
  }

  public void setTesterName(String testerName) {
    this.testerName = testerName;
  }

  public String getTestDay() {
    return this.testDay;
  }

  public void setTestDay(String testDay) {
    this.testDay = testDay;
  }

  public int getBookServerId() {
    return this.bookServerId;
  }

  public void setBookServerId(int bookServerId) {
    this.bookServerId = bookServerId;
  }

}