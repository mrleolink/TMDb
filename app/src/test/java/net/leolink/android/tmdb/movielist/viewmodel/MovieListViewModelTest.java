package net.leolink.android.tmdb.movielist.viewmodel;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;
import android.content.Context;

import net.leolink.android.tmdb.common.io.network.api.model.DiscoverMovie;
import net.leolink.android.tmdb.common.io.network.api.model.DiscoverResponse;
import net.leolink.android.tmdb.common.test.RxImmediateSchedulerRule;
import net.leolink.android.tmdb.common.test.TestUtils;
import net.leolink.android.tmdb.movielist.service.MovieListService;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowApplication;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * TODO: this is just a demo unit test, add more tests later
 *
 * @author Leo
 */
@RunWith(RobolectricTestRunner.class)
public class MovieListViewModelTest {
    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    MovieListService mMovieListService;

    private MovieListViewModel subject;

    @Captor
    private ArgumentCaptor<List<DiscoverMovie>> captor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        setupMovieListService();
        Context context = ShadowApplication.getInstance().getApplicationContext();
        subject = new MovieListViewModel(context, context.getResources(), mMovieListService);
    }

    private void setupMovieListService() {
        when(mMovieListService.getMovieList(anyInt())).thenReturn(Observable.just(createFakeDiscoverResponse()));
    }

    private DiscoverResponse createFakeDiscoverResponse() {
        DiscoverResponse discoverResponse = new DiscoverResponse();
        List<DiscoverMovie> movies = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DiscoverMovie movie = new DiscoverMovie();
            movie.setTitle("Title " + i);
            movies.add(movie);
        }
        discoverResponse.setResults(movies);
        discoverResponse.setPage(1); // for next page
        discoverResponse.setTotalPages(10); // for next page
        return discoverResponse;
    }

    @Test
    public void firstLoad_returnFakeMovieList() {
        Observer<List<DiscoverMovie>> observer = mock(Observer.class);
        subject.getMovieListLiveData().observe(TestUtils.TEST_OBSERVER, observer);

        verify(observer).onChanged(captor.capture());
        List<DiscoverMovie> captured = captor.getValue();
        assertThat(captured.size(), is(10));
    }

    @Test
    public void nextPage_returnTwiceFakeMovieList() {
        Observer<List<DiscoverMovie>> observer = mock(Observer.class);
        subject.getMovieListLiveData().observe(TestUtils.TEST_OBSERVER, observer);

        // ignore the default load in view model's constructor
        reset(observer);

        subject.nextPage();

        verify(observer).onChanged(captor.capture());
        List<DiscoverMovie> captured = captor.getValue();
        assertThat(captured.size(), is(20));
    }

    @Test
    public void clearFilterYear_expectCurrentCalendarYear() {
        subject.clearYearFilter();
        int calendarYear = Calendar.getInstance().get(Calendar.YEAR);
        assertThat(subject.getFilterYear(), is(calendarYear));
    }
}