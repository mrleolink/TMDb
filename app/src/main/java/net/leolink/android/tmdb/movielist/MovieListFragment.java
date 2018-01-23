package net.leolink.android.tmdb.movielist;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.leolink.android.tmdb.common.base.BaseFragment;
import net.leolink.android.tmdb.common.dialog.NumberPickerDialog;
import net.leolink.android.tmdb.databinding.FragmentMovieListBinding;
import net.leolink.android.tmdb.movielist.viewmodel.MovieListViewModel;
import net.leolink.android.tmdb.movielist.viewmodel.MovieListViewModelFactory;

import javax.inject.Inject;

/**
 * @author Leo
 */
public class MovieListFragment extends BaseFragment {
    private static final int MIN_YEAR = 1000;
    private static final int MAX_YEAR = 2050;

    @Inject
    Context mContext; // application context
    @Inject
    MovieListViewModelFactory mMovieListViewModelFactory;

    private FragmentMovieListBinding mDataBinding;
    private MovieListViewModel mViewModel;
    private MovieListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mDataBinding = FragmentMovieListBinding.inflate(inflater, container, false);
        mViewModel = ViewModelProviders.of(this, mMovieListViewModelFactory).get(MovieListViewModel.class);
        mDataBinding.setViewmodel(mViewModel);
        setupView();
        return mDataBinding.getRoot();
    }

    private void setupView() {
        // recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mDataBinding.recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MovieListAdapter(mViewModel);
        mDataBinding.recyclerView.setAdapter(mAdapter);
        mViewModel.getMovieListLiveData().observe(this, mAdapter::setData);
        // filter button
        mDataBinding.filter.setOnClickListener(view -> filterByYear());
        // clear filter
        mDataBinding.clearFilter.setOnClickListener(view -> mViewModel.clearYearFilter());
    }

    private void filterByYear() {
        Activity activity = getActivity();
        if (activity != null) {
            new NumberPickerDialog.Builder(activity)
                    .setCallback(mViewModel::setYear)
                    .setCurValue(mViewModel.getFilterYear())
                    .setMinValue(MIN_YEAR)
                    .setMaxValue(MAX_YEAR)
                    .build()
                    .show();
        }
    }
}
