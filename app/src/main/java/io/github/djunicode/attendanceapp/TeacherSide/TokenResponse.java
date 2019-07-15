package io.github.djunicode.attendanceapp.TeacherSide;

import io.github.djunicode.attendanceapp.CommonModels.User;

public class TokenResponse {
    String token;
    Boolean isTeacher,isStudent;
    User user;

    public Boolean getTeacher() {
        return isTeacher;
    }

    public Boolean getStudent() {
        return isStudent;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
