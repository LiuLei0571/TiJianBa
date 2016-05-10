package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

/**
 * com.hengtiansoft.tijianba.model.RemoteDataManager
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 5, 2014 15:44:32 PM
 */
public class LoginResult extends ResponseResult {
  @SerializedName("data")
  private User user = new User();

  public class User {
    @SerializedName("userId")
    private int userId;
    @SerializedName("userType")
    private int userType;
    @SerializedName("companyId")
    private int companyId;

    public int getUserId() {
      return this.userId;
    }

    public void setUserId(int userId) {
      this.userId = userId;
    }

    public int getUserType() {
      return this.userType;
    }

    public void setUserType(int userType) {
      this.userType = userType;
    }

    public int getCompanyId() {
      return companyId;
    }

    public void setCompanyId(int companyId) {
      this.companyId = companyId;
    }

  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getUserString() {
    String userString = "\"userId\":" + this.user.getUserId() + ",\"userType\":" + this.user.getUserType() + ",\"companyId\":"
        + this.user.getCompanyId();
    return userString;
  }
}
