package com.yanyi.translation;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ui.Messages;

import java.util.ArrayList;
import java.util.List;


/**
 * @author YanYi
 * @date 2019/3/27 11:28
 * @email ben@yanyi.red
 * @overview
 */
public class Translation extends AnAction {
    private static final String APP_ID = "20181221000250890";
    private static final String SECURITY_KEY = "TfP62BRxA7t1QNiyWxAE";

    @Override
    public void actionPerformed(AnActionEvent e) {
        String text = Messages.showInputDialog("请输入要翻译的内容", "提示", Messages.getQuestionIcon());
        trans(text);
    }

    private void trans(String text) {
        if (text != null && !text.isEmpty()) {
            TransApi transApi = new TransApi(APP_ID, SECURITY_KEY);
            String languageResult = transApi.getLanguageResult(text);
            String language = JsonUtil.jsonToLanguage(languageResult);
            if (language != null && !language.isEmpty()) {
                List<TransBean> list = new ArrayList<>();
                if (language.equals("zh")) {
                    list.addAll(JsonUtil.jsonToTrans(transApi.getTransResult(text, "auto", "en")));
                } else {
                    list.addAll(JsonUtil.jsonToTrans(transApi.getTransResult(text, "auto", "zh")));
                }
                if (!list.isEmpty()) {
                    StringBuilder str = new StringBuilder();
                    for (TransBean transBean : list) {
                        if (transBean.getSrc() != null && !transBean.getSrc().isEmpty() && transBean.getSrc().equals(text)) {
                            str.append(transBean.getDst()).append(";");
                        }
                    }
                    str.deleteCharAt(str.length() - 1);
                    show(text, str.toString());
                } else {
                    show(text, "翻译失败");
                }
            } else {
                show(text, "翻译失败");
            }
        }
    }

    private void show(String text, String msg) {
        //这个回调是线程里面的，所以要加个方法，不然会报错
        ApplicationManager.getApplication().invokeLater(() -> {
            //弹出翻译结果对话框
            String str = Messages.showInputDialog(text, "翻译结果", Messages.getInformationIcon(), msg, null);
            trans(str);
        });
    }
}
