package com.wptdxii.playground.todo.taskdetails;

import android.support.annotation.NonNull;

import com.wptdxii.framekit.util.Strings;
import com.wptdxii.playground.todo.data.source.Task;
import com.wptdxii.playground.todo.taskdetails.usecase.CheckTask;
import com.wptdxii.playground.todo.taskdetails.usecase.DeleteTask;
import com.wptdxii.playground.todo.usecase.GetTask;

import javax.inject.Inject;

import io.reactivex.subscribers.DisposableSubscriber;

final class TaskDetailsPresenter implements TaskDetailsContract.Presenter {

    private TaskDetailsContract.View mTaskDetailsView;
    private final String mTaskId;
    private final GetTask mGetTask;
    private final DeleteTask mDeleteTask;
    private final CheckTask mCheckTask;

    @Inject
    TaskDetailsPresenter(@NonNull String taskId, @NonNull GetTask getTask,
                         @NonNull DeleteTask deleteTask, @NonNull CheckTask checkTask) {
        mTaskId = taskId;
        mGetTask = getTask;
        mDeleteTask = deleteTask;
        mCheckTask = checkTask;
    }

    @Override
    public void attach(TaskDetailsContract.View view) {
        mTaskDetailsView = view;
        getTask();
    }

    @Override
    public void detach() {
        mGetTask.unsubscribe();
        mDeleteTask.unsubscribe();
        mCheckTask.unsubscribe();
        mTaskDetailsView = null;
    }

    private static final String TAG = "TaskDetailsPresenter";

    private void getTask() {

        GetTask.Request request = new GetTask.Request(mTaskId);
        mGetTask.subscribe(request, new DisposableSubscriber<Task>() {
            @Override
            protected void onStart() {
                super.onStart();
                mTaskDetailsView.showLoadingIndicator();
            }

            @Override
            public void onNext(Task task) {
                showTask(task);
            }

            @Override
            public void onError(Throwable t) {
                mTaskDetailsView.showMissingTask();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void showTask(Task task) {
        mTaskDetailsView.showTaskCompletionStatus(task.isCompleted());

        String title = task.getTitle();
        String description = task.getDescription();
        if (Strings.isEmpty(task.getTitle())) {
            mTaskDetailsView.hideTaskTitle();
        } else {
            mTaskDetailsView.showTaskTitle(title);
        }

        if (Strings.isEmpty(task.getDescription())) {
            mTaskDetailsView.hideTaskDescription();
        } else {
            mTaskDetailsView.showTaskDescription(description);
        }


    }

    @Override
    public void editTask() {
        mTaskDetailsView.showEditTask(mTaskId);
    }

    @Override
    public void deleteTask() {
        DeleteTask.Request request = new DeleteTask.Request(mTaskId);
        mDeleteTask.subscribe(request, () -> mTaskDetailsView.showTaskDeleted());
    }

    @Override
    public void checkTask(boolean checked) {
        CheckTask.Request request = new CheckTask.Request(mTaskId, checked);
        mCheckTask.subscribe(request, () -> mTaskDetailsView.showTaskChecked(checked));
    }
}
