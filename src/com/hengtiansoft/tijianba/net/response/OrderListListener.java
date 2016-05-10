package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

public interface OrderListListener {
  public void onSuccess(ArrayList<Order> orderList);

  public void onError(String errorCode, String errorMessage);
}