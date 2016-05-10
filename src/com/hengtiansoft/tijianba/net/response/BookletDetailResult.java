package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

public class BookletDetailResult extends ResponseResult {

  @SerializedName("data")
  private BookletDetailItem bookletDetailItem = new BookletDetailItem();

  public BookletDetailItem getBookletDetailItem() {
    return this.bookletDetailItem;
  }

  public void setBookletDetailItem(BookletDetailItem bookletDetailItem) {
    this.bookletDetailItem = bookletDetailItem;
  }

}