package com.yanyi.translation;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author YanYi
 * @date 2019/3/27 11:28x
 * @email ben@yanyi.red
 * @overview
 */
public class Translation extends AnAction {
    private static final String APP_ID = "20181221000250890";
    private static final String SECURITY_KEY = "TfP62BRxA7t1QNiyWxAE";

    @Override
    public void actionPerformed(AnActionEvent e) {
//        String text = getSysClipboardText();
//        if (text == null || text.equals("") || text.trim().equals("") || text.length() <= 0) {
//            isDialog();
//        } else {
//            trans(text);
//        }
        isDialog();
    }

    private void isDialog() {
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
                    if (str.length() > 0) {
                        str.deleteCharAt(str.length() - 1);
                    }
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
//        //这个回调是线程里面的，所以要加个方法，不然会报错
//        ApplicationManager.getApplication().invokeLater(() -> {
//            //弹出翻译结果对话框
            Messages.showMessageDialog(text + "\n" + "->" + "\n" + msg, "翻译结果", Messages.getInformationIcon());
            isDialog();
//            String str = Messages.showInputDialog(text, "翻译结果", Messages.getInformationIcon(), msg, null);
//            trans(str);
//        });
    }

    /**
     * 从剪切板获得文字。
     */
    private String getSysClipboardText() {
        String ret = "";
        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 获取剪切板中的内容
        Transferable clipTf = sysClip.getContents(null);
        if (clipTf != null) {
            // 检查内容是否是文本类型
            if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    ret = (String) clipTf
                            .getTransferData(DataFlavor.stringFlavor);
                    return ret;
                } catch (Exception e) {
                    return "";
                }
            }
        }
        return ret;
    }
}
