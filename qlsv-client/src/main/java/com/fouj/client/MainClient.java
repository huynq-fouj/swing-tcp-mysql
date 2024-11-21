package com.fouj.client;

import javax.swing.*;

public class MainClient {

    public static void main(String[] args) {
        StudentClient client = new StudentClient("127.0.0.1", 8080);
        new ClientView(client);
    }

}
