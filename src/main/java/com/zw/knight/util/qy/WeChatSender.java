package com.zw.knight.util.qy;

import com.google.gson.reflect.TypeToken;
import com.zw.knight.util.GsonUtils;
import com.zw.knight.util.HttpClient;
import com.zw.knight.util.qy.pojp.WeChatRes;
import org.apache.http.HttpHost;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信发送工具
 *
 * @author zw
 * @date 2019/10/23
 */
public class WeChatSender {

    private HttpHost httpHost;
    private String corpId;
    private String corpSecret;
    private static final String QYWX_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
    private static final String QYWX_PUSH_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send";


    private String getUrlWithToken() {
        Map<String, String> params = new HashMap<>(2);
        params.put("corpid", corpId);
        params.put("corpsecret", corpSecret);
        String resp = HttpClient.get(QYWX_TOKEN_URL, params);
        Map<String, String> result = GsonUtils.fromJson(resp, new TypeToken<Map<String, String>>() {
        }.getType());
        String token = result.get("access_token");
        return QYWX_PUSH_URL + "?access_token=" + token;
    }

    public void sendMsg(WeChatRes weChatRes) {
        String url = getUrlWithToken();
        String resp = HttpClient.post(url, GsonUtils.toJson(weChatRes));
        Map<String, String> result = GsonUtils.fromJson(resp, new TypeToken<Map<String, String>>() {
        }.getType());
        if (!"0".equals(result.get("errcode"))) {
            HttpClient.post(url, GsonUtils.toJson(weChatRes));
        }
    }

    public HttpHost getHttpHost() {
        return httpHost;
    }

    public void setHttpHost(HttpHost httpHost) {
        this.httpHost = httpHost;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getCorpSecret() {
        return corpSecret;
    }

    public void setCorpSecret(String corpSecret) {
        this.corpSecret = corpSecret;
    }
}
