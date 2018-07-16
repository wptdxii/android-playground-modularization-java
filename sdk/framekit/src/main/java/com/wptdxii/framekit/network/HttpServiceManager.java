package com.wptdxii.framekit.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;
import com.wptdxii.framekit.Extension;
import com.wptdxii.framekit.di.qualifier.AppContext;
import com.wptdxii.framekit.di.qualifier.BuildType;
import com.wptdxii.framekit.network.okhttp.interceptor.HeaderInterceptor;
import com.wptdxii.framekit.network.okhttp.interceptor.OfflineCacheInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class HttpServiceManager {

    private static final String CACHE_PATH = "httpResponseCache";
    private static final int CACHE_SIZE = 10 * 1204 * 1024;
    private static final long TIMEOUT_READ = 15L;
    private static final long TIMEOUT_CONNECTION = 15L;
    private static final String TAG_REQUEST = "Request";
    private static final String TAG_RESPONSE = "Response";

    private final OkHttpClient mOkHttpClient;

    @Inject
    HttpServiceManager(@AppContext Context context) {
        mOkHttpClient = getOkHttpClient(context.getApplicationContext());
    }

    private OkHttpClient getOkHttpClient(Context context) {
        return new OkHttpClient.Builder()
                .addInterceptor(getLoggingInterceptor())
                .addInterceptor(new HeaderInterceptor())
                .addNetworkInterceptor(new OfflineCacheInterceptor(context))
                .addInterceptor(new OfflineCacheInterceptor(context))
                .retryOnConnectionFailure(true)
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .cache(getCacheDirectory(context))
                .build();
    }

    @NonNull
    private Cache getCacheDirectory(Context context) {
        File httpCacheDirectory = new File(context.getCacheDir(), CACHE_PATH);
        return new Cache(httpCacheDirectory, CACHE_SIZE);
    }

    @NonNull
    private LoggingInterceptor getLoggingInterceptor() {
        return new LoggingInterceptor.Builder()
                .loggable(Extension.getExtension().getBuildType())
                .setLevel(Level.BASIC)
                .log(Platform.WARN)
                .request(TAG_REQUEST)
                .response(TAG_RESPONSE)
                .build();
    }


    public <T> T createApi(String baseUrl, Class<T> clazz) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient)
                .baseUrl(baseUrl)
                .build()
                .create(clazz);
    }
}
