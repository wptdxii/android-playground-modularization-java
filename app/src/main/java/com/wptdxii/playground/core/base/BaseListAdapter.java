package com.wptdxii.playground.core.base;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wptdxii.framekit.base.BaseActivity;
import com.wptdxii.playground.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wptdxii on 2018/1/17 0017
 */

public class BaseListAdapter extends RecyclerView.Adapter<BaseListAdapter.ViewHolder> {
    private List<Module> mModules;

    BaseListAdapter(List<Module> modules) {
        this.mModules = modules;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_base_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Module module = mModules.get(position);
        holder.mTarget = module.getTarget();
        holder.tvItem.setText(module.getName());
    }

    @Override
    public int getItemCount() {
        return mModules.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Context mContext;
        Class<? extends BaseActivity> mTarget;

        @BindView(R.id.tv_item)
        TextView tvItem;

        ViewHolder(View itemView) {
            super(itemView);

            this.mContext = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.tv_item)
        void startActivity() {
            mContext.startActivity(new Intent(mContext, mTarget));
        }
    }
}
