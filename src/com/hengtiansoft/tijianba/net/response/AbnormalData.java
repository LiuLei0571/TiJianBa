package com.hengtiansoft.tijianba.net.response;

/**
 * com.hengtiansoft.tijianba.net.response.AbnormalData
 * 
 * @author 陈飞 <br/>
 *         create at Dec 9, 2014 10:26:39 AM
 */
public class AbnormalData {

  private String item;
  private String data;
  private String current;
  private String normal;
  private String msg;
  public AbnormalData() {
    
  }
  
  /**
   * @param item
   * @param data
   * @param current
   * @param normal
   * @param msg
   */
  public AbnormalData(String item, String data, String current, String normal, String msg) {
   
    this.item = item;
    this.data = data;
    this.current = current;
    this.normal = normal;
    this.msg = msg;
  }

  /**
   * @return the item
   */
  public String getItem() {
    return this.item;
  }

  /**
   * @param item the item to set
   */
  public void setItem(String item) {
    this.item = item;
  }
  

  /**
   * @return the data
   */
  public String getData() {
    return this.data;
  }

  /**
   * @param data the data to set
   */
  public void setData(String data) {
    this.data = data;
  }
  
  /**
   * @return the current
   */
  public String getCurrent() {
    return this.current;
  }

  /**
   * @param current the current to set
   */
  public void setCurrent(String current) {
    this.current = current;
  }
  
  /**
   * @return the normal
   */
  public String getNormal() {
    return this.normal;
  }

  /**
   * @param normal the normal to set
   */
  public void setNormal(String normal) {
    this.normal = normal;
  }
  
  /**
   * @return the msg
   */
  public String getMsg() {
    return this.msg;
  }

  /**
   * @param msg the msg to set
   */
  public void setMsg(String msg) {
    this.msg = msg;
  }
  
}
