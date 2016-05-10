package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * com.hengtiansoft.tijianba.net.response.HotOrg
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Jan 5, 2015 9:10:40 PM
 */
public class HotOrg {
  @SerializedName("id")
  private int id;
  @SerializedName("img")
  private String img;
  @SerializedName("orgId")
  private int orgId;
  @SerializedName("name")
  private String name;
  @SerializedName("orgType")
  private int orgType;
  private String orgTypeName;

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getImg() {
    return this.img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public int getOrgId() {
    return this.orgId;
  }

  public void setOrgId(int orgId) {
    this.orgId = orgId;
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

  public String getOrgTypeName() {
    return this.orgTypeName;
  }

  public void setOrgTypeName(String orgTypeName) {
    this.orgTypeName = orgTypeName;
  }

}