package com.hengtiansoft.tijianba.net.response;

public interface PaySuccessListener {
  public void onSuccess(PaySuccess paySuccess);

  public void onError(String errorCode, String errorMessage);
}
