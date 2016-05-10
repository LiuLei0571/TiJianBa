package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

/**
 * com.hengtiansoft.tijianba.net.response.SubscribeRecordListListener
 * @author flychen <br/>
 * create at Dec 29, 2014 10:40:18 AM
 */
public interface SubscribeRecordListListener {
  public void onSuccess(ArrayList<SubscribeRecord> records);
  public void onError(String errorCode, String errorMessage);
}
