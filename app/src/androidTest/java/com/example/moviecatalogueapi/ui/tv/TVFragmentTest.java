package com.example.moviecatalogueapi.ui.tv;

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

public class TVFragmentTest {

    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityRule = new ActivityTestRule<>(SingleFragmentActivity.class);
    private TVFragment tvFragment = new TVFragment();

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
        onView(withId(R.id.rvTv)).check(matches(isDisplayed()));
        onView(withId(R.id.rvTv)).check(new RecyclerViewItemCountAssertion(20));
    }

    @Test
    public void gotoDetailActivity() {
        onView(withId(R.id.rvTv)).perform(RecyclerViewActions.actionOnItemAtPosition(10, click()));

        onView(withId(R.id.overview)).check(matches(isDisplayed()));

        pressBack();
    }
}