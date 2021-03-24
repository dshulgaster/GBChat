package com.geekbrains.server;

import java.util.logging.Logger;

public class MainServer {

    // логер пока нигде не используется, в будущем нужно предусмотреть запись
    // сообщений об ошибках (из исключений?)
    Logger logger = Logger.getLogger("");

    public static void main(String[] args) {
        new Server();
    }

    // пока нигде не используется
    public static void config() {

    }
}
