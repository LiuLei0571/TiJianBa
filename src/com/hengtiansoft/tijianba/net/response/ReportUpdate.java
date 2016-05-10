 
package com.hengtiansoft.tijianba.net.response;

import java.io.File;

import com.google.gson.annotations.SerializedName;

 
public class ReportUpdate {
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
  private int attachmentIdList;
  @SerializedName("attachments")
  private File[] attachments;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getOrgName() {
    return orgName;
  }
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  public String getExamTime() {
    return examTime;
  }
  public void setExamTime(String examTime) {
    this.examTime = examTime;
  }
  public int getSrc() {
    return src;
  }
  public void setSrc(int src) {
    this.src = src;
  }
  public int getAttachmentIdList() {
    return attachmentIdList;
  }
  public void setAttachmentIdList(int attachmentIdList) {
    this.attachmentIdList = attachmentIdList;
  }
  public File[] getAttachments() {
    return attachments;
  }
  public void setAttachments(File[] attachments) {
    this.attachments = attachments;
  }
  
}
