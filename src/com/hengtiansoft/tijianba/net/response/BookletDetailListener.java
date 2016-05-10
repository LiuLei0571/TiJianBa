package com.hengtiansoft.tijianba.net.response;


public interface BookletDetailListener {

	  public void onSuccess(BookletDetailItem bookletDetailItem);

	  public void onError(String errorCode, String errorMessage);

}
