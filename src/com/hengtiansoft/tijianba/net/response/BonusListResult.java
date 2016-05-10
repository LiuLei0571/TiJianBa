package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;
import com.hengtiansoft.tijianba.model.BonusList;


public class BonusListResult extends ResponseResult {
  @SerializedName("data")
 private BonusList bonusList=new BonusList();

  public BonusList getBonusList() {
    return this.bonusList;
  }

  public void setBonusList(BonusList bonusList) {
   this.bonusList = bonusList;}
}


