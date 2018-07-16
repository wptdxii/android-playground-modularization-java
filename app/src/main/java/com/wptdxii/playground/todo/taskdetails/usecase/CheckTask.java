package com.wptdxii.playground.todo.taskdetails.usecase;

import android.support.annotation.NonNull;

import com.wptdxii.framekit.executor.PostExecutionThread;
import com.wptdxii.framekit.executor.ThreadExecutor;
import com.wptdxii.framekit.interactor.CompletableUseCase;
import com.wptdxii.framekit.di.scope.ActivityScoped;
import com.wptdxii.playground.todo.data.TasksRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

@ActivityScoped
public class CheckTask extends CompletableUseCase<CheckTask.Request> {

    private final TasksRepository mTasksRepository;

    @Inject
    CheckTask(ThreadExecutor threadExecutor, PostExecutionThread executionThread,
              @NonNull TasksRepository tasksRepository) {
        super(threadExecutor, executionThread);
        mTasksRepository = tasksRepository;
    }

    @Override
    protected Completable buildUseCase(Request request) {
        String taskId = request.getTaskId();
        boolean taskCompelted = request.isTaskCompleted();
        return Completable.fromAction(() -> mTasksRepository.updateTask(taskId, taskCompelted));
    }

    public static final class Request {
        private final String mTaskId;
        private final boolean mTaskCompleted;

        public Request(@NonNull String taskId, boolean taskCompleted) {
            mTaskId = taskId;
            mTaskCompleted = taskCompleted;
        }

        public String getTaskId() {
            return mTaskId;
        }

        public boolean isTaskCompleted() {
            return mTaskCompleted;
        }
    }

}
