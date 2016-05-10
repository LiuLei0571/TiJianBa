package com.hengtiansoft.tijianba.net.response;


import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class BookDataList {
  @SerializedName("id")
  private int id  ;
  
  public int getId() {
    return this.id;
  }
  public void setId(int id) {
 this.id = id;}
  
  @SerializedName("dataList")
  private ArrayList<BookData> dataList ;

  public ArrayList<BookData> getDataList() {
    return this.dataList;
  }

  public void setDataList(ArrayList<BookData> dataList) {
  this.dataList = dataList;}
  
 
  
}
