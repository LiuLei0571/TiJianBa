package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * com.hengtiansoft.tijianba.net.response.MallMenuType
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Jan 7, 2015 5:14:43 PM
 */
public class MallMenuType {
  @SerializedName("id")
  private int id;
  @SerializedName("menuTypeId")
  private int menuTypeId;
  @SerializedName("title")
  private String title;
  @SerializedName("img")
  private String img;
  @SerializedName("type")
  private int type;
  @SerializedName("value")
  private int value;

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

  public int getType() {
    return this.type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getValue() {
    return this.value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public int getMenuTypeId() {
    return this.menuTypeId;
  }

  public void setMenuTypeId(int menuTypeId) {
    this.menuTypeId = menuTypeId;
  }

}
