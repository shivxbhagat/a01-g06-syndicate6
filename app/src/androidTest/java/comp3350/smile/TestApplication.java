package comp3350.smile;

import android.app.Application;
import android.util.Log;

import java.sql.SQLException;

import comp3350.smile.application.Services;
import comp3350.smile.persistence.DBConnection;

public class TestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TestApplication", "TestApplication onCreate called");

        try {
            DBConnection db = new DBConnection(this);
            Services.init(this, false);
            db.getConnection();  // this sets up the DB
            Log.d("TestApplication", "DB initialized successfully");
        } catch (SQLException e) {
            Log.e("TestApplication", "DB init failed", e);
            throw new RuntimeException(e);
        }
    }
}
