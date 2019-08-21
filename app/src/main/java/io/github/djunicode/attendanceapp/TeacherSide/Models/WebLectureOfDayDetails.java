package io.github.djunicode.attendanceapp.TeacherSide.Models;
//used for get-lecture-of-the-day RetrofitInterface
public class WebLectureOfDayDetails {
    String timing,date,subject,roomNumber,div,type;
    Integer attendanceTaken,predicted;

    public Integer getPredicted() {
        return predicted;
    }

    public String getType() {
        return type;
    }

    public Integer getAttendanceTaken() {
        return attendanceTaken;
    }

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


    public String getRoomNumber() {
        return roomNumber;
    }

    public WebLectureOfDayDetails(String timing, String date, String subject, String roomNumber, String div, Integer attendanceTaken,String type,Integer predicted) {
        this.timing = timing;
        this.date = date;
        this.subject = subject;
        this.roomNumber = roomNumber;
        this.div = div;
        this.attendanceTaken = attendanceTaken;
        this.type=type;
        this.predicted=predicted;
    }
}
