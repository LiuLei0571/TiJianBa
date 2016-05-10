package com.hengtiansoft.tijianba.model;

/**
 * 
 * com.hengtiansoft.tijianba.model.Report
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 5, 2014 3:21:40 PM
 */
public class Report {
  private String name;
  private String createTime;
  private String testTime;
  private String location;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getTestTime() {
    return this.testTime;
  }

  public void setTestTime(String testTime) {
    this.testTime = testTime;
  }

  public String getLocation() {
    return this.location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

}
