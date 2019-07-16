package io.github.djunicode.attendanceapp.TeacherSide.Models;
//used for get-lecture-of-the-day RetrofitInterface
public class WebLectureOfDayDetails {
    String timing,date,subject,teacher,roomNumber,div;

    public String getDiv() {
        return div;
    }

    public String getTiming() {
        return timing;
    }

    public String getDate() {
        return date;
    }

    public String getSubject() {
        return subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}
