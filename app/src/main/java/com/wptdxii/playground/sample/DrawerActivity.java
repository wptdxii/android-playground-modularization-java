package com.wptdxii.playground.sample;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.wptdxii.playground.R;
import com.wptdxii.playground.core.base.BaseDrawerActivity;

import javax.inject.Inject;

public class DrawerActivity extends BaseDrawerActivity {

    @Inject
    DrawerFragment mDrawerFragment;

    @NonNull
    @Override
    protected Fragment onCreateFragment() {
        return mDrawerFragment;
    }

    @Override
    protected void setupToolbar(Toolbar toolbar) {
        toolbar.setTitle(R.string.sample_drawer_title);
    }

    @Override
    protected int onCreateNavigationHeader() {
        return R.layout.activity_layout_nav_header;
    }

    @Override
    protected int onCreateNavigationMenu() {
        return R.menu.sample_layout_nav_drawer;
    }

    @Override
    protected void setupNavigationView(NavigationView nvDrawer) {
        nvDrawer.setNavigationItemSelectedListener(item -> {
            Toast.makeText(this, "item", Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}
