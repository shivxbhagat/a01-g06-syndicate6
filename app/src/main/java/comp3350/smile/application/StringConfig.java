package comp3350.smile.application;

public class StringConfig {

    public static final String LOG_TAG_DB = "DBConnection";
    public static final String LOG_TAG_USER = "UserDb";
    public static final String LOG_TAG_ITEM = "ItemDb";

    // Database
    public static final String DB_INIT_PATH_LOG = "Database path initialized: ";
    public static final String DB_CONNECTED_LOG = "Connected to HSQLDB";
    public static final String DB_SCRIPT_EXECUTED_LOG = "SQL script executed successfully";
    public static final String DB_CONNECTION_CLOSED_LOG = "Connection closed";
    public static final String ERROR_DB_DRIVER = "HSQLDB driver not found";
    public static final String ERROR_DB_INIT = "Context cannot be null";
    public static final String SQL_CHECK_TABLES_EXIST = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'";
    public static final String ERROR_DB_SCRIPT_EXECUTION = "Error executing SQL file";
    public static final String ERROR_DB_CLOSE_CONNECTION = "Error closing connection";

    // UserDb
    public static final String SQL_USERS_SELECT_EMAIL = "SELECT * FROM USERS WHERE EMAIL = ?";
    public static final String SQL_USERS_INSERT = "INSERT INTO USERS (FIRST_NAME, LAST_NAME, EMAIL, PHONE, PASSWORD) VALUES (?, ?, ?, ?, ?)";
    public static final String SQL_USERS_UPDATE = "UPDATE USERS SET FIRST_NAME = ?, LAST_NAME = ?, PHONE = ?, PASSWORD = ? WHERE EMAIL = ?";
    public static final String SQL_USERS_DELETE = "DELETE FROM USERS WHERE EMAIL = ?";
    public static final String LOG_USER_PROFILE_ERROR = "Error getting user profile by email";
    public static final String LOG_USER_INSERT_SUCCESS = "User profile inserted successfully.";
    public static final String LOG_USER_INSERT_ERROR = "Error inserting user profile";
    public static final String LOG_USER_UPDATE_SUCCESS = "User profile updated successfully.";

    public static final String LOG_USER_UPDATE_ERROR = "Error updating user profile";
    public static final String LOG_USER_DELETE_SUCCESS = "User profile deleted successfully.";
    public static final String LOG_USER_DELETE_ERROR = "Error deleting user profile";


    // ItemDb
    public static final String SQL_USERS_SELECT_ID = "SELECT * FROM USERS WHERE USER_ID = ?";
    public static final String SQL_ITEMS_SELECT_ALL = "SELECT * FROM ITEMS";
    public static final String SQL_ITEMS_SELECT_CATEGORY = "SELECT * FROM ITEMS WHERE CATEGORY = ?";
    public static final String SQL_ITEMS_SELECT_ID = "SELECT * FROM ITEMS WHERE ITEM_ID = ?";
    public static final String SQL_ITEMS_UPDATE = "UPDATE ITEMS SET NAME = ?, DESCRIPTION = ?, CONDITION = ?, PRICE = ?, PAYMENT_MODES = ? WHERE ITEM_ID = ?";
    public static final String SQL_ITEMS_DELETE = "DELETE FROM ITEMS WHERE ITEM_ID = ?";
    public static final String SQL_ITEMS_INSERT = "INSERT INTO ITEMS (NAME, DESCRIPTION, CATEGORY, CONDITION, PRICE, SELLER_ID, IMG_PATH, PAYMENT_MODES) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_SAVED_ITEMS_INSERT = "INSERT INTO SAVED_ITEMS (EMAIL_ID, ITEM_ID) VALUES (?, ?)";
    public static final String SQL_SAVED_ITEMS_CHECK = "SELECT COUNT(*) AS count FROM SAVED_ITEMS WHERE EMAIL_ID = ? AND ITEM_ID = ?";
    public static final String SQL_SAVED_ITEMS_DELETE = "DELETE FROM SAVED_ITEMS WHERE EMAIL_ID = ? AND ITEM_ID = ?";
    public static final String SQL_SAVED_ITEMS_SELECT = "SELECT ITEM_ID FROM SAVED_ITEMS WHERE EMAIL_ID = ?";
    public static final String SQL_ITEMS_SELECT_SELLER = "SELECT ITEM_ID FROM ITEMS WHERE SELLER_ID = ?";

    public static final String LOG_ITEMS_FETCH_ERROR = "Error fetching random items";
    public static final String LOG_ITEMS_CATEGORY_ERROR = "Error fetching items by category";
    public static final String LOG_ITEM_FETCH_ERROR = "Error fetching item by ID";
    public static final String LOG_USER_FETCH_ERROR = "Error fetching user by ID";
    public static final String LOG_ITEM_SAVE_ERROR = "Error saving item";
    public static final String LOG_ITEM_CHECK_ERROR = "Error checking saved item";
    public static final String LOG_ITEM_REMOVE_ERROR = "Error removing saved item";
    public static final String LOG_SAVED_ITEMS_FETCH_ERROR = "Error fetching saved items";
    public static final String LOG_LISTED_ITEMS_ERROR = "Error fetching listed items";
    public static final String LOG_ITEM_UPDATE_ERROR = "Error updating item";
    public static final String LOG_ITEM_DELETE_ERROR = "Error deleting item";
    public static final String LOG_ITEM_ADD_ERROR = "Error adding new item";

    public static final String ERROR_NO_CURRENT_USER = "No current user";
    public static final String ERROR_ITEM_ADD_FAILED = "Adding item failed, no rows affected";

    // Services
    public static final String ERROR_SERVICES_NOT_INITIALIZED = "Services not initialized";
    public static final String ERROR_DB_NOT_INITIALIZED = "Database connection not initialized";
    public static final String ERROR_CONTEXT_REQUIRED = "Context is required for real database initialization";
}