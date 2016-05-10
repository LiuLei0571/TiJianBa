
package com.hengtiansoft.tijianba.net.response;

import com.hengtiansoft.tijianba.net.response.BonusUsedListResult.BonusUsed;

import java.util.ArrayList;

public interface BonusUsedListListener {

    public void onSuccess(ArrayList<BonusUsed> bonusUsed);

    public void onError(String errorCode, String errorMessage);
}
