package comp3350.smile.presentation.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.smile.R;
import comp3350.smile.logic.UserProfileService;
import comp3350.smile.objects.User;

import comp3350.smile.presentation.PresentationConfig;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText firstNameText, lastNameText, phoneNumberText, emailEditText, passwordEditText;
    private UserProfileService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        initializeViews();
        userService = new UserProfileService();

        Button signupButton = findViewById(R.id.signupButton);
        signupButton.setOnClickListener(v -> attemptSignup());
    }

    /**
     * Initializes all view references used in the sign-up form.
     */
    private void initializeViews() {
        firstNameText = findViewById(R.id.firstName);
        lastNameText = findViewById(R.id.lastName);
        phoneNumberText = findViewById(R.id.phoneNumber);
        emailEditText = findViewById(R.id.signUpEmail);
        passwordEditText = findViewById(R.id.signUpPassword);
    }

    /**
     * Attempts to sign up a new user using input from the form fields.
     * Catches and displays any IllegalArgumentException thrown due to invalid fields.
     */
    private void attemptSignup() {
        String firstName = firstNameText.getText().toString().trim();
        String lastName = lastNameText.getText().toString().trim();
        String phoneNumber = phoneNumberText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        try{
            User newUser = new User(firstName, lastName, email, phoneNumber, password);
            userService.signup(newUser);
            handleSuccessfulSignup();

            } catch (IllegalArgumentException e) {
                Toast.makeText(CreateAccountActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
    }

    /**
     * Handles the post-signup success actions including showing a toast and navigating to the home screen.
     */
    private void handleSuccessfulSignup() {
        Toast.makeText(CreateAccountActivity.this, PresentationConfig.SIGNUP_SUCCESS_MSG , Toast.LENGTH_SHORT).show();
        navigateToHome();
    }

    /**
     * Navigates the user to the HomeActivity and clears the back stack.
     */
    private void navigateToHome() {
        Intent homePageIntent = new Intent(CreateAccountActivity.this, HomeActivity.class);
        homePageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homePageIntent);
    }
}