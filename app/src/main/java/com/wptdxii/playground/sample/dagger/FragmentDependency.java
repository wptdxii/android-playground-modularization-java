package com.wptdxii.playground.sample.dagger;

import android.content.Context;

public final class FragmentDependency {

    private final Context mContext;

    public FragmentDependency(Context context) {
        mContext = context;
    }

    @Override
    public String toString() {
        return "FragmentDependency: " + hashCode() + ", Fragment: " + mContext.hashCode();
    }
}
