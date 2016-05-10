package com.hengtiansoft.tijianba.net.response;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class HealthUser implements Serializable {

  @SerializedName("name")
  private String name;
  @SerializedName("headImg")
  private String headImg;
  @SerializedName("gender")
  private int gender;
  @SerializedName("bornDate")
  private String bornDate;
  @SerializedName("age")
  private int age;
  @SerializedName("height")
  private float height;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getHeadImg() {
    return this.headImg;
  }

  public void setHeadImg(String headImg) {
    this.headImg = headImg;
  }

  public int getGender() {
    return this.gender;
  }

  public void setGender(int gender) {
    this.gender = gender;
  }

  public String getBornDate() {
    return this.bornDate;
  }

  public void setBornDate(String bornDate) {
    this.bornDate = bornDate;
  }

  public int getAge() {
    return this.age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public float getHeight() {
    return this.height;
  }

  public void setHeight(float height) {
    this.height = height;
  }

}
