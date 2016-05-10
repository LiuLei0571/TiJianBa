package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class BookletTypeResult extends ResponseResult {

  @SerializedName("data")
  private ArrayList<HealthHomeBookletDate> bookletTypeList = new ArrayList<HealthHomeBookletDate>();

  public ArrayList<HealthHomeBookletDate> getBookletTypeList() {
    return this.bookletTypeList;
  }
  public void setBookletTypeList(ArrayList<HealthHomeBookletDate> bookletTypeList) {
    this.bookletTypeList = bookletTypeList;
  }

}