package com.wptdxii.framekit.executor;

import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by wptdxii on 2018/1/24 0024 14:59
 */
@Singleton
public class AppExecutors {
    private final Executor mDiskIO;
    private final Executor mNetworkIO;
    private final Executor mMainThread;

    @Inject
    AppExecutors() {
        this.mDiskIO = new DiskIOThreadExecutor();
        this.mNetworkIO = new NetworkIOExecutor();
        this.mMainThread = new MainThreadExecutor();
    }

    public Executor getDiskIO() {
        return mDiskIO;
    }

    public Executor getNetworkIO() {
        return mNetworkIO;
    }

    public Executor getMainThread() {
        return mMainThread;
    }
}
