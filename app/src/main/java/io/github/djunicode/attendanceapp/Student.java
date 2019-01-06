package io.github.djunicode.attendanceapp;

import java.io.Serializable;

public class Student implements Serializable {
    private String studentName, sapID;
    private boolean isPresent;

    public Student(String sapID, String studentName, boolean isPresent) {
        this.studentName = studentName;
        this.sapID = sapID;
        this.isPresent = isPresent;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSapID() {
        return sapID;
    }

    public void setSapID(String sapID) {
        this.sapID = sapID;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }
}
