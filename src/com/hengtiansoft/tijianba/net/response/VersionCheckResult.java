
package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

public class VersionCheckResult extends ResponseResult {
    @SerializedName("data")
    private String linkUrl;

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

}
