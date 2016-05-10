package com.hengtiansoft.tijianba.net.response;

public interface UploadStepListener {
	public void onSuccess(StepData data);

	  public void onError(String errorCode, String errorMessage);
}
