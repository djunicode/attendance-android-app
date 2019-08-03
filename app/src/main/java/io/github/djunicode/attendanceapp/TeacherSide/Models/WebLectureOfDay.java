package io.github.djunicode.attendanceapp.TeacherSide.Models;

import java.util.ArrayList;

public class WebLectureOfDay {
    String date;

    ArrayList<WebLectureOfDayDetails> lectures=new ArrayList<>();

    public String getDate() {
        return date;
    }

    public ArrayList<WebLectureOfDayDetails> getLectures() {
        return lectures;
    }
}
