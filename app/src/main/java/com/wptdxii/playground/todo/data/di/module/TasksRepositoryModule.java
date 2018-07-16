package com.wptdxii.playground.todo.data.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.wptdxii.framekit.di.qualifier.AppContext;
import com.wptdxii.playground.todo.data.di.qualifier.Local;
import com.wptdxii.playground.todo.data.di.qualifier.Remote;
import com.wptdxii.playground.todo.data.source.TasksDataSource;
import com.wptdxii.playground.todo.data.source.local.TasksDao;
import com.wptdxii.playground.todo.data.source.local.TasksLocalDataSource;
import com.wptdxii.playground.todo.data.source.local.ToDoDatabase;
import com.wptdxii.playground.todo.data.source.remote.TasksRemoteDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class TasksRepositoryModule {

    @Binds
    @Singleton
    @Local
    abstract TasksDataSource provideTasksLocalDataSource(TasksLocalDataSource dataSource);


    @Binds
    @Singleton
    @Remote
    abstract TasksDataSource provideTasksRemoteDataSource(TasksRemoteDataSource dataSource);

    @Singleton
    @Provides
    static ToDoDatabase provideDatabase(@AppContext Context context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                ToDoDatabase.class,
                ToDoDatabase.DB_NAME_TODO)
                .build();
    }

    @Singleton
    @Provides
    static TasksDao provideTasksDao(ToDoDatabase toDoDatabase) {
        return toDoDatabase.tasksDao();
    }
}
