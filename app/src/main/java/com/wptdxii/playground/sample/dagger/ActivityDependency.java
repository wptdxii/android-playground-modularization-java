package com.wptdxii.playground.sample.dagger;

import android.content.Context;

import com.wptdxii.framekit.di.qualifier.ActivityContext;
import com.wptdxii.framekit.di.scope.ActivityScoped;

import javax.inject.Inject;

@ActivityScoped
public final class ActivityDependency {

    private final Context mContext;

    @Inject
    ActivityDependency(@ActivityContext Context context) {
        mContext = context;
    }

    @Override
    public String toString() {
        return "ActivityDependency: " + hashCode() + ", Activity: " + mContext.hashCode();
    }
}
