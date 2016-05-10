package com.hengtiansoft.tijianba.net.response;

public interface StepGiftDetailListener {
	  public void onSuccess(StepGiftDetailData message);

	  public void onError(String errorCode, String errorMessage);
}
