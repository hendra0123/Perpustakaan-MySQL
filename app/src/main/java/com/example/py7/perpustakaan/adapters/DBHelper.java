package com.example.py7.perpustakaan.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {

    public static final String database_name = "db_perpus";
    public static final String tabel_name = "tabel_perpus";

    public static final String row_id = "_id";
    public static final String row_nama = "Nama";
    public static final String row_judul = "Judul";
    public static final String row_pinjam = "TglPinjam";
    public static final String row_kembali = "TglKembali";
    public static final String row_status = "Status";

    private Connection connection;
    private Statement statement;

    public DBHelper(Context context) {
        connection = openConnection();
        statement = createStatement();
    }

    private Connection openConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/db_perpus?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String username = "root";
            String password = "";
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private Statement createStatement() {
        Statement statement = null;
        try {
            if (connection != null) {
                statement = connection.createStatement();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    // Get All MySQL Data
    public Cursor allData() {
        String query = "SELECT * FROM " + tabel_name + " ORDER BY " + row_id + " DESC ";

        ResultSet resultSet = null;
        try {
            if (statement != null) {
                resultSet = statement.executeQuery(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Convert ResultSet to Cursor
        MatrixCursor cursor = new MatrixCursor(new String[]{row_id, row_nama, row_judul, row_pinjam, row_kembali, row_status});
        try {
            while (resultSet != null && resultSet.next()) {
                long id = resultSet.getLong(row_id);
                String nama = resultSet.getString(row_nama);
                String judul = resultSet.getString(row_judul);
                String pinjam = resultSet.getString(row_pinjam);
                String kembali = resultSet.getString(row_kembali);
                String status = resultSet.getString(row_status);

                cursor.addRow(new Object[]{id, nama, judul, pinjam, kembali, status});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cursor;
    }

    // GET 1 DATA BY ID
    public Cursor oneData(long id) {
        String query = "SELECT * FROM " + tabel_name + " WHERE " + row_id + "=" + id;

        ResultSet resultSet = null;
        try {
            if (statement != null) {
                resultSet = statement.executeQuery(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Convert ResultSet to Cursor
        MatrixCursor cursor = new MatrixCursor(new String[]{row_id, row_nama, row_judul, row_pinjam, row_kembali, row_status});
        try {
            if (resultSet != null && resultSet.next()) {
                String nama = resultSet.getString(row_nama);
                String judul = resultSet.getString(row_judul);
                String pinjam = resultSet.getString(row_pinjam);
                String kembali = resultSet.getString(row_kembali);
                String status = resultSet.getString(row_status);

                cursor.addRow(new Object[]{id, nama, judul, pinjam, kembali, status});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cursor;
    }

    // Insert Data
    public void insertData(ContentValues values) {
        // Mendapatkan nilai dari ContentValues
        String nama = values.getAsString(row_nama);
        String judul = values.getAsString(row_judul);
        String pinjam = values.getAsString(row_pinjam);
        String kembali = values.getAsString(row_kembali);
        String status = values.getAsString(row_status);

        // Membuat query INSERT INTO dengan nilai dari EditText
        String query = "INSERT INTO " + tabel_name + " (Nama, Judul, TglPinjam, TglKembali, Status) VALUES ('" + nama + "', '" + judul + "', '" + pinjam + "', '" + kembali + "', '" + status + "')";

        // Menjalankan query
        executeQuery(query);
    }


    // Update Data
    public void updateData(ContentValues values, long id) {
        // Convert ContentValues to SQL query
        // ...
        String query = "UPDATE " + tabel_name + " SET ... WHERE " + row_id + "=" + id;
        executeQuery(query);
    }

    // Delete Data
    public void deleteData(long id) {
        String query = "DELETE FROM " + tabel_name + " WHERE " + row_id + "=" + id;
        executeQuery(query);
    }

    private void executeQuery(String query) {
        try {
            if (statement != null) {
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
