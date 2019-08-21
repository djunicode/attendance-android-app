package io.github.djunicode.attendanceapp;
import java.util.ArrayList;

import io.github.djunicode.attendanceapp.StudentSide.Models.DaywiseDetailsModel;
import io.github.djunicode.attendanceapp.StudentSide.WebLecturesAttended;
import io.github.djunicode.attendanceapp.TeacherSide.Models.WebDivAndSubjectsForForm;
import io.github.djunicode.attendanceapp.TeacherSide.Models.WebLectureOfDay;
import io.github.djunicode.attendanceapp.TeacherSide.Models.WebSendAttendance;
import io.github.djunicode.attendanceapp.TeacherSide.Models.WebStudents;
import io.github.djunicode.attendanceapp.TeacherSide.Models.deleteResponse;
import io.github.djunicode.attendanceapp.TeacherSide.TokenRequest;
import io.github.djunicode.attendanceapp.TeacherSide.TokenResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {
    @GET("get-lectures-of-the-day/{date}")
    Call<WebLectureOfDay> teacherTimeTable(@Header("Authorization")String header, @Path("date")String date);

    @GET("get-student-list/{subject}/{batch}/{date}/{startTime}")
    Call<WebStudents> studentList(@Header("Authorization")String header,@Path("subject")String subject,@Path("batch")String batch,@Path("date")String date,@Path("startTime")String startTime);

    @GET("get-previous-lecture-attendance/{batch}/{date}/{startTime}")
    Call<WebStudents> studentListFromPrevious(@Header("Authorization")String header,@Path("batch")String batch,@Path("date")String date,@Path("startTime")String startTime);

    @GET("get-students-attendance/")
    Call<WebLecturesAttended> studentLectures(@Header("Authorization")String header);

    @GET("get-all-subjects-and-divisions/")
    Call<ArrayList<WebDivAndSubjectsForForm>>formSpinnerData(@Header("Authorization")String header);

    @GET("get-students-attendance-history/{subject}/{type}")
    Call<ArrayList<DaywiseDetailsModel>>getDateWiseDetails(@Header("Authorization")String header, @Path("subject")String subject, @Path("type")String type);

    @POST("save-lecture-and-get-student-list/")
    Call<WebStudents> formStudentList(@Header("Authorization")String header,@Body WebSendAttendance webSendAttendance);

    @POST("generic-login/")
    Call<TokenResponse> login(@Body TokenRequest tokenRequest);

    @POST("save-attendance/")
    Call<WebSendAttendance>sendAttendance(@Header("Authorization")String header,@Body WebSendAttendance webSendAttendance);

    @POST("delete-lecture/")
    Call<deleteResponse>deleteLecture(@Header("Authorization")String header, @Body WebSendAttendance webSendAttendance);

    @POST("change-password/")
    Call<deleteResponse>changePassword(@Header("Authorization")String header,@Body changePassword changePassword);

}
