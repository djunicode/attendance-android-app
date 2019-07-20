package io.github.djunicode.attendanceapp.TeacherSide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.github.djunicode.attendanceapp.LoginActivity;
import io.github.djunicode.attendanceapp.R;
import io.github.djunicode.attendanceapp.RetrofitInterface;
import io.github.djunicode.attendanceapp.StudentSide.StudentHome;
import io.github.djunicode.attendanceapp.TeacherSide.Adapters.MyLectureListAdapt;
import io.github.djunicode.attendanceapp.TeacherSide.FormDialogFragment.OnDetailsSaved;
import io.github.djunicode.attendanceapp.TeacherSide.Models.Lecture;
import io.github.djunicode.attendanceapp.TeacherSide.Models.WebLectureOfDay;
import io.github.djunicode.attendanceapp.TeacherSide.Models.WebLectureOfDayDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TeacherHome extends AppCompatActivity implements OnDetailsSaved {
    ArrayList<Lecture> lectureList = new ArrayList<>();
    ListView lectureListView;
    MyLectureListAdapt myLectureListAdapt;
    SharedPreferences spref;
    Lecture lectureForm;
    public SharedPreferences.Editor edit;
    private FloatingActionButton newLectureFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_home);

        newLectureFAB = findViewById(R.id.new_lecture_fab);
        newLectureFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                FormDialogFragment fragment = new FormDialogFragment();
                fragment.show(transaction, "form-dialog-fragment");
            }
        });

        spref = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        edit=spref.edit();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date = sdf.format(Calendar.getInstance().getTime());
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://wizdem.pythonanywhere.com/Attendance/").addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<WebLectureOfDay> lectureOfDay = retrofitInterface.teacherTimeTable("Token " + spref.getString("token", null), date);
        lectureOfDay.enqueue(new Callback<WebLectureOfDay>() {
            @Override
            public void onResponse(Call<WebLectureOfDay> call, Response<WebLectureOfDay> response) {
                WebLectureOfDay webLectureOfDays = response.body();
                if (webLectureOfDays != null) {
                    if (webLectureOfDays.getLectures().size() != 0) {
                        for (WebLectureOfDayDetails e : webLectureOfDays.getLectures()) {

                            lectureList.add(new Lecture(e.getSubject(), e.getTiming(), e.getRoomNumber(), e.getDiv().substring(3), e.getDiv().substring(0, 2), e.getAttendanceTaken()));

                        }
                        lectureListView = findViewById(R.id.list_lectures);
                        myLectureListAdapt = new MyLectureListAdapt(TeacherHome.this, lectureList);
                    lectureListView.setAdapter(myLectureListAdapt);
                    } else {
                        Toast.makeText(TeacherHome.this, "Something went wrong. Please try again", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(TeacherHome.this,"Something went wrong. Please try again.",Toast.LENGTH_LONG).show();

                }
            }


            @Override
            public void onFailure(Call<WebLectureOfDay> call, Throwable t) {
                Toast.makeText(TeacherHome.this, "" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onDetailsSaved(String year, String subject, String startTime, String endTime,String roomNumber,String division) {
        lectureForm=new Lecture(subject,startTime+":00 - "+endTime+":00",roomNumber,division,year,0);
        Intent intent = new Intent(TeacherHome.this, PickerActivity.class);
        intent.putExtra("LectureData", lectureForm);
        intent.putExtra("type","form");
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            edit.clear();
            edit.commit();
            startActivity(new Intent(TeacherHome.this, LoginActivity.class));
            finish();
            return true;
        }
        return false;
    }
}