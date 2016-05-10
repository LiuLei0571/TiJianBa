package com.hengtiansoft.tijianba.net.response;
import com.google.gson.annotations.SerializedName;

public class BookletItem  {
  @SerializedName("id")
  private int id;
  @SerializedName("name")
  private String name;

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
