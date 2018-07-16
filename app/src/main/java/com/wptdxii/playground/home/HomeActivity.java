package com.wptdxii.playground.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.wptdxii.playground.R;
import com.wptdxii.playground.core.base.BaseListActivity;
import com.wptdxii.playground.core.base.Module;
import com.wptdxii.playground.sample.SampleActivity;
import com.wptdxii.playground.todo.tasks.TasksActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseListActivity {
    private static final int RESIDENCE_TIME = 1000;
    private boolean mIsExited = false;

    public static void start(Context context) {
        Intent starter = new Intent(context, HomeActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void setupToolbar(Toolbar toolbar) {
        toolbar.setTitle(R.string.module_main);
    }

    @Override
    protected void setupActionBar(ActionBar actionBar) {
        actionBar.setDisplayHomeAsUpEnabled(false);
    }

    @NonNull
    @Override
    protected List<Module> createModules() {
        List<Module> modules = new ArrayList<>();
        modules.add(new Module(getString(R.string.module_sample), SampleActivity.class));
        modules.add(new Module(getString(R.string.module_to_do), TasksActivity.class));
        return modules;
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    private void exit() {
        if (!mIsExited) {
            mIsExited = true;
            getWindow().getDecorView().postDelayed(() -> mIsExited = false, RESIDENCE_TIME);
            Toast.makeText(this, R.string.main_click_again, Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }
}
