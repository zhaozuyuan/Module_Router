package com.module_router.go;

import android.content.Context;
import android.os.Bundle;

/**
 * create by zuyuan on 2019/3/10
 */
public class GoAttr {
    private int enterAnim = -1, exitAnim = -1;
    private Go.CallBack callBack = defaultCallBack;
    private Context context = null;
    private Bundle bundle = null;
    private String key = "default";
    private String text = "";
    private String url = "";

    /**
     * init
     */
    public void clear() {
        enterAnim = -1;
        exitAnim = -1;
        callBack = defaultCallBack;
        context = null;
        bundle = null;
        key = "default";
        text = "";
        url = "";
    }

    /**
     * default callback
     */
    private static Go.CallBack defaultCallBack = new Go.CallBack() {
        @Override
        public void error(int code) {
        }
    };

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getEnterAnim() {
        return enterAnim;
    }

    public void setEnterAnim(int enterAnim) {
        this.enterAnim = enterAnim;
    }

    public int getExitAnim() {
        return exitAnim;
    }

    public void setExitAnim(int exitAnim) {
        this.exitAnim = exitAnim;
    }

    public Go.CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(Go.CallBack callBack) {
        this.callBack = callBack;
    }

    public void setExtra(String key, String text) {
        this.text = text;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
