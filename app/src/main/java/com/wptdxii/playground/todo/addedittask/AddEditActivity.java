package com.wptdxii.playground.todo.addedittask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.wptdxii.playground.R;
import com.wptdxii.framekit.base.BaseActivity;
import com.wptdxii.playground.todo.data.source.Task;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddEditActivity extends BaseActivity implements AddEditContract.View {

    public static final String EXTRA_TASK_ID = "extra_task_id";
    public static final int REQUEST_CODE_TASK_ID = 1;
    private static final String BUNDLE_SHOUlD_LOAD_DATA_FROM_REPO = "should_load_data_from_repo";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_task_title)
    EditText etTaskTitle;
    @BindView(R.id.et_task_description)
    EditText etTaskDescription;
    @BindView(R.id.fab_task_done)
    FloatingActionButton fabTaskDone;

    @Inject
    @Nullable
    String mTaskId;
    @Inject
    AddEditContract.Presenter mAddEditPresenter;

    private boolean mIsDataMissing = true;

    public static void start(Context context) {
        Intent intent = new Intent(context, AddEditActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void showTasksList() {
        if (mTaskId != null) {
            setResult(RESULT_OK);
        }
        finish();
    }

    public static void start(Activity activity, String taskId) {
        Intent intent = new Intent(activity, AddEditActivity.class);
        intent.putExtra(EXTRA_TASK_ID, taskId);
        activity.startActivityForResult(intent, REQUEST_CODE_TASK_ID);
    }

    @Override
    public void showTask(Task task) {
        etTaskTitle.setText(task.getTitle());
        etTaskDescription.setText(task.getDescription());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_activity_add_edit);
        ButterKnife.bind(this);

        setupAppBar(mTaskId);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mIsDataMissing = savedInstanceState.getBoolean(BUNDLE_SHOUlD_LOAD_DATA_FROM_REPO);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAddEditPresenter.attach(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(BUNDLE_SHOUlD_LOAD_DATA_FROM_REPO,
                mAddEditPresenter.isDataMissing());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupAppBar(String taskId) {
        String title = taskId == null ?
                getString(R.string.todo_title_new_task) : getString(R.string.todo_title_edit_task);
        toolbar.setTitle(title);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @OnClick(R.id.fab_task_done)
    public void saveTask() {
        mAddEditPresenter.saveTask(etTaskTitle.getText().toString().trim(),
                etTaskDescription.getText().toString().trim());
    }

    @Override
    public void showEmptyTaskError() {
        Snackbar.make(fabTaskDone, R.string.todo_task_no_empty, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAddEditPresenter.detach();
    }

    boolean isDataMissing() {
        return mIsDataMissing;
    }
}
