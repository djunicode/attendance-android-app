package io.github.djunicode.attendanceapp.TeacherSide;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.github.djunicode.attendanceapp.R;
import io.github.djunicode.attendanceapp.RetrofitInterface;
import io.github.djunicode.attendanceapp.TeacherSide.Adapters.MyLectureListAdapt;
import io.github.djunicode.attendanceapp.TeacherSide.Models.Lecture;
import io.github.djunicode.attendanceapp.TeacherSide.Models.WebLectureOfDay;
import io.github.djunicode.attendanceapp.TeacherSide.Models.WebLectureOfDayDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TeacherHome extends AppCompatActivity {
    ArrayList<Lecture> lectureList = new ArrayList<>();
    ListView lectureListView;
    MyLectureListAdapt myLectureListAdapt;
    SharedPreferences spref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_home);
        spref = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String format = sdf.format(Calendar.getInstance().getTime());
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://wizdem.pythonanywhere.com/Attendance/").addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        //TODO: CHANGE DATE
        Call<WebLectureOfDay> lectureOfDay = retrofitInterface.teacherTimeTable("Token " + spref.getString("token", null), "04-07-2019");
        lectureOfDay.enqueue(new Callback<WebLectureOfDay>() {
            @Override
            public void onResponse(Call<WebLectureOfDay> call, Response<WebLectureOfDay> response) {
                WebLectureOfDay webLectureOfDays = response.body();
                if (webLectureOfDays != null) {

                    for (WebLectureOfDayDetails e : webLectureOfDays.getLectures()) {
                        //TODO:e.getAttendanceTaken
                        lectureList.add(new Lecture(e.getSubject(), e.getTiming(), e.getRoomNumber(), e.getDiv().substring(3), e.getDiv().substring(0, 2),0));

                    }
                    lectureListView = findViewById(R.id.list_lectures);
                    myLectureListAdapt = new MyLectureListAdapt(TeacherHome.this, lectureList);
                    lectureListView.setAdapter(myLectureListAdapt);
                } else {
                    Toast.makeText(TeacherHome.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<WebLectureOfDay> call, Throwable t) {
                Toast.makeText(TeacherHome.this,""+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}