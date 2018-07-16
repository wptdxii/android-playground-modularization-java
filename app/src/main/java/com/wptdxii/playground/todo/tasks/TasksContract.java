package com.wptdxii.playground.todo.tasks;

import android.support.annotation.NonNull;

import com.wptdxii.framekit.base.Contract;
import com.wptdxii.playground.todo.data.source.Task;

import java.util.List;

public interface TasksContract {

    interface View extends Contract.View {

        void showLoadingIndicator(boolean show);

        void showLoadingError();

        void showNoTasks(TasksFilterType filterType);

        void showFilterLabel(TasksFilterType filterType);

        void showTasks(@NonNull List<Task> tasks);

        void showAddNewTask();

        void showTaskDetails(@NonNull Task task);

        void showTaskChecked(boolean checked);

        void showCompletedTasksCleared();

        void showAllTasksCleared();
    }

    interface Presenter extends Contract.Presenter<View> {

        void loadTasks(boolean forceUpdate, boolean showLoadingIndicator);

        void checkTask(@NonNull Task task);

        void clearCompletedTasks();

        void clearAllTasks();

        TasksFilterType getFilter();

        void setFilter(TasksFilterType filterType);
    }
}
