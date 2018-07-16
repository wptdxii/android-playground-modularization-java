package com.wptdxii.playground.todo.data.source.local;

import android.support.annotation.NonNull;

import com.wptdxii.framekit.executor.AppExecutors;
import com.wptdxii.playground.todo.data.source.Task;
import com.wptdxii.playground.todo.data.source.TasksDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public final class TasksLocalDataSource implements TasksDataSource {

    private static volatile TasksLocalDataSource sINSTANCE;

    private final AppExecutors mAppExecutors;
    private final TasksDao mTasksDao;

    @Inject
    TasksLocalDataSource(final AppExecutors appExecutors, final TasksDao tasksDao) {
        this.mAppExecutors = appExecutors;
        this.mTasksDao = tasksDao;
    }

    public static TasksLocalDataSource getInstance(@NonNull AppExecutors appExecutors, @NonNull TasksDao tasksDao) {
        TasksLocalDataSource instance = sINSTANCE;
        if (instance == null) {
            synchronized (TasksLocalDataSource.class) {
                instance = sINSTANCE;
                if (instance == null) {
                    sINSTANCE = instance = new TasksLocalDataSource(appExecutors, tasksDao);
                }
            }
        }
        return instance;
    }

    @Override
    public void saveTask(@NonNull Task task) {
        mAppExecutors.getDiskIO().execute(() -> mTasksDao.insertTask(task));
    }

    @Override
    public void deleteTask(@NonNull Task task) {
        mAppExecutors.getDiskIO().execute(() -> mTasksDao.deleteTask(task));
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        mAppExecutors.getDiskIO().execute(() -> mTasksDao.deleteTask(taskId));
    }

    @Override
    public void deleteCompletedTasks() {
        mAppExecutors.getDiskIO().execute(mTasksDao::deleteCompletedTasks);
    }

    @Override
    public void deleteAllTasks() {
        mAppExecutors.getDiskIO().execute(mTasksDao::deleteAllTasks);
    }

    @Override
    public void updateTask(@NonNull String taskId, boolean completed) {
        mAppExecutors.getDiskIO().execute(() -> mTasksDao.updateTask(taskId, completed));
    }

    @Override
    public void updateTask(@NonNull Task task) {
        mAppExecutors.getDiskIO().execute(() -> mTasksDao.updateTask(task));
    }

    @Override
    public Flowable<List<Task>> getTasks() {
        return mTasksDao
                .getTasks()
                .toFlowable();
    }

    @Override
    public Flowable<Task> getTak(@NonNull String taskId) {
        return mTasksDao
                .getTask(taskId)
                .toFlowable();
    }
}
