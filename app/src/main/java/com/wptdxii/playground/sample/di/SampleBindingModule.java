package com.wptdxii.playground.sample.di;

import com.wptdxii.framekit.di.scope.ActivityScoped;
import com.wptdxii.playground.sample.DrawerActivity;
import com.wptdxii.playground.sample.DrawerFragment;
import com.wptdxii.playground.sample.LayoutActivity;
import com.wptdxii.playground.sample.SampleActivity;
import com.wptdxii.playground.sample.SystemBarActivity;
import com.wptdxii.playground.sample.dagger.DaggerActivity;
import com.wptdxii.playground.sample.dagger.DaggerActivityModule;
import com.wptdxii.playground.sample.rx.RxSampleActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SampleBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = DaggerActivityModule.class)
    abstract DaggerActivity daggerActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract DrawerActivity drawerActivity();

    @ActivityScoped
    @ContributesAndroidInjector()
    abstract DrawerFragment drawerFragment();

    @ContributesAndroidInjector
    abstract SampleActivity sampleActivity();

    @ContributesAndroidInjector
    abstract RxSampleActivity rxSampleActivity();

    @ContributesAndroidInjector
    abstract LayoutActivity layoutActivity();

    @ContributesAndroidInjector
    abstract SystemBarActivity systemBarActivity();
}
