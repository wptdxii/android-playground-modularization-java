package com.wptdxii.playground.sample.dagger;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wptdxii.playground.R;
import com.wptdxii.framekit.base.BaseFragment;
import com.wptdxii.framekit.di.qualifier.ActivityContext;
import com.wptdxii.framekit.di.qualifier.AppContext;
import com.wptdxii.playground.todo.data.TasksRepository;

import javax.inject.Inject;

public class DaggerFragment extends BaseFragment {

    private static final String TAG = "Dagger";

    @Inject
    @AppContext
    Context mAppContext;
    @Inject
    @ActivityContext
    Context mActivityContext;
    @Inject
    TasksRepository mTasksRepository;
    @Inject
    ActivityDependency mActivityDependency;
    @Inject
    FragmentDependency mFragmentDependency;

    public static DaggerFragment newInstance() {
        return new DaggerFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "Fragment:AppContext::" + mAppContext);
        Log.e(TAG, "Fragment:ActivityContext::" + mActivityContext);
        Log.e(TAG, "Fragment:TasksRepository::" + mTasksRepository);
        Log.e(TAG, "Fragment:ActivityDependency::" + mActivityDependency);
        Log.e(TAG, "Fragment:FragmentDependency::" + mFragmentDependency);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sample_fragment_dagger_sample, container, false);
    }

}
