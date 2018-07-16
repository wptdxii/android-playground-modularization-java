package com.wptdxii.playground.todo.usecase;

import android.support.annotation.NonNull;

import com.wptdxii.framekit.executor.PostExecutionThread;
import com.wptdxii.framekit.executor.ThreadExecutor;
import com.wptdxii.framekit.interactor.FlowableUseCase;
import com.wptdxii.framekit.di.scope.ActivityScoped;
import com.wptdxii.playground.todo.data.TasksRepository;
import com.wptdxii.playground.todo.data.source.Task;

import javax.inject.Inject;

import io.reactivex.Flowable;

@ActivityScoped
public class GetTask extends FlowableUseCase<GetTask.Request, Task> {

    private final TasksRepository mTasksRepository;

    @Inject
    GetTask(ThreadExecutor threadExecutor, PostExecutionThread executionThread,
            @NonNull TasksRepository tasksRepository) {
        super(threadExecutor, executionThread);
        mTasksRepository = tasksRepository;
    }

    @Override
    protected Flowable<Task> buildUseCase(Request request) {
        return mTasksRepository.getTak(request.getTaskId());
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
