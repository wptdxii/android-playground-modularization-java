package com.wptdxii.playground.todo.taskdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wptdxii.playground.R;
import com.wptdxii.framekit.base.BaseActivity;
import com.wptdxii.playground.todo.addedittask.AddEditActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskDetailsActivity extends BaseActivity implements TaskDetailsContract.View {

    public static final String EXTRA_TASK_ID = "extra_task_id";

    @BindView(R.id.cb_completed)
    CheckBox cbCompleted;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.cl_details)
    ConstraintLayout clDetails;
    @BindView(R.id.fab_edit_task)
    FloatingActionButton fabEditTask;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    TaskDetailsContract.Presenter mTaskDetailsPresenter;

    public static void start(Context context, String taskId) {
        Intent intent = new Intent(context, TaskDetailsActivity.class);
        intent.putExtra(EXTRA_TASK_ID, taskId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_activity_task_details);
        ButterKnife.bind(this);

        setupAppBar();
    }

    private void setupAppBar() {
        toolbar.setTitle(R.string.todo_mvp_title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo_task_details, menu);
        return true;
    }

    @Override
    public void showTaskDeleted() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                mTaskDetailsPresenter.deleteTask();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTaskDetailsPresenter.attach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTaskDetailsPresenter.detach();
    }

    @OnClick(R.id.cb_completed)
    public void checkTask(View view) {
        CheckBox checkBox = (CheckBox) view;
        mTaskDetailsPresenter.checkTask(checkBox.isChecked());
    }

    @Override
    public void showMissingTask() {
        tvTitle.setText("");
        tvDescription.setText(R.string.todo_task_details_no_data);
    }

    @Override
    public void hideTaskTitle() {
        tvTitle.setVisibility(View.GONE);
    }

    @Override
    public void showTaskTitle(String title) {
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
    }

    @Override
    public void hideTaskDescription() {
        tvDescription.setVisibility(View.GONE);
    }

    @Override
    public void showTaskDescription(String description) {
        tvDescription.setVisibility(View.VISIBLE);
        tvDescription.setText(description);
    }

    @Override
    public void showTaskCompletionStatus(boolean completed) {
        cbCompleted.setChecked(completed);
    }

    @Override
    public void showLoadingIndicator() {
        tvTitle.setText("");
        tvDescription.setText(R.string.todo_task_details_loading);
    }

    @Override
    public void showTaskChecked(boolean checked) {
        String content = checked ? getString(R.string.todo_tasks_fragment_task_marked_complete) :
                getString(R.string.todo_tasks_fragment_task_marked_active);
        Snackbar.make(fabEditTask, content, Snackbar.LENGTH_SHORT).show();

    }

    @OnClick(R.id.fab_edit_task)
    public void editTask() {
        mTaskDetailsPresenter.editTask();
    }

    @Override
    public void showEditTask(String taskId) {
        AddEditActivity.start(this, taskId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AddEditActivity.REQUEST_CODE_TASK_ID && resultCode == RESULT_OK) {
            finish();
        }
    }
}
