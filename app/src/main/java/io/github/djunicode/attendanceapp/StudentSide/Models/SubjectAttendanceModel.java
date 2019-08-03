package io.github.djunicode.attendanceapp.StudentSide.Models;

public class SubjectAttendanceModel {
    private Integer attended,conducted;
    private String lectureType,name;

    public String getLectureType() {
        return lectureType;
    }

    public SubjectAttendanceModel(Integer attended, Integer conducted, String lectureType, String name) {
        this.attended = attended;
        this.conducted = conducted;
        this.lectureType = lectureType;
        this.name = name;
    }

    public Integer getAttended() {
        return attended;
    }

    public Integer getConducted() {
        return conducted;
    }



    public String getName() {
        return name;
    }
}
