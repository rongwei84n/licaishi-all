package com.auts.lcss.model.response;

/**
 * 授权码返回结果.
 *
 */
public class AuthorizationCodeResponseCode {

    private String error;

    private String authorizationcode;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getAuthorizationcode() {
        return authorizationcode;
    }

    public void setAuthorizationcode(String authorizationcode) {
        this.authorizationcode = authorizationcode;
    }

    @Override
    public String toString() {
        return "AuthorizationCodeResponseCode [error=" + error + ", authorizationcode=" + authorizationcode + "]";
    }
}
