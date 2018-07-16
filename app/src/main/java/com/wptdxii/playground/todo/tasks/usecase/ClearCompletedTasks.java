package com.wptdxii.playground.todo.tasks.usecase;

import android.support.annotation.NonNull;

import com.wptdxii.framekit.executor.PostExecutionThread;
import com.wptdxii.framekit.executor.ThreadExecutor;
import com.wptdxii.framekit.interactor.CompletableUseCase;
import com.wptdxii.framekit.di.scope.ActivityScoped;
import com.wptdxii.playground.todo.data.TasksRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

@ActivityScoped
public class ClearCompletedTasks extends CompletableUseCase<Void> {
    private final TasksRepository mTasksRepository;

    @Inject
    ClearCompletedTasks(ThreadExecutor threadExecutor, PostExecutionThread executionThread,
                        @NonNull TasksRepository tasksRepository) {
        super(threadExecutor, executionThread);
        mTasksRepository = tasksRepository;
    }

    @Override
    protected Completable buildUseCase(Void aVoid) {
        return Completable.fromAction(mTasksRepository::deleteCompletedTasks);
    }
}
