package com.hengtiansoft.tijianba.net.response;

import com.hengtiansoft.tijianba.model.BonusList;

public interface BonusListListener {
  public void onSuccess(BonusList bonusList);

  public void onError(String errorCode, String errorMessage);
}
