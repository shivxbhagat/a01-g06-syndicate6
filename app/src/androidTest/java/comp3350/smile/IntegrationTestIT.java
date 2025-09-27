package comp3350.smile;

import comp3350.smile.ItemPersistenceIT;
import comp3350.smile.UserPersistenceIT;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ItemPersistenceIT.class,
        UserPersistenceIT.class
})
public class IntegrationTestIT {
}