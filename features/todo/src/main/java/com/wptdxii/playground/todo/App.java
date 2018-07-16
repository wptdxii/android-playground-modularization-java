package com.wptdxii.playground.todo;

import com.wptdxii.framekit.Extension;
import com.wptdxii.framekit.base.BaseApplication;
import com.wptdxii.playground.todo.di.DaggerAppComponent;

public class App extends BaseApplication {
    @Override
    protected Extension initExtension() {
        return new Extension.Builder()
                .buildType(BuildConfig.DEBUG)
                .build();
    }

    @Override
    protected void initInjector() {
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);
    }
}
