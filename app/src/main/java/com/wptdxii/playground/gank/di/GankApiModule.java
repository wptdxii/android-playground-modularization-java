package com.wptdxii.playground.gank.di;

import com.wptdxii.playground.BuildConfig;
import com.wptdxii.framekit.network.HttpServiceManager;
import com.wptdxii.playground.gank.api.GankApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GankApiModule {

    @Singleton
    @Provides
    static GankApi provideGankApi(HttpServiceManager httpServiceManager) {
        return httpServiceManager.createApi(BuildConfig.BASE_URL_GANK, GankApi.class);
    }
}
