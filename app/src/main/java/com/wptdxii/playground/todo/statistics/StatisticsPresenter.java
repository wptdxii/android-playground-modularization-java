package com.wptdxii.playground.todo.statistics;

import android.support.annotation.NonNull;
import android.util.Pair;

import com.wptdxii.framekit.di.scope.FragmentScoped;
import com.wptdxii.playground.todo.statistics.usecase.GetTasksStatistics;

import javax.inject.Inject;

import io.reactivex.subscribers.DisposableSubscriber;

@FragmentScoped
final class StatisticsPresenter implements StatisticsContract.Presenter {

    private StatisticsContract.View mStatisticsView;
    private final GetTasksStatistics mTasksRepository;

    @Inject
    StatisticsPresenter(@NonNull GetTasksStatistics getTasksStatistics) {
        mTasksRepository = getTasksStatistics;
    }

    @Override
    public void attach(StatisticsContract.View view) {
        mStatisticsView = view;
        getTasksStatistics();
    }

    @Override
    public void detach() {
        mTasksRepository.unsubscribe();
        mStatisticsView = null;
    }

    private void getTasksStatistics() {
        mTasksRepository.subscribe(null, new DisposableSubscriber<Pair<Long, Long>>() {
            @Override
            protected void onStart() {
                super.onStart();
                mStatisticsView.showLoadingIndicator(true);
            }

            @Override
            public void onNext(Pair<Long, Long> longLongPair) {
                mStatisticsView.showTasksStatistics(String.valueOf(longLongPair.second),
                        String.valueOf(longLongPair.first));
            }

            @Override
            public void onError(Throwable t) {
                mStatisticsView.showLoadingIndicator(false);
                mStatisticsView.showLoadingStatisticsError();
            }

            @Override
            public void onComplete() {
                mStatisticsView.showLoadingIndicator(false);
            }
        });
    }
}
