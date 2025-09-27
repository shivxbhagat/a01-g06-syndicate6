package comp3350.smile.systemTests;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;


import java.sql.SQLException;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;

import comp3350.smile.persistence.DBConnection;

import comp3350.smile.R;
import comp3350.smile.presentation.Activities.HomeActivity;

@RunWith(AndroidJUnit4.class)
public class CategorizeItemsTest {

    @Rule
    public ActivityScenarioRule<HomeActivity> activityRule = new ActivityScenarioRule<>(HomeActivity.class);
    @Test
    public void testCategorizeItems() {

        // Step 1: Ensure we are at the home screen
        onView(withId(R.id.buttonForYou)).check(matches(isDisplayed()));

        // Step 2: Verify categories are displayed
        onView(withId(R.id.categoryFilterButton)).check(matches(isDisplayed()));
        onView(withId(R.id.categoryFilterButton)).perform(click());

        // Step 3: Click on a category (e.g., "Books")
        onView(withText("Books")).perform(click());
        try{
            Thread.sleep(5000); // 2000ms = 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Step 4: Verify that only items in the "Books" category are shown
        //onView(withId(R.id.recyclerViewForYou)).check(matches(isDisplayed()));
        onView(withText("Free Books - Mystery Genre")).check(matches(isDisplayed()));  // Example item

        // Step 5: Return to main item list
        onView(withId(R.id.nav_marketplace)).perform(click());

    }
}
