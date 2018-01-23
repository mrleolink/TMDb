package net.leolink.android.tmdb.movielist;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.leolink.android.tmdb.common.base.BaseFragment;
import net.leolink.android.tmdb.databinding.FragmentMovieListBinding;
import net.leolink.android.tmdb.movielist.viewmodel.MovieListViewModel;
import net.leolink.android.tmdb.movielist.viewmodel.MovieListViewModelFactory;

import javax.inject.Inject;

/**
 * @author Leo
 */
public class MovieListFragment extends BaseFragment {
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
        mViewModel.getMovieListLiveData().observe(this, list -> mAdapter.setData(list));
        // filter button
        mDataBinding.filter.setOnClickListener(view -> {
            Log.e("linhln", "RV shown = "+ mDataBinding.recyclerView.isShown());
            Log.e("linhln", "RV size = "+ mDataBinding.recyclerView.getChildCount());
            Log.e("linhln", "RV adapter = "+ mDataBinding.recyclerView.getAdapter().getItemCount());
        });
    }
}
