package comp3350.smile;


import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.smile.logic.ItemServiceTest;
import comp3350.smile.logic.ItemValidatorTest;
import comp3350.smile.logic.UserProfileServiceTest;
import comp3350.smile.logic.UserValidatorTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ItemServiceTest.class,
        UserProfileServiceTest.class,
        ItemValidatorTest.class,
        UserValidatorTest.class,
})
public class AllTests {
}