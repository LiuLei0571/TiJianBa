package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * com.hengtiansoft.tijianba.model.RemoteDataManager
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 5, 2014 12:21:32 PM
 */
public class CityResult extends ResponseResult {
	@SerializedName("data")
	private Data data = new Data();

	public class Data {
		@SerializedName("refreshTime")
		private String refreshTime;
		@SerializedName("cityList")
		private ArrayList<City> cityList;

		public String getRefreshTime() {
			return this.refreshTime;
		}

		public void setRefreshTime(String refreshTime) {
			this.refreshTime = refreshTime;
		}

		public ArrayList<City> getCityList() {
			return this.cityList;
		}

		public void setCityList(ArrayList<City> cityList) {
			this.cityList = cityList;
		}

	}

	public class City {
		@SerializedName("code")
		private String code;
		@SerializedName("name")
		private String name;
		@SerializedName("parentCode")
		private String parentCode;
		@SerializedName("level")
		private int level;
		@SerializedName("weight")
		private int weight;
		@SerializedName("id")
		private int id;
		@SerializedName("status")
		private int status;

		public String getCode() {
			return this.code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getId() {
			return this.id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getParentCode() {
			return this.parentCode;
		}

		public void setParentCode(String parentCode) {
			this.parentCode = parentCode;
		}

		public int getLevel() {
			return this.level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public int getWeight() {
			return this.weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

	}

	public Data getData() {
		return this.data;
	}

	public void setData(Data data) {
		this.data = data;
	}

}
