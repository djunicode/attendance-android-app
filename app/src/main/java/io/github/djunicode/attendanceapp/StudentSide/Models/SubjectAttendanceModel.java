package io.github.djunicode.attendanceapp.StudentSide.Models;

public class SubjectAttendanceModel {
    private Integer attended,conducted,subjectCode;
    private String name;

    public SubjectAttendanceModel(Integer attended, Integer conducted, Integer subjectCode, String name) {
        this.attended = attended;
        this.conducted = conducted;
        this.subjectCode = subjectCode;
        this.name = name;
    }

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
