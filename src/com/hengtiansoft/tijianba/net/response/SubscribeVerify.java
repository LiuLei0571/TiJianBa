package com.hengtiansoft.tijianba.net.response;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class SubscribeVerify implements Serializable {
  @SerializedName("companyDaysList")
  private ArrayList<String> companyDaysList;
  
  @SerializedName("menuId")
  private int menuId;

  @SerializedName("isFamily")
  private boolean isFamily;

  @SerializedName("logo")
  private String logo;

  @SerializedName("bookServerId")
  private int bookServerId;

  @SerializedName("examType")
  private int examType;

  @SerializedName("orgNum")
  private int orgNum;

  @SerializedName("cityOrgList")
  private ArrayList<OrgListOfCity> cityOrgList;

  @SerializedName("testerName")
  private String testerName;

  @SerializedName("testerGender")
  private int testerGender;

  @SerializedName("testerMarried")
  private boolean testerMarried;

  @SerializedName("testerIDNumber")
  private String testerIDNumber;

  @SerializedName("testerMobile")
  private String testerMobile;

  @SerializedName("contactName")
  private String contactName;

  @SerializedName("contactMobile")
  private String contactMobile;

  @SerializedName("contactPostAddr")
  private String contactPostAddr;

  @SerializedName("contactZipCode")
  private String contactZipCode;

  @SerializedName("orgId")
  private int orgId;

  @SerializedName("testDay")
  private String testDay;

  @SerializedName("agreeUploadReport")
  private boolean agreeUploadReport;

  @SerializedName("testEndDate")
  private String testEndDate;

  private String menuName;
  private String menuType;
  private String menuSuitPeo;
  private String menuSuitGender;
  private String orgName;
  private String testStartDate;
  private String workTime;
  
  public ArrayList<String> getCompanyDaysList() {
    return this.companyDaysList;
  }

  public void setCompanyDaysList(ArrayList<String> companyDaysList) {
  this.companyDaysList = companyDaysList;}
  
  
  
  public int getMenuId() {
    return menuId;
  }

  public void setMenuId(int menuId) {
    this.menuId = menuId;
  }

  public int getBookServerId() {
    return bookServerId;
  }

  public void setBookServerId(int bookServerId) {
    this.bookServerId = bookServerId;
  }

  public int getExamType() {
    return examType;
  }

  public void setExamType(int examType) {
    this.examType = examType;
  }

  public String getTesterName() {
    return testerName;
  }

  public void setTesterName(String testerName) {
    this.testerName = testerName;
  }

  public int getTesterGender() {
    return testerGender;
  }

  public void setTesterGender(int testerGender) {
    this.testerGender = testerGender;
  }

  public boolean isTesterMarried() {
    return testerMarried;
  }

  public void setTesterMarried(boolean testerMarried) {
    this.testerMarried = testerMarried;
  }

  public String getTesterIDNumber() {
    return testerIDNumber;
  }

  public void setTesterIDNumber(String testerIDNumber) {
    this.testerIDNumber = testerIDNumber;
  }

  public String getTesterMobile() {
    return testerMobile;
  }

  public void setTesterMobile(String testerMobile) {
    this.testerMobile = testerMobile;
  }

  public String getContactName() {
    return contactName;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  public String getContactMobile() {
    return contactMobile;
  }

  public void setContactMobile(String contactMobile) {
    this.contactMobile = contactMobile;
  }

  public String getContactPostAddr() {
    return contactPostAddr;
  }

  public void setContactPostAddr(String contactPostAddr) {
   this.contactPostAddr = contactPostAddr;
  }

  public String getContactZipCode() {
    return contactZipCode;
  }

  public void setContactZipCode(String contactZipCode) {
    this.contactZipCode = contactZipCode;
  }

  public ArrayList<OrgListOfCity> getCityOrgList() {
    return cityOrgList;
  }

  public void setCityOrgList(ArrayList<OrgListOfCity> cityOrgList) {
    this.cityOrgList = cityOrgList;
  }

  public boolean isFamily() {
    return isFamily;
  }

  public void setFamily(boolean isFamily) {
    this.isFamily = isFamily;
  }

  public int getOrgId() {
    return orgId;
  }

  public void setOrgId(int orgId) {
    this.orgId = orgId;
  }

  public String getTestDay() {
    return testDay;
  }

  public void setTestDay(String testDay) {
    this.testDay = testDay;
  }

  public String getTestStartDate() {
    return testStartDate;
  }

  public void setTestStartDate(String testStartDate) {
    this.testStartDate = testStartDate;
  }

  public boolean isAgreeUploadReport() {
    return agreeUploadReport;
  }

  public void setAgreeUploadReport(boolean agreeUploadReport) {
    this.agreeUploadReport = agreeUploadReport;
  }

  public String getMenuName() {
    return this.menuName;
  }

  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }

  public String getMenuType() {
    return this.menuType;
  }

  public void setMenuType(String menuType) {
    this.menuType = menuType;
  }

  public String getMenuSuitPeo() {
    return this.menuSuitPeo;
  }

  public void setMenuSuitPeo(String menuSuitPeo) {
    this.menuSuitPeo = menuSuitPeo;
  }

  public String getMenuSuitGender() {
    return this.menuSuitGender;
  }

  public void setMenuSuitGender(String menuSuitGender) {
    this.menuSuitGender = menuSuitGender;
  }

  public String getOrgName() {
    return this.orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }

  public int getOrgNum() {
    return this.orgNum;
  }

  public void setOrgNum(int orgNum) {
    this.orgNum = orgNum;
  }

  public String getLogo() {
    return this.logo;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }

  public String getTestEndDate() {
    return this.testEndDate;
  }

  public void setTestEndDate(String testEndDate) {
    this.testEndDate = testEndDate;
  }

  public String getWorkTime() {
    return this.workTime;
  }

  public void setWorkTime(String workTime) {
    this.workTime = workTime;
  }

}
