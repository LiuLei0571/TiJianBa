package com.hengtiansoft.tijianba.net.response;

public interface OrderDetailListener {
  public void onSuccess(OrderDetail orderDetail);

  public void onError(String errorCode, String errorMessage);
}
