package com.hengtiansoft.tijianba.net.response;

public interface HealthHomeListener {
  public void onSuccess(HealthHome healthHome);

  public void onError(String errorCode, String errorMessage);
}
