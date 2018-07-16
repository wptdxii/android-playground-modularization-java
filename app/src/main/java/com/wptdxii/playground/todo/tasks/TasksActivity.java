package com.wptdxii.playground.todo.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.wptdxii.playground.R;
import com.wptdxii.playground.core.base.BaseDrawerActivity;
import com.wptdxii.playground.todo.statistics.StatisticsActivity;

import javax.inject.Inject;

import dagger.Lazy;

public class TasksActivity extends BaseDrawerActivity {

    private static final String BUNDLE_CURRENT_FILTER = "bundle_current_filter";

    @Inject
    Lazy<TasksFragment> mTasksFragmentProvider;
    @Inject
    TasksContract.Presenter mTasksPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, TasksActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setupToolbar(Toolbar toolbar) {
        toolbar.setTitle(R.string.todo_app_name);
    }

    @NonNull
    @Override
    protected Fragment onCreateFragment() {
        return mTasksFragmentProvider.get();
    }

    @LayoutRes
    @Override
    protected int onCreateNavigationHeader() {
        return R.layout.todo_activity_tasks_nav_header;
    }

    @Override
    protected int onCreateNavigationMenu() {
        return R.menu.todo_activity_tasks_drawer;
    }

    @Override
    protected void setupNavigationView(NavigationView nvDrawer) {
        nvDrawer.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_statistics:
                    StatisticsActivity.start(this);
                    break;
                default:
                    return false;
            }
            toggleDrawer();
            return true;
        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        TasksFilterType tasksFilterType = (TasksFilterType) savedInstanceState.getSerializable(BUNDLE_CURRENT_FILTER);
        mTasksPresenter.setFilter(tasksFilterType);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(BUNDLE_CURRENT_FILTER, mTasksPresenter.getFilter());
        super.onSaveInstanceState(outState);
    }
}
