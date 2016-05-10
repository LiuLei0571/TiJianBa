
package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

public class HealthHomeResult extends ResponseResult {
  @SerializedName("data")
  private HealthHome healthHome=new HealthHome();

  public HealthHome getHealthHome() {
    return this.healthHome;
  }

  public void setHealthHome(HealthHome healthHome) {
 this.healthHome = healthHome;}
  

  
}
