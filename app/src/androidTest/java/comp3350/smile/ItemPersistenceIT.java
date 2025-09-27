package comp3350.smile;

import static org.junit.Assert.*;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.SQLException;
import java.util.List;

import comp3350.smile.application.Services;
import comp3350.smile.objects.Item;
import comp3350.smile.persistence.ItemPersistence;

@RunWith(AndroidJUnit4.class)
public class ItemPersistenceIT {

    private ItemPersistence itemPersistence;

    @Before
    public void setUp() throws SQLException {
        Context context = ApplicationProvider.getApplicationContext();
        Services.init(context, false);
        itemPersistence = Services.getItemPersistence(); // Use real database
    }

    @Test
    public void testDatabaseConnection() {
        assertNotNull("Database connection should be established", itemPersistence);
    }

    @Test
    public void testGetItemsRandom() {
        List<Item> items = itemPersistence.getItemsRandom();
        assertNotNull("Random items list should not be null", items);
        assertFalse("Random items list should not be empty", items.isEmpty());
    }

    @Test
    public void testGetItemByID() {
        Item item = itemPersistence.getItemByID(1);
        assertNotNull("Item with ID 1 should exist", item);
    }

    @Test
    public void testGetItemsByCategory() {
        List<Item> items = itemPersistence.getItemsByCategory("Electronics", 0);
        assertNotNull("Category fetch should return a list", items);
    }
}