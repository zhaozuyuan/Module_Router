package com.module_router.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.module_router.annotation.Route;
import com.module_router.common.AppConfig;
import com.module_router.go.Go;

@Route(name = "app", module = AppConfig.APP_MODULE, desc = "App壳")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Go.readyToJump(this)
                .setUrl("router://home/home_activity")
                .launch();
    }
}
