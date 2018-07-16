package com.wptdxii.playground.home.di;

import com.wptdxii.playground.home.HomeActivity;
import com.wptdxii.playground.home.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HomeBindingModule {

    @ContributesAndroidInjector
    abstract SplashActivity splashActivity();

    @ContributesAndroidInjector
    abstract HomeActivity mainActivity();
}
