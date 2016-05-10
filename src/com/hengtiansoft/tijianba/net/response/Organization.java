package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

public class Organization {
  @SerializedName("id")
  private int id;
  @SerializedName("name")
  private String name;
  @SerializedName("orgType")
  private int orgType;
  @SerializedName("pic")
  private String pic;
  @SerializedName("brandName")
  private String brandName;
  @SerializedName("address")
  private String address;
  private String orgTypeName;

  public String getOrgTypeName() {
    return this.orgTypeName;
  }

  public void setOrgTypeName(String orgTypeName) {
    this.orgTypeName = orgTypeName;
  }

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

  public int getOrgType() {
    return this.orgType;
  }

  public void setOrgType(int orgType) {
    this.orgType = orgType;
  }

  public String getPic() {
    return this.pic;
  }

  public void setPic(String pic) {
    this.pic = pic;
  }

  public String getBrandName() {
    return this.brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

}