package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * com.hengtiansoft.tijianba.net.response.CartListResult
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 23, 2014 12:47:22 PM
 */
public class CartListResult extends ResponseResult {
  @SerializedName("data")
  private ArrayList<CartDetail> cartDetails = new ArrayList<CartDetail>();

  public ArrayList<CartDetail> getCartDetails() {
    return this.cartDetails;
  }

  public void setCartDetails(ArrayList<CartDetail> cartDetails) {
    this.cartDetails = cartDetails;
  }

}
