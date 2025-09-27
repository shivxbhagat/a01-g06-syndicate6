package comp3350.smile.systemTests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.contrib.RecyclerViewActions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.smile.R;
import comp3350.smile.presentation.Activities.HomeActivity;

import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ItemDescriptTest {

    @Rule
    public ActivityScenarioRule<HomeActivity> activityRule = new ActivityScenarioRule<>(HomeActivity.class);

    @Test
    public void testItemDescription() {

        //navigate to the item list
        onView(withId(R.id.buttonForYou)).perform(click());

        //click first item
        onView(withId(R.id.recyclerViewForYou)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // 3. Scroll to the text and verify visibility
        onView(withText("Laptop - Dell XPS 13"))
                .perform(scrollTo())  // Scrolls the parent ScrollView/NestedScrollView
                .check(matches(isDisplayed()));
        //verify item details are displayed
        //onView(withText("Laptop - Dell XPS 13")).check(matches(isDisplayed()));
        //onView(withId(R.id.item_name)).check(matches(isDisplayed()));
        //onView(withId(R.id.item_price)).check(matches(isDisplayed()));

        // Return to list
      //  onView(withId(R.id.btnClose)).perform(click());
        pressBack();
        onView(withId(R.id.recyclerViewForYou)).check(matches(isDisplayed()));

    }
}
