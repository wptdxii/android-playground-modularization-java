package com.wptdxii.playground.todo.addedittask.usecase;

import android.support.annotation.NonNull;

import com.wptdxii.framekit.executor.PostExecutionThread;
import com.wptdxii.framekit.executor.ThreadExecutor;
import com.wptdxii.framekit.interactor.CompletableUseCase;
import com.wptdxii.framekit.di.scope.ActivityScoped;
import com.wptdxii.playground.todo.data.TasksRepository;
import com.wptdxii.playground.todo.data.source.Task;

import javax.inject.Inject;

import io.reactivex.Completable;

@ActivityScoped
public class UpdateTask extends CompletableUseCase<UpdateTask.Request> {

    private final TasksRepository mTasksRepository;

    @Inject
    UpdateTask(ThreadExecutor threadExecutor, PostExecutionThread executionThread,
               @NonNull TasksRepository tasksRepository) {
        super(threadExecutor, executionThread);
        mTasksRepository = tasksRepository;
    }

    @Override
    protected Completable buildUseCase(Request request) {
        return Completable.fromAction(() -> mTasksRepository.updateTask(request.getTask()));
    }

    public static final class Request {

        private final Task mTask;

        public Request(Task task) {
            mTask = task;
        }

        public Task getTask() {
            return mTask;
        }
    }
}
