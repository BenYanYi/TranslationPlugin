package com.yanyi.translation;

import java.util.HashMap;
import java.util.Map;

 class TransApi {
    private static final String Language_Url = "http://api.fanyi.baidu.com/api/trans/vip/language";
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    private String appid;
    private String securityKey;

     TransApi(String appid, String securityKey) {
        this.appid = appid;
        this.securityKey = securityKey;
    }

     String getTransResult(String query, String from, String to) {
        Map<String, String> params = buildParams(query, from, to);
        return HttpUtil.get(TRANS_API_HOST, params);
    }

     String getLanguageResult(String query) {
        Map<String, String> params = buildLanguage(query);
        return HttpUtil.get(Language_Url, params);
    }

    private Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", Md5Util.md5(src));

        return params;
    }

    private Map<String, String> buildLanguage(String query) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);

        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", Md5Util.md5(src));

        return params;
    }

}
