package com.wptdxii.playground.todo.statistics;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.wptdxii.playground.R;
import com.wptdxii.playground.core.base.BaseDrawerActivity;
import com.wptdxii.playground.todo.tasks.TasksActivity;

import javax.inject.Inject;

import dagger.Lazy;

public class StatisticsActivity extends BaseDrawerActivity {

    @Inject
    Lazy<StatisticsFragment> mStatisticsFragment;

    public static void start(Context context) {
        Intent intent = new Intent(context, StatisticsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setupToolbar(Toolbar toolbar) {
        toolbar.setTitle(R.string.todo_statistics_title);
    }

    @NonNull
    @Override
    protected Fragment onCreateFragment() {
        return mStatisticsFragment.get();
    }

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
                case R.id.action_list:
                    TasksActivity.start(this);
                    break;
                default:
                    return false;
            }
            toggleDrawer();
            return true;
        });
    }
}
