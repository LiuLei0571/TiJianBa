package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class HealthHomeBookletResult extends ResponseResult {
  @SerializedName("data")
  private ArrayList<HealthHomeBookletItem> healthHomeBookletList = new ArrayList<HealthHomeBookletItem>();

  public ArrayList<HealthHomeBookletItem> getHealthHomeBookletList() {
    return this.healthHomeBookletList;
  }

  public void setHealthHomeBookletList(ArrayList<HealthHomeBookletItem> healthHomeBookletList) {
    this.healthHomeBookletList = healthHomeBookletList;
  }
}
