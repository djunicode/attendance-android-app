package io.github.djunicode.attendanceapp.StudentSide.Models;

public class DaywiseDetailsModel {
    private String date, time;
    private Integer present;

    public DaywiseDetailsModel(String date, String time, Integer present) {
        this.date = date;
        this.time = time;
        this.present = present;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Integer getPresent() {
        return present;
    }
}