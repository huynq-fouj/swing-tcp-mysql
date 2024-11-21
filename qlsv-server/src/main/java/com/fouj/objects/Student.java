package com.fouj.objects;

import java.io.Serializable;

public class Student implements Serializable {
    private int id;
    private String name;
    private String student_code;
    private String major;
    private String languages;
    private double final_score;

    public int getId() {
        return id;
    }

    public Student setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public String getStudent_code() {
        return student_code;
    }

    public Student setStudent_code(String student_code) {
        this.student_code = student_code;
        return this;
    }

    public String getMajor() {
        return major;
    }

    public Student setMajor(String major) {
        this.major = major;
        return this;
    }

    public String getLanguages() {
        return languages;
    }

    public Student setLanguages(String languages) {
        this.languages = languages;
        return this;
    }

    public double getFinal_score() {
        return final_score;
    }

    public Student setFinal_score(double final_score) {
        this.final_score = final_score;
        return this;
    }

    @Override
    public String toString() {
        return """
                Ho va ten: %s
                Ma sinh vien: %s 
                Nganh hoc: %s
                Ngoai ngu: %s
                Diem tong ket: %.2f
                """.formatted(name, student_code, major, languages, final_score);
    }
}
