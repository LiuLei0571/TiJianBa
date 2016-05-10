package com.hengtiansoft.tijianba.model;

import java.io.Serializable;

public class CalendarDate implements Serializable {
  private static final long serialVersionUID = 1918217860220083551L;
  private int year;
  private int month;
  private int date;

  public int getDate() {
    return this.date;
  }

  public void setDate(int date) {
    this.date = date;
  }

  public int getState() {
    return this.state;
  }

  public void setState(int state) {
    this.state = state;
  }

  private int state;

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
  }

}
