package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

public class UploadStepResponse extends ResponseResult {
	@SerializedName("data")
	private StepData stepdata;

	public StepData getStepdata() {
		return stepdata;
	}

	public void setStepdata(StepData stepdata) {
		this.stepdata = stepdata;
	}
	
}
