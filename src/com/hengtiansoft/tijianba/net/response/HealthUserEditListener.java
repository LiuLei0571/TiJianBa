package com.hengtiansoft.tijianba.net.response;


public interface HealthUserEditListener {
  public void onSuccess(int suitObject);

  public void onError(String errorCode, String errorMessage);
}
