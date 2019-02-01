package io.github.djunicode.attendanceapp.CommonModels;

import io.github.djunicode.attendanceapp.StudentSide.Models.StudentModel;

public class UserModel {
    private Boolean isTeacher,isStudent;
    private StudentModel studentModel;
    // private TeacherModel teacherModel;

    public Boolean getTeacher() {
        return isTeacher;
    }

    public Boolean getStudent() {
        return isStudent;
    }

    public StudentModel getStudentModel() {
        return studentModel;
    }

   /* public TeacherModel getTeacherModel() {
        return teacherModel;
    }
    */
}
