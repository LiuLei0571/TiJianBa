package com.hengtiansoft.tijianba.model;

import com.google.gson.annotations.SerializedName;

public class Bonus {
  @SerializedName("bonusTypeId")
  private int bonusTypeId;
  @SerializedName("type")
  private int type;
  @SerializedName("money")
  private int money;
  @SerializedName("expireTime")
  private String expireTime;
  @SerializedName("number")
  private int number;
  @SerializedName("isExpired")
  private boolean isExpired;
  
  public Bonus() {
    super();
  }

  public boolean isExpired() {
    return this.isExpired;
  }

  public void setExpired(boolean isExpired) {
    this.isExpired = isExpired;
  }

  public int getBonusTypeId() {
    return bonusTypeId;
  }

  public void setBonusTypeId(int bonusTypeId) {
    this.bonusTypeId = bonusTypeId;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getMoney() {
    return money;
  }

  public void setMoney(int money) {
    this.money = money;
  }

  public String getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(String expireTime) {
    this.expireTime = expireTime;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }
}

