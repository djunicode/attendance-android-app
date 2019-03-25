package io.github.djunicode.attendanceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;

import io.github.djunicode.attendanceapp.StudentSide.StudentHome;
import io.github.djunicode.attendanceapp.TeacherSide.Adapters.MyLectureListAdapt;
import io.github.djunicode.attendanceapp.TeacherSide.Models.Lecture;
import io.github.djunicode.attendanceapp.TeacherSide.Models.TeacherTimeTableModel;
import io.github.djunicode.attendanceapp.TeacherSide.TeacherHome;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    SharedPreferences spref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spref=getApplication().getSharedPreferences("userType",MODE_PRIVATE);
        if(spref.getString("userType","temp").equals("temp"))
        {
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }
        else if (spref.getString("userType","temp").equals("teacher"))
        {
            startActivity(new Intent(MainActivity.this, TeacherHome.class));
            finish();
        }
        else
        {
            startActivity(new Intent(MainActivity.this, StudentHome.class));
            finish();
        }
    }
}