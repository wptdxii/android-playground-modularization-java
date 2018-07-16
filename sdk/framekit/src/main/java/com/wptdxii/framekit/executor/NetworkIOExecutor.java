package com.wptdxii.framekit.executor;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * <p>
 * Executor that runs a task on multiple thread.
 * </p>
 * Created by wptdxii on 2018/1/24
 */

public class NetworkIOExecutor implements Executor {

    private static final int THREAD_COUNT = 3;

    private final Executor mNetworkIO;

    NetworkIOExecutor() {
        this.mNetworkIO = Executors.newFixedThreadPool(THREAD_COUNT);
    }

    @Override
    public void execute(@NonNull Runnable command) {
        mNetworkIO.execute(command);
    }
}
