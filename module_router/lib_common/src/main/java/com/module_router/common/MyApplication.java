package com.module_router.common;

import android.app.Application;

import com.module_router.go.Go;

/**
 * create by zuyuan on 2019/3/10
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        registerModule();
    }

    private void registerModule() {
        //app壳组件
        Go.registerModule(AppConfig.APP_MODULE);
        //home组件
        Go.registerModule(AppConfig.HOME_MODULE);
        //聊天组件
        Go.registerModule(AppConfig.CHAT_MODULE);
    }
}
