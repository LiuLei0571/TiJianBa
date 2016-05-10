
package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * com.hengtiansoft.tijianba.net.response.SubscribeResult
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 10, 2014 2:34:40 PM
 */
public class SubscribeResult extends ResponseResult {
    @SerializedName("data")
    private ArrayList<Subscribe> subscribes = new ArrayList<Subscribe>();

    public class Subscribe {
        @SerializedName("id")
        private int id;

        @SerializedName("menuSuitId")
        private int menuSuitId;

        @SerializedName("cardNo")
        private String cardNo;

        @SerializedName("cardPassword")
        private String cardPassword;

        @SerializedName("testerName")
        private String testerName;

        @SerializedName("testerGender")
        private int testerGender;

        @SerializedName("testerMarried")
        private boolean testerMarried;

        @SerializedName("testerIDNumber")
        private String testerIDNumber;

        @SerializedName("testerMobile")
        private String testerMobile;

        @SerializedName("contactName")
        private String contactName;

        @SerializedName("contactMobile")
        private String contactMobile;

        @SerializedName("testDay")
        private String testDay;

        @SerializedName("agreeUploadReport")
        private boolean agreeUploadReport;

        @SerializedName("branchOrgId")
        private int branchOrgId;

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMenuSuitId() {
            return this.menuSuitId;
        }

        public void setMenuSuitId(int menuSuitId) {
            this.menuSuitId = menuSuitId;
        }

        public String getCardNo() {
            return this.cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getCardPassword() {
            return this.cardPassword;
        }

        public void setCardPassword(String cardPassword) {
            this.cardPassword = cardPassword;
        }

        public String getTesterName() {
            return this.testerName;
        }

        public void setTesterName(String testerName) {
            this.testerName = testerName;
        }

        public int getTesterGender() {
            return this.testerGender;
        }

        public void setTesterGender(int testerGender) {
            this.testerGender = testerGender;
        }

        public boolean isTesterMarried() {
            return this.testerMarried;
        }

        public void setTesterMarried(boolean testerMarried) {
            this.testerMarried = testerMarried;
        }

        public String getTesterIDNumber() {
            return this.testerIDNumber;
        }

        public void setTesterIDNumber(String testerIDNumber) {
            this.testerIDNumber = testerIDNumber;
        }

        public String getTesterMobile() {
            return this.testerMobile;
        }

        public void setTesterMobile(String testerMobile) {
            this.testerMobile = testerMobile;
        }

        public String getContactName() {
            return this.contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getContactMobile() {
            return this.contactMobile;
        }

        public void setContactMobile(String contactMobile) {
            this.contactMobile = contactMobile;
        }

        public String getTestDay() {
            return this.testDay;
        }

        public void setTestDay(String testDay) {
            this.testDay = testDay;
        }

        public boolean isAgreeUploadReport() {
            return this.agreeUploadReport;
        }

        public void setAgreeUploadReport(boolean agreeUploadReport) {
            this.agreeUploadReport = agreeUploadReport;
        }

        public int getBranchOrgId() {
            return this.branchOrgId;
        }

        public void setBranchOrgId(int branchOrgId) {
            this.branchOrgId = branchOrgId;
        }

    }

    public Subscribe getSubscribe() {
        return this.subscribes.get(0);
    }

    public void setSubscribes(ArrayList<Subscribe> subscribes) {
        this.subscribes = subscribes;
    }

}
