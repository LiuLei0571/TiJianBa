package com.hengtiansoft.tijianba.net.response;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class BookData implements Serializable {
  @SerializedName("bookletId")
  private int bookletId;
  @SerializedName("dataTime")
  private String dataTime;
  @SerializedName("value")
  private float value;

  public float getValue() {
    return this.value;
  }

  public void setValue(float value) {
    this.value = value;
  }

  public int getBookletId() {
    return this.bookletId;
  }

  public void setBookletId(int bookletId) {
    this.bookletId = bookletId;
  }

  public String getDataTime() {
    return this.dataTime;
  }

  public void setDataTime(String dataTime) {
    this.dataTime = dataTime;
  }

}
