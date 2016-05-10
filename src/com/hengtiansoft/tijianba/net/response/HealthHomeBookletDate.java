package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

public class HealthHomeBookletDate {
  @SerializedName("dataTime")
  private String dataTime;
  @SerializedName("value")
  private String value;
  public String getDataTime() {
    return this.dataTime;
  }
  public void setDataTime(String dataTime) {
    this.dataTime = dataTime;
  }
  public String getValue() {
    return this.value;
  }
  public void setValue(String value) {
  this.value = value;}
}
