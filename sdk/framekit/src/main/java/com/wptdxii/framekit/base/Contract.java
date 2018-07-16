package com.wptdxii.framekit.base;

public interface Contract {

    interface View {}

    interface Presenter<V extends View> {

        void attach(V view);

        void detach();
    }
}
