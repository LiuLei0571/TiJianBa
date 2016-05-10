package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * com.hengtiansoft.tijianba.net.response.CheckInteractionResult
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Jan 15, 2015 12:24:30 PM
 */
public class CheckInteractionResult extends ResponseResult {

  @SerializedName("data")
  private ArrayList<InteractionStatus> interactionStatus;

  public ArrayList<InteractionStatus> getInteractionStatus() {
    return this.interactionStatus;
  }

  public void setInteractionStatus(ArrayList<InteractionStatus> interactionStatus) {
    this.interactionStatus = interactionStatus;
  }

  public class InteractionStatus {
    @SerializedName("name")
    private String name;
    @SerializedName("val")
    private String val;

    public String getName() {
      return this.name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getVal() {
      return this.val;
    }

    public void setVal(String val) {
      this.val = val;
    }

  }
}
