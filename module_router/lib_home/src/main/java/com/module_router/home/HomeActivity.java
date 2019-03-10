package com.module_router.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.module_router.annotation.Route;
import com.module_router.app.R;
import com.module_router.common.AppConfig;
import com.module_router.common.mvp.BaseActivity;
import com.module_router.common.mvp.BasePresenter;
import com.module_router.go.Go;

@Route(name = "home_activity", module = AppConfig.HOME_MODULE, desc = "home组件的主活动")
public class HomeActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.home_activity_home;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void init() {
        ImageView iv = findViewById(R.id.home_iv_home);
        Glide.with(this).load(
                "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u" +
                        "=1576235375,1901414160&fm=26&gp=0.jpg")
                .into(iv);
    }

    public void onClick(View view) {
        Go.readyToJump(this)
                .setAnimatation(R.anim.home_in_chat, R.anim.home_out_chat)
                .setUrl("router://chat/chat_activity")
                .setCallBack(new Go.CallBack() {
                    @Override
                    public void error(int code) {
                        Log.d("TAG", "目标类类型错误-->" + (code == Go.CallBack.CLASS_ERROR));
                        Log.d("TAG", "未找到目标类-->" + (code == Go.CallBack.NOT_FOUND));
                        Log.d("TAG", "URL错误-->" + (code == Go.CallBack.URL_ERROR));
                        Log.d("TAG", "操作频繁-->" + (code == Go.CallBack.FREQUENT_OPERATION));
                    }
                })
                .setExtra("", "")
                .setBundle(new Bundle())
                .launch();
    }
}
