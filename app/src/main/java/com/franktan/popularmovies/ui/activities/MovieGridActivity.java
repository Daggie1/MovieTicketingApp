package com.franktan.popularmovies.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.franktan.popularmovies.model.MovieGroup;
import com.franktan.popularmovies.sync.MovieSyncAdapter;
import com.franktan.popularmovies.ui.adapters.MovieGroupViewPagerAdapter;
import com.franktan.popularmovies.ui.fragments.MovieDetailFragment;
import com.franktan.popularmovies.ui.fragments.MoviesGridFragment;
import com.franktan.popularmovies.ui.fragments.TicketsListDialogFragment;
import com.franktan.popularmovies.R;
public class MovieGridActivity extends AppCompatActivity
        implements MoviesGridFragment.OnFragmentInteractionListener {

    private boolean mTwoPaneMode;
    ViewPager mMovieGroupViewPager;
    MovieGroupViewPagerAdapter mMovieGroupViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TabLayout tabLayout;

        setContentView(R.layout.activity_movie_grid);
        setSupportActionBar((Toolbar) findViewById(R.id.movie_group_toolbar));
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        MovieSyncAdapter.initialize(this);

        mTwoPaneMode = (findViewById(R.id.fragment_movie_details) != null) ? true : false;

        // Set up movie group tab layout
        mMovieGroupViewAdapter = new MovieGroupViewPagerAdapter(getSupportFragmentManager());
        mMovieGroupViewAdapter.addMovieGroup(MovieGroup.POPULAR);
        mMovieGroupViewAdapter.addMovieGroup(MovieGroup.TOP_RATED);
        mMovieGroupViewAdapter.addMovieGroup(MovieGroup.FAVORITE);

        mMovieGroupViewPager = (ViewPager) findViewById(R.id.movie_grid_view_pager);
        mMovieGroupViewPager.setAdapter(mMovieGroupViewAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mMovieGroupViewPager);

        mMovieGroupViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                // when switching page, restore the selected movie on that page
                mMovieGroupViewAdapter.checkAllFragmentsForViewPageChange();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * Check if the app is running in two pane mode
     * @return
     */
    @Override
    public boolean isInTwoPaneMode() {
        return mTwoPaneMode;
    }

    /**
     * Ask the movie details fragment to use the movie identified by movieDBId
     * @param movieDBId
     */
    @Override
    public void onMovieItemSelected(long movieDBId) {
        MovieDetailFragment fragment = (MovieDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_movie_details);
        fragment.showDetailsByMovieDBId(movieDBId);
    }

    /**
     * Check if fragment is the active one the user is reading on screen
     * @param fragment
     * @return
     */
    @Override
    public boolean isActiveFragment(MoviesGridFragment fragment) {
        int activePosition = mMovieGroupViewPager.getCurrentItem();
        MovieGroup activeMovieGroup = mMovieGroupViewAdapter.getMovieGroup(activePosition);
        MovieGroup fragmentMovieGroup = fragment.getMovieGroup();
        return (activeMovieGroup == fragmentMovieGroup);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_shop:

                  startActivity(new Intent(getApplicationContext(),DisplayTicketsActivity.class));
                    return true;

                case R.id.navigation_gifts:

                    return true;
                case R.id.navigation_cart:

                    return true;

            }
            return false;
        }
    };


}
