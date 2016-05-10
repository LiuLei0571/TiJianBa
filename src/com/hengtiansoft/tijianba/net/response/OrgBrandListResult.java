package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * com.hengtiansoft.tijianba.net.response.OrgBrandListResult
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 19, 2014 2:00:02 PM
 */
public class OrgBrandListResult extends ResponseResult {
  @SerializedName("data")
  private ArrayList<Brand> brands = new ArrayList<Brand>();

  public class Brand {
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

  public ArrayList<Brand> getBrands() {
    return this.brands;
  }

  public void setBrands(ArrayList<Brand> brands) {
    this.brands = brands;
  }

}
