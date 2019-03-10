package com.module_router.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.module_router.annotation.Route;
import com.module_router.app.R;
import com.module_router.common.AppConfig;
import com.module_router.common.mvp.BaseActivity;
import com.module_router.common.mvp.BasePresenter;

@Route(name = "chat_activity", module = AppConfig.CHAT_MODULE, desc = "home组件的主活动")
public class ChatActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.chat_activity_chat;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void init() {
        ImageView iv = findViewById(R.id.chat_iv_home);
        Glide.with(this).load(
                "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u" +
                        "=2147059063,3371668217&fm=26&gp=0.jpg")
                .into(iv);

    }
}
