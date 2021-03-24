package com.geekbrains.server;

public class SimpleAuthService implements AuthService {
    private final DBHelper dbHelper = DBHelper.getInstance();

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        return dbHelper.findByLoginAndPassword(login, password);
    }

    @Override
    public boolean updateNickName(String oldNickName, String newNickName) {
        int result = dbHelper.updateNickName(oldNickName, newNickName);
        return result !=0;
    }
}
