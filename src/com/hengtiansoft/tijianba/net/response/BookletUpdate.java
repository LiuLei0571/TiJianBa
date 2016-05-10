 
package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

 
public class BookletUpdate {
  @SerializedName("id")
  private int id;
  @SerializedName("bookletId")
  private int bookletId;
  @SerializedName("value")
  private float value;
  @SerializedName("dataTime")
  private String dataTiem;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public int getBookletId() {
    return bookletId;
  }
  public void setBookletId(int bookletId) {
    this.bookletId = bookletId;
  }
  public float getValue() {
    return value;
  }
  public void setValue(float value) {
    this.value = value;
  }
  public String getDataTiem() {
    return dataTiem;
  }
  public void setDataTiem(String dataTiem) {
    this.dataTiem = dataTiem;
  }
  
  
}
