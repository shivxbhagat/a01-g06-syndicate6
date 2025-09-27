package comp3350.smile.systemTests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.SQLException;

import comp3350.smile.R;
import comp3350.smile.persistence.DBConnection;
import comp3350.smile.presentation.Activities.LoginActivity;

@RunWith(AndroidJUnit4.class)
public class ProfileManagementTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testUserCanChangeFirstName() {

        // Step 1: User starts at fresh home page
        onView(withId(R.id.loginIcon)).check(matches(isDisplayed()));

        // Step 2: User logs in with username and password
        onView(withId(R.id.emailEditText)).perform(typeText("parkerp@myumanitoba.ca"));
        onView(withId(R.id.passwordEditText)).perform(typeText("4563"));
        closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());

        // Step 3: User navigates to the profile page
        onView(withId(R.id.nav_user)).perform(click());

        // Step 4: User changes current first Name
        onView(withId(R.id.firstName)).perform(clearText());
        onView(withId(R.id.firstName)).perform(typeText("camryn"));
        closeSoftKeyboard();

        // Step 5: User clicks save
        onView(withId(R.id.btnUpdate)).perform(click());

        // Step 6: Verify that the change was successful
        onView(withId(R.id.firstName)).check(matches(withText("camryn")));

        // Step 7: User logs out
        onView(withId(R.id.logoutButton)).perform(click());

        // Verify that user is back to login page
        onView(withId(R.id.loginIcon)).check(matches(isDisplayed()));
    }
}
