package com.wptdxii.playground.sample;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

import com.wptdxii.playground.R;
import com.wptdxii.playground.core.base.BaseListActivity;
import com.wptdxii.playground.core.base.Module;
import com.wptdxii.playground.sample.dagger.DaggerActivity;
import com.wptdxii.playground.sample.rx.RxSampleActivity;

import java.util.ArrayList;
import java.util.List;

public class SampleActivity extends BaseListActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, SampleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setupToolbar(Toolbar toolbar) {
        toolbar.setTitle(R.string.module_sample);

    }

    @NonNull
    @Override
    protected List<Module> createModules() {
        List<Module> modules = new ArrayList<>();
        modules.add(new Module(getString(R.string.sample_system_bar_title), SystemBarActivity.class));
        modules.add(new Module(getString(R.string.sample_layout_title), LayoutActivity.class));
        modules.add(new Module(getString(R.string.sample_drawer_title), DrawerActivity.class));
        modules.add(new Module(getString(R.string.sample_dagger_sample_title), DaggerActivity.class));
        modules.add(new Module(getString(R.string.sample_rx_sample_title), RxSampleActivity.class));
        return modules;
    }
}
