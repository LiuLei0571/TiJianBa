package com.hengtiansoft.tijianba.net.response;


import com.google.gson.annotations.SerializedName;

public class ReportDetailResult extends ResponseResult {
  @SerializedName("data")
  private ReportDetail examReportDetail = new ReportDetail ();

  public ReportDetail getExamReportDetail() {
    return this.examReportDetail;
  }

  public void setExamReportDetail(ReportDetail examReportDetail) {
 this.examReportDetail = examReportDetail;}
  

  
}
