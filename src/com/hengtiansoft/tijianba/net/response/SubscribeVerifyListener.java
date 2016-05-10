package com.hengtiansoft.tijianba.net.response;


public interface SubscribeVerifyListener {
  public void onSuccess(SubscribeVerify subscribeVerify);

  public void onError(String errorCode, String errorMessage);
}
