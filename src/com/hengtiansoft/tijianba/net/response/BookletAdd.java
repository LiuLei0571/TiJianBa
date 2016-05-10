package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class BookletAdd {
  
   @SerializedName("id")
   private int id;
   @SerializedName("value")
   private float value;
   @SerializedName("dataTime")
   private String dataTime;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public float getValue() {
    return value;
  }
  public void setValue(float value) {
    this.value = value;
  }
  public String getDataTime() {
    return dataTime;
  }
  public void setDataTime(String dataTime) {
    this.dataTime = dataTime;
  }
  
  }
 