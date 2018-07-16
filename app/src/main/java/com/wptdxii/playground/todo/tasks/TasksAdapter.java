package com.wptdxii.playground.todo.tasks;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wptdxii.playground.R;
import com.wptdxii.playground.todo.data.source.Task;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private static final String TAG = "TasksAdapter";
    private List<Task> mTasks;
    private TaskItemListener mTaskItemListener;

    public TasksAdapter(@NonNull TaskItemListener taskItemListener) {
        mTasks = new ArrayList<>();
        mTaskItemListener = taskItemListener;
    }

    public void setTasks(@NonNull List<Task> tasks) {
        mTasks.clear();
        mTasks.addAll(tasks);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.todo_item_tasks, parent, false);
        ViewHolder holder = new ViewHolder(view);

        holder.clTask.setOnClickListener(v ->
                mTaskItemListener.onTaskClick(holder.mTask));

        holder.cbCompleted.setOnClickListener(v -> {
            holder.mTask.setCompleted(!holder.mTask.isCompleted());
            mTaskItemListener.onTaskChecked(holder.mTask);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = mTasks.get(position);
        holder.mTask = task;
        holder.tvTitle.setText(task.getTitleForList());
        holder.cbCompleted.setChecked(task.isCompleted());
        if (task.isCompleted()) {
            holder.clTask.setBackgroundResource(R.drawable.selector_todo_tasks_item_bg_selected);
        } else {
            holder.clTask.setBackgroundResource(R.drawable.selector_todo_tasks_item_bg);
        }
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public interface TaskItemListener {
        void onTaskClick(@NonNull Task task);

        void onTaskChecked(@NonNull Task task);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cb_completed)
        CheckBox cbCompleted;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.cl_task)
        ConstraintLayout clTask;

        Task mTask;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
