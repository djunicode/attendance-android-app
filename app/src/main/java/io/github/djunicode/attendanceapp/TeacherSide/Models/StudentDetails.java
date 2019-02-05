package io.github.djunicode.attendanceapp.TeacherSide.Models;
//use StudentDetailsModel in CommonModels
public class StudentDetails {
    String name;
    long sapId;

    public StudentDetails(String name, long sapId) {
        this.name = name;
        this.sapId = sapId;
    }

    public String getName() {
        return name;
    }
    public long getSapId() {
        return sapId;
    }
}


