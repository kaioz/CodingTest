package com.cocosw.westpac.ui;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.cocosw.westpac.R;
import com.cocosw.westpac.app.WestpacApplication;
import com.cocosw.westpac.service.MockLocation;
import com.cocosw.westpac.service.MockServiceProvider;
import com.cocosw.westpac.service.MockWeather;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;

/**
 * NBN Atlas Wayfinder
 * <p/>
 * Created by kai on 27/11/2015.
 */
@RunWith(AndroidJUnit4.class)
public class MainTest {

    @Rule
    public final ActivityTestRule<Main> main =
            new ActivityTestRule<>(Main.class, true, false);

    MockServiceProvider provider;

    @Before
    public void before() {
        provider = (MockServiceProvider) ((WestpacApplication) InstrumentationRegistry.getInstrumentation().getTargetContext()
                .getApplicationContext()).getProvider();
    }

    @Test
    public void fineLocationWithFineWeather() {
        provider.setResponse(MockLocation.class,MockLocation.SYDNEY);
        provider.setResponse(MockWeather.class,MockWeather.LA);
        main.launchActivity(new Intent());
        onView(withId(R.id.temperature)).check(matches(withText("45.44")));
    }

    @Test
    public void noLocation() {
        provider.setResponse(MockLocation.class,MockLocation.NULL);
        main.launchActivity(new Intent());
        onView(withId(R.id.temperature)).check(matches(withText("--")));
        onView(withId(R.id.summary)).check(matches(withText(R.string.failed_get_location)));
    }

    @Test
    public void fineLocationWithWrongJson() {
        provider.setResponse(MockLocation.class,MockLocation.SYDNEY);
        provider.setResponse(MockWeather.class,MockWeather.ERROR);
        main.launchActivity(new Intent());
        onView(withId(R.id.temperature)).check(matches(withText("--")));
        onView(withId(R.id.summary)).check(matches(withText(containsString("JSON"))));
    }

    @Test
    public void fineLocationWithNetworkException() {
        provider.setResponse(MockLocation.class,MockLocation.SYDNEY);
        provider.setResponse(MockWeather.class,MockWeather.EXCEPTION);
        main.launchActivity(new Intent());
        onView(withId(R.id.temperature)).check(matches(withText("--")));
        onView(withId(R.id.summary)).check(matches(withText(containsString("Mock Exception"))));
    }

    @Test
    public void pullToRefresh() {
        fineLocationWithFineWeather();
        provider.setResponse(MockWeather.class,MockWeather.CHENGDU);
        onView(withId(R.id.swipe)).perform(ViewActions.swipeDown());
        onView(withId(R.id.temperature)).check(matches(withText("50.1")));
        provider.setResponse(MockWeather.class,MockWeather.MELB);
        onView(withId(R.id.swipe)).perform(ViewActions.swipeDown());
        onView(withId(R.id.temperature)).check(matches(withText("20.1")));
    }

    @After
    public void after() {
        provider.setResponse(MockLocation.class,MockLocation.NONMOCK);
        provider.setResponse(MockWeather.class,MockWeather.NONMOCK);
    }
}