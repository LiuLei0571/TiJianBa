package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

/**
 * com.hengtiansoft.tijianba.model.RemoteDataManager
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 5, 2014 11:21:32 AM
 */
public class PackedNewAccount {
	@SerializedName("mobile")
	private String mobile;
	@SerializedName("verifyCode")
	private String verifyCode;
	@SerializedName("password")
	private String password;
	@SerializedName("source")
	private int source;

	public PackedNewAccount() {
		this.source = 1;
	}

	public String getVerifyCode() {
		return this.verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
