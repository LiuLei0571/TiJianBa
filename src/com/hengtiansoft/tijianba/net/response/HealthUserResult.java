package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

public class HealthUserResult extends ResponseResult {
  @SerializedName("data")
  private int suitObject;

  public int getSuitObject() {
    return this.suitObject;
  }

  public void setSuitObject(int suitObject) {
    this.suitObject = suitObject;
  }

}
