package comp3350.smile.persistence.hsqldb;


import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comp3350.smile.application.Services;
import comp3350.smile.application.StringConfig;
import comp3350.smile.objects.Item;
import comp3350.smile.objects.User;
import comp3350.smile.persistence.ItemPersistence;

public class ItemDb implements ItemPersistence {

    @Override
    public List<Item> getItemsRandom() {
        List<Item> items = new ArrayList<>();
        String query = StringConfig.SQL_ITEMS_SELECT_ALL;

        try (Connection connection = Services.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(query)) {

            while (resultSet.next()) {
                items.add(extractItemFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            Log.e(StringConfig.LOG_TAG_ITEM, StringConfig.LOG_ITEMS_FETCH_ERROR, e);
        }
        return items;
    }

    @Override
    public List<Item> getItemsByCategory(String category, int id) {
        List<Item> items = new ArrayList<>();
        String query = StringConfig.SQL_ITEMS_SELECT_CATEGORY;

        try (Connection connection = Services.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, category);

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Item item = extractItemFromResultSet(resultSet);
                if (item.getItemId() != id) {
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            Log.e(StringConfig.LOG_TAG_ITEM, StringConfig.LOG_ITEMS_CATEGORY_ERROR, e);
        }
        return items;
    }

    @Override
    public Item getItemByID(int itemID) {
        Item item = null;
        String query = StringConfig.SQL_ITEMS_SELECT_ID;

        try (Connection connection = Services.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, itemID);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                item = extractItemFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            Log.e(StringConfig.LOG_TAG_ITEM, StringConfig.LOG_ITEM_FETCH_ERROR, e);
        }
        return item;
    }

    private User getUserByID(int userID) {
        User user = null;
        String query = StringConfig.SQL_USERS_SELECT_ID;

        try (Connection connection = Services.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userID);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                user = new User(
                        resultSet.getString("FIRST_NAME"),
                        resultSet.getString("LAST_NAME"),
                        resultSet.getString("EMAIL"),
                        resultSet.getString("PHONE"),
                        resultSet.getString("PASSWORD")
                );
            }
        } catch (SQLException e) {
            Log.e(StringConfig.LOG_TAG_ITEM, StringConfig.LOG_USER_FETCH_ERROR, e);
        }
        return user;
    }

    private Item extractItemFromResultSet(ResultSet resultSet) throws SQLException {
        return new Item(
                resultSet.getInt("ITEM_ID"),
                resultSet.getString("NAME"),
                resultSet.getString("DESCRIPTION"),
                resultSet.getString("CATEGORY"),
                resultSet.getString("CONDITION"),
                resultSet.getDouble("PRICE"),
                getUserByID(resultSet.getInt("SELLER_ID")),
                resultSet.getString("IMG_PATH"),
                resultSet.getString("PAYMENT_MODES")
        );
    }

    @Override
    public void saveItem(Item item) {
        User currentUser = Services.getCurrentUser();
        if (currentUser == null) {
            Log.e(StringConfig.LOG_TAG_ITEM, StringConfig.ERROR_NO_CURRENT_USER);
            return;
        }

        if (!isItemSaved(item)) {
            try (Connection connection = Services.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(StringConfig.SQL_SAVED_ITEMS_INSERT)) {
                stmt.setString(1, currentUser.getEmail());
                stmt.setInt(2, item.getItemId());
                stmt.executeUpdate();
            } catch (SQLException e) {
                Log.e(StringConfig.LOG_TAG_ITEM, StringConfig.LOG_ITEM_SAVE_ERROR, e);
            }
        }
    }

    @Override
    public boolean isItemSaved(Item item) {
        User currentUser = Services.getCurrentUser();
        if (currentUser == null) return false;

        try (Connection connection = Services.getConnection();
             PreparedStatement stmt = connection.prepareStatement(StringConfig.SQL_SAVED_ITEMS_CHECK)) {
            stmt.setString(1, currentUser.getEmail());
            stmt.setInt(2, item.getItemId());
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt("count") > 0;
        } catch (SQLException e) {
            Log.e(StringConfig.LOG_TAG_ITEM, StringConfig.LOG_ITEM_CHECK_ERROR, e);
            return false;
        }
    }

    @Override
    public void removeSaved(Item item) {
        User currentUser = Services.getCurrentUser();
        if (currentUser == null) {
            Log.e(StringConfig.LOG_TAG_ITEM, StringConfig.ERROR_NO_CURRENT_USER);
            return;
        }

        try (Connection connection = Services.getConnection();
             PreparedStatement stmt = connection.prepareStatement(StringConfig.SQL_SAVED_ITEMS_DELETE)) {
            stmt.setString(1, currentUser.getEmail());
            stmt.setInt(2, item.getItemId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            Log.e(StringConfig.LOG_TAG_ITEM, StringConfig.LOG_ITEM_REMOVE_ERROR, e);
        }
    }

    @Override
    public List<Item> getSaved() {
        List<Item> savedItems = new ArrayList<>();
        User currentUser = Services.getCurrentUser();
        if (currentUser == null) return savedItems;

        try (Connection connection = Services.getConnection();
             PreparedStatement stmt = connection.prepareStatement(StringConfig.SQL_SAVED_ITEMS_SELECT)) {
            stmt.setString(1, currentUser.getEmail());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Item item = getItemByID(rs.getInt("ITEM_ID"));
                if (item != null) savedItems.add(item);
            }
        } catch (SQLException e) {
            Log.e(StringConfig.LOG_TAG_ITEM, StringConfig.LOG_SAVED_ITEMS_FETCH_ERROR, e);
        }
        return savedItems;
    }

    @Override
    public List<Item> getListedItems() {
        List<Item> listedItems = new ArrayList<>();
        User currentUser = Services.getCurrentUser();
        if (currentUser == null) return listedItems;

        try (Connection connection = Services.getConnection();
             PreparedStatement stmt = connection.prepareStatement(StringConfig.SQL_ITEMS_SELECT_SELLER)) {
            stmt.setInt(1, currentUser.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Item item = getItemByID(rs.getInt("ITEM_ID"));
                if (item != null) listedItems.add(item);
            }
        } catch (SQLException e) {
            Log.e(StringConfig.LOG_TAG_ITEM, StringConfig.LOG_LISTED_ITEMS_ERROR, e);
        }
        return listedItems;
    }

    @Override
    public void updateItem(Item item) {
        try (Connection connection = Services.getConnection();
             PreparedStatement stmt = connection.prepareStatement(StringConfig.SQL_ITEMS_UPDATE)) {
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setString(3, item.getCondition());
            stmt.setDouble(4, item.getPrice());
            stmt.setString(5, item.getPaymentModes());
            stmt.setInt(6, item.getItemId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            Log.e(StringConfig.LOG_TAG_ITEM, StringConfig.LOG_ITEM_UPDATE_ERROR, e);
        }
    }

    @Override
    public void deleteItem(Item item) {
        try (Connection connection = Services.getConnection();
             PreparedStatement stmt = connection.prepareStatement(StringConfig.SQL_ITEMS_DELETE)) {
            stmt.setInt(1, item.getItemId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            Log.e(StringConfig.LOG_TAG_ITEM, StringConfig.LOG_ITEM_DELETE_ERROR, e);
        }
    }

    @Override
    public void addItem(Item item) {
        User seller = Services.getCurrentUser();
        if (seller == null) {
            Log.e(StringConfig.LOG_TAG_ITEM, StringConfig.ERROR_NO_CURRENT_USER);
            return;
        }

        try (Connection connection = Services.getConnection();
             PreparedStatement stmt = connection.prepareStatement(StringConfig.SQL_ITEMS_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setString(3, item.getCategory());
            stmt.setString(4, item.getCondition());
            stmt.setDouble(5, item.getPrice());
            stmt.setInt(6, seller.getId());
            stmt.setString(7, item.getImgPath());
            stmt.setString(8, item.getPaymentModes());

            if (stmt.executeUpdate() == 0) {
                throw new SQLException(StringConfig.ERROR_ITEM_ADD_FAILED);
            }
        } catch (SQLException e) {
            Log.e(StringConfig.LOG_TAG_ITEM, StringConfig.LOG_ITEM_ADD_ERROR, e);
        }
    }
}