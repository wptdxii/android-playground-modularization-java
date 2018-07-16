package com.wptdxii.framekit.util;

import android.support.annotation.Nullable;

import com.wptdxii.framekit.exception.InstantiationException;

import java.util.Arrays;

/**
 * Created by wptdxii on 2018/1/26 0026 18:02
 */

public class Objects {

    private Objects() {
        throw new InstantiationException();
    }

    public static boolean equals(@Nullable Object a, @Nullable Object b) {
        return a == b || (a != null && a.equals(b));
    }

    public static int hashCode(Object... objects) {
        return Arrays.hashCode(objects);
    }
}
