package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

/**
 * com.hengtiansoft.tijianba.model.RemoteDataManager
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 5, 2014 12:21:32 PM
 */
public class MallDataResult extends ResponseResult {
  @SerializedName("data")
  private MallData mallData = new MallData();

  public MallData getMallData() {
    return this.mallData;
  }

  public void setMallData(MallData mallData) {
    this.mallData = mallData;
  }

}
