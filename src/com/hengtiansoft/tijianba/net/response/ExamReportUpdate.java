package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class ExamReportUpdate {
  @SerializedName("id")
  private int id;
  @SerializedName("name")
  private String name;
  @SerializedName("orgName")
  private String orgName;
  @SerializedName("examTime")
  private String examTime;
  @SerializedName("src")
  private int src;
  @SerializedName("attachmentIdList")
  private ArrayList<Integer> attachmentIdList;

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOrgName() {
    return this.orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }

  public String getExamTime() {
    return this.examTime;
  }

  public void setExamTime(String examTime) {
    this.examTime = examTime;
  }

  public int getSrc() {
    return this.src;
  }

  public void setSrc(int src) {
    this.src = src;
  }

  public ArrayList<Integer> getAttachmentIdList() {
    return this.attachmentIdList;
  }

  public void setAttachmentIdList(ArrayList<Integer> attachmentIdList) {
    this.attachmentIdList = attachmentIdList;
  }

}
