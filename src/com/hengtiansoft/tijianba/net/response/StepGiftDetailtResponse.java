package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

public class StepGiftDetailtResponse extends ResponseResult{
	@SerializedName("data")
	private StepGiftDetailData data;

	public StepGiftDetailData getData() {
		return data;
	}

	public void setData(StepGiftDetailData data) {
		this.data = data;
	}
	
}
