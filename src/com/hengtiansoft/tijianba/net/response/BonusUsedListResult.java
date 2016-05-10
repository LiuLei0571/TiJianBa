
package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BonusUsedListResult extends ResponseResult {
    @SerializedName("data")
    private ArrayList<BonusUsed> bonusUsed = new ArrayList<BonusUsed>();

    public ArrayList<BonusUsed> getBonusUsed() {
        return bonusUsed;
    }

    public void setBonusUsed(ArrayList<BonusUsed> bonusUsed) {
        this.bonusUsed = bonusUsed;
    }

    public class BonusUsed {
        @SerializedName("bonusMoney")
        private int bonusMoney;
        @SerializedName("useTime")
        private String useTime;

        public int getBonusMoney() {
            return bonusMoney;
        }

        public void setBonusMoney(int bonusMoney) {
            this.bonusMoney = bonusMoney;
        }

        public String getUseTime() {
            return useTime;
        }

        public void setUseTime(String useTime) {
            this.useTime = useTime;
        }

    }
}
