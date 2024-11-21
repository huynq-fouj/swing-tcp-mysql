package com.fouj.server;

import com.fouj.objects.Student;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TCPServer {

    private ServerSocket serverSocket;
    private ServerView serverView;
    private StudentRepository repository;

    public TCPServer(String ipAddress, int port) {
        repository = new StudentRepository();
        loadUI();
        try {
            InetAddress serverAddress = InetAddress.getByName(ipAddress);
            serverSocket = new ServerSocket(port, 5, serverAddress);
            while(true) {
                Socket client = serverSocket.accept();
                new Thread(() -> handleClient(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadUI() {
        serverView = new ServerView();
        updateTable();
    }

    private void updateTable() {
        List<Student> students = repository.getStudents();
        serverView.updateTable(students);
    }

    private void handleClient(Socket client) {
        try(
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream())
        ) {

            InetAddress clientAddress = client.getInetAddress();
            int clientPort = client.getPort();
            System.out.println("Handle client: " + clientAddress.getHostName() + " [" + clientAddress.getHostAddress() + ":" + clientPort + "]");

            while(true) {
                Map<String, Object> request = (Map<String, Object>) in.readObject();
                String query = (String) request.get("query");
                switch (query.toUpperCase()) {
                    case "ADD":
                        addStudent(request, out);
                        break;
                    case "UPDATE":
                        editStudent(request, out);
                        break;
                    case "DELETE":
                        deleteStudent(request, out);
                        break;
                    case "SEARCH":
                        searchStudent(request, out);
                        break;
                    case "EXIT":
                        in.close();
                        out.close();
                        client.close();
                        return;
                    default:
                        out.writeObject("Invalid query.");
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addStudent(Map<String, Object> request, ObjectOutputStream out) throws IOException {
        Student student = (Student) request.get("student");
        Map<String, Object> response = new HashMap<>();
        if(repository.addStudent(student)) {
            response.put("message", "Them moi sinh vien thanh cong");
            response.put("result", student);
            updateTable();
        } else response.put("message", "Them moi sinh vien khong thanh cong");
        out.writeObject(response);
    }

    private void editStudent(Map<String, Object> request, ObjectOutputStream out) throws IOException {
        Student student = (Student) request.get("student");
        Map<String, Object> response = new HashMap<>();
        if(repository.editStudent(student)) {
            response.put("message", "Chinh sua thong tin sinh vien thanh cong");
            response.put("result", student);
            updateTable();
        } else response.put("message", "Chinh sua thong tin sinh vien khong thanh cong");
        out.writeObject(response);
    }

    private void deleteStudent(Map<String, Object> request, ObjectOutputStream out) throws IOException {
        String student_code = (String) request.get("student_code");
        Map<String, Object> response = new HashMap<>();
        if(repository.deleteStudent(student_code)) {
            response.put("message", "Xoa sinh vien thanh cong");
            updateTable();
        } else response.put("message", "Xoa sinh vien khong thanh cong");
        out.writeObject(response);
    }

    private void searchStudent(Map<String, Object> request, ObjectOutputStream out) throws IOException {
        String student_code = (String) request.get("student_code");
        Map<String, Object> response = new HashMap<>();
        Student student = repository.getStudent(student_code);
        if(student != null) {
            response.put("message", "Thong tin sinh vien");
            response.put("result", student);
        } else response.put("message", "Khong tim thay sinh vien");
        out.writeObject(response);
    }

}
