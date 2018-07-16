package com.wptdxii.playground.todo.tasks;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.wptdxii.playground.R;
import com.wptdxii.framekit.base.BaseFragment;
import com.wptdxii.playground.todo.addedittask.AddEditActivity;
import com.wptdxii.playground.todo.data.source.Task;
import com.wptdxii.playground.todo.taskdetails.TaskDetailsActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TasksFragment extends BaseFragment implements TasksContract.View {

    @BindView(R.id.tv_filtering_label)
    TextView tvFilteringLabel;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.group_list)
    Group groupList;
    @BindView(R.id.tv_no_tasks)
    TextView tvNoTasks;
    @BindView(R.id.iv_no_tasks)
    ImageView ivNoTasks;
    @BindView(R.id.group_no_tasks)
    Group groupNoTasks;
    @BindView(R.id.cl_content)
    ConstraintLayout clContent;
    @BindView(R.id.fab_add_task)
    FloatingActionButton fabAddTask;
    Unbinder unbinder;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    TasksContract.Presenter mTaskPresenter;

    private TasksAdapter mTasksAdapter;

    public static TasksFragment newInstance() {
        return new TasksFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTasksAdapter = new TasksAdapter(new TasksAdapter.TaskItemListener() {
            @Override
            public void onTaskClick(@NonNull Task task) {
                TaskDetailsActivity.start(getContext(), task.getId());
            }

            @Override
            public void onTaskChecked(@NonNull Task task) {
                mTaskPresenter.checkTask(task);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.todo_fragment_tasks, container, false);
        unbinder = ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mTasksAdapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.todo_color_primary, R.color
                .todo_color_primary_dark);
        swipeRefreshLayout.setOnRefreshListener(() ->
                mTaskPresenter.loadTasks(false, true));

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.todo_tasks_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                showFilterPopUpMenu();
                break;
            case R.id.action_clear_completed:
                mTaskPresenter.clearCompletedTasks();
                break;
            case R.id.action_clear_all:
                mTaskPresenter.clearAllTasks();
                break;

            case R.id.action_local_source:
                mTaskPresenter.loadTasks(false, false);
                break;
            case R.id.action_Remote_Source:
                mTaskPresenter.loadTasks(true, true);
                break;
            default:
                return false;
        }
        return true;
    }

    private void showFilterPopUpMenu() {
        if (getActivity() != null) {
            View anchor = getActivity().findViewById(R.id.action_filter);
            PopupMenu popupMenu = new PopupMenu(getActivity(), anchor);
            popupMenu.getMenuInflater().inflate(R.menu.todo_tasks_fragment_filter, popupMenu
                    .getMenu());
            popupMenu.show();

            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.action_all:
                        mTaskPresenter.setFilter(TasksFilterType.ALL_TASKS);
                        break;
                    case R.id.action_active:
                        mTaskPresenter.setFilter(TasksFilterType.ACTIVE_TASKS);
                        break;
                    case R.id.action_completed:
                        mTaskPresenter.setFilter(TasksFilterType.COMPLETED_TASKS);
                        break;
                    default:
                        return false;
                }
                mTaskPresenter.loadTasks(false, true);
                return true;
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mTaskPresenter.attach(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mTaskPresenter.detach();
    }

    @Override
    public void showLoadingIndicator(boolean show) {
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(show));
    }

    @Override
    public void showLoadingError() {
        showSnackBar(getString(R.string.todo_loading_tasks_error));
    }

    @Override
    public void showNoTasks(TasksFilterType filterType) {
        groupList.setVisibility(View.GONE);
        groupNoTasks.setVisibility(View.VISIBLE);

        switch (filterType) {
            case ALL_TASKS:
                tvNoTasks.setText(R.string.todo_no_tasks_all);
                ivNoTasks.setImageResource(R.drawable.todo_vector_ic_assignment_turned_in);
                break;
            case ACTIVE_TASKS:
                tvNoTasks.setText(R.string.todo_no_tasks_active);
                ivNoTasks.setImageResource(R.drawable.todo_vector_ic_check_circle);
                break;
            case COMPLETED_TASKS:
                tvNoTasks.setText(R.string.todo_no_tasks_completed);
                ivNoTasks.setImageResource(R.drawable.todo_vector_ic_verfied_user);
                break;
            default:
                break;
        }
    }

    @Override
    public void showFilterLabel(TasksFilterType filterType) {
        switch (filterType) {
            case ALL_TASKS:
                tvFilteringLabel.setText(R.string.todo_tasks_filter_all);
                break;
            case ACTIVE_TASKS:
                tvFilteringLabel.setText(R.string.todo_tasks_filter_active);
                break;
            case COMPLETED_TASKS:
                tvFilteringLabel.setText(R.string.todo_tasks_filter_completed);
                break;
            default:
                break;
        }
    }

    @Override
    public void showTasks(@NonNull List<Task> tasks) {
        groupList.setVisibility(View.VISIBLE);
        groupNoTasks.setVisibility(View.GONE);

        mTasksAdapter.setTasks(tasks);
    }

    @OnClick(R.id.fab_add_task)
    @Override
    public void showAddNewTask() {
        AddEditActivity.start(getActivity());
    }

    @Override
    public void showTaskDetails(@NonNull Task task) {
        // TODO: 2018/4/24 0024 star activity to TaskDetailsActivity

    }

    @Override
    public void showTaskChecked(boolean checked) {
        if (checked) {
            showSnackBar(getString(R.string.todo_tasks_fragment_task_marked_complete));
        } else {
            showSnackBar(getString(R.string.todo_tasks_fragment_task_marked_active));
        }
    }

    @Override
    public void showCompletedTasksCleared() {
        showSnackBar(getString(R.string.todo_tasks_fragment_task_completed_cleared));
    }

    @Override
    public void showAllTasksCleared() {
        showSnackBar(getString(R.string.todo_tasks_fragment_all_task_cleared));
    }

    private void showSnackBar(String message) {
        Snackbar.make(fabAddTask, message, Snackbar.LENGTH_SHORT).show();
    }
}
