package com.csse.sync.fourinone.po;

import java.io.Serializable;

public class App implements Serializable {

    //appid
    private String appId;

    //秘钥
    private String appSecret;

    //授权类型，固定为”desktop”
    private String grantType;

    public App() {
    }

    public App(String appId, String appSecret, String grantType) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.grantType = grantType;
    }

    public App(String appId, String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
}
