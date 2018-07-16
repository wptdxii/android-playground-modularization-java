package com.wptdxii.playground.todo.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.wptdxii.playground.todo.data.source.Task;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class ToDoDatabase extends RoomDatabase {

    public static final String DB_NAME_TODO = "ToDo.db";

    public abstract TasksDao tasksDao();
}
