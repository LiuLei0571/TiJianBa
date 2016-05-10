package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * com.hengtiansoft.tijianba.net.response.BuyResult
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 26, 2014 6:54:00 PM
 */
public class Advertise {
  @SerializedName("id")
  private int id;
  @SerializedName("title")
  private String title;
  @SerializedName("img")
  private String img;
  @SerializedName("value")
  private String value;
  @SerializedName("type")
  private int type;

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImg() {
    return this.img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public int getType() {
    return this.type;
  }

  public void setType(int type) {
    this.type = type;
  }

}