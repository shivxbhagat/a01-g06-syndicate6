package comp3350.smile.presentation.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import comp3350.smile.R;

public abstract class NavigationActivity extends AppCompatActivity {
    protected BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);
        setupBottomNavigation();
    }

    /**
     * Sets up the bottom navigation view and handles navigation item selection.
     */
    private void setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Class<?> targetActivity = getTargetActivity(item.getItemId());
            if (targetActivity != null && !this.getClass().equals(targetActivity)) {
                navigateToActivity(targetActivity);
                return true;
            }
            return false;
        });
    }

    /**
     * Returns the activity class associated with a given navigation item ID.
     *
     * @param itemId the ID of the selected navigation item
     * @return the corresponding activity class, or null if no match
     */
    private Class<?> getTargetActivity(int itemId) {
        if (itemId == R.id.nav_marketplace) return HomeActivity.class;
        if (itemId == R.id.nav_user) return ProfileActivity.class;
        if (itemId == R.id.nav_chat) return FaqActivity.class;
        return null;
    }

    /**
     * Starts the given activity class with flags to prevent duplication and smooth transition.
     *
     * @param activityClass the activity class to navigate to
     */
    private void navigateToActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateNavBarSelection();
    }

    /**
     * Updates the bottom navigation bar to highlight the current activity's nav item.
     */
    protected void updateNavBarSelection() {
        int navItemId = getNavBarItemId();
        bottomNavigationView.setSelectedItemId(navItemId);
        bottomNavigationView.getMenu().findItem(navItemId).setChecked(true);
    }

    /**
     * Each subclass must return the corresponding navigation item ID for that activity.
     *
     * @return the navigation item ID associated with this activity
     */
    protected abstract int getNavBarItemId();

    /**
     * Sets the content layout inside the shared FrameLayout container.
     *
     * @param layoutResID the layout resource ID to inflate and display
     */
    protected void setActivityContent(@LayoutRes int layoutResID) {
        FrameLayout contentFrame = findViewById(R.id.contentFrame);
        contentFrame.removeAllViews(); // Clear previous content
        LayoutInflater.from(this).inflate(layoutResID, contentFrame, true);
    }
}