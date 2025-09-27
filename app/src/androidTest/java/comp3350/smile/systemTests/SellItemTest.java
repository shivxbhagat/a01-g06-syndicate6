package comp3350.smile.systemTests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.contrib.RecyclerViewActions;
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
import comp3350.smile.R;
@RunWith(AndroidJUnit4.class)
public class SellItemTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testManageListing()
    {
        //login with admin
        onView(withId(R.id.emailEditText)).perform(typeText("admin@myumanitoba.ca"));
        onView(withId(R.id.passwordEditText)).perform(typeText("1234"));
        closeSoftKeyboard();

        onView(withId(R.id.loginButton)).perform(click());

        //navigate to item lists
        onView(withId(R.id.buttonLists)).perform(click());

        //open first item listing to edit
        onView(withId(R.id.recyclerViewLists)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //scroll up till you see edit product name
        onView(withId(R.id.editProductName)).perform(scrollTo());

        //clear the current text and type new text

        onView(withId(R.id.editProductName)).perform(clearText());
        onView(withId(R.id.editProductName)).perform(typeText("White Paper"));

        try { Thread.sleep(3000); } catch (InterruptedException e) {}
        closeSoftKeyboard();


        pressBack();







    }
}
