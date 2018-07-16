package com.wptdxii.playground.todo.di;

import com.wptdxii.framekit.di.scope.ActivityScoped;
import com.wptdxii.playground.todo.addedittask.AddEditActivity;
import com.wptdxii.playground.todo.addedittask.AddEditModule;
import com.wptdxii.playground.todo.data.di.module.TasksRepositoryModule;
import com.wptdxii.playground.todo.statistics.StatisticsActivity;
import com.wptdxii.playground.todo.statistics.StatisticsModule;
import com.wptdxii.playground.todo.taskdetails.TaskDetailsActivity;
import com.wptdxii.playground.todo.taskdetails.TaskDetailsModule;
import com.wptdxii.playground.todo.tasks.TasksActivity;
import com.wptdxii.playground.todo.tasks.TasksModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = TasksRepositoryModule.class)
public abstract class ToDoBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = TasksModule.class)
    abstract TasksActivity tasksActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {TaskDetailsModule.class})
    abstract TaskDetailsActivity taskDetailsActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = StatisticsModule.class)
    abstract StatisticsActivity statisticsActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = AddEditModule.class)
    abstract AddEditActivity addEditActivity();
}
