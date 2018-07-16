package com.wptdxii.playground.sample.dagger;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.wptdxii.playground.R;
import com.wptdxii.framekit.base.BaseActivity;
import com.wptdxii.framekit.di.qualifier.AppContext;
import com.wptdxii.playground.todo.data.TasksRepository;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DaggerActivity extends BaseActivity {

    private static final String TAG = "DaggerActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    DaggerFragment mDaggerFragment;
    @Inject
    @AppContext
    Context mAppContext;
    @Inject
    TasksRepository mTasksRepository;
    @Inject
    ActivityDependency mActivityDependency;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity_dagger_sample);
        ButterKnife.bind(this);

        setupToolbar(toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            setupActionBar(actionBar);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        DaggerFragment sampleFragment =
                (DaggerFragment) fragmentManager.findFragmentById(R.id.fl_content);
        if (sampleFragment == null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fl_content, mDaggerFragment);
            transaction.commit();
        }

        Log.e(TAG, "AppContext::" + mAppContext);
        Log.e(TAG, "ActivityContext::" + this);
        Log.e(TAG, "TasksRepository::" + mTasksRepository);
        Log.e(TAG, "ActivityDependency::" + mActivityDependency.toString());
    }

    private void setupActionBar(ActionBar actionBar) {
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setupToolbar(Toolbar toolbar) {
        toolbar.setTitle(R.string.sample_dagger_sample_title);
    }
}
