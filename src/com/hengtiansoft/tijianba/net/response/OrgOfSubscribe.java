package com.hengtiansoft.tijianba.net.response;

import java.io.Serializable; 

import com.google.gson.annotations.SerializedName;

public class OrgOfSubscribe implements Serializable {
  @SerializedName("orgId")
  private int orgId;
  @SerializedName("orgName")
  private String orgName;
  @SerializedName("orgType")
  private int orgType;
  @SerializedName("brandName")
  private String brandName;
  @SerializedName("address")
  private String address;
  @SerializedName("advanceDays")
  private int advanceDays;
  @SerializedName("testStartDate")
  private String testStartDate;
  @SerializedName("workTime")
  private String workTime;
  @SerializedName("canUploadReport")
  private boolean canUploadReport;
  private boolean isSelected;
  
  public boolean isCanUploadReport() {
    return this.canUploadReport;
  }

  public void setCanUploadReport(boolean canUploadReport) {
this.canUploadReport = canUploadReport;}
  public String getTestStartDate() {
    return this.testStartDate;
  }

  public void setTestStartDate(String testStartDate) {
    this.testStartDate = testStartDate;
  }

  public String getWorkTime() {
    return this.workTime;
  }

  public void setWorkTime(String workTime) {
    this.workTime = workTime;
  }

  public int getOrgId() {
    return orgId;
  }

  public void setOrgId(int orgId) {
    this.orgId = orgId;
  }

  public String getOrgName() {
    return orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }

  public int getOrgType() {
    return orgType;
  }

  public void setOrgType(int orgType) {
    this.orgType = orgType;
  }

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getAdvanceDays() {
    return advanceDays;
  }

  public void setAdvanceDays(int advanceDays) {
    this.advanceDays = advanceDays;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean isSelected) {
    this.isSelected = isSelected;
  }

}
