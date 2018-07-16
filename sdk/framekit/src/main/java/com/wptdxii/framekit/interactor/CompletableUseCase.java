package com.wptdxii.framekit.interactor;

import com.wptdxii.framekit.executor.PostExecutionThread;
import com.wptdxii.framekit.executor.ThreadExecutor;

import io.reactivex.Completable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public abstract class CompletableUseCase<Request> {

    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mExecutionThread;
    private final CompositeDisposable mCompositeDisposable;

    public CompletableUseCase(@NonNull ThreadExecutor threadExecutor,
                              @NonNull PostExecutionThread executionThread) {
        mThreadExecutor = threadExecutor;
        mExecutionThread = executionThread;
        mCompositeDisposable = new CompositeDisposable();
    }

    protected abstract Completable buildUseCase(Request request);

    public void subscribe(Request request, Action response) {
        mCompositeDisposable.clear();
        Disposable disposable = buildUseCase(request)
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mExecutionThread.getScheduler())
                .subscribe(response);
        mCompositeDisposable.add(disposable);
    }

    public void unsubscribe() {
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }
}
