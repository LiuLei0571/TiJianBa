package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * com.hengtiansoft.tijianba.net.response.HotMenu
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Jan 5, 2015 9:09:14 PM
 */
public class HotMenu {
  @SerializedName("id")
  private int id;
  @SerializedName("img")
  private String img;
  @SerializedName("menuId")
  private int menuId;

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getImg() {
    return this.img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public int getMenuId() {
    return this.menuId;
  }

  public void setMenuId(int menuId) {
    this.menuId = menuId;
  }

}