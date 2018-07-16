package com.wptdxii.playground.todo;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ToBindingModule {

    @ContributesAndroidInjector
    abstract MainActivity mainActivity();
}
