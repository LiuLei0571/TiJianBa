package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * com.hengtiansoft.tijianba.model.RemoteDataManager
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 5, 2014 16:18:32 PM
 */
public class OrganizationListResult extends ResponseResult {
  @SerializedName("data")
  private ArrayList<Organization> organizations = new ArrayList<Organization>();

  public ArrayList<Organization> getOrganizations() {
    return this.organizations;
  }

  public void setOrganizations(ArrayList<Organization> organizations) {
    this.organizations = organizations;
  }

}
