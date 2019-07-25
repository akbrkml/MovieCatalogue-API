package com.example.moviecatalogueapi.ui.detail;

import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.utils.Constant;
import com.example.moviecatalogueapi.utils.EspressoIdlingResources;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class DetailActivityTest {

    @Rule
    public ActivityTestRule<DetailActivity> activityRule = new ActivityTestRule<DetailActivity>(DetailActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent result = new Intent(targetContext, DetailActivity.class);
            int movieId = 429617;
            result.putExtra(Constant.ID, movieId);
            result.putExtra(Constant.EXTRA_FROM, Constant.MOVIE);
            return result;
        }
    };

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResources.getEspressoIdlingResource());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.getEspressoIdlingResource());
    }

    @Test
    public void loadDataDetailActivity() {
        onView(withId(R.id.ivPosterDetail)).check(matches(isDisplayed()));
        onView(withId(R.id.backdrop)).check(matches(isDisplayed()));
        onView(withId(R.id.year)).check(matches(isDisplayed()));
        onView(withId(R.id.rating)).check(matches(isDisplayed()));
        onView(withId(R.id.tvDescription)).check(matches(isDisplayed()));
    }

    @Test
    public void loadDataDetailAndClickFavoriteButton() {
        onView(withId(R.id.btnFavorite)).check(matches(isDisplayed()));
        onView(withId(R.id.btnFavorite)).perform(click());
    }

}