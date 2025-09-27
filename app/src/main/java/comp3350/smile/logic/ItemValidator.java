package comp3350.smile.logic;

import comp3350.smile.objects.Item;
import comp3350.smile.logic.Interfaces.IValidator;
import comp3350.smile.persistence.ItemPersistence;
import comp3350.smile.logic.LogicConfig;

public class ItemValidator implements IValidator<Item> {

    /**
     * Validates the given item by checking all of its required fields.
     *
     * @param item the item to validate
     * @throws IllegalArgumentException if the item or any of its fields are invalid
     */
    public void validate(Item item) {
        if (item == null) {
            throw new IllegalArgumentException(LogicConfig.ITEM_NULL_EXC);
        }
        validateName(item.getName());
        validateCategory(item.getCategory());
        validatePrice(item.getPrice());
        validateCondition(item.getCondition());
        validatePaymentModes(item.getPaymentModes());
        validateID(item.getItemId());
    }

    /**
     * Validates that the item name is not null or empty.
     *
     * @param name the name of the item to validate
     * @throws IllegalArgumentException if the name is null or empty
     */
    private void validateName(String name) {
        if (emptyFields(name)) {
            throw new IllegalArgumentException("Item name cannot be empty.");
        }
    }

    /**
     * Validates that the item ID is not less than -1.
     *
     * @param id the item ID to validate
     * @throws IllegalArgumentException if the ID is less than -1
     */
    private void validateID(int id) {
        if (id < -1) {
            throw new IllegalArgumentException(LogicConfig.ITEM_ID_INVALID_EXC);
        }
    }

    /**
     * Validates that the item category is not null or empty.
     *
     * @param category the category to validate
     * @throws IllegalArgumentException if the category is null or empty
     */
    private void validateCategory(String category) {
        if (emptyFields(category)) {
            throw new IllegalArgumentException(LogicConfig.ITEM_CATEGORY_EMPTY_EXC);
        }
    }


    /**
     * Validates that the item price is not negative.
     *
     * @param price the price to validate
     * @throws IllegalArgumentException if the price is negative
     */
    private void validatePrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException(LogicConfig.ITEM_PRICE_NEGATIVE_EXC);
        }
    }


    /**
     * Validates that the item condition is not null or empty.
     *
     * @param condition the condition to validate
     * @throws IllegalArgumentException if the condition is null or empty
     */
    private void validateCondition(String condition) {
        if (emptyFields(condition)) {
            throw new IllegalArgumentException(LogicConfig.ITEM_CONDITION_EMPTY_EXC);
        }
    }


    /**
     * Validates that the payment modes string is not null or empty.
     *
     * @param paymentModes the payment modes string to validate
     * @throws IllegalArgumentException if the payment modes string is null or empty
     */
    private void validatePaymentModes(String paymentModes) {
        if (emptyFields(paymentModes)) {
            throw new IllegalArgumentException(LogicConfig.ITEM_PAYMENT_MODES_EMPTY_EXC);
        }
    }



    /**
     * Checks if any of the provided string fields are null or empty.
     *
     * @param fields one or more string fields to validate
     * @return true if any field is null or empty, false otherwise
     */
    public boolean emptyFields(String... fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
