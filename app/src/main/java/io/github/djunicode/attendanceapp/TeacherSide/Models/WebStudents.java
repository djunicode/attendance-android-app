package io.github.djunicode.attendanceapp.TeacherSide.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WebStudents {
    @SerializedName("students")
    ArrayList<WebStudentsList> students;

    public ArrayList<WebStudentsList> getStudents() {
        return students;
    }
}
