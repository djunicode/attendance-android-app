package io.github.djunicode.attendanceapp.TeacherSide;

import com.google.gson.annotations.SerializedName;

public class TokenRequest {
    @SerializedName("id")
    String id;

    @SerializedName("password")
    String password;

    public TokenRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
