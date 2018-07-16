package com.wptdxii.playground.todo.addedittask;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wptdxii.framekit.util.Strings;
import com.wptdxii.playground.todo.addedittask.usecase.SaveTask;
import com.wptdxii.playground.todo.addedittask.usecase.UpdateTask;
import com.wptdxii.playground.todo.data.source.Task;
import com.wptdxii.playground.todo.usecase.GetTask;

import java.util.Objects;

import javax.inject.Inject;

import dagger.Lazy;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Dagger generated code doesn't require public access to the constructor or class, and
 * therefore, to ensure the developer doesn't instantiate the class manually bypassing Dagger,
 * it's good practice minimise the visibility of the class/constructor as much as possible.
 */
final class AddEditPresenter implements AddEditContract.Presenter {

    private AddEditContract.View mAddEditView;
    private final String mTaskId;
    private final Lazy<Boolean> mIsDataMissingProvider;
    private boolean mIsDataMissing;
    private final GetTask mGetTask;
    private final SaveTask mSaveTask;
    private final UpdateTask mUpdateTask;

    @Inject
    AddEditPresenter(@Nullable String taskId, @NonNull GetTask getTask, @NonNull SaveTask saveTask,
                     @NonNull UpdateTask updateTask, Lazy<Boolean> shouldLoadDataFromRepo) {
        mTaskId = taskId;
        mGetTask = getTask;
        mSaveTask = saveTask;
        mUpdateTask = updateTask;
        mIsDataMissingProvider = shouldLoadDataFromRepo;
    }

    @Override
    public void attach(AddEditContract.View view) {
        mAddEditView = view;
        mIsDataMissing = mIsDataMissingProvider.get();
        if (!Strings.isEmpty(mTaskId) && mIsDataMissing) {
            getTask();
        }
    }

    @Override
    public void detach() {
        mGetTask.unsubscribe();
        mSaveTask.unsubscribe();
        mUpdateTask.unsubscribe();
        mAddEditView = null;
    }

    @Override
    public void saveTask(String title, String description) {
        if (mTaskId == null) {
            createTask(title, description);
        } else {
            updateTask(title, description);
        }
    }

    @Override
    public void getTask() {
        if (mTaskId != null) {
            GetTask.Request request = new GetTask.Request(mTaskId);
            mGetTask.subscribe(request, new DisposableSubscriber<Task>() {
                @Override
                public void onNext(Task task) {
                    mAddEditView.showTask(task);
                    mIsDataMissing = false;
                }

                @Override
                public void onError(Throwable t) {
                    mAddEditView.showEmptyTaskError();
                }

                @Override
                public void onComplete() {}
            });
        }
    }

    private void updateTask(String title, String description) {
        Task task = Task.createNewTaskWithId(Objects.requireNonNull(mTaskId), title, description);
        if (task.isEmpty()) {
            mAddEditView.showEmptyTaskError();
            return;
        }

        UpdateTask.Request request = new UpdateTask.Request(task);
        mUpdateTask.subscribe(request, () -> mAddEditView.showTasksList());
    }

    private void createTask(String title, String description) {
        Task newTask = Task.createNewTask(title, description);
        if (newTask.isEmpty()) {
            mAddEditView.showEmptyTaskError();
            return;
        }

        SaveTask.Request request = new SaveTask.Request(newTask);
        mSaveTask.subscribe(request, () -> mAddEditView.showTasksList());
    }

    @Override
    public boolean isDataMissing() {
        return mIsDataMissing;
    }
}
