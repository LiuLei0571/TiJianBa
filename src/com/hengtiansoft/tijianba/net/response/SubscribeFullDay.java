package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class SubscribeFullDay extends ResponseResult {
  @SerializedName("data")
  private ArrayList<String> date;

  public ArrayList<String> getDate() {
    return this.date;
  }

  public void setDate(ArrayList<String> date) {
    this.date = date;
  }
}
