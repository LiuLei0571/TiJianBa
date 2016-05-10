package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

public class StepGiftDetailData {
	@SerializedName("rules")
	private String rules;
	@SerializedName("price")
	private float price;
	@SerializedName("detail")
	private String detail;
	@SerializedName("picture")
	private String picture;
	@SerializedName("giftname")
	private String giftname;
	@SerializedName("activitytime")
	private String activitytime;
	@SerializedName("lotterytime")
	private String lotterytime;
	@SerializedName("descr")
	private String descr;
	public String getRules() {
		return rules;
	}
	public void setRules(String rules) {
		this.rules = rules;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getGiftname() {
		return giftname;
	}
	public void setGiftname(String giftname) {
		this.giftname = giftname;
	}
	public String getActivitytime() {
		return activitytime;
	}
	public void setActivitytime(String activitytime) {
		this.activitytime = activitytime;
	}
	public String getLotterytime() {
		return lotterytime;
	}
	public void setLotterytime(String lotterytime) {
		this.lotterytime = lotterytime;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
}
