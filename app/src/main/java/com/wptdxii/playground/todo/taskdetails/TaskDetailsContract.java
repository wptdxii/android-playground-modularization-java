package com.wptdxii.playground.todo.taskdetails;

import com.wptdxii.framekit.base.Contract;
import com.wptdxii.playground.todo.data.source.Task;

public interface TaskDetailsContract {

    interface View extends Contract.View {

        void showMissingTask();

        void hideTaskTitle();

        void showTaskTitle(String title);

        void hideTaskDescription();

        void showTaskDescription(String description);

        void showTaskCompletionStatus(boolean completed);

        void showLoadingIndicator();

        void showTaskChecked(boolean checked);

        void showTaskDeleted();

        void showEditTask(String taskId);
    }


    interface Presenter extends Contract.Presenter<View> {

        void editTask();

        void deleteTask();

        void checkTask(boolean checked);
    }
}

