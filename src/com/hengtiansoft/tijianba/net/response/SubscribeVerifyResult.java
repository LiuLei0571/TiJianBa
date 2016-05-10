package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

public class SubscribeVerifyResult extends ResponseResult {
  @SerializedName("data")
  private SubscribeVerify subscribeVerify = new SubscribeVerify();

  public SubscribeVerify getSubscribeVerify() {
    return subscribeVerify;
  }

  public void setSubscribeVerify(SubscribeVerify subscribeVerify) {
    this.subscribeVerify = subscribeVerify;
  }

}
