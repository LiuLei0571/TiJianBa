package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * com.hengtiansoft.tijianba.net.response.CartCountResult
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 23, 2014 11:55:31 AM
 */
public class CartCountResult extends ResponseResult {
  @SerializedName("data")
  private CartCount cartCount = new CartCount();

  public class CartCount {
    @SerializedName("number")
    private int number;

    public int getNumber() {
      return this.number;
    }

    public void setNumber(int number) {
      this.number = number;
    }

  }

  public CartCount getCartCount() {
    return this.cartCount;
  }

  public void setCartCount(CartCount cartCount) {
    this.cartCount = cartCount;
  }
}
