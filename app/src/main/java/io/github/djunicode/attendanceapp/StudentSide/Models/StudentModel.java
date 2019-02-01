package io.github.djunicode.attendanceapp.StudentSide.Models;

import java.util.List;

import io.github.djunicode.attendanceapp.CommonModels.StudentDetailsModel;

public class StudentModel {
    private StudentDetailsModel studentDetailsModel;
    private List<SubjectAttendanceModel> subjectAttendanceModel;

    public StudentDetailsModel getStudentDetailsModel() {
        return studentDetailsModel;
    }

    public List<SubjectAttendanceModel> getSubjectAttendanceModel() {
        return subjectAttendanceModel;
    }
}
