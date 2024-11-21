package com.fouj.server;

public class MainServer {

    public static void main(String[] args) {
        new TCPServer("127.0.0.1", 8080);
    }

}
