package io.github.djunicode.attendanceapp.StudentSide.Models;

import java.util.ArrayList;

public class Student {

    long sapId;
    String studentName;
    ArrayList<Subject> subjects;

    public Student(long sapId, String studentName, ArrayList<Subject> subjects) {
        this.sapId = sapId;
        this.studentName = studentName;
        this.subjects = subjects;
    }
}