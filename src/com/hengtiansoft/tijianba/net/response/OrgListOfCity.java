package com.hengtiansoft.tijianba.net.response;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class OrgListOfCity implements Serializable {
  @SerializedName("cityName")
  private String cityName;
  @SerializedName("orgList")
  private ArrayList<OrgOfSubscribe> orgList;

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public ArrayList<OrgOfSubscribe> getOrgList() {
    return orgList;
  }

  public void setOrgList(ArrayList<OrgOfSubscribe> orgList) {
    this.orgList = orgList;
  }

}
