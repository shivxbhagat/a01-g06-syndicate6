package comp3350.smile.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.smile.application.Services;
import comp3350.smile.objects.Item;
import comp3350.smile.objects.User;
import comp3350.smile.persistence.ItemPersistence;

public class ItemPersistenceStub implements ItemPersistence {

    private final List<Item> items;
    public ItemPersistenceStub(){
        items = new ArrayList<>();

        // dummy users for items - can be used for login/signup
        User user1 = new User( "Bruce", "Wayne", "wayneb@myumanitoba.ca", "2041234567", "2341");
        User user2 = new User("Clark", "Kent", "kentc@umanitoba.ca", "2049876543", "3452");
        User user3 = new User("Peter", "Parker", "parkerp@myumanitoba.ca", "2046373736", "4563");
        User user4 = new User("Tony", "Stark", "starkt@umanitoba.ca", "2040485764", "5674");
        User user5 = new User("Thor", "Odinson", "thoro@myumanitoba.ca", "2040987234", "6785");

        // Laptops
        items.add(new Item(1, "Laptop - Dell XPS 13", "A lightweight laptop with high performance for work and play.", "Electronics", "Used", 999.99, user1, "dell_xps13", "Interac"));
        items.add(new Item(2, "Laptop - HP Spectre x360", "Convertible laptop with a touchscreen, great for students.", "Electronics", "Used", 799.99, user3, "hp_spectre", "Interac"));
        items.add(new Item(3, "Laptop - MacBook Air", "Lightweight Apple MacBook Air, perfect for students.", "Electronics", "Used", 899.99, user4, "macbook_air", "Cash"));

        // Notes
        items.add(new Item(4, "COMP 3350 Notes", "Comprehensive notes for COMP 3350 course, covering key concepts.", "Books", "New", 10.99, user2, "notes1", "Cash"));
        items.add(new Item(5, "Math 1010 Notes", "Summarized notes for introductory math course, easy to follow.", "Books", "New", 7.99, user5, "notes2", "Cash"));
        items.add(new Item(6, "History 2020 Notes", "Complete set of notes for History 2020, including key events and dates.", "Books", "Used", 12.99, user4, "notes3", "Cash"));
        items.add(new Item(7, "Biology 1001 Notes", "Extensive notes on Biology 1001, includes diagrams and explanations.", "Books", "Used", 9.99, user5, "notes4", "Cash"));
        items.add(new Item(8, "Psychology 101 Notes", "Notes for Psychology 101, great for exam prep.", "Books", "New", 8.99, user1, "notes5", "Cash"));

        // Free Items
        items.add(new Item(9, "Free Yoga Mat", "Free gently used yoga mat. Great for beginners.", "Free", "Used", 0.00, user4, "yoga_mat", "None"));
        items.add(new Item(10, "Free Books - Mystery Genre", "A collection of free mystery genre books, perfect for reading lovers.", "Free", "Used", 0.00, user1, "free_books", "None"));
        items.add(new Item(11, "Free Kitchen Utensils", "Set of free kitchen utensils, perfect for new home owners.", "Free", "Used", 0.00, user2, "kitchen_utensils", "None"));
        items.add(new Item(12, "Free Old TV", "Old TV, still works, free to a good home.", "Free", "Used", 0.00, user3, "free_tv", "None"));

        // Lost and Found Items
        items.add(new Item(13, "Lost Wallet - Brown", "Found black leather wallet. Has ID and credit cards.", "Lost and Found", "Used", 0.00, user2, "lost_wallet", "None"));
        items.add(new Item(14, "Lost Phone", "Found an iPhone 13, looks like it's been misplaced. Contact to claim.", "Lost and Found", "Used", 0.00, user3, "lost_phone", "None"));
        items.add(new Item(15, "Lost Watch - Rolex", "Found Rolex watch in the park. Great condition.", "Lost and Found", "Used", 0.00, user5, "lost_watch", "None"));

        // Miscellaneous Items
        items.add(new Item(16, "Old Guitar", "Vintage guitar in good condition. Needs new strings.", "Electronics", "Used", 150.00, user2, "old_guitar", "Interac"));
        items.add(new Item(17, "Brand New Headphones", "Wireless headphones, brand new in box.", "Electronics", "New", 50.00, user2, "headphones", "Interac"));
        items.add(new Item(18, "Textbooks for Sale", "Various textbooks available for sale from different subjects.", "Books", "Used", 20.00, user5, "textbooks", "Interac"));
        items.add(new Item(19, "Portable Speaker", "Compact portable speaker, great sound quality.", "Electronics", "New", 25.00, user1, "speaker", "Interac"));
        items.add(new Item(20, "Bike for Sale", "Used bike in good condition, perfect for commuting.", "", "Outdoor", 120.00, user1, "bike", "Interac"));


        //save some items
        items.get(2).setSaved(true);
        items.get(4).setSaved(true);
        items.get(3).setSaved(true);
        items.get(7).setSaved(true);
        items.get(16).setSaved(true);

    }

    @Override
    public List<Item> getItemsRandom(){

        List<Item> shuffledItems = new ArrayList<>(items);
        Collections.shuffle(shuffledItems);
        return shuffledItems;

    }

    @Override
    public List<Item> getItemsByCategory(String category, int id) {
        List <Item> categorisedItems = new ArrayList<>();

        for (Item item : items){
            if (item.getCategory().equalsIgnoreCase(category) && item.getItemId() != id){
                categorisedItems.add(item);
            }
        }

        return categorisedItems;
    }

    @Override
    public Item getItemByID(int itemID) {
        Item item = null;

        for (Item i : items) {
            if (i.getItemId() == itemID) {
                item = i;
                break;
            }
        }

        return item;
    }

    @Override
    public void saveItem(Item item) {
        for (Item i : items) {
            if (i.getItemId() == item.getItemId()) {
                i.setSaved(true);  // Mark item as saved
                break;
            }
        }
    }

    @Override
    public boolean isItemSaved(Item item) {
        for (Item i : items) {
            if (i.getItemId() == item.getItemId()) {
                return i.isSaved();  // Return the saved status of the item
            }
        }
        return false; // Default if not found (shouldn't happen)
    }

    @Override
    public void removeSaved(Item item){
        item.setSaved(false);
    }

    @Override
    public List<Item> getSaved() {
        List<Item> savedItems = new ArrayList<>();

        for (Item item : items) {
            if (item.isSaved()) {
                savedItems.add(item);
            }
        }

        return savedItems;
    }


    @Override
    public List<Item> getListedItems() {
        User currentUser = Services.getCurrentUser();
        List<Item> listedItems = new ArrayList<>();

        for (Item item : items) {
            if (item.getSeller().equals(currentUser)) {
                listedItems.add(item);
            }
        }

        return listedItems;
    }

    @Override
    public void updateItem(Item item) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getItemId() == item.getItemId()) {
                items.get(i).setName(item.getName());
                items.get(i).setDescription(item.getDescription());
                items.get(i).setCondition(item.getCondition());
                items.get(i).setPrice(item.getPrice());
                items.get(i).setPaymentModes(item.getPaymentModes()); // Added Mode of Payment
                break;
            }
        }
    }


    @Override
    public void deleteItem(Item item) {
        items.remove(item);
    }

    @Override
    public void addItem(Item item) {
        items.add(item);
    }




}
