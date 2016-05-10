package com.hengtiansoft.tijianba.model;

public class Health {

  private String titleName;
  private int type;// 0:editText 1:radiobutton yes 2.radiobutton 阳性
  private boolean isEnable;
  private String value;
  public Health(String titleName, int type, boolean isEnable, String value) {

    this.titleName = titleName;
    this.type = type;
    this.isEnable = isEnable;
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getTitleName() {
    return this.titleName;
  }

  public void setTitleName(String titleName) {
    this.titleName = titleName;
  }

  public boolean isEnable() {
    return this.isEnable;
  }

  public void setEnable(boolean isEnable) {
    this.isEnable = isEnable;
  }

  public int getType() {
    return this.type;
  }

  public void setType(int type) {
    this.type = type;
  }

}
