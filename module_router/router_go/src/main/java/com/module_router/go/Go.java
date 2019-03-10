package com.module_router.go;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * create by zuyuan on 2019/3/10
 */
public class Go {
    private static Go go;

    private GoAttr mAttr;

    private long mRecordTime = -1000;

    private int mRecordCode = -1;

    public static final long MIN_TIME  = 500;

    public static final String WEB_ACTIVITY_URL = "router://common/web_activity";

    public static final String WEB_KEY = "web_key";

    private static final String mRegex =
            "^(router://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$ ";


    private Go() {
    }

    public static Go readyToJump(Context context) {
        if (go == null) {
            go = new Go();
            go.mAttr = new GoAttr();
        }
        go.mAttr.setContext(context);
        return go;
    }

    /**
     * 注册组件
     */
    public static void registerModule(String name) {
        String path = "com.module_router." + name + "." + name + "$$Inject";
        try {
            Class cla = Class.forName(path);
            BaseClassMap map = (BaseClassMap) cla.newInstance();
            map.add();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Fragment
     */
    public static Class<? extends Fragment> getFragment(String url) {
        Class cla = BaseClassMap.mMap.get(url);
        if (!cla.isInstance(Fragment.class)) cla = null;
        return cla;
    }

    /**
     * 启动
     */
    public void launch() {
        Class a = getTargetClass(mAttr.getUrl());
        if (a != null) {
            realLaunch(a);
        }
        mAttr.clear();
    }

    private void realLaunch(Class a) {
        Intent intent = new Intent(mAttr.getContext(), a);
        intent.putExtra(mAttr.getKey(), mAttr.getText());
        if (mAttr.getBundle() != null) {
            mAttr.getContext().startActivity(intent, mAttr.getBundle());
        } else {
            mAttr.getContext().startActivity(intent);
        }
        if (mAttr.getEnterAnim() != -1 && mAttr.getExitAnim() != -1) {
            AppCompatActivity activity = (AppCompatActivity) mAttr.getContext();
            //设置动画
            activity.overridePendingTransition(mAttr.getEnterAnim(), mAttr.getExitAnim());
        }
    }

    public Go setUrl(String url) {
        mAttr.setUrl(url);
        return this;
    }

    public Go setBundle(Bundle bundle) {
        mAttr.setBundle(bundle);
        return this;
    }

    public Go setExtra(String key, String text) {
        mAttr.setExtra(key, text);
        return this;
    }

    public Go setAnimatation(int enterAnim, int exitAnim) {
        if (enterAnim <= 0 || exitAnim <= 0) {
            return this;
        }
        mAttr.setEnterAnim(enterAnim);
        mAttr.setExitAnim(exitAnim);

        return this;
    }

    public Go setCallBack(CallBack callBack) {
        mAttr.setCallBack(callBack);
        return this;
    }

    public boolean isNetworkUrl(String url) {
        if (url.length() < 6 || url.substring(0, 6).equals("router")) {
            return false;
        }
        return url.substring(0, 7).equals("http://") ||url.substring(0, 8).equals("https://");
    }

    private Class getTargetClass(String url) {
        Class a ;
        if (isNetworkUrl(url)) {
            a = BaseClassMap.mMap.get(WEB_ACTIVITY_URL);
            //MatesConfig.TO_WEB_KEY
            mAttr.setExtra(WEB_KEY, url);
        } else {
            a = BaseClassMap.mMap.get(url);
        }

        if (a == null) {
            //失败
            mAttr.getCallBack().error(parseUrl(url));
            return null;
        }
        if (!Activity.class.isAssignableFrom(a) ) {
            mAttr.getCallBack().error(CallBack.CLASS_ERROR);
            return null;
        }
        long nowTime = System.currentTimeMillis();
        mRecordTime = nowTime;
        if (mRecordCode == a.hashCode() && nowTime - mRecordTime <= MIN_TIME) {
            mAttr.getCallBack().error(CallBack.FREQUENT_OPERATION);
            mRecordCode = a.hashCode();
            return null;
        }
        return a;
    }

    private int parseUrl(String url) {
        if (!url.contains("router://")) {
            return CallBack.URL_ERROR;
        }
        return CallBack.NOT_FOUND;
    }

    public interface CallBack {
        int URL_ERROR = 0x1234561;
        int NOT_FOUND = 0x1234562;
        int CLASS_ERROR = 0x1234563;
        int FREQUENT_OPERATION = 0x1234564;

        void error(int code);
    }
}
