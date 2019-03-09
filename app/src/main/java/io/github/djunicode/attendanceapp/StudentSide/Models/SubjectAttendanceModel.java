package io.github.djunicode.attendanceapp.StudentSide.Models;

public class SubjectAttendanceModel {
    private int attended,conducted,subjectCode;
    private String name;

    public SubjectAttendanceModel(int attended, int conducted, int subjectCode, String name) {
        this.attended = attended;
        this.conducted = conducted;
        this.subjectCode = subjectCode;
        this.name = name;
    }

    public int getAttended() {
        return attended;
    }

    public int getConducted() {
        return conducted;
    }

    public int getSubjectCode() {
        return subjectCode;
    }

    public String getName() {
        return name;
    }
}
