package com.wptdxii.playground.todo.tasks;

import com.wptdxii.framekit.di.scope.ActivityScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TasksModule {

    @ContributesAndroidInjector
    abstract TasksFragment tasksFragment();

    @Provides
    @ActivityScoped
    static TasksFragment provideTasksFragment() {
        return TasksFragment.newInstance();
    }

    @Binds
    @ActivityScoped
    abstract TasksContract.Presenter providePresenter(TasksPresenter presenter);
}
