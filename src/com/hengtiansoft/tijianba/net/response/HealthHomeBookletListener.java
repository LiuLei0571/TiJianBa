package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

public interface HealthHomeBookletListener {
  public void onSuccess(ArrayList<HealthHomeBookletItem> healthHomeBookletItem);

  public void onError(String errorCode, String errorMessage);
}
