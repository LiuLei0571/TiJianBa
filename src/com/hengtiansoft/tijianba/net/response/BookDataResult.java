package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

public class BookDataResult extends ResponseResult {
  @SerializedName("data")
  private BookDataList dataList = new BookDataList();

  public BookDataList getDataList() {
    return this.dataList;
  }

  public void setDataList(BookDataList dataList) {
    this.dataList = dataList;
  }
}