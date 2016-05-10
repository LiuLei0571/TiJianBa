package com.hengtiansoft.tijianba.model;

/**
 * 
 * com.hengtiansoft.tijianba.model.OrderType
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 24, 2014 1:51:38 PM
 */
public class OrderType {
  private int id;
  private String name;
  private boolean isSelected = false;

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isSelected() {
    return this.isSelected;
  }

  public void setSelected(boolean isSelected) {
    this.isSelected = isSelected;
  }

}
