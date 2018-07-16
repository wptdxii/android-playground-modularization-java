package com.wptdxii.playground.todo.data.source;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wptdxii.framekit.util.Objects;
import com.wptdxii.framekit.util.Strings;

import java.util.UUID;

@Entity(tableName = "todo")
public final class Task {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "entryid")
    private String mId;

    @Nullable
    @ColumnInfo(name = "title")
    private String mTitle;

    @Nullable
    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "completed")
    private boolean mCompleted;

    public Task(@NonNull String id, @Nullable String title, @Nullable String description, boolean completed) {
        this.mId = id;
        this.mTitle = title;
        this.mDescription = description;
        this.mCompleted = completed;
    }

    public static Task createNewTask(@NonNull String title, @NonNull String description) {
        return new Task(UUID.randomUUID().toString(), title, description, false);
    }

    public static Task createNewTaskWithId(@NonNull String id, @Nullable String title,
                                           @Nullable String description) {
        return new Task(id, title, description, false);
    }

    @Nullable
    public String getTitleForList() {
        if (!Strings.isEmpty(mTitle)) {
            return mTitle;
        } else {
            return mDescription;
        }
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(@Nullable String title) {
        mTitle = title;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    public void setDescription(@Nullable String description) {
        mDescription = description;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public void setCompleted(boolean completed) {
        mCompleted = completed;
    }

    public boolean isEmpty() {
        return Strings.isEmpty(mTitle) && Strings.isEmpty(mDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mTitle, mDescription, mCompleted);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return isCompleted() == task.isCompleted() &&
                Objects.equals(mId, task.getId()) &&
                Objects.equals(mTitle, task.getTitle()) &&
                Objects.equals(mDescription, task.getDescription());
    }

    @Override
    public String toString() {
        return "Task {\n" +
                "id:" + mId + ",\n" +
                "title:" + mTitle + ",\n" +
                "description:" + mDescription + ",\n" +
                "completed:" + mCompleted + "}";
    }
}
