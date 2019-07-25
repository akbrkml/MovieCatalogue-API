package com.example.moviecatalogueapi.ui;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import com.example.moviecatalogueapi.R;
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

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResources.getEspressoIdlingResource());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.getEspressoIdlingResource());
    }

    @Test
    public void gotoDetailActivity() {
        onView(withId(R.id.rvMovie)).perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));

        onView(withId(R.id.overview)).check(matches(isDisplayed()));

        pressBack();
    }

}