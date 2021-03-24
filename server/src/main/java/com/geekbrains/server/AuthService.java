package com.geekbrains.server;

public interface AuthService {

    String getNicknameByLoginAndPassword(String login, String password);

    boolean updateNickName(String oldNickName, String newNickName);
}
