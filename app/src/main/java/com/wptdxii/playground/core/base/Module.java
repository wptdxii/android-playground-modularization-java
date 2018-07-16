package com.wptdxii.playground.core.base;

import com.wptdxii.framekit.base.BaseActivity;

/**
 * Created by wptdxii on 2018/1/17 0017
 */

public class Module {
    private String mName;
    private Class<? extends BaseActivity> mTarget;

    public Module(String name, Class<? extends BaseActivity> target) {
        this.mName = name;
        this.mTarget = target;
    }

    public String getName() {
        return mName;
    }

    public Class<? extends BaseActivity> getTarget() {
        return mTarget;
    }
}
