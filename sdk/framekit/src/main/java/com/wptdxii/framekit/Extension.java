package com.wptdxii.framekit;

import com.wptdxii.framekit.util.Preconditions;

public class Extension {

    private final boolean mBuildType;

    private volatile static Extension sExtension;

    public static Extension getExtension() {
        Preconditions.checkNotNull(sExtension);
        return sExtension;
    }

    public boolean getBuildType() {
        return mBuildType;
    }

    /**
     * only instantiated once
     *
     * @param extension
     */
    public static void install(Extension extension) {
        Extension result = sExtension;
        if (result == null) {
            synchronized (Extension.class) {
                result = sExtension;
                if (result == null) {
                    sExtension = extension;
                }
            }
        }
    }

    private Extension(Builder builder) {
        mBuildType = builder.mBuildType;
    }

    public static class Builder {
        private boolean mBuildType = BuildConfig.DEBUG;

        public Builder buildType(boolean buildType) {
            mBuildType = buildType;
            return this;
        }

        public Extension build() {
            return new Extension(this);
        }
    }


}
