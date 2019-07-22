package io.github.djunicode.attendanceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.github.djunicode.attendanceapp.StudentSide.StudentHome;
import io.github.djunicode.attendanceapp.TeacherSide.TeacherHome;

public class MainActivity extends AppCompatActivity {
    SharedPreferences spref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spref=getApplication().getSharedPreferences("user",MODE_PRIVATE);
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