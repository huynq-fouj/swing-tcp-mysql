package com.fouj.client;

import com.fouj.objects.Student;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StudentClient extends TCPClient {

    public StudentClient(String serverAddress, int serverPort) {
        new Thread(() -> {
            try {
                connect(serverAddress, serverPort);
                System.out.println("Connect server: " + serverAddress + ":" + serverPort);
            } catch (IOException e) {
                System.err.println("Can not connect server: " + e.getMessage());
            }
        }).start();
    }

    public Map<String, Object> addStudent(Student student) throws IOException, ClassNotFoundException {
        Map<String, Object> request = new HashMap<>();
        request.put("query", "ADD");
        request.put("student", student);
        send(request);
        return receive();
    }

    public Map<String, Object> editStudent(Student student) throws IOException, ClassNotFoundException {
        Map<String, Object> request = new HashMap<>();
        request.put("query", "UPDATE");
        request.put("student", student);
        send(request);
        return receive();
    }

    public Map<String, Object> deleteStudent(String student_code) throws IOException, ClassNotFoundException {
        Map<String, Object> request = new HashMap<>();
        request.put("query", "DELETE");
        request.put("student_code", student_code);
        send(request);
        return receive();
    }

    public Map<String, Object> searchStudent(String student_code) throws IOException, ClassNotFoundException {
        Map<String, Object> request = new HashMap<>();
        request.put("query", "SEARCH");
        request.put("student_code", student_code);
        send(request);
        return receive();
    }

}
