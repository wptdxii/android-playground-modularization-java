package com.wptdxii.playground.todo.statistics;

import com.wptdxii.framekit.base.Contract;

public interface StatisticsContract {

    interface View extends Contract.View {

        void showLoadingIndicator(boolean show);

        void showTasksStatistics(String active, String completed);

        void showLoadingStatisticsError();
    }

    interface Presenter extends Contract.Presenter<View> {}
}
