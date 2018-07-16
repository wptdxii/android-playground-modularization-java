package com.wptdxii.playground.todo.data.source.local;

import android.arch.persistence.room.*;
import android.support.annotation.NonNull;

import com.wptdxii.playground.todo.data.source.Task;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(@NonNull Task task);

    @Query("DELETE FROM TODO WHERE entryid = :taskId")
    void deleteTask(@NonNull String taskId);

    @Delete
    void deleteTask(@NonNull Task task);

    @Query("DELETE FROM TODO WHERE completed = 1")
    void deleteCompletedTasks();

    @Query("DELETE FROM TODO")
    void deleteAllTasks();

    @Query("UPDATE TODO SET completed = :completed WHERE entryid = :taskId")
    void updateTask(@NonNull String taskId, boolean completed);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(@NonNull Task task);

    @Query("SELECT * FROM TODO WHERE entryid = :taskId")
    Single<Task> getTask(@NonNull String taskId);

    @Query("SELECT * FROM TODO")
    Single<List<Task>> getTasks();

}
