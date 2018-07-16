package com.wptdxii.playground.todo.taskdetails;

import com.wptdxii.framekit.di.scope.ActivityScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class TaskDetailsModule {

    @Binds
    @ActivityScoped
    abstract TaskDetailsContract.Presenter providePresenter(TaskDetailsPresenter presenter);

    @Provides
    @ActivityScoped
    static String provideTaskId(TaskDetailsActivity activity) {
        return activity.getIntent().getStringExtra(TaskDetailsActivity.EXTRA_TASK_ID);
    }
}
