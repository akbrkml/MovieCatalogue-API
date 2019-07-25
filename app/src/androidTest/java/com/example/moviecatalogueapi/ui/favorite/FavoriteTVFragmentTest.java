package com.example.moviecatalogueapi.ui.favorite;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.testing.SingleFragmentActivity;
import com.example.moviecatalogueapi.utils.EspressoIdlingResources;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class FavoriteTVFragmentTest {

    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityRule = new ActivityTestRule<>(SingleFragmentActivity.class);
    private FavoriteTVFragment tvFragment = new FavoriteTVFragment();

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResources.getEspressoIdlingResource());
        activityRule.getActivity().setFragment(tvFragment);
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.getEspressoIdlingResource());
    }

    @Test
    public void loadTvShows() {
        onView(withId(R.id.rvFavTv)).check(matches(isDisplayed()));
    }

    @Test
    public void gotoDetailActivityAndRemoveFavorite() {
        onView(withId(R.id.rvFavTv)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.overview)).check(matches(isDisplayed()));

        onView(withId(R.id.btnFavorite)).check(matches(isDisplayed()));
        onView(withId(R.id.btnFavorite)).perform(click());

        pressBack();
    }
}