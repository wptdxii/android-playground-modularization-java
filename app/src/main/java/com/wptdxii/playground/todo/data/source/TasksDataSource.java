package com.wptdxii.playground.todo.data.source;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;

public interface TasksDataSource {

    void saveTask(@NonNull Task task);

    void deleteTask(@NonNull Task task);

    void deleteTask(@NonNull String taskId);

    void deleteCompletedTasks();

    void deleteAllTasks();

    void updateTask(@NonNull String taskId, boolean completed);

    void updateTask(@NonNull Task task);

    Flowable<List<Task>> getTasks();

    Flowable<Task> getTak(@NonNull String taskId);
}
