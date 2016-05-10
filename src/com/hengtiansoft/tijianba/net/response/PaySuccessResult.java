package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

public class PaySuccessResult extends ResponseResult {
  @SerializedName("data")
  private PaySuccess paySuccess = new PaySuccess();

  public PaySuccess getPaySuccess() {
    return paySuccess;
  }

  public void setPaySuccess(PaySuccess paySuccess) {
    this.paySuccess = paySuccess;
  }

}
