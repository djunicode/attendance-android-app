package io.github.djunicode.attendanceapp.TeacherSide.Models;

import java.util.List;

public class TeacherModel {
    private TeacherDetailsModel teacherDetailsModel;
    private List<TeacherTimeTableModel> teacherTimeTableModel;

    public TeacherDetailsModel getTeacherDetailsModel() {
        return teacherDetailsModel;
    }

    public List<TeacherTimeTableModel> getTeacherTimeTableModel() {
        return teacherTimeTableModel;
    }
}
