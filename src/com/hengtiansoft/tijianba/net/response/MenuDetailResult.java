package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

/**
 * com.hengtiansoft.tijianba.model.RemoteDataManager
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 5, 2014 16:21:32 PM
 */
public class MenuDetailResult extends ResponseResult {
  @SerializedName("data")
  private MenuDetail menuDetail = new MenuDetail();

  public MenuDetail getMenuDetail() {
    return this.menuDetail;
  }

  public void setMenuDetail(MenuDetail menuDetail) {
    this.menuDetail = menuDetail;
  }
}
