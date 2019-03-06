package io.github.djunicode.attendanceapp;

import java.util.ArrayList;

import io.github.djunicode.attendanceapp.CommonModels.StudentDetailsModel;
import io.github.djunicode.attendanceapp.TeacherSide.Models.TeacherTimeTableModel;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @POST
    Call<TeacherTimeTableModel> teacherTimeTable(@Query("id")String id, @Query("day")String day);

    @POST
    Call<ArrayList<StudentDetailsModel>> studentList(@Query("sem")String sem, @Query("subject")String subject, @Query("division")String division);
}
