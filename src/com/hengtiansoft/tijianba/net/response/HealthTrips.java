package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

public class HealthTrips {
  @SerializedName("id")
  private int id;
  @SerializedName("title")
  private String title;

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
