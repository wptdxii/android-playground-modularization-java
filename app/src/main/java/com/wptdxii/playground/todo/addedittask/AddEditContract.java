package com.wptdxii.playground.todo.addedittask;

import com.wptdxii.framekit.base.Contract;
import com.wptdxii.playground.todo.data.source.Task;

public interface AddEditContract {

    interface View extends Contract.View {

        void showEmptyTaskError();

        void showTask(Task task);

        void showTasksList();
    }

    interface Presenter extends Contract.Presenter<View> {

        void saveTask(String title, String description);

        void getTask();

        boolean isDataMissing();
    }
}
