package com.yanyi.translation;

/**
 * @author YanYi
 * @date 2019/3/27 12:50
 * @email ben@yanyi.red
 * @overview
 */
public class BaiduTest {
    private static final String APP_ID = "20181221000250890";
    private static final String SECURITY_KEY = "TfP62BRxA7t1QNiyWxAE";

    public static void main(String[] arg0) {
//        TransApi transApi = new TransApi(APP_ID, SECURITY_KEY);
//        String q = "hello";
//        System.out.println(JsonUtil.jsonToTrans(transApi.getTransResult(q, "auto", "zh")));
//        {"error_code":0,"error_msg":"success","data":{"src":"en"}}
//       {"error_code":"52003","error_msg":"UNAUTHORIZED USER"}
        StringBuilder str = new StringBuilder();
        str.append("加加加").append(";");
        str.deleteCharAt(str.length() - 1);
        System.out.println(str.toString());
    }
}
