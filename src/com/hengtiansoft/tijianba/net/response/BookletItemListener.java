package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;


public interface BookletItemListener {
	  public void onSuccess(ArrayList<BookletItem> bookletItemList);

	  public void onError(String errorCode, String errorMessage);

}
