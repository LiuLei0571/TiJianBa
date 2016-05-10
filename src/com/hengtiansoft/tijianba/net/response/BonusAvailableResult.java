package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * com.hengtiansoft.tijianba.net.response.BonusAvailableResult
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Jan 15, 2015 12:54:15 PM
 */
public class BonusAvailableResult extends ResponseResult {

  @SerializedName("data")
  private int bonusAvailable;

  public int getBonusAvailable() {
    return this.bonusAvailable;
  }

  public void setBonusAvailable(int bonusAvailable) {
    this.bonusAvailable = bonusAvailable;
  }

}
