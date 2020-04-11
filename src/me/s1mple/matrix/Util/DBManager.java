package me.s1mple.matrix.Util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    Connection connection;
    private String url;
    private String username;
    private String pass;

    public DBManager(String url, String username, String pass) {
        this.url = url;
        this.pass = pass;
        this.username = username;

        try {
            connection = getDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the Database.
     *
     * @return Database
     * @throws SQLException SQL connection
     */
    public Connection getDatabase() throws SQLException {
        return (connection != null && !connection.isClosed()) ? connection : DriverManager.getConnection(url, username, pass);
    }

    /**
     * Check if database exists.
     *
     * @return Database exists
     */
    private boolean dbExists() {
        try {
            return getDatabase() != null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
