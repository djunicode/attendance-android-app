package io.github.djunicode.attendanceapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import io.github.djunicode.attendanceapp.TeacherSide.Adapters.MyLectureListAdapt;
import io.github.djunicode.attendanceapp.TeacherSide.Models.Lecture;
import io.github.djunicode.attendanceapp.TeacherSide.Models.TeacherTimeTableModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ArrayList<Lecture> lectureList;
    ListView lectureListView;
    MyLectureListAdapt myLectureListAdapt;
    public static RetrofitInterface retrofitInterface;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Retrofit retrofit=new Retrofit.Builder().baseUrl("URL HERE").addConverterFactory(GsonConverterFactory.create()).build();
        //RetrofitInterface retrofitInterface =retrofit.create(RetrofitInterface.class);

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
        //Comment off below code
        loadData();
    }

    private void loadData() {
        //TODO:Teacher id in shared prefs after login

        String daysArray[]={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        Call<TeacherTimeTableModel> teacherTimeTableModelCall= retrofitInterface.teacherTimeTable(id,daysArray[day]);
        teacherTimeTableModelCall.enqueue(new Callback<TeacherTimeTableModel>() {
            @Override
            public void onResponse(Call<TeacherTimeTableModel> call, Response<TeacherTimeTableModel> response) {
                TeacherTimeTableModel teacherTimeTableModel=response.body();
                //TODO:FRONTEND HERE
            }

            @Override
            public void onFailure(Call<TeacherTimeTableModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



//        //***TEMPORARY***
//        lectureList.add(new Lecture("Data Structures", "7:00", "Class 1A","A", "F.E."));
//        lectureList.add(new Lecture("AOA", "8:00", "Class 1A", "B", "S.E."));
//        lectureList.add(new Lecture("AI", "9:30", "Class 1B", "A", "F.E."));
//        lectureList.add(new Lecture("ML", "10:30", "Class 1C", "A", "T.E."));
//        lectureList.add(new Lecture("Web Design", "12:30", "Class 1B", "B", "F.E."));
//        lectureList.add(new Lecture("Mobile Dev", "2:00", "Class 1C", "B", "B.E."));
//
//        //----
//        //PERMANENT: AFTER BACKEND IMPLEMENTATION
//        // ----
    }
    private void instantiateLectureItems() {

        myLectureListAdapt = new MyLectureListAdapt(MainActivity.this, lectureList);
        lectureListView.setAdapter(myLectureListAdapt);
    }
}