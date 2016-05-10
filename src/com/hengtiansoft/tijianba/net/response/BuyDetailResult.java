package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * com.hengtiansoft.tijianba.net.response.BuyDetailResult
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 29, 2014 12:26:10 PM
 */
public class BuyDetailResult extends ResponseResult {

  @SerializedName("data")
  private BuyDetail buyDetail = new BuyDetail();

  public BuyDetail getBuyDetail() {
    return this.buyDetail;
  }

  public void setBuyDetail(BuyDetail buyDetail) {
    this.buyDetail = buyDetail;
  }

}