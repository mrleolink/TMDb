package net.leolink.android.tmdb.movielist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import net.leolink.android.tmdb.common.base.BaseActivity;

public class MovieListActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFragment();
    }

    private void setupFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(android.R.id.content);
        if (fragment == null) {
            fragment = new MovieListFragment();
            fm.beginTransaction().add(android.R.id.content, fragment).commit();
        }
    }
}
