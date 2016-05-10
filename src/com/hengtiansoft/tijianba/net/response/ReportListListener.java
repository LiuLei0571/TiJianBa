package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

public interface ReportListListener {
  public void onSuccess(ArrayList<Report> reportList);

  public void onError(String errorCode, String errorMessage);
}
