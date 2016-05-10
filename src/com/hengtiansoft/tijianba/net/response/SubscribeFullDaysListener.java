package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

public interface SubscribeFullDaysListener {
  public void onSuccess(boolean b, ArrayList<String> date);

  public void onError(String errorCode, String errorMessage);
}
