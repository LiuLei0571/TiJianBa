package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * com.hengtiansoft.tijianba.net.response.SubscribeRecordList
 * @author flychen <br/>
 * create at Dec 29, 2014 10:33:05 AM
 */
public class SubscribeRecordList extends ResponseResult{
  
  @SerializedName("data")
  private ArrayList<SubscribeRecord> records = new ArrayList<SubscribeRecord>();

  public ArrayList<SubscribeRecord> getRecords() {
    return this.records;
  }
  public void setRecords(ArrayList<SubscribeRecord> records) {
    this.records = records;
  }
  
}
