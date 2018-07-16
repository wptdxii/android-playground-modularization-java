package com.wptdxii.playground.todo.statistics;

import com.wptdxii.framekit.di.scope.FragmentScoped;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class StatisticsFragmentModule {

    @FragmentScoped
    @Binds
    abstract StatisticsContract.Presenter providePresenter(StatisticsPresenter presenter);
}
