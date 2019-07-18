package io.github.djunicode.attendanceapp.TeacherSide.Models;

import java.io.Serializable;
import java.util.ArrayList;
//use LectureModel
public class Lecture implements Serializable{

    private String subjectName;
    private String timing;
    private String classRoomName;
    private String division;
    private String year;
    Integer attendanceTaken;

    public Integer getAttendanceTaken() {
        return attendanceTaken;
    }

    public Lecture(String subjectName, String timing, String classRoomName, String division, String year,Integer attendanceTaken) {
        this.subjectName = subjectName;
        this.timing = timing;
        this.classRoomName = classRoomName;
        this.division = division;
        this.year = year;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getTiming() {
        return timing;
    }

    public String getClassRooomName() {
        return classRoomName;
    }

    public String getDivision() { return division; }

    public String getYear() { return year; }
}
