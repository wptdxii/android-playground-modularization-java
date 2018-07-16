package com.wptdxii.playground.todo.statistics.usecase;

import android.util.Pair;

import com.wptdxii.framekit.executor.PostExecutionThread;
import com.wptdxii.framekit.executor.ThreadExecutor;
import com.wptdxii.framekit.interactor.FlowableUseCase;
import com.wptdxii.framekit.di.scope.FragmentScoped;
import com.wptdxii.playground.todo.data.TasksRepository;
import com.wptdxii.playground.todo.data.source.Task;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

@FragmentScoped
public class GetTasksStatistics extends FlowableUseCase<Void, Pair<Long, Long>> {

    private final TasksRepository mTasksRepository;

    @Inject
    GetTasksStatistics(ThreadExecutor threadExecutor, PostExecutionThread executionThread,
                       @NonNull TasksRepository tasksRepository) {
        super(threadExecutor, executionThread);
        mTasksRepository = tasksRepository;
    }

    @Override
    protected Flowable<Pair<Long, Long>> buildUseCase(Void aVoid) {
        Flowable<Task> taskFlowable = mTasksRepository.getTasks()
                .flatMap(Flowable::fromIterable);

        Flowable<Long> completedTasksCount = taskFlowable
                .filter(Task::isCompleted)
                .count()
                .toFlowable();

        Flowable<Long> activeTaskCount = taskFlowable
                .filter(task -> !task.isCompleted())
                .count()
                .toFlowable();

        return Flowable.zip(completedTasksCount, activeTaskCount, Pair::create);
    }
}
