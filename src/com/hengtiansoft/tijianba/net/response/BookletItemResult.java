package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class BookletItemResult extends ResponseResult {
  @SerializedName("data")
  private ArrayList<BookletItem> bookletItemList = new ArrayList<BookletItem>();

  public ArrayList<BookletItem> getBookletItemList() {
    return this.bookletItemList;
  }

  public void setBookletItemList(ArrayList<BookletItem> bookletItemList) {
    this.bookletItemList = bookletItemList;
  }

}