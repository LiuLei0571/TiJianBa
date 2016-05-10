package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * com.hengtiansoft.tijianba.net.response.PackedDeleteCartRequest
 * 
 * @author flychen <br/>
 *         create at Jan 13, 2015 1:21:59 PM
 */
public class PackedDeleteCartRequest {
  @SerializedName("cartDetailIdList")
  private ArrayList<Integer> CartDeleteList = new ArrayList<Integer>();

  public ArrayList<Integer> getCartDeleteList() {
    return this.CartDeleteList;
  }

  public void setCartDeleteList(ArrayList<Integer> cartDeleteList) {
    this.CartDeleteList = cartDeleteList;
  }

}
