package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class ReportDetail {
  @SerializedName("id")
  private int id;
  @SerializedName("name")
  private String name;
  @SerializedName("orgName")
  private String orgName;
  @SerializedName("examTime")
  private String examTime;
  @SerializedName("createTime")
  private String createTime; 
  @SerializedName("attachmentType")
  private int attachmentType;
  @SerializedName("src")
  private int src;
  @SerializedName("attachmentList")
  private ArrayList<ReportItem> attachmentList;
  public int getId() {
    return this.id;
  }
  public void setId(int id) {
  this.id = id;}
  
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
  this.name = name;}
  
  public String getOrgName() {
    return this.orgName;
  }
  public void setOrgName(String orgName) {
  this.orgName = orgName;}
  
  public String getExamTime() {
    return this.examTime;
  }
  public void setExamTime(String examTime) {
  this.examTime = examTime;}
  
  public String getCreateTime() {
    return this.createTime;
  }
  public void setCreateTime(String createTime) {
  this.createTime = createTime;}
  
  public int getAttachmentType() {
    return this.attachmentType;
  }
  public void setAttachmentType(int attachmentType) {
  this.attachmentType = attachmentType;}
  
  public int getSrc() {
    return this.src;
  }
  public void setSrc(int src) {
  this.src = src;}
  
  public ArrayList<ReportItem> getAttachmentList() {
    return this.attachmentList;
  }
  public void setAttachmentList(ArrayList<ReportItem> attachmentList) {
  this.attachmentList = attachmentList;}
  
  
  
}
