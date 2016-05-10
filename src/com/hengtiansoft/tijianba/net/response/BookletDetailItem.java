package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class BookletDetailItem {
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
  @SerializedName("rangeMin")
  private String rangeMin;
  @SerializedName("isMinExists")
  private int isMinExists;
  @SerializedName("rangeMax")
  private String rangeMax;
  @SerializedName("isMaxExists")
  private int isMaxExists;
  @SerializedName("newestData")
  private String newestData;
  @SerializedName("newestDataDate")
  private String newestDataDate;
  @SerializedName("isNormal")
  private int isNormal;
  @SerializedName("clinic")
  private String clinic;
  @SerializedName("advice")
  private String advice;
  @SerializedName("isShow")
  private int isShow;
  @SerializedName("bmiValue")
  private String bmiValue;
  @SerializedName("bmiIsNormal")
  private int bmiIsNormal;
  @SerializedName("bmiClinic")
  private String bmiClinic;
  @SerializedName("bmiAdvice")
  private String bmiAdvice;
  @SerializedName("valueType")
  private int valueType;
  
  
  public int getValueType() {
    return this.valueType;
  }

  public void setValueType(int valueType) {
this.valueType = valueType;}
  

  public String getBmiValue() {
    return this.bmiValue;
  }

  public void setBmiValue(String bmiValue) {
    this.bmiValue = bmiValue;
  }

  public int getBmiIsNormal() {
    return this.bmiIsNormal;
  }

  public void setBmiIsNormal(int bmiIsNormal) {
    this.bmiIsNormal = bmiIsNormal;
  }

  public String getBmiClinic() {
    return this.bmiClinic;
  }

  public void setBmiClinic(String bmiClinic) {
    this.bmiClinic = bmiClinic;
  }

  public String getBmiAdvice() {
    return this.bmiAdvice;
  }

  public void setBmiAdvice(String bmiAdvice) {
    this.bmiAdvice = bmiAdvice;
  }

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

  public String getRangeMin() {
    return this.rangeMin;
  }

  public void setRangeMin(String rangeMin) {
    this.rangeMin = rangeMin;
  }

  public int getIsMinExists() {
    return this.isMinExists;
  }

  public void setIsMinExists(int isMinExists) {
    this.isMinExists = isMinExists;
  }

  public String getRangeMax() {
    return this.rangeMax;
  }

  public void setRangeMax(String rangeMax) {
    this.rangeMax = rangeMax;
  }

  public int getIsMaxExists() {
    return this.isMaxExists;
  }

  public void setIsMaxExists(int isMaxExists) {
    this.isMaxExists = isMaxExists;
  }

  public int getIsNormal() {
    return this.isNormal;
  }

  public void setIsNormal(int isNormal) {
    this.isNormal = isNormal;
  }

  public String getClinic() {
    return this.clinic;
  }

  public void setClinic(String clinic) {
    this.clinic = clinic;
  }

  public String getNewestData() {
    return newestData;
  }

  public void setNewestData(String newestData) {
    this.newestData = newestData;
  }

  public String getNewestDataDate() {
    return newestDataDate;
  }

  public void setNewestDataDate(String newestDataDate) {
    this.newestDataDate = newestDataDate;
  }

  public String getAdvice() {
    return this.advice;
  }

  public void setAdvice(String advice) {
    this.advice = advice;
  }

  public int getIsShow() {
    return this.isShow;
  }

  public void setIsShow(int isShow) {
    this.isShow = isShow;
  }

  public ArrayList<HealthHomeBookletDate> getDataList() {
    return this.dataList;
  }

  public void setDataList(ArrayList<HealthHomeBookletDate> dataList) {
    this.dataList = dataList;
  }

}
