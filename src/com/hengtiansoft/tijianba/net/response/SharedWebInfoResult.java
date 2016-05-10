
package com.hengtiansoft.tijianba.net.response;

import com.google.gson.annotations.SerializedName;

public class SharedWebInfoResult extends ResponseResult {

    @SerializedName("data")
    private SharedWebInfor sharedWebInfor;

    public SharedWebInfor getSharedWebInfor() {
        return sharedWebInfor;
    }

    public void setSharedWebInfor(SharedWebInfor sharedWebInfor) {
        this.sharedWebInfor = sharedWebInfor;
    }

    public class SharedWebInfor {
        @SerializedName("title")
        private String title;
        @SerializedName("bonusURL")
        private String bonusURL;
        @SerializedName("content")
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBonusURL() {
            return bonusURL;
        }

        public void setBonusURL(String bonusURL) {
            this.bonusURL = bonusURL;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

    }
}
