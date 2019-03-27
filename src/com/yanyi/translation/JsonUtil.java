package com.yanyi.translation;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YanYi
 * @date 2019/3/27 13:58
 * @email ben@yanyi.red
 * @overview
 */
class JsonUtil {
    static String jsonToLanguage(String json) {
//        {"error_code":0,"error_msg":"success","data":{"src":"en"}}
//        {"error_code":"52003","error_msg":"UNAUTHORIZED USER"}
        try {
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.optInt("error_code", -1);
            if (code == 0) {
                JSONObject data = jsonObject.optJSONObject("data");
                if (data != null) {
                    String src = data.getString("src");
                    if (src != null && !src.isEmpty()) {
                        return src;
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    static List<TransBean> jsonToTrans(String json) {
//        {"error_code":"52003","error_msg":"UNAUTHORIZED USER"}
//        {"from":"en","to":"zh","trans_result":[{"src":"hello","dst":"\u4f60\u597d"}]}
        List<TransBean> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.optInt("error_code", -1);
            if (code == -1) {
                JSONArray result = jsonObject.getJSONArray("trans_result");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject object = result.optJSONObject(i);
                    TransBean transBean = new TransBean();
                    transBean.setSrc(object.optString("src", ""));
                    transBean.setDst(object.optString("dst", ""));
                    list.add(transBean);
                }
                return list;
            } else {
                return list;
            }
        } catch (Exception e) {
            return list;
        }
    }
}
