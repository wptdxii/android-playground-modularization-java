package com.wptdxii.playground.todo.statistics;

import com.wptdxii.framekit.di.scope.ActivityScoped;
import com.wptdxii.framekit.di.scope.FragmentScoped;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class StatisticsModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = StatisticsFragmentModule.class)
    abstract StatisticsFragment statisticsFragment();

    @Provides
    @ActivityScoped
    static StatisticsFragment proivdeFragment() {
        return StatisticsFragment.newInstance();
    }
}
