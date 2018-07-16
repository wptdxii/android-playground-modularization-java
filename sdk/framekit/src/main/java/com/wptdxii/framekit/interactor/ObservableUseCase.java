package com.wptdxii.framekit.interactor;

import android.support.annotation.NonNull;

import com.wptdxii.framekit.executor.PostExecutionThread;
import com.wptdxii.framekit.executor.ThreadExecutor;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class ObservableUseCase<Request, Response> {
    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mExecutionThread;
    private final CompositeDisposable mCompositeDisposable;

    public ObservableUseCase(@NonNull ThreadExecutor threadExecutor,
                             @NonNull PostExecutionThread executionThread) {
        mThreadExecutor = threadExecutor;
        mExecutionThread = executionThread;
        mCompositeDisposable = new CompositeDisposable();
    }

    protected abstract Observable<Response> buildUseCase(Request request);

    public void subscribe(Request request, DisposableObserver<Response> observer) {
        DisposableObserver<Response> disposable = buildUseCase(request)
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mExecutionThread.getScheduler())
                .subscribeWith(observer);
        mCompositeDisposable.add(disposable);
    }

    public void unsubscribe() {
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }
}
