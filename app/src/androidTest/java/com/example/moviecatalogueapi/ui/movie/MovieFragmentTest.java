package com.example.moviecatalogueapi.ui.movie;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.testing.SingleFragmentActivity;
import com.example.moviecatalogueapi.utils.EspressoIdlingResources;
import com.example.moviecatalogueapi.utils.RecyclerViewItemCountAssertion;

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

public class MovieFragmentTest {

    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityRule = new ActivityTestRule<>(SingleFragmentActivity.class);
    private MovieFragment movieFragment = new MovieFragment();

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResources.getEspressoIdlingResource());
        activityRule.getActivity().setFragment(movieFragment);
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.getEspressoIdlingResource());
    }

    @Test
    public void loadMovies() {
        onView(withId(R.id.rvMovie)).check(matches(isDisplayed()));
        onView(withId(R.id.rvMovie)).check(new RecyclerViewItemCountAssertion(20));
    }

    @Test
    public void gotoDetailActivity() {
        onView(withId(R.id.rvMovie)).perform(RecyclerViewActions.actionOnItemAtPosition(12, click()));

        onView(withId(R.id.overview)).check(matches(isDisplayed()));

        pressBack();
    }

    @Test
    public void gotoDetailActivityAndAddFavorite() {
        onView(withId(R.id.rvMovie)).perform(RecyclerViewActions.actionOnItemAtPosition(12, click()));

        onView(withId(R.id.overview)).check(matches(isDisplayed()));

        onView(withId(R.id.btnFavorite)).check(matches(isDisplayed()));
        onView(withId(R.id.btnFavorite)).perform(click());

        pressBack();
    }
}