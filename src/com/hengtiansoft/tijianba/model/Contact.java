package com.hengtiansoft.tijianba.model;

/**
 * 
 * com.hengtiansoft.tijianba.model.Contact
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 16, 2014 1:23:54 PM
 */
public class Contact {
  private String name;
  private String nickName;
  private String mobile;
  // 0:已添加1添加2已解除3日期
  private int type;
  private String time;
  private String content;

  public Contact(String name, String nickName, String mobile, int type, String time, String content) {

    this.name = name;
    this.nickName = nickName;
    this.mobile = mobile;
    this.type = type;
    this.time = time;
    this.content = content;
  }

  public Contact() {
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNickName() {
    return this.nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getMobile() {
    return this.mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public int getType() {
    return this.type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getTime() {
    return this.time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
