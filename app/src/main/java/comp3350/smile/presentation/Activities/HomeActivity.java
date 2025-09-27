package comp3350.smile.presentation.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import comp3350.smile.R;
import comp3350.smile.presentation.CategoryPopupMenuBuilder;
import comp3350.smile.presentation.Fragments.ForYouFragment;
import comp3350.smile.presentation.Fragments.SavedFragment;
import comp3350.smile.presentation.Fragments.SearchableFragment;
import comp3350.smile.presentation.MyListingsFragment;
import comp3350.smile.presentation.SellItemActivity;
import comp3350.smile.presentation.Fragments.ForYouFragment;
import comp3350.smile.presentation.Fragments.SavedFragment;
import comp3350.smile.presentation.Fragments.SearchableFragment;

public class HomeActivity extends NavigationActivity {

    // Search UI elements
    private LinearLayout searchBar;
    private EditText searchInput;
    private ImageButton btnSearch, clearSearch;
    private Button buttonForYou, buttonSaved, buttonLists, categoryFilterButton;
    private TextView noResultsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Optionally reset the database if needed
        super.onCreate(savedInstanceState);

        // Inflate the HomeActivity layout (should be your merged layout)
        setActivityContent(R.layout.activity_home);

        // Highlight the current navigation item (for example, Marketplace)
        bottomNavigationView.setSelectedItemId(R.id.nav_marketplace);

        // --- Initialize Search UI ---
        searchBar = findViewById(R.id.searchBar);
        searchInput = findViewById(R.id.searchInput);
        btnSearch = findViewById(R.id.btnSearch);
        clearSearch = findViewById(R.id.clearSearch);

        // Toggle search bar visibility on search button click
        btnSearch.setOnClickListener(v -> {
            if (searchBar.getVisibility() == View.GONE) {
                searchBar.setVisibility(View.VISIBLE);
            } else {
                searchBar.setVisibility(View.GONE);
            }
        });

        // Clear search input, update current fragment, and hide search bar
        clearSearch.setOnClickListener(v -> {
            searchInput.setText("");
            updateCurrentFragmentSearch("");
            searchBar.setVisibility(View.GONE);
        });

        // Listen for text changes in search input to update the current fragment in real time
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No operation
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateCurrentFragmentSearch(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
                // No operation
            }
        });

        // --- Initialize Fragment Switching Buttons ---
        buttonForYou = findViewById(R.id.buttonForYou);
        buttonSaved = findViewById(R.id.buttonSaved);
        buttonLists = findViewById(R.id.buttonLists);
        categoryFilterButton = findViewById(R.id.categoryFilterButton);

        // Load the ForYouFragment by default, passing the current search text (could be empty)
        loadFragment(ForYouFragment.newInstance(searchInput.getText().toString()));

        // Set click listener for "For You" button
        buttonForYou.setOnClickListener(v -> {
            loadFragment(ForYouFragment.newInstance(searchInput.getText().toString()));
            buttonForYou.setBackgroundTintList(getResources().getColorStateList(android.R.color.darker_gray));
            buttonSaved.setBackgroundTintList(getResources().getColorStateList(android.R.color.white));
            buttonLists.setBackgroundTintList(getResources().getColorStateList(android.R.color.white));
        });

        // Set click listener for "Saved" button
        buttonSaved.setOnClickListener(v -> {
            loadFragment(SavedFragment.newInstance(searchInput.getText().toString()));
            buttonSaved.setBackgroundTintList(getResources().getColorStateList(android.R.color.darker_gray));
            buttonForYou.setBackgroundTintList(getResources().getColorStateList(android.R.color.white));
            buttonLists.setBackgroundTintList(getResources().getColorStateList(android.R.color.white));
        });

        // Set click listener for "My Listings" button
        buttonLists.setOnClickListener(v -> {
            loadFragment(MyListingsFragment.newInstance(searchInput.getText().toString()));
            buttonLists.setBackgroundTintList(getResources().getColorStateList(android.R.color.darker_gray));
            buttonForYou.setBackgroundTintList(getResources().getColorStateList(android.R.color.white));
            buttonSaved.setBackgroundTintList(getResources().getColorStateList(android.R.color.white));

        });

        // Set click listener for category filter button to show a popup menu
        categoryFilterButton.setOnClickListener(v -> showCategoryPopupMenu(v));

        // Optional: Initialize noResultsText if it's needed by the activity (could be used by fragments instead)
        noResultsText = findViewById(R.id.noResultsText);

        // Handle the back button so that if HomeActivity is root, it moves to the background
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isTaskRoot()) {
                    moveTaskToBack(true);
                } else {
                    finish();
                }
            }
        });


        ImageButton btnPlus = findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SellItemActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Displays the category selection popup menu anchored to the given view.
     *
     * @param view the view to anchor the popup menu to
     */
    private void showCategoryPopupMenu(View view) {
        new CategoryPopupMenuBuilder(this, view)
                .setMenuResource(R.menu.category_menu)
                .setCategorySelectedListener(this::handleCategorySelection)
                .show();
    }

    /**
     * Handles the selection of a category from the popup menu.
     *
     * @param item the selected menu item
     */
    private void handleCategorySelection(MenuItem item) {
        final String category = item.getTitle().toString();
        final String searchQuery = isAllCategory(item) ? "" : category;
        updateCurrentFragmentSearch(searchQuery);
    }

    /**
     * Checks whether the selected menu item corresponds to the "All Categories" option.
     *
     * @param item the menu item to check
     * @return true if the item is the "All Categories" option, false otherwise
     */
    private boolean isAllCategory(MenuItem item) {
        return item.getItemId() == R.id.menu_item_all; // Use ID comparison instead of string
    }

    /**
     * Replaces the current fragment with the given fragment in the fragment container.
     *
     * @param fragment the fragment to display
     */
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    /**
     * Updates the currently displayed fragment's search query, if it supports search.
     *
     * @param query the search query to apply
     */
    private void updateCurrentFragmentSearch(String query) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (currentFragment instanceof SearchableFragment) {
            ((SearchableFragment) currentFragment).applySearchQuery(query);
        }
    }

    @Override
    protected int getNavBarItemId() {
        return R.id.nav_marketplace;
    }
}
