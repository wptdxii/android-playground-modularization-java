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
public class DeleteTask extends CompletableUseCase<DeleteTask.Request> {

    private final TasksRepository mTasksRepository;

    @Inject
    DeleteTask(ThreadExecutor threadExecutor, PostExecutionThread executionThread,
               @NonNull TasksRepository tasksRepository) {
        super(threadExecutor, executionThread);
        mTasksRepository = tasksRepository;
    }

    @Override
    protected Completable buildUseCase(Request request) {
        return Completable.fromAction(() -> mTasksRepository.deleteTask(request.getTaskId()));
    }

    public static final class Request {
        private final String mTaskId;

        public Request(@NonNull String taskId) {
            mTaskId = taskId;
        }

        public String getTaskId() {
            return mTaskId;
        }
    }
}
