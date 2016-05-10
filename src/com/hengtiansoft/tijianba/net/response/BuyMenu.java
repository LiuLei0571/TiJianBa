package com.hengtiansoft.tijianba.net.response;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * com.hengtiansoft.tijianba.net.response.BuyMenu
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 26, 2014 6:33:57 PM
 */
public class BuyMenu implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 6646216332639201977L;
  @SerializedName("buyNum")
  private int buyNum;
  @SerializedName("menuId")
  private int menuId;
  @SerializedName("isFamily")
  private boolean isFamily;

  public int getBuyNum() {
    return this.buyNum;
  }

  public void setBuyNum(int buyNum) {
    this.buyNum = buyNum;
  }

  public int getMenuId() {
    return this.menuId;
  }

  public void setMenuId(int menuId) {
    this.menuId = menuId;
  }

  public boolean isFamily() {
    return this.isFamily;
  }

  public void setFamily(boolean isFamily) {
    this.isFamily = isFamily;
  }

}