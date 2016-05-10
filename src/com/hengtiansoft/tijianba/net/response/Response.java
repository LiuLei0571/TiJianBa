
package com.hengtiansoft.tijianba.net.response;

/**
 * com.hengtiansoft.tijianba.model.RemoteDataManager
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 5, 2014 11:21:32 AM
 */
public class Response {
    private int statusCode;
    private String content;
    private Boolean networkError;
    private String message;
    private ResponseResult responseResult;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ResponseResult getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(ResponseResult responseResult) {
        this.responseResult = responseResult;
    }

    public Boolean isNetworkError() {
        return networkError;
    }

    public void setNetworkError(Boolean networkError) {
        this.networkError = networkError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
