package com.module_router.common.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * create by zuyuan on 2019/3/10
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity
        implements IView {

    protected T mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        if (mPresenter != null) {
            mPresenter.attach(this);
        }
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }

    protected abstract int getLayoutId() ;

    protected abstract T createPresenter();

    protected abstract void init();
}
