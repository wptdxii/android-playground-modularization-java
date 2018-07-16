package com.wptdxii.playground.todo.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.wptdxii.framekit.util.Collections;
import com.wptdxii.playground.todo.data.di.qualifier.Local;
import com.wptdxii.playground.todo.data.di.qualifier.Remote;
import com.wptdxii.playground.todo.data.source.Task;
import com.wptdxii.playground.todo.data.source.TasksDataSource;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public final class TasksRepository implements TasksDataSource {

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<String, Task> mTasksCachedDataSource = new LinkedHashMap<>();

    private TasksDataSource mTasksLocalDataSource;
    private TasksDataSource mTasksRemoteDataSource;

    private boolean mCacheDirty = false;

    @Inject
    public TasksRepository(@Local TasksDataSource localDataSource,
                           @Remote TasksDataSource remoteDataSource) {
        this.mTasksLocalDataSource = localDataSource;
        this.mTasksRemoteDataSource = remoteDataSource;
    }

    @Override
    public void saveTask(@NonNull Task task) {
        mTasksCachedDataSource.put(task.getId(), task);
        mTasksLocalDataSource.saveTask(task);
        mTasksRemoteDataSource.saveTask(task);
    }

    @Override
    public void deleteTask(@NonNull Task task) {
        mTasksCachedDataSource.remove(task.getId());
        mTasksLocalDataSource.deleteTask(task);
        mTasksRemoteDataSource.deleteTask(task);
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        mTasksCachedDataSource.remove(taskId);
        mTasksLocalDataSource.deleteTask(taskId);
        mTasksRemoteDataSource.deleteTask(taskId);
    }

    @Override
    public void deleteCompletedTasks() {
        if (!mTasksCachedDataSource.isEmpty()) {
            Iterator<Map.Entry<String, Task>> iterator = mTasksCachedDataSource.entrySet()
                    .iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getValue().isCompleted()) iterator.remove();
            }
        }

        mTasksLocalDataSource.deleteCompletedTasks();
        mTasksRemoteDataSource.deleteCompletedTasks();
    }

    @Override
    public void deleteAllTasks() {
        mTasksCachedDataSource.clear();
        mTasksLocalDataSource.deleteAllTasks();
        mTasksRemoteDataSource.deleteAllTasks();
    }

    @Override
    public void updateTask(@NonNull String taskId, boolean completed) {
        final Task task = mTasksCachedDataSource.get(taskId);
        if (task != null) {
            task.setCompleted(completed);
        }

        mTasksLocalDataSource.updateTask(taskId, completed);
        mTasksRemoteDataSource.updateTask(taskId, completed);
    }

    @Override
    public void updateTask(@NonNull Task task) {
        mTasksCachedDataSource.put(task.getId(), task);
        mTasksLocalDataSource.updateTask(task);
        mTasksRemoteDataSource.updateTask(task);
    }

    @Override
    public Flowable<Task> getTak(@NonNull String taskId) {
        final Task cachedTask = mTasksCachedDataSource.get(taskId);
        if (cachedTask != null) {
            return Flowable.just(cachedTask);
        }

        Flowable<Task> localTaskFlowable = mTasksLocalDataSource.getTak(taskId)
                .doOnNext(task -> {
                    if (task != null) {
                        mTasksCachedDataSource.put(task.getId(), task);
                    }
                })
                .firstElement()
                .toFlowable();

        Flowable<Task> remoteTaskFlowable =
                mTasksRemoteDataSource.getTak(taskId)
                        .doOnNext(task -> {
                            if (task != null) {
                                mTasksCachedDataSource.put(taskId, task);
                                mTasksLocalDataSource.saveTask(task);
                            }
                        });
        return Flowable.concat(localTaskFlowable, remoteTaskFlowable)
                .firstElement()
                .toFlowable();
    }

    @Override
    public Flowable<List<Task>> getTasks() {

        if (!mCacheDirty) {
            Flowable<List<Task>> cachedTasks = Flowable
                    .fromIterable(Collections.newArrayList(mTasksCachedDataSource.values()))
                    .toList()
                    .toFlowable();
            Flowable<List<Task>> localTasks = getTasksFromLocal();
            return Flowable
                    .concat(cachedTasks, localTasks)
                    .filter(tasks -> !tasks.isEmpty())
                    .firstOrError()
                    .toFlowable();
        }

        return getTasksFromRemote();
    }

    private Flowable<List<Task>> getTasksFromRemote() {
        return mTasksRemoteDataSource
                .getTasks()
                .flatMap(tasks -> Flowable.fromIterable(tasks)
                        .doOnNext(task -> {
                            mTasksCachedDataSource.put(task.getId(), task);
                            mTasksLocalDataSource.saveTask(task);
                        }))
                .toList()
                .toFlowable()
                .doOnComplete(() -> mCacheDirty = false);
    }

    private Flowable<List<Task>> getTasksFromLocal() {
        return mTasksLocalDataSource
                .getTasks()
                .flatMap(tasks -> Flowable
                        .fromIterable(tasks)
                        .doOnNext(task -> mTasksCachedDataSource.put(task.getId(), task))
                        .toList()
                        .toFlowable());
    }

    private void refreshLocal(List<Task> tasks) {
        mTasksLocalDataSource.deleteAllTasks();
        for (Task task : tasks) {
            mTasksLocalDataSource.saveTask(task);
        }
    }

    private void refreshCache(@NonNull List<Task> tasks) {
        mTasksCachedDataSource.clear();
        for (Task task : tasks) {
            mTasksCachedDataSource.put(task.getId(), task);
        }
    }

    public void refreshTasks() {
        mCacheDirty = true;
    }
}
