package com.wptdxii.framekit.interactor;

import com.wptdxii.framekit.executor.PostExecutionThread;
import com.wptdxii.framekit.executor.ThreadExecutor;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public abstract class FlowableUseCase<Request, Response> {

    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mExecutionThread;
    private final CompositeDisposable mCompositeDisposable;

    public FlowableUseCase(@NonNull ThreadExecutor threadExecutor, @NonNull PostExecutionThread
            executionThread) {
        mThreadExecutor = threadExecutor;
        mExecutionThread = executionThread;
        mCompositeDisposable = new CompositeDisposable();
    }

    protected abstract Flowable<Response> buildUseCase(Request request);

    public void subscribe(Request request, DisposableSubscriber<Response> subscriber) {
        DisposableSubscriber disposable = buildUseCase(request)
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mExecutionThread.getScheduler())
                .subscribeWith(subscriber);
        mCompositeDisposable.add(disposable);
    }

    public void unsubscribe() {
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }
}
