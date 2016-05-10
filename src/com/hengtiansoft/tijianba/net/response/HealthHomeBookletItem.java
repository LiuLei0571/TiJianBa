package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class HealthHomeBookletItem {
  @SerializedName("id")
  private int id;
  @SerializedName("name")
  private String name;
  @SerializedName("unit")
  private String unit;
  @SerializedName("valueMin")
  private String valueMin;
  @SerializedName("valueMax")
  private String valueMax;
  @SerializedName("normalMin")
  private String normalMin;
  @SerializedName("normalMax")
  private String normalMax;
  @SerializedName("newestData")
  private String newestData;
  @SerializedName("newestDataDate")
  private String newestDataDate;
  public String getNewestData() {
    return this.newestData;
  }

  public void setNewestData(String newestData) {
  this.newestData = newestData;}
  

  public String getNewestDataDate() {
    return this.newestDataDate;
  }

  public void setNewestDataDate(String newestDataDate) {
 this.newestDataDate = newestDataDate;}
  

  @SerializedName("dataList")
  private ArrayList<HealthHomeBookletDate> dataList;

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

  public String getUnit() {
    return this.unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public String getValueMin() {
    return this.valueMin;
  }

  public void setValueMin(String valueMin) {
    this.valueMin = valueMin;
  }

  public String getValueMax() {
    return this.valueMax;
  }

  public void setValueMax(String valueMax) {
    this.valueMax = valueMax;
  }

  public String getNormalMin() {
    return this.normalMin;
  }

  public void setNormalMin(String normalMin) {
    this.normalMin = normalMin;
  }

  public String getNormalMax() {
    return this.normalMax;
  }

  public void setNormalMax(String normalMax) {
    this.normalMax = normalMax;
  }

  public ArrayList<HealthHomeBookletDate> getDataList() {
    return this.dataList;
  }

  public void setDataList(ArrayList<HealthHomeBookletDate> dataList) {
    this.dataList = dataList;
  }
}
