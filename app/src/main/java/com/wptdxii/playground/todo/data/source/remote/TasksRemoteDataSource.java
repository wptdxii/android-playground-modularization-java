package com.wptdxii.playground.todo.data.source.remote;

import android.support.annotation.NonNull;

import com.wptdxii.framekit.util.Collections;
import com.wptdxii.playground.todo.data.source.Task;
import com.wptdxii.playground.todo.data.source.TasksDataSource;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public final class TasksRemoteDataSource implements TasksDataSource {
    private static final int SERVICE_LATENCY_IN_MILLIS = 3000;
    private static final Map<String, Task> mTasksMap = new LinkedHashMap<>();

    static {
        addTask("Build tower in Pisa", "Ground looks good, no foundation work required.");
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!");
    }

    private static void addTask(String title, String description) {
        Task newTask = Task.createNewTask(title, description);
        mTasksMap.put(newTask.getId(), newTask);
    }

    @Inject
    TasksRemoteDataSource() {
    }

    @Override
    public void saveTask(@NonNull Task task) {
        mTasksMap.put(task.getId(), task);
    }

    @Override
    public void deleteTask(@NonNull Task task) {
        mTasksMap.remove(task.getId());
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        mTasksMap.remove(taskId);
    }

    @Override
    public void deleteCompletedTasks() {
        Iterator<Map.Entry<String, Task>> iterator = mTasksMap.entrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getValue().isCompleted()) iterator.remove();
        }
    }

    @Override
    public void deleteAllTasks() {
        mTasksMap.clear();
    }

    @Override
    public void updateTask(@NonNull String taskId, boolean completed) {
        Task task = mTasksMap.get(taskId);
        if (task != null) task.setCompleted(completed);
    }

    @Override
    public void updateTask(@NonNull Task task) {
        mTasksMap.put(task.getId(), task);
    }

    @Override
    public Flowable<List<Task>> getTasks() {
        return Flowable.fromIterable(Collections.newArrayList(mTasksMap.values()))
                .delay(SERVICE_LATENCY_IN_MILLIS, TimeUnit.MILLISECONDS)
                .toList()
                .toFlowable();
    }

    @Override
    public Flowable<Task> getTak(@NonNull String taskId) {
        final Task task = mTasksMap.get(taskId);
        if (task != null) {
            return Flowable.just(task).delay(SERVICE_LATENCY_IN_MILLIS, TimeUnit.MILLISECONDS);
        } else {
            return Flowable.empty();
        }
    }
}
