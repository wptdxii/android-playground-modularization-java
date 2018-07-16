package com.wptdxii.framekit.util;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by wptdxii on 2018/1/24 0024 16:12
 */

public class ActivityUtils {

    private ActivityUtils() {
        throw new UnsupportedOperationException("Can not be instantiated.");
    }

    public static void addFragmentToActivity(@NonNull FragmentManager manager,
                                             @NonNull Fragment fragment, @IdRes int frameId) {

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
