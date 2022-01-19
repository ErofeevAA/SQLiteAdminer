package utils;

import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

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

    public void setDB(String fullName) {
        try {
            connection = DriverManager.getConnection(BEGIN_URL + fullName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TableModel request(String r) throws SQLException {
        if (r.charAt(r.length() - 1) != ';') {
            r += ";";
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(r);
        ResultSetMetaData metaData = resultSet.getMetaData();

        Vector<String> header = new Vector<>();
        int columnCount = metaData.getColumnCount();

        //from 1?!
        for (int i = 1; i <= columnCount; ++i) {
            header.add(metaData.getColumnName(i));
        }

        Vector<Vector<String>> dataTable = new Vector<>();
        while (resultSet.next()) {
            Vector<String> row = new Vector<>();
            for (int i = 1; i <= columnCount; ++i) {
                Object o = resultSet.getObject(i);
                row.add(String.valueOf(o));
            }
            dataTable.add(row);
        }

        TableModel tableModel = new TableModel();
        tableModel.data = dataTable;
        tableModel.header = header;
        return tableModel;
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
            if (tables.size() > 0) {
                return tables;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void disconnect() {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isClosed() {
        if (connection == null) {
            return true;
        }
        try {
            return connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
