package com.wptdxii.framekit.util;

import android.support.annotation.Nullable;

import com.wptdxii.framekit.exception.InstantiationException;

public final class Strings {
    private Strings() {
        throw new InstantiationException();
    }

    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }
}
