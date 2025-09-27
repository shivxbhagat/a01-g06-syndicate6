package comp3350.smile.presentation.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import comp3350.smile.application.Services;

import comp3350.smile.R;
import comp3350.smile.logic.UserProfileService;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private CheckBox rememberMeCheckBox;
    private UserProfileService userService;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Services.init(this, false);

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        TextView signupButton = findViewById(R.id.signUpMessage);
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox);
        Button loginButton = findViewById(R.id.loginButton);

        // Initialize UserService to handle login/signup logic
        userService = new UserProfileService();

        // Initialize SharedPreferences
        prefs = getSharedPreferences("LoginSaved", Context.MODE_PRIVATE);

        // Load saved credentials
        loadSavedCredentials();

        // Handle Sign Up Button Click
        signupButton.setOnClickListener(v -> navigateToSignUp());

        // Handle Login Button Click
        loginButton.setOnClickListener(v -> attemptLogin());


    }

    /**
     * Loads saved login credentials from shared preferences and populates the login form if available.
     */
    private void loadSavedCredentials() {
        String savedEmail = prefs.getString("savedEmail", "");
        String savedPassword = prefs.getString("savedPassword", "");
        if (!savedEmail.isEmpty() && !savedPassword.isEmpty()) {
            emailEditText.setText(savedEmail);
            passwordEditText.setText(savedPassword);
            rememberMeCheckBox.setChecked(true);
        }
    }

    /**
     * Navigates the user to the sign-up activity.
     */
    private void navigateToSignUp() {
        Intent signUpIntent = new Intent(LoginActivity.this, CreateAccountActivity.class);
        startActivity(signUpIntent);
    }

    /**
     * Attempts to log in using the email and password provided in the input fields.
     * Shows appropriate messages on success or failure.
     */
    private void attemptLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        try {
            userService.login(email, password);
            handleSuccessfulLogin(email, password);
        } catch (IllegalArgumentException e) {
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // Catch any other unexpected exception.
            Toast.makeText(LoginActivity.this, "An error occurred while logging in.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Saves or clears login credentials based on the Remember Me checkbox state.
     *
     * @param shouldSave true to save credentials, false to clear them
     * @param email the email to save
     * @param password the password to save
     */
    private void manageLoginCredentials(boolean shouldSave, String email, String password) {
        SharedPreferences.Editor editor = prefs.edit();
        if (shouldSave) {
            editor.putString("savedEmail", email);
            editor.putString("savedPassword", password);
        } else {
            editor.remove("savedEmail");
            editor.remove("savedPassword");
        }
        editor.apply();
    }

    /**
     * Handles successful login by saving credentials (if selected) and navigating to the home screen.
     *
     * @param email the logged-in user's email
     * @param password the logged-in user's password
     */
    private void handleSuccessfulLogin(String email, String password) {
        manageLoginCredentials(rememberMeCheckBox.isChecked(), email, password);
        Toast.makeText(LoginActivity.this, getString(R.string.login_successful), Toast.LENGTH_SHORT).show();
        navigateToHome();
    }

    /**
     * Navigates to the home screen and clears the activity stack.
     */
    private void navigateToHome() {
        Intent homePageIntent = new Intent(LoginActivity.this, HomeActivity.class);
        homePageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homePageIntent);
        finish();
    }
}