package io.github.djunicode.attendanceapp;

import java.util.ArrayList;

import io.github.djunicode.attendanceapp.CommonModels.StudentDetailsModel;
import io.github.djunicode.attendanceapp.TeacherSide.Models.TeacherTimeTableModel;
import io.github.djunicode.attendanceapp.TeacherSide.TokenRequest;
import io.github.djunicode.attendanceapp.TeacherSide.TokenResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET
    Call<TeacherTimeTableModel> teacherTimeTable(@Query("id")String id, @Query("day")String day);

    @GET
    Call<ArrayList<StudentDetailsModel>> studentList(@Query("sem")String sem, @Query("subject")String subject, @Query("division")String division);

    @POST("generic-login/")
    Call<TokenResponse> login(@Body TokenRequest tokenRequest);
}
