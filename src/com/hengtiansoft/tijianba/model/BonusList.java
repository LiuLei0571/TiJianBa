
package com.hengtiansoft.tijianba.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class BonusList {
  public int getBonusTotal() {
    return this.bonusTotal;
  }
  public void setBonusTotal(int bonusTotal) {
  this.bonusTotal = bonusTotal;}
  
  public ArrayList<Bonus> getItemList() {
    return this.itemList;
  }
  public void setItemList(ArrayList<Bonus> itemList) {
this.itemList = itemList;}
  
  @SerializedName("bonusTotal")
  private int bonusTotal;
  @SerializedName("itemList")
  private ArrayList<Bonus> itemList = new ArrayList<Bonus>();
  public BonusList() {
    super();
  }
  

}
