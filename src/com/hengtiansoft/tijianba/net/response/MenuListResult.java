package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * com.hengtiansoft.tijianba.model.RemoteDataManager
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 5, 2014 16:18:32 PM
 */
public class MenuListResult extends ResponseResult {
  @SerializedName("data")
  private ArrayList<Menu> menus = new ArrayList<Menu>();

  public ArrayList<Menu> getMenus() {
    return this.menus;
  }

  public void setMenus(ArrayList<Menu> menus) {
    this.menus = menus;
  }

}
