package net.leolink.android.tmdb.movielist;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.leolink.android.tmdb.R;
import net.leolink.android.tmdb.common.base.BaseFragment;
import net.leolink.android.tmdb.common.dialog.NumberPickerDialog;
import net.leolink.android.tmdb.common.io.network.api.model.DiscoverMovie;
import net.leolink.android.tmdb.databinding.FragmentMovieListBinding;
import net.leolink.android.tmdb.moviedetail.MovieDetailActivity;
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
        mAdapter = new MovieListAdapter(mViewModel, this::openMovieDetail);
        mDataBinding.recyclerView.setAdapter(mAdapter);
        // observe view model
        mViewModel.getMovieListLiveData().observe(this, mAdapter::setData);
        mViewModel.getShowToastNextPageLiveData().observe(this, page -> {
            if (page != null && page > 0) {
                String message = mResources.getString(R.string.loading_next_page, page);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                // reset
                mViewModel.getShowToastNextPageLiveData().setValue(0);
            }
        });
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

    private void openMovieDetail(DiscoverMovie movie) {
        startActivity(MovieDetailActivity.newIntent(mContext, movie));
    }
}
