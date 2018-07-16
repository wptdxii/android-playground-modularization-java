package com.wptdxii.framekit.executor;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * <p>
 * Executor that runs a task on main thread.
 * </p>
 * Created by wptdxii on 2018/1/24
 */

public class MainThreadExecutor implements Executor {

    private final Handler mHandler;

    MainThreadExecutor() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void execute(@NonNull Runnable command) {
        mHandler.post(command);
    }
}
