package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class OrganizationDetail {
  @SerializedName("id")
  private int id;
  @SerializedName("name")
  private String name;
  @SerializedName("region")
  private String region;
  @SerializedName("address")
  private String address;
  @SerializedName("workTime")
  private String workTime;
  @SerializedName("advanceDays")
  private int advanceDays;
  @SerializedName("introduce")
  private String introduce;
  @SerializedName("howToGo")
  private String howToGo;
  @SerializedName("canUploadReport")
  private boolean canUploadReport;
  @SerializedName("pic")
  private String pic;
  @SerializedName("orgType")
  private int orgType;
  @SerializedName("brandName")
  private String brandName;
  @SerializedName("menuNum")
  private int menuNum;
  @SerializedName("regionCode")
  private String regionCode;
  @SerializedName("cityCode")
  private String cityCode;
  @SerializedName("provincecode")
  private String provincecode;
  @SerializedName("menuList")
  private ArrayList<Menu> menuList;
  @SerializedName("isSelected")
  private boolean isSelected;
  @SerializedName("contactPhone")
  private String contactPhone;

  public int getOrgId() {
    return this.id;
  }

  public void setOrgId(int id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRegion() {
    return this.region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getWorkTime() {
    return this.workTime;
  }

  public void setWorkTime(String workTime) {
    this.workTime = workTime;
  }

  public int getAdvanceDays() {
    return this.advanceDays;
  }

  public void setAdvanceDays(int advanceDays) {
    this.advanceDays = advanceDays;
  }

  public String getIntroduce() {
    return this.introduce;
  }

  public void setIntroduce(String introduce) {
    this.introduce = introduce;
  }

  public String getHowToGo() {
    return this.howToGo;
  }

  public void setHowToGo(String howToGo) {
    this.howToGo = howToGo;
  }

  public boolean isCanUploadReport() {
    return this.canUploadReport;
  }

  public void setCanUploadReport(boolean canUploadReport) {
    this.canUploadReport = canUploadReport;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getPic() {
    return this.pic;
  }

  public void setPic(String pic) {
    this.pic = pic;
  }

  public int getOrgType() {
    return this.orgType;
  }

  public void setOrgType(int orgType) {
    this.orgType = orgType;
  }

  public String getBrandName() {
    return this.brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  public int getMenuNum() {
    return this.menuNum;
  }

  public void setMenuNum(int menuNum) {
    this.menuNum = menuNum;
  }

  public String getRegionCode() {
    return this.regionCode;
  }

  public void setRegionCode(String regionCode) {
    this.regionCode = regionCode;
  }

  public String getCityCode() {
    return this.cityCode;
  }

  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  public String getProvincecode() {
    return this.provincecode;
  }

  public void setProvincecode(String provincecode) {
    this.provincecode = provincecode;
  }

  public ArrayList<Menu> getMenuList() {
    return this.menuList;
  }

  public void setMenuList(ArrayList<Menu> menuList) {
    this.menuList = menuList;
  }

  public boolean isSelected() {
    return this.isSelected;
  }

  public void setSelected(boolean isSelected) {
    this.isSelected = isSelected;
  }

  public String getContactPhone() {
    return this.contactPhone;
  }

  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
  }

  @Override
  public String toString() {
    // TODO Auto-generated method stub
    return "id:" + id + " name:" + name + region + advanceDays + cityCode + address;
  }

}