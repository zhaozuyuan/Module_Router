package com.module_router.go;

import java.util.HashMap;

/**
 * create by zuyuan on 2019/3/10
 */
public abstract class BaseClassMap {
    protected volatile static HashMap<String, Class> mMap = new HashMap<>();

    public abstract void add();
}
