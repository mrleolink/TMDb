package net.leolink.android.tmdb.common.base;

import android.content.Context;
import android.content.res.Resources;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * @author Leo
 */
public class BaseFragment extends DaggerFragment {
    // add more base methods here if necessary
    @Inject
    protected Context mContext; // application context
    @Inject
    protected Resources mResources;
}
