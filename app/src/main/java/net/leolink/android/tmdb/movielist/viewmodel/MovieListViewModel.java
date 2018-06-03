package net.leolink.android.tmdb.movielist.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.res.Resources;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.widget.Toast;

import net.leolink.android.tmdb.R;
import net.leolink.android.tmdb.common.Utils;
import net.leolink.android.tmdb.common.io.network.api.model.DiscoverMovie;
import net.leolink.android.tmdb.common.io.network.api.model.DiscoverResponse;
import net.leolink.android.tmdb.movielist.service.MovieListService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * @author Leo
 */
public class MovieListViewModel extends ViewModel {
    /** Max page that TMDB API allows **/
    private static final int MAX_PAGE = 1000;
    /** Default none value of year **/
    private static final int YEAR_NONE = Integer.MIN_VALUE;

    // data binding fields
    public final ObservableBoolean empty = new ObservableBoolean();
    public final ObservableField<String> year = new ObservableField<>();
    public final ObservableField<String> message = new ObservableField<>();

    // private fields
    @SuppressLint("StaticFieldLeak")
    private Context mContext;
    private Resources mResources;
    private MovieListService mMovieListService;

    private final MutableLiveData<Integer> mShowToastNextPageLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<DiscoverMovie>> mMovieListLiveData = new MutableLiveData<>();
    private final List<DiscoverMovie> mMovieList = new ArrayList<>();

    private boolean mIsLoading = false;
    private int mYearFilterInt = YEAR_NONE;
    private int mCurrentPage = 0;
    private boolean mAllLoaded = false;
    private int mLastPageItemCount = 0;

    private Disposable mDisposable;

    @Inject
    MovieListViewModel(Context context, Resources resources, MovieListService movieListService) {
        this.mContext = context;
        this.mResources = resources;
        this.mMovieListService = movieListService;
        load(true);
    }

    @NonNull
    public LiveData<List<DiscoverMovie>> getMovieListLiveData() {
        return mMovieListLiveData;
    }

    @NonNull
    public MutableLiveData<Integer> getShowToastNextPageLiveData() {
        return mShowToastNextPageLiveData;
    }

    public int getLastPageItemCount() {
        return mLastPageItemCount;
    }

    /** Get current year filter if set, current year on calendar if not **/
    public int getFilterYear() {
        int filterYear = mYearFilterInt;
        if (mYearFilterInt == YEAR_NONE) filterYear = Calendar.getInstance().get(Calendar.YEAR);
        return filterYear;
    }

    /** Load next page with current settings **/
    @MainThread
    public void nextPage() {
        if (mIsLoading || mAllLoaded) return;
        mIsLoading = true;
        load(false);
    }

    /** Filter movies by release year **/
    @MainThread
    public void setYear(int year) {
        if (year == mYearFilterInt) return;
        this.mYearFilterInt = year;
        this.year.set(getYearTitle());
        // load
        load(true);
    }

    private String getYearTitle() {
        return mYearFilterInt == YEAR_NONE ? mResources.getString(R.string.latest) : String.valueOf(mYearFilterInt);
    }

    /** Clear year filter **/
    @MainThread
    public void clearYearFilter() {
        setYear(YEAR_NONE);
    }

    @MainThread
    private void load(boolean reset) {
        // mark as loading
        mIsLoading = true;
        // reset data if necessary
        if (reset) {
            resetData();
            mMovieListLiveData.setValue(mMovieList); // trigger notifyDataSetChange() for empty list to avoid RV's crash
            empty.set(true);
            message.set(mResources.getString(R.string.loading));
        }
        // increase page
        mCurrentPage += 1;
        // loading indicator
        mShowToastNextPageLiveData.setValue(mCurrentPage);
        // log
        Utils.loge("About to load: " + mCurrentPage);
        // get
        mDisposable = getMovieListObservable(mCurrentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
    }

    private Observable<DiscoverResponse> getMovieListObservable(int pageToLoad) {
        if (mYearFilterInt == YEAR_NONE) return mMovieListService.getMovieList(pageToLoad);
        return mMovieListService.getMovieListByYear(mCurrentPage, mYearFilterInt);
    }

    @MainThread
    private void onSuccess(DiscoverResponse discoverResponse) {
        // mark as done
        mIsLoading = false;
        // mark all loaded
        if (mCurrentPage >= MAX_PAGE || discoverResponse.getPage() == discoverResponse.getTotalPages()) {
            mAllLoaded = true;
        }
        // save data
        mLastPageItemCount = discoverResponse.getResults().size();
        mMovieList.addAll(discoverResponse.getResults());
        mMovieListLiveData.setValue(mMovieList);
        // check empty
        empty.set(mMovieList.isEmpty());
        message.set(mResources.getString(R.string.empty));
    }

    @MainThread
    private void onError(Throwable throwable) throws IOException {
        // mark as done
        mIsLoading = false;
        // show error
        String errorBody = throwable.getMessage();
        if (throwable instanceof HttpException) {
            ResponseBody rb = ((HttpException) throwable).response().errorBody();
            if (rb != null) errorBody = rb.string();
        }
        String errorMessage = mResources.getString(R.string.error, errorBody);
        // decide how to show the errorMessage
        if (mMovieList.isEmpty()) {
            empty.set(true);
            message.set(errorMessage);
        } else {
            Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    private void resetData() {
        mIsLoading = false;
        mAllLoaded = false;
        mCurrentPage = 0;
        mMovieList.clear();
    }

    @Override
    protected void onCleared() {
        resetData();
        mDisposable.dispose();
    }
}
