package comp3350.smile.systemTests;
import comp3350.smile.persistence.DBConnection;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import androidx.test.espresso.contrib.RecyclerViewActions;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;

import comp3350.smile.R;
import comp3350.smile.persistence.DBConnection;
import comp3350.smile.presentation.Activities.LoginActivity;

import java.sql.SQLException;


@RunWith(AndroidJUnit4.class)
public class LookForAvailbaleItemsTest {


    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testSearchAndBookmarkItem() {
        //login with admin
        onView(withId(R.id.emailEditText)).perform(replaceText("admin@myumanitoba.ca"));
        onView(withId(R.id.passwordEditText)).perform(replaceText("1234"));
        closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());

        // 1. User clicks search button
        onView(withId(R.id.btnSearch)).perform(click());

        // 2. User types in a search term
        onView(withId(R.id.searchInput)).perform(replaceText("Laptop"));

        // 3. Verify that search results are displayed
        onView(withId(R.id.recyclerViewForYou))
                .check(matches(isDisplayed()));

        // 4. Select the first item in the list (scroll if necessary)
        onView(withId(R.id.recyclerViewForYou)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // 5. Bookmark the item
        onView(withId(R.id.btnBookmark)).perform(click());

        // 6. Navigate to the "Saved" page and verify the item is bookmarked
        pressBack();
        onView(withId(R.id.buttonSaved)).perform(click()); //check this!!!
        onView(withText("Laptop")).check(matches(isDisplayed()));

        //7.Unbookmark the item and verify it's removed
        onView(withId(R.id.recyclerViewSaved)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.btnBookmark)).perform(click());
        pressBack();
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        onView(withId(R.id.recyclerViewSaved))
                .check(matches(isDisplayed()));

    }


}
