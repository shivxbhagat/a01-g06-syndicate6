package comp3350.smile.logic;

import static org.junit.Assert.*;
import org.junit.Test;

import comp3350.smile.objects.Item;
import comp3350.smile.objects.User;

public class ItemValidatorTest {
    private final ItemValidator validator = new ItemValidator();
    private final User testUser = new User("Test", "User", "test@myumanitoba.ca", "2042022221", "password");

    @Test
    public void testEmptyFields_SingleFieldEmpty() {
        assertTrue(validator.emptyFields(""));
    }

    @Test
    public void testEmptyFields_SingleFieldNull() {
        assertTrue(validator.emptyFields((String)null));
    }

    @Test
    public void testEmptyFields_MultipleFieldsOneEmpty() {
        assertTrue(validator.emptyFields("Valid", "", "AlsoValid"));
    }

    @Test
    public void testEmptyFields_AllFieldsValid() {
        assertFalse(validator.emptyFields("Valid", "AlsoValid", "AnotherOne"));
    }

    @Test
    public void testValidatePrice_Zero() {
        Item item = new Item(1, "Item", "Desc", "Category", "New", 0.0, testUser, "/img", "Cash");
        validator.validate(item); // Should pass since 0 is valid
    }

    @Test
    public void testValidateID_Zero() {
        Item item = new Item(0, "Item", "Desc", "Category", "New", 10.0, testUser, "/img", "Cash");
        validator.validate(item); // Should pass since 0 is valid
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidate_NegativePrice() {
        Item item = new Item(1, "Item", "Desc", "Category", "New", -1.0, testUser, "/img", "Cash");
        validator.validate(item);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidate_NegativeID() {
        Item item = new Item(-2, "Item", "Desc", "Category", "New", 10.0, testUser, "/img", "Cash");
        validator.validate(item);
    }
}