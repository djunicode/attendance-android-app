package io.github.djunicode.attendanceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;


import java.util.ArrayList;

import io.github.djunicode.attendanceapp.TeacherSide.Adapters.MyLectureListAdapt;
import io.github.djunicode.attendanceapp.TeacherSide.Models.Lecture;

public class MainActivity extends AppCompatActivity {
    ArrayList<Lecture> lectureList;
    ListView lectureListView;
    MyLectureListAdapt myLectureListAdapt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setListeners();
    }

    private void init() {
        //UI:
        lectureListView = findViewById(R.id.list_lectures);
        //Misc:
        lectureList = new ArrayList<>();
        myLectureListAdapt = new MyLectureListAdapt(this, lectureList);
    }

    private void setListeners() {
    }


    @Override
    protected void onResume() {
        super.onResume();

        instantiateLectureItems();
        loadData();
    }

    private void loadData() {
        //***TEMPORARY***
        lectureList.add(new Lecture("Data Structures", "7:00", "Class 1A","A", "F.E."));
        lectureList.add(new Lecture("AOA", "8:00", "Class 1A", "B", "S.E."));
        lectureList.add(new Lecture("AI", "9:30", "Class 1B", "A", "F.E."));
        lectureList.add(new Lecture("ML", "10:30", "Class 1C", "A", "T.E."));
        lectureList.add(new Lecture("Web Design", "12:30", "Class 1B", "B", "F.E."));
        lectureList.add(new Lecture("Mobile Dev", "2:00", "Class 1C", "B", "B.E."));

        //----
        //PERMANENT: AFTER BACKEND IMPLEMENTATION
        // ----
    }
    private void instantiateLectureItems() {

        myLectureListAdapt = new MyLectureListAdapt(MainActivity.this, lectureList);
        lectureListView.setAdapter(myLectureListAdapt);
    }
}
