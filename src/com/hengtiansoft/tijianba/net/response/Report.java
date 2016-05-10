package com.hengtiansoft.tijianba.net.response;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Report implements Serializable {
  @SerializedName("id")
  private int id;
  @SerializedName("name")
  private String name;
  @SerializedName("createTime")
  private String createTime;
  @SerializedName("attachmentType")
  private int attachmentType;
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

  public String getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public int getAttachmentType() {
    return this.attachmentType;
  }

  public void setAttachmentType(int attachmentType) {
  this.attachmentType = attachmentType;}
  

}
