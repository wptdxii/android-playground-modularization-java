package com.wptdxii.framekit.util;

import android.annotation.SuppressLint;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Menu;

/**
 * Created by wptdxii on 17-11-21 下午10:38
 */

public final class MenuUtil {
    private MenuUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void showOptionalIcons(Menu menu) {
        setOptionalIconsVisible(menu, true);
    }


    public static void hideOptionalIcons(Menu menu) {
        setOptionalIconsVisible(menu, false);
    }

    @SuppressLint("RestrictedApi")
    private static void setOptionalIconsVisible(Menu menu, boolean visible) {
        if (menu instanceof MenuBuilder) {
            ((MenuBuilder) menu).setOptionalIconsVisible(visible);
        }
    }
}
