package com.geekbrains.server;

import java.sql.*;

// используем паттерн Singleton
public class DBHelper implements AutoCloseable {
    private static DBHelper instance;
    private static Connection connection;

    private static PreparedStatement findByLoginAndPassword;
    private static PreparedStatement changeNick;

    private DBHelper() { }

    public static synchronized DBHelper getInstance() {
        if (instance == null) {
            loadDriverAndOpenConnection();
            createPreparedStatement();
            instance = new DBHelper();
        }
        return instance;
    }

    public static void loadDriverAndOpenConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:chat.db");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Ошибка открытия соединения с БД!");
            e.printStackTrace();
        }
    }

    private static void createPreparedStatement() {
        try {
            findByLoginAndPassword = connection.prepareStatement("SELECT * FROM participant WHERE " +
                    "LOWER(login)=LOWER(?) and password=?");
            changeNick = connection.prepareStatement("UPDATE participant SET nickname=? WHERE " +
                    "nickname=?");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String findByLoginAndPassword(String login, String password) {
        ResultSet resultSet = null;

        try {
            findByLoginAndPassword.setString(1, login);
            findByLoginAndPassword.setString(2, password);
            resultSet = findByLoginAndPassword.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("nickname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
        }
        return null;
    }

    private void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public int updateNickName(String oldNickname, String newNickname) {
        try {
            changeNick.setString(1, newNickname);
            changeNick.setString(2, oldNickname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void close() {
        try {
            findByLoginAndPassword.close();
            changeNick.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


