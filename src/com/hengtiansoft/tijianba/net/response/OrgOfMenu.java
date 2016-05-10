package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

/**
 * com.hengtiansoft.tijianba.net.response.OrgOfMenu
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 12, 2014 12:50:29 PM
 */
public class OrgOfMenu {
  @SerializedName("orgId")
  private int orgId;
  @SerializedName("orgName")
  private String orgName;
  @SerializedName("orgType")
  private int orgType;

  public int getOrgId() {
    return this.orgId;
  }

  public void setOrgId(int orgId) {
    this.orgId = orgId;
  }

  public String getOrgName() {
    return this.orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }

  public int getOrgType() {
    return orgType;
  }

  public void setOrgType(int orgType) {
    this.orgType = orgType;
  }

}
