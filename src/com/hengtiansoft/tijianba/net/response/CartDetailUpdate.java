package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * com.hengtiansoft.tijianba.net.response.CartDetailUpdate
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 26, 2014 4:39:10 PM
 */
public class CartDetailUpdate {
  @SerializedName("cartDetailId")
  private int cartDetailId;
  @SerializedName("number")
  private int number;

  public int getCartDetailId() {
    return this.cartDetailId;
  }

  public void setCartDetailId(int cartDetailId) {
    this.cartDetailId = cartDetailId;
  }

  public int getNumber() {
    return this.number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

}