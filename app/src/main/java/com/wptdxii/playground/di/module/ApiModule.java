package com.wptdxii.playground.di.module;

import com.wptdxii.playground.BuildConfig;
import com.wptdxii.framekit.network.HttpServiceManager;
import com.wptdxii.playground.douban.api.MovieApi;
import com.wptdxii.playground.gank.api.GankApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule {

    @Singleton
    @Provides
    static GankApi provideGankApi(HttpServiceManager httpServiceManager) {
        return httpServiceManager.createApi(BuildConfig.BASE_URL_GANK, GankApi.class);
    }

    @Singleton
    @Provides
    static MovieApi provideMovieApi(HttpServiceManager httpServiceManager) {
        return httpServiceManager.createApi(BuildConfig.BASE_URL_DOUBAN, MovieApi.class);
    }
}
