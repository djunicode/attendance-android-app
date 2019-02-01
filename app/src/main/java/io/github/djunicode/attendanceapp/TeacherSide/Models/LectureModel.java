package io.github.djunicode.attendanceapp.TeacherSide.Models;

class LectureModel {
    private String startTime;
    private String endTime;
    private String division;
    private String roomNumber;
    private String date;
    private Integer semester;
    private SubjectModel subjectModel;

    public String getDate() {
        return date;
    }


    public SubjectModel getSubjectModel() {
        return subjectModel;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDivision() {
        return division;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public Integer getSemester() {
        return semester;
    }


}
