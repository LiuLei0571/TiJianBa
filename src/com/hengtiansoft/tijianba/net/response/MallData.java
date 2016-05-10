package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * com.hengtiansoft.tijianba.net.response.MallData
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Jan 5, 2015 9:04:44 PM
 */
public class MallData {
  @SerializedName("advertiseList")
  private ArrayList<Advertise> advertises;
  @SerializedName("hotMenuList")
  private ArrayList<HotMenu> hotMenus;
  @SerializedName("hotOrgList")
  private ArrayList<HotOrg> hotOrgs;
  @SerializedName("menuTypeList")
  private ArrayList<MallMenuType> menuTypes;

  public ArrayList<Advertise> getAdvertises() {
    return this.advertises;
  }

  public void setAdvertises(ArrayList<Advertise> advertises) {
    this.advertises = advertises;
  }

  public ArrayList<HotMenu> getHotMenus() {
    return this.hotMenus;
  }

  public void setHotMenus(ArrayList<HotMenu> hotMenus) {
    this.hotMenus = hotMenus;
  }

  public ArrayList<HotOrg> getHotOrgs() {
    return this.hotOrgs;
  }

  public void setHotOrgs(ArrayList<HotOrg> hotOrgs) {
    this.hotOrgs = hotOrgs;
  }

  public ArrayList<MallMenuType> getMenuTypes() {
    return this.menuTypes;
  }

  public void setMenuTypes(ArrayList<MallMenuType> menuTypes) {
    this.menuTypes = menuTypes;
  }

}
