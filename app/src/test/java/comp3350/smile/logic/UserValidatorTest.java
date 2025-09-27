
package comp3350.smile.logic;

import static org.junit.Assert.*;
import org.junit.Test;

import comp3350.smile.objects.User;

public class UserValidatorTest {
    private final UserValidator validator = new UserValidator();

    // Test emptyFields() method
    @Test
    public void testEmptyFields_SingleEmpty() {
        assertTrue(validator.emptyFields(""));
    }

    @Test
    public void testEmptyFields_SingleNull() {
        assertTrue(validator.emptyFields((String)null));
    }

    @Test
    public void testEmptyFields_MultipleWithOneEmpty() {
        assertTrue(validator.emptyFields("Valid", "", "AlsoValid"));
    }

    @Test
    public void testEmptyFields_AllValid() {
        assertFalse(validator.emptyFields("Valid", "AlsoValid"));
    }

    // Test nullUser() method
    @Test(expected = IllegalArgumentException.class)
    public void testNullUser_NullInput() {
        validator.nullUser(null);
    }

    @Test
    public void testNullUser_ValidUser() {
        User user = new User("John", "Doe", "john@myumanitoba.ca", "2041234567", "password");
        validator.nullUser(user); // Should not throw
    }

    // Test validateEmail() method
    @Test(expected = IllegalArgumentException.class)
    public void testValidateEmail_Empty() {
        validator.validateEmail("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateEmail_Null() {
        validator.validateEmail(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateEmail_NoAtSymbol() {
        validator.validateEmail("invalid.email");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateEmail_AtStart() {
        validator.validateEmail("@domain.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateEmail_AtEnd() {
        validator.validateEmail("user@");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateEmail_WrongDomain() {
        validator.validateEmail("user@gmail.com");
    }

    @Test
    public void testValidateEmail_ValidMyumanitoba() {
        validator.validateEmail("user@myumanitoba.ca");
    }

    @Test
    public void testValidateEmail_ValidUmanitoba() {
        validator.validateEmail("user@umanitoba.ca");
    }

    // Test validateFirstName()
    @Test(expected = IllegalArgumentException.class)
    public void testValidateFirstName_Empty() {
        validator.validateFirstName("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateFirstName_Null() {
        validator.validateFirstName(null);
    }

    @Test
    public void testValidateFirstName_Valid() {
        validator.validateFirstName("John");
    }

    // Test validateLastName()
    @Test(expected = IllegalArgumentException.class)
    public void testValidateLastName_Empty() {
        validator.validateLastName("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateLastName_Null() {
        validator.validateLastName(null);
    }

    @Test
    public void testValidateLastName_Valid() {
        validator.validateLastName("Doe");
    }

    // Test validatePhone()
    @Test(expected = IllegalArgumentException.class)
    public void testValidatePhone_Empty() {
        validator.validatePhone("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidatePhone_Null() {
        validator.validatePhone(null);
    }

    @Test
    public void testValidatePhone_Valid() {
        validator.validatePhone("2041234567");
    }

    // Test validatePassword()
    @Test(expected = IllegalArgumentException.class)
    public void testValidatePassword_Empty() {
        validator.validatePassword("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidatePassword_Null() {
        validator.validatePassword(null);
    }

    @Test
    public void testValidatePassword_Valid() {
        validator.validatePassword("securePass123");
    }

    // Test validateID()
    @Test
    public void testValidateID_Zero() {
        validator.validateID(0); // Should pass
    }

    @Test
    public void testValidateID_Positive() {
        validator.validateID(1); // Should pass
    }

    // Test complete validate() method
    @Test
    public void testValidate_CompleteValidUser() {
        User user = new User("John", "Doe", "john@myumanitoba.ca", "2041234567", "password");
        validator.validate(user); // Should not throw
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidate_NullUser() {
        validator.validate(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidate_InvalidEmail() {
        User user = new User("John", "Doe", "invalid.email", "2041234567", "password");
        validator.validate(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidate_EmptyFirstName() {
        User user = new User("", "Doe", "john@myumanitoba.ca", "2041234567", "password");
        validator.validate(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidate_EmptyLastName() {
        User user = new User("John", "", "john@myumanitoba.ca", "2041234567", "password");
        validator.validate(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidate_EmptyPhone() {
        User user = new User("John", "Doe", "john@myumanitoba.ca", "", "password");
        validator.validate(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidate_EmptyPassword() {
        User user = new User("John", "Doe", "john@myumanitoba.ca", "2041234567", "");
        validator.validate(user);
    }

}