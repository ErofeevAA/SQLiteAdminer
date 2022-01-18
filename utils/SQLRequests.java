package utils;

;

import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;

public class SQLRequests {

    private static final String BEGIN_URL = "jdbc:sqlite:";

    private static SQLRequests instance = null;

    private Connection connection = null;

    private SQLRequests() {
        try {
            DriverManager.registerDriver(new JDBC());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static SQLRequests getInstance() {
        if (instance == null) {
            instance = new SQLRequests();
        }
        return instance;
    }

    public boolean isConnect() {
        return connection != null;
    }

    public void setDB(String fullName) {
        try {
            connection = DriverManager.getConnection(BEGIN_URL + fullName);
            if (connection != null) {
                //
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String request(String r) {
        if (r.charAt(r.length() - 1) != ';') {
            r += ";";
        }
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(r);
            ResultSetMetaData data = resultSet.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<String> getTables() {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet set = metaData.getTables(null, null, null, null);

            ArrayList<String> tables = new ArrayList<>();
            while (set.next()) {
                tables.add(set.getString("TABLE_NAME"));
            }
            set.close();
            return tables;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void disconnect() {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
