package comp3350.smile.logic;



import java.util.List;

import comp3350.smile.application.Services;
import comp3350.smile.objects.Item;
import comp3350.smile.objects.CategoryType;
import comp3350.smile.persistence.ItemPersistence;
import comp3350.smile.persistence.stubs.ItemPersistenceStub;
import  comp3350.smile.logic.Interfaces.IItemService;
import  comp3350.smile.logic.ItemValidator;
import comp3350.smile.logic.LogicConfig;
public class ItemService implements IItemService {
    private final ItemPersistence itemPersistence;
    private final ItemValidator itemValidator;


    public ItemService() {
        this.itemPersistence = Services.getItemPersistence();
        this.itemValidator = new ItemValidator();
    }

    // Constructor for stub persistence (useful for testing)
    public ItemService(ItemPersistence itemPersistence) {
        this.itemPersistence = itemPersistence;
        this.itemValidator = new ItemValidator();
    }

    // Existing methods
    /**
     * Retrieves a list of items that belong to the specified category for the given user ID.
     *
     * @param category the category to filter items by
     * @param id the user ID associated with the items
     * @return a list of items in the specified category
     */
    public List<Item> getItemsByCategory(String category, int id) {
        return itemPersistence.getItemsByCategory(category, id);
    }

    /**
     * @return a list of randomly selected items
     */
    public List<Item> getRandomItems() {
        return itemPersistence.getItemsRandom();
    }

    /**
     * @param id the unique ID of the item to retrieve
     * @return the Item with the specified ID, or null if not found
     */
    public Item getItemByID(int id) {
        return itemPersistence.getItemByID(id);
    }



    /**
     * Assigns and returns the corresponding icon drawable ID based on the item category.
     *
     * @param category the category name to assign an icon for
     * @return the drawable resource ID for the specified category
     */
    public int assignCategoryVisual(String category) {
        int categoryIcon = CategoryType.getIconFromString(category);
        System.out.println("" + LogicConfig.CATEGORY + category);
        System.out.println("" + LogicConfig.DRAWABLE_ID + categoryIcon);
        return categoryIcon;
    }

    // Additional methods from the old ItemService for saved items management

    /**
     * Saves an item for the current user after validating it.
     *
     * @param item the item to be added to the saved list
     * @throws IllegalArgumentException if the item is invalid
     * @throws IllegalStateException if the item is already saved
     */
    public void addItemToSaved(Item item) {
        itemValidator.validate(item);

        if (isItemSaved(item)) {
            throw new IllegalStateException(LogicConfig.SAVED_EXCEPTION);
        }

        itemPersistence.saveItem(item);
    }



    /**
     * Removes a saved item for the current user.
     *
     * @param item the item to be removed from the saved list
     */
    public void removeItemFromSaved(Item item) {
        itemPersistence.removeSaved(item);
    }

    /**
     * Checks if an item is saved for the current user.
     *
     * @param item the item to check
     * @return true if the item is saved, false otherwise
     */
    public boolean isItemSaved(Item item) {
        return itemPersistence.isItemSaved(item);
    }

    /**
     * Retrieves the list of saved items for the current user.
     *
     * @return a list of saved items
     */
    public List<Item> getSavedItems() {
        return itemPersistence.getSaved();
    }

    /**
     * Retrieves the list of items listed by the current user.
     *
     * @return a list of items listed by the user
     */
    public List<Item> getListedItems() {
        return itemPersistence.getListedItems();
    }

    /**
     * Updates the specified item in the data store.
     *
     * @param item the item to be updated
     */
    public void updateItem(Item item) {
        itemPersistence.updateItem(item);
    }

    /**
     * Deletes the specified item from the data store.
     *
     * @param item the item to be deleted
     */
    public void deleteItem(Item item) {
        itemPersistence.deleteItem(item);
    }

    /**
     * Adds a new item to the data store.
     *
     * @param newItem the item to be added
     */
    public void addItem(Item newItem) {
        itemPersistence.addItem(newItem);
    }
}
