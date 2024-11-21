package com.fouj.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class TCPClient {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public void connect(String serverAddress, int serverPort) throws IOException {
        socket = new Socket(serverAddress, serverPort);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void send(Map<String, Object> request) throws IOException {
        out.writeObject(request);
    }

    public Map<String, Object> receive() throws IOException, ClassNotFoundException {
        return (Map<String, Object>) in.readObject();
    }

    public void close() {
        try {
            if(out != null) out.close();
            if(in != null) in.close();
            if(socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
