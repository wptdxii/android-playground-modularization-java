package com.wptdxii.framekit.network.okhttp.authenticator;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by wptdxii on 2016/8/4 0004.
 */
public class TokenAuthenticator implements Authenticator {

    //    @Nullable
    @Override
    public Request authenticate(@NonNull Route route, @NonNull Response response) throws IOException {
        return null;
    }
}
