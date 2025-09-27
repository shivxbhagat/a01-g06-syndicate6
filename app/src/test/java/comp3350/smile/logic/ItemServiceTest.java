package comp3350.smile.logic;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import comp3350.smile.R;
import comp3350.smile.objects.CategoryType;
import comp3350.smile.objects.Item;
import comp3350.smile.objects.User;
import comp3350.smile.persistence.ItemPersistence;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {

    @Mock
    private ItemPersistence mockItemPersistence;

    @InjectMocks
    private ItemService itemService;

    private Item testItem;
    private User testUser;

    @Before
    public void setUp() {
        testUser = new User("Test", "User", "test@myumanitoba.ca", "2042022221", "password");
        testItem = new Item(
                1, "Laptop", "High-performance laptop", "Electronics",
                "New", 999.99, testUser, "/img/laptop.jpg", "Credit Card"
        );
    }

    @Test
    public void testGetRandomItems() {
        when(mockItemPersistence.getItemsRandom()).thenReturn(Collections.singletonList(testItem));

        List<Item> items = itemService.getRandomItems();
        assertFalse(items.isEmpty());
        assertEquals(1, items.size());
        verify(mockItemPersistence).getItemsRandom();
    }

    @Test
    public void testGetItemByID_Found() {
        when(mockItemPersistence.getItemByID(1)).thenReturn(testItem);

        Item result = itemService.getItemByID(1);
        assertEquals(testItem, result);
        verify(mockItemPersistence).getItemByID(1);
    }

    @Test
    public void testGetItemByID_NotFound() {
        when(mockItemPersistence.getItemByID(999)).thenReturn(null);

        Item result = itemService.getItemByID(999);
        assertNull(result);
        verify(mockItemPersistence).getItemByID(999);
    }

    @Test
    public void testAddItemToSaved_Success() {
        when(mockItemPersistence.isItemSaved(testItem)).thenReturn(false);

        itemService.addItemToSaved(testItem);
        verify(mockItemPersistence).saveItem(testItem);
    }

    @Test(expected = IllegalStateException.class)
    public void testAddItemToSaved_AlreadySaved() {
        when(mockItemPersistence.isItemSaved(testItem)).thenReturn(true);

        itemService.addItemToSaved(testItem);
    }

    @Test
    public void testRemoveItemFromSaved() {
        itemService.removeItemFromSaved(testItem);
        verify(mockItemPersistence).removeSaved(testItem);
    }

    @Test
    public void testUpdateItem() {
        itemService.updateItem(testItem);
        verify(mockItemPersistence).updateItem(testItem);
    }

    @Test
    public void testDeleteItem() {
        itemService.deleteItem(testItem);
        verify(mockItemPersistence).deleteItem(testItem);
    }

    @Test
    public void testGetSavedItems() {
        List<Item> savedItems = Collections.singletonList(testItem);
        when(mockItemPersistence.getSaved()).thenReturn(savedItems);

        List<Item> result = itemService.getSavedItems();
        assertEquals("Saved items should match", savedItems, result);
        verify(mockItemPersistence).getSaved();
    }

    @Test
    public void testGetListedItems() {
        List<Item> listedItems = Collections.singletonList(testItem);
        when(mockItemPersistence.getListedItems()).thenReturn(listedItems);

        List<Item> result = itemService.getListedItems();
        assertEquals("Listed items should match", listedItems, result);
        verify(mockItemPersistence).getListedItems();
    }

    @Test
    public void testAddItem() {
        itemService.addItem(testItem);
        verify(mockItemPersistence).addItem(testItem);
    }

    @Test
    public void testAssignCategoryVisual_AllCategories() {
        assertEquals(R.drawable.electricity_icon, itemService.assignCategoryVisual("Electronics"));
        assertEquals(R.drawable.book_icon, itemService.assignCategoryVisual("Books"));
        assertEquals(R.drawable.house_icon, itemService.assignCategoryVisual("House"));
        assertEquals(R.drawable.lost_and_found, itemService.assignCategoryVisual("Lost and Found"));

        // Check the actual default behavior of CategoryType.getIconFromString()
        int defaultIcon = CategoryType.getIconFromString("Invalid Category");
        assertEquals(defaultIcon, itemService.assignCategoryVisual("Invalid Category"));
    }

    @Test
    public void testGetItemsByCategory_WithExclusion() {
        Item excludedItem = new Item(2, "Excluded", "Desc", "Electronics", "Used", 500.0, testUser, "/img", "Cash");
        List<Item> mockItems = Arrays.asList(testItem, excludedItem);

        when(mockItemPersistence.getItemsByCategory("Electronics", 2)).thenReturn(
                mockItems.stream().filter(item -> item.getItemId() != 2).toList()
        );

        List<Item> result = itemService.getItemsByCategory("Electronics", 2);
        assertEquals("Should exclude item with ID 2", 1, result.size());
        assertEquals(testItem, result.get(0));
    }

    @Test
    public void testIsItemSaved() {
        when(mockItemPersistence.isItemSaved(testItem)).thenReturn(true);
        assertTrue("Item should be saved", itemService.isItemSaved(testItem));
        verify(mockItemPersistence).isItemSaved(testItem);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddItemToSaved_InvalidItem() {
        Item invalidItem = new Item(-2, "", "Desc", "", "", -5.0, testUser, "", "");
        itemService.addItemToSaved(invalidItem); // Triggers ItemValidator
    }
}