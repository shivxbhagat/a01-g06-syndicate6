package comp3350.smile.persistence;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import comp3350.smile.persistence.DatabaseConfig;
import comp3350.smile.application.StringConfig;

public class DBConnection {
    private final String databasePath;
    private final Context appContext;
    private Connection connection = null;

    public DBConnection(Context context) {
        if (context == null) {
            throw new IllegalArgumentException(StringConfig.ERROR_DB_INIT);
        }
        this.appContext = context.getApplicationContext();

        File databaseDir = context.getDir("db", Context.MODE_PRIVATE);
        this.databasePath = new File(databaseDir, DatabaseConfig.DB_NAME).getAbsolutePath();
        Log.d(StringConfig.LOG_TAG_DB, StringConfig.DB_INIT_PATH_LOG + databasePath);
    }

    public Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("org.hsqldb.jdbc.JDBCDriver");
                connection = DriverManager.getConnection(
                        DatabaseConfig.DB_URL_PREFIX + databasePath,
                        DatabaseConfig.USER,
                        DatabaseConfig.PASSWORD
                );
                Log.d(StringConfig.LOG_TAG_DB, StringConfig.DB_CONNECTED_LOG);

                if (!isDatabasePopulated()) {
                    executeSQLFile(DatabaseConfig.SQL_FILE_NAME);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new SQLException(StringConfig.ERROR_DB_DRIVER, e);
        }
        return connection;
    }

    private boolean isDatabasePopulated() throws SQLException {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(StringConfig.SQL_CHECK_TABLES_EXIST)) {
            return rs.next() && rs.getInt(1) == 3;
        }
    }

    private void executeSQLFile(String fileName) {
        String fullPath = DatabaseConfig.ASSETS_DB_PATH + fileName;

        try (InputStream inputStream = appContext.getAssets().open(fullPath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             Statement stmt = connection.createStatement()) {

            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
                if (line.trim().endsWith(";")) {
                    stmt.execute(sql.toString());
                    sql.setLength(0);
                }
            }
            Log.d(StringConfig.LOG_TAG_DB, StringConfig.DB_SCRIPT_EXECUTED_LOG);
        } catch (IOException | SQLException e) {
            Log.e(StringConfig.LOG_TAG_DB, StringConfig.ERROR_DB_SCRIPT_EXECUTION, e);
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                Log.d(StringConfig.LOG_TAG_DB, StringConfig.DB_CONNECTION_CLOSED_LOG);
            } catch (SQLException e) {
                Log.e(StringConfig.LOG_TAG_DB, StringConfig.ERROR_DB_CLOSE_CONNECTION, e);
            }
        }
    }
}