package comp3350.smile.presentation.Activities;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;

import comp3350.smile.R;


public class FaqActivity extends NavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContent(R.layout.activity_faq);
        bottomNavigationView.setSelectedItemId(R.id.nav_chat);

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
    }

    @Override
    protected int getNavBarItemId() {
        return R.id.nav_chat;
    }
}
