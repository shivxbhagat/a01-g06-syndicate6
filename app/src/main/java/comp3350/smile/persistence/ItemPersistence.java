package comp3350.smile.persistence;

import java.util.List;

import comp3350.smile.objects.Item;

public interface ItemPersistence {

    List<Item> getItemsRandom();

    List<Item> getItemsByCategory(String category, int id);

    Item getItemByID(int itemID);

    void saveItem(Item item);

    boolean isItemSaved(Item item);

    void removeSaved(Item item);

    List<Item> getSaved();

    List<Item> getListedItems();

    void updateItem(Item item);

    void deleteItem(Item item);

    void addItem(Item newItem);
}