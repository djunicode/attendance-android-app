package io.github.djunicode.attendanceapp.StudentSide.Models;

public class SubjectAttendanceModel {
    private Integer attended,conducted,subjectCode;
    private String name;

    public Integer getAttended() {
        return attended;
    }

    public Integer getConducted() {
        return conducted;
    }

    public Integer getSubjectCode() {
        return subjectCode;
    }

    public String getName() {
        return name;
    }
}
