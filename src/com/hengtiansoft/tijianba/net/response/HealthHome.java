package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class HealthHome {
  @SerializedName("healthUser")
  private HealthUser healthUser;
  @SerializedName("examReport")
  private Report examReport;

  public HealthUser getHealthUser() {
    return this.healthUser;
  }

  public void setHealthUser(HealthUser healthUser) {
    this.healthUser = healthUser;
  }

  public Report getExamReport() {
    return this.examReport;
  }

  public void setExamReport(Report examReport) {
    this.examReport = examReport;
  }


}
