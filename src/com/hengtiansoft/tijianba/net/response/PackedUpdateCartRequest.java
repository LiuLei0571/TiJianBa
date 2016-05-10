package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * com.hengtiansoft.tijianba.net.response.PackedUpdateCartRequest
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 26, 2014 3:57:26 PM
 */
public class PackedUpdateCartRequest {
  @SerializedName("menuList")
  private ArrayList<CartDetailUpdate> menus = new ArrayList<CartDetailUpdate>();

  public ArrayList<CartDetailUpdate> getMenus() {
    return this.menus;
  }

  public void setMenus(ArrayList<CartDetailUpdate> menus) {
    this.menus = menus;
  }

}
