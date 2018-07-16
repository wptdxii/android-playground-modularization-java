package com.wptdxii.framekit.network.okhttp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;
import com.wptdxii.framekit.Extension;
import com.wptdxii.framekit.network.okhttp.interceptor.HeaderInterceptor;
import com.wptdxii.framekit.network.okhttp.interceptor.OfflineCacheInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;

/**
 * Created by wptdxii on 2016/8/16 0016.
 */
public final class OkHttpManager {

    private static final String TAG_REQUEST = "Request";
    private static final String TAG_RESPONSE = "Response";
    private static final String CACHE_PATH = "httpResponseCache";
    private static final int CACHE_SIZE = 10 * 1204 * 1024;
    private static final long TIMEOUT_READ = 15L;
    private static final long TIMEOUT_CONNECTION = 15L;
    private OkHttpClient mOkHttpClient;

    private volatile static OkHttpManager sInstance;


    public static OkHttpManager getInstance(Context context) {
        OkHttpManager result = sInstance;
        if (sInstance == null) {
            synchronized (OkHttpManager.class) {
                result = sInstance;
                if (result == null) {
                    sInstance = result = new OkHttpManager(context);
                }
            }
        }
        return result;
    }

    private OkHttpManager(Context context) {
        if (sInstance != null) {
            throw new UnsupportedOperationException("Already instantiated");
        }
        init(context.getApplicationContext());
    }

    private void init(Context context) {

        mOkHttpClient = new OkHttpClient.Builder()
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

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }
}
