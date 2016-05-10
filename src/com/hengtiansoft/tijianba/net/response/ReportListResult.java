package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class ReportListResult extends ResponseResult {
  @SerializedName("data")
  private ArrayList<Report> examReportList = new ArrayList<Report>();

  public ArrayList<Report> getExamReportList() {
    return this.examReportList;
  }

  public void setExamReportList(ArrayList<Report> examReportList) {
    this.examReportList = examReportList;
  }
}
