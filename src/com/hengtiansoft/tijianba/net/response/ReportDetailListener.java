package com.hengtiansoft.tijianba.net.response;



public interface ReportDetailListener {
  public void onSuccess(ReportDetail examReportDetail);

  public void onError(String errorCode, String errorMessage);
}
