package com.fouj.server;

import com.fouj.objects.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    private final String url = "jdbc:mysql://localhost:3306/swing_qlsv";
    private final String username = "root";
    private final String password = "123456";
    private final String driver = "com.mysql.jdbc.Driver";

    public StudentRepository() {
        loadDriver();
    }

    private void loadDriver() {
        try {
            Class.forName(this.driver);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        System.out.println("Create new connection.");
        return DriverManager.getConnection(url, username, password);
    }

    public boolean addStudent(Student student) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO student");
        sql.append("(name, student_code, major, languages, final_score) ");
        sql.append("VALUES(?,?,?,?,?)");
        try {
            Connection con = getConnection();
            PreparedStatement pre = con.prepareStatement(sql.toString());
            pre.setString(1, student.getName());
            pre.setString(2, student.getStudent_code());
            pre.setString(3, student.getMajor());
            pre.setString(4, student.getLanguages());
            pre.setDouble(5, student.getFinal_score());
            int result = pre.executeUpdate();
            con.close();
            return result != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean editStudent(Student student) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE student SET ");
        sql.append("name=?, major=?, languages=?, final_score=? ");
        sql.append("WHERE student_code=?");
        try {
            Connection con = getConnection();
            PreparedStatement pre = con.prepareStatement(sql.toString());
            pre.setString(1, student.getName());
            pre.setString(2, student.getMajor());
            pre.setString(3, student.getLanguages());
            pre.setDouble(4, student.getFinal_score());
            pre.setString(5, student.getStudent_code());
            int result = pre.executeUpdate();
            con.close();
            return result != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteStudent(String student_code) {
        String sql = "DELETE FROM student WHERE student_code=?";
        try {
            Connection con = getConnection();
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, student_code);
            int result = pre.executeUpdate();
            con.close();
            return result != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Student getStudent(String student_code) {
        Student student = null;
        String sql = "SELECT * FROM student WHERE student_code=?";
        try {
            Connection con = getConnection();
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, student_code);
            ResultSet rs = pre.executeQuery();

            if(rs.next()) {
                student = new Student()
                        .setId(rs.getInt("id"))
                        .setName(rs.getString("name"))
                        .setStudent_code(rs.getString("student_code"))
                        .setMajor(rs.getString("major"))
                        .setLanguages(rs.getString("languages"))
                        .setFinal_score(rs.getDouble("final_score"));
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student";

        try {
            Connection con = getConnection();
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();

            while(rs.next()) {
                Student student = new Student()
                        .setId(rs.getInt("id"))
                        .setName(rs.getString("name"))
                        .setStudent_code(rs.getString("student_code"))
                        .setMajor(rs.getString("major"))
                        .setLanguages(rs.getString("languages"))
                        .setFinal_score(rs.getDouble("final_score"));
                students.add(student);
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

}
