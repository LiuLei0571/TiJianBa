
package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

public class PackedSubscribeInfo {
    @SerializedName("bookServerId")
    private int bookServerId;
    @SerializedName("orgId")
    private int orgId;
    @SerializedName("examType")
    private int examType;
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
    @SerializedName("contactPostAddr")
    private String contactPostAddr;
    @SerializedName("contactZipCode")
    private String contactZipCode;
    @SerializedName("testDay")
    private String testDay;
    @SerializedName("agreeUploadReport")
    private boolean agreeUploadReport;
    @SerializedName("source")
    private int source;

    public PackedSubscribeInfo() {
        this.source = 1;
    }

    public int getBookServerId() {
        return bookServerId;
    }

    public void setBookServerId(int bookServerId) {
        this.bookServerId = bookServerId;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getExamType() {
        return examType;
    }

    public void setExamType(int examType) {
        this.examType = examType;
    }

    public String getTesterName() {
        return testerName;
    }

    public void setTesterName(String testerName) {
        this.testerName = testerName;
    }

    public int getTesterGender() {
        return testerGender;
    }

    public void setTesterGender(int testerGender) {
        this.testerGender = testerGender;
    }

    public boolean isTesterMarried() {
        return testerMarried;
    }

    public void setTesterMarried(boolean testerMarried) {
        this.testerMarried = testerMarried;
    }

    public String getTesterIDNumber() {
        return testerIDNumber;
    }

    public void setTesterIDNumber(String testerIDNumber) {
        this.testerIDNumber = testerIDNumber;
    }

    public String getTesterMobile() {
        return testerMobile;
    }

    public void setTesterMobile(String testerMobile) {
        this.testerMobile = testerMobile;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactPostAddr() {
        return contactPostAddr;
    }

    public void setContactPostAddr(String contactPostAddr) {
        this.contactPostAddr = contactPostAddr;
    }

    public String getContactZipCode() {
        return contactZipCode;
    }

    public void setContactZipCode(String contactZipCode) {
        this.contactZipCode = contactZipCode;
    }

    public String getTestDay() {
        return testDay;
    }

    public void setTestDay(String testDay) {
        this.testDay = testDay;
    }

    public boolean isAgreeUploadReport() {
        return agreeUploadReport;
    }

    public void setAgreeUploadReport(boolean agreeUploadReport) {
        this.agreeUploadReport = agreeUploadReport;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

}
