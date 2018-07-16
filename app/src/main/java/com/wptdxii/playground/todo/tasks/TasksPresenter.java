package com.wptdxii.playground.todo.tasks;

import android.support.annotation.NonNull;
import android.util.Log;

import com.wptdxii.framekit.di.scope.ActivityScoped;
import com.wptdxii.playground.todo.data.source.Task;
import com.wptdxii.playground.todo.tasks.usecase.CheckTask;
import com.wptdxii.playground.todo.tasks.usecase.ClearAllTasks;
import com.wptdxii.playground.todo.tasks.usecase.ClearCompletedTasks;
import com.wptdxii.playground.todo.tasks.usecase.GetTasks;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.subscribers.DisposableSubscriber;

@ActivityScoped
final class TasksPresenter implements TasksContract.Presenter {

    private TasksContract.View mTaskView;
    private TasksFilterType mFilterType = TasksFilterType.ALL_TASKS;
    private boolean mFirstLoad = true;

    private final CheckTask mCheckTask;
    private final ClearAllTasks mClearAllTasks;
    private final ClearCompletedTasks mClearCompletedTasks;
    private final GetTasks mGetTasks;

    @Inject
    TasksPresenter(@NonNull CheckTask checkTask,
                   @NonNull ClearAllTasks clearAllTasks,
                   @NonNull ClearCompletedTasks clearCompletedTasks,
                   @NonNull GetTasks getTasks) {
        mCheckTask = checkTask;
        mClearAllTasks = clearAllTasks;
        mClearCompletedTasks = clearCompletedTasks;
        mGetTasks = getTasks;
    }

    @Override
    public void checkTask(@NonNull Task task) {
        CheckTask.Request request = new CheckTask.Request(task);
        mCheckTask.subscribe(request, () -> loadTasks(false, false));
    }

    @Override
    public void clearCompletedTasks() {
        mClearCompletedTasks.subscribe(null, () -> {
            loadTasks(false, false);
            mTaskView.showCompletedTasksCleared();
        });
    }

    @Override
    public void clearAllTasks() {
        mClearAllTasks.subscribe(null, () -> {
            mTaskView.showAllTasksCleared();
            mTaskView.showNoTasks(mFilterType);
        });
    }


    @Override
    public void loadTasks(boolean forceUpdate, boolean showLoadingIndicator) {
        GetTasks.Request request = new GetTasks.Request(forceUpdate, mFilterType);
        mGetTasks.subscribe(request, new DisposableSubscriber<List<Task>>() {
            @Override
            protected void onStart() {
                super.onStart();
                mTaskView.showLoadingIndicator(showLoadingIndicator || mFirstLoad);
                mFirstLoad = false;
            }

            @Override
            public void onNext(List<Task> tasks) {
                processTasks(tasks);
            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG, "onError: " + t.getStackTrace());
                mTaskView.showLoadingIndicator(false);
                mTaskView.showNoTasks(mFilterType);
            }

            @Override
            public void onComplete() {
                mTaskView.showLoadingIndicator(false);
            }
        });
    }

    private static final String TAG = "TasksPresenter";

    private void processTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            mTaskView.showNoTasks(mFilterType);
        } else {
            mTaskView.showTasks(tasks);
            mTaskView.showFilterLabel(mFilterType);
        }
    }

    @Override
    public TasksFilterType getFilter() {
        return mFilterType;
    }

    @Override
    public void setFilter(TasksFilterType filterType) {
        this.mFilterType = filterType;
    }

    @Override
    public void attach(TasksContract.View view) {
        this.mTaskView = view;
        loadTasks(false, false);
    }

    @Override
    public void detach() {
        mCheckTask.unsubscribe();
        mGetTasks.unsubscribe();
        mClearCompletedTasks.unsubscribe();
        mClearAllTasks.unsubscribe();
        mTaskView = null;
    }
}
