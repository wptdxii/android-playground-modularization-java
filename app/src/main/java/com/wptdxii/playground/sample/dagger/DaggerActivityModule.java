package com.wptdxii.playground.sample.dagger;

import android.content.Context;

import com.wptdxii.framekit.di.qualifier.ActivityContext;
import com.wptdxii.framekit.di.scope.ActivityScoped;
import com.wptdxii.framekit.di.scope.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DaggerActivityModule {

    /**
     * when create fragment component as subcomponent of AppComponent，We should create
     * Fragment instance manually
     */
    @Provides
    @ActivityScoped
    static DaggerFragment provideFragment() {
        return DaggerFragment.newInstance();
    }

    @Binds
    @ActivityScoped
    @ActivityContext
    abstract Context provideActivity(DaggerActivity sampleActivity);

    @FragmentScoped
    @ContributesAndroidInjector(modules = DaggerFragmentModule.class)
    abstract DaggerFragment daggerFragment();
}
