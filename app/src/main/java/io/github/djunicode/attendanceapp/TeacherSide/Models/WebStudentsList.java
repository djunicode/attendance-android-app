package io.github.djunicode.attendanceapp.TeacherSide.Models;

public class WebStudentsList {
    String name,sapID;
    Integer Attendance;

    public WebStudentsList(String name, String sapID, Integer attendance) {
        this.name = name;
        this.sapID = sapID;
        Attendance = attendance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSapID(String sapID) {
        this.sapID = sapID;
    }

    public void setAttendance(Integer attendance) {
        Attendance = attendance;
    }

    public String getName() {
        return name;
    }

    public String getSapID() {
        return sapID;
    }

    public Integer getAttendance() {
        return Attendance;
    }
}
