package com.kewargs.cs309;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.kewargs.cs309.activity.auth.LoginActivity;
import com.kewargs.cs309.activity.dashboard.DashboardActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void beforeTests() {
        Intents.init();
    }

    @After
    public void afterTests() {
        Intents.release();
    }

    @Test
    public void performLogin() {
        // MainActivity redirects to LoginActivity
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        intended(hasComponent(LoginActivity.class.getName()));

        onView(withId(R.id.newname))
            .perform(typeText("s"), closeSoftKeyboard());

        onView(withId(R.id.newDisplay))
            .perform(typeText("s"), closeSoftKeyboard());

        onView(withId(R.id.loginButton)).perform(click());

        // Check if we got the dashboard
//        intended(hasComponent(DashboardActivity.class.getName()));
        // This element is only found in DashboardActivity, check if it's present
        onView(withId(R.id.userInfoDump)).check(matches(isDisplayed()));
    }
}
