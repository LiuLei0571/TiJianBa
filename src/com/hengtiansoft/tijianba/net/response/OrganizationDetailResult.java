package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

/**
 * com.hengtiansoft.tijianba.model.RemoteDataManager
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 5, 2014 16:21:32 PM
 */
public class OrganizationDetailResult extends ResponseResult {
  @SerializedName("data")
  private OrganizationDetail organizationDetail = new OrganizationDetail();

  public OrganizationDetail getOrganizationDetail() {
    return this.organizationDetail;
  }

  public void setOrganizationDetail(OrganizationDetail organizationDetail) {
    this.organizationDetail = organizationDetail;
  }

}
