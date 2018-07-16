package com.wptdxii.playground.douban.di;

import com.wptdxii.playground.BuildConfig;
import com.wptdxii.framekit.network.HttpServiceManager;
import com.wptdxii.playground.douban.api.MovieApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DouBanApiModule {

    @Singleton
    @Provides
    static MovieApi provideMovieApi(HttpServiceManager httpServiceManager) {
        return httpServiceManager.createApi(BuildConfig.BASE_URL_DOUBAN, MovieApi.class);
    }
}
