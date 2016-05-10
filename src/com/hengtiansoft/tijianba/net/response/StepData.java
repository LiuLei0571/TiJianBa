package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

public class StepData {
	@SerializedName("weekstep")
	private int weekstep;
	@SerializedName("daytopstep")
	private int daytopstep;
	@SerializedName("ranking")
	private int ranking;
	@SerializedName("giftid")
	private int giftid;
	public int getWeekstep() {
		return weekstep;
	}
	public void setWeekstep(int weekstep) {
		this.weekstep = weekstep;
	}
	public int getDaytopstep() {
		return daytopstep;
	}
	public void setDaytopstep(int daytopstep) {
		this.daytopstep = daytopstep;
	}
	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	public int getGiftid() {
		return giftid;
	}
	public void setGiftid(int giftid) {
		this.giftid = giftid;
	}
}
