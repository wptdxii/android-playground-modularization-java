package com.wptdxii.playground.sample.rx;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.wptdxii.playground.R;
import com.wptdxii.framekit.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxSampleActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity_rx_sample);
        ButterKnife.bind(this);

        setupToolbar(toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            setupActionBar(actionBar);
        }

        testRx();
    }

    private static final String TAG = "RxSampleActivity";

    private void testRx() {
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            emitter.onNext(1);
            emitter.onComplete();
        }).subscribeOn(Schedulers.newThread())
                .doOnNext(integer -> Log.e(TAG, "testRx: " + integer))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> Log.e(TAG, "testRx: " + integer));

    }

    private void setupActionBar(ActionBar actionBar) {
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setupToolbar(Toolbar toolbar) {
        toolbar.setTitle(R.string.sample_rx_sample_title);
    }
}
