package comp3350.smile.logic.Interfaces;

import java.util.List;
import comp3350.smile.objects.Item;

public interface IItemService {
    List<Item> getItemsByCategory(String category, int id); //get tge items based on the category
    List<Item> getRandomItems(); //get random items
    Item getItemByID(int id); //get the items based the id passed
    int assignCategoryVisual(String category); //assigns a catergory visual to the item
    void addItemToSaved(Item item); //bookmarks the item
    void removeItemFromSaved(Item item); //de-bookmarks
    boolean isItemSaved(Item item); //checks if the item is bookmarked
    List<Item> getSavedItems(); //get all bookmark items
}
