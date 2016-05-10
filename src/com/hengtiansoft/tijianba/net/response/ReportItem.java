package com.hengtiansoft.tijianba.net.response;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class ReportItem implements Serializable {
  @SerializedName("attachmentId")
  private  int attachmentId;
  @SerializedName("url")
  private String url;
  @SerializedName("smallUrl")
  private String smallUrl;
  public int getAttachmentId() {
    return this.attachmentId;
  }
  public void setAttachmentId(int attachmentId) {
  this.attachmentId = attachmentId;}
  
  public String getUrl() {
    return this.url;
  }
  public void setUrl(String url) {
 this.url = url;}
  
  public String getSmallUrl() {
    return this.smallUrl;
  }
  public void setSmallUrl(String smallUrl) {
this.smallUrl = smallUrl;}
  
 


}
