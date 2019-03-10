package com.module_router.common.mvp;

/**
 * create by zuyuan on 2019/3/10
 */
public abstract class BasePresenter<T extends IView> {
    protected T mView;

    public void attach(T view) {
        mView = view;
    }

    public void detach() {
        mView = null;
    }
}
