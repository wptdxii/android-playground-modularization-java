package com.wptdxii.framekit.network.retrofit;

import android.content.Context;

import com.wptdxii.framekit.network.okhttp.OkHttpManager;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitManager {

    private volatile static RetrofitManager sInstance;
    private final Retrofit mRetrofit;

    public static RetrofitManager getInstance(Context context) {
        RetrofitManager result = sInstance;
        if (sInstance == null) {
            synchronized (RetrofitManager.class) {
                result = sInstance;
                if (result == null) {
                    sInstance = result = new RetrofitManager(context);
                }
            }
        }
        return result;
    }

    private RetrofitManager(Context context) {
        if (sInstance != null) {
            throw new UnsupportedOperationException("Already instantiated");
        }

        context = context.getApplicationContext();
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpManager.getInstance(context).getOkHttpClient())
                .build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}

