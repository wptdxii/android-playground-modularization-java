package com.wptdxii.playground.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wptdxii.playground.R;
import com.wptdxii.framekit.base.BaseFragment;
import com.wptdxii.framekit.di.scope.ActivityScoped;

import javax.inject.Inject;

/**
 * Created by wptdxii on 18-1-24
 */

@ActivityScoped
public class DrawerFragment extends BaseFragment {

    //    @Inject
    //    CoffeeMaker mCoffeeMaker;

    @Inject
    public DrawerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frgment_drawer, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        //        Log.e(TAG, "onResume: " + mCoffeeMaker.toString());
    }

    private static final String TAG = "DrawerFragment";
}
