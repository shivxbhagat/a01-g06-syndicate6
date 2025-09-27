package comp3350.smile.logic.Interfaces;

import comp3350.smile.objects.User;

public interface IUserService {
    boolean validateLoginInput(String email, String password); //validatates the provided login credentials
    boolean validateProfileInput(String firstName, String lastName, String phoneNumber, String email, String password); //validates the provided edit credentials
    boolean updateUser(User user); //update the user
    void signup(User newUser); //create a new user
    void login(String email, String password); //login to the app
    void delete(User user); // delete the user
    boolean isValidUniversityEmail(String email); //validates the email
}
