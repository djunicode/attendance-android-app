package io.github.djunicode.attendanceapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import io.github.djunicode.attendanceapp.TeacherSide.Models.LectureModel;

public class TimetableActivity extends AppCompatActivity {

    private static final String TAG = "TimetableActivity";

    private RelativeLayout[] days = new RelativeLayout[6];
    private FloatingActionButton insert;
//    private ArrayList<LectureModel> lectures = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViews();
    }

    private void findViews() {
        //Index of Monday is 0, Sunday not counted
        days[0] = findViewById(R.id.day_0);
        days[1] = findViewById(R.id.day_1);
        days[2] = findViewById(R.id.day_2);
        days[3] = findViewById(R.id.day_3);
        days[4] = findViewById(R.id.day_4);
        days[5] = findViewById(R.id.day_5);
    }

    /*
    * Add an inflated View in the timetable layout
    * @param lecture A lecture object with details
    */
    private void addLecture(LectureModel lecture) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.layout_tt_box, null);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        int start = getTimeInMinutes(lecture.getStartTime());
        params.topMargin = getSizeInPixels(start);


        int i = lecture.getDayOfTheWeek();
        days[i].addView(view, params);
        int length = getTimeDifference(lecture.getStartTime(), lecture.getEndTime());
        view.getLayoutParams().height = getSizeInPixels(length);
    }

    private int getTimeInMinutes(String time) {
        return Integer.parseInt(time.substring(3, 5)) + 60 * Integer.parseInt(time.substring(0,2));
    }

    private int getTimeDifference(String startTime, String endTime){
        return Integer.parseInt(endTime.substring(3, 5))
                + 60 * Integer.parseInt(endTime.substring(0, 2))
                -(Integer.parseInt(startTime.substring(3, 5))
                + 60 * Integer.parseInt(startTime.substring(0, 2)));
    }

    private int getSizeInPixels(int dp) {
        float density = getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

//    private void dummyTT(){
//        Log.e(TAG, "dummyTT: Entered here");
//        String startTime = "19:15";
//        String endTime = "22:45";
//
//        LectureModel lecture1 = new LectureModel();
//        lecture1.setDayOfTheWeek(0);
//        lecture1.setSubject("AOA");
//        lecture1.setStartTime(startTime);
//        lecture1.setEndTime("21:30");
//        lectures.add(lecture1);
//
//
//        LectureModel lecture2 = new LectureModel();
//        lecture2.setDayOfTheWeek(2);
//        lecture2.setSubject("DS");
//        lecture2.setStartTime(startTime);
//        lecture2.setEndTime("09:30");
//        lectures.add(lecture2);
//
//        LectureModel lecture3 = new LectureModel();
//        lecture3.setStartTime(startTime);
//        lecture3.setEndTime("22:30");
//        lecture3.setDayOfTheWeek(0);
//        lecture3.setSubject("SPCC");
//        lectures.add(lecture3);
//    }

//    private void dummyPairs() {
//        times.add(new Pair<>(60, 120));
//        times.add(new Pair<>(540, 30));
//        times.add(new Pair<>(180, 90));
//        times.add(new Pair<>(240, 30));
//        times.add(new Pair<>(60, 60));
//        times.add(new Pair<>(1380, 60));
//    }

//    private void addTimeTableViews(){
//        for (LectureModel lectureModel : lectures) {
//            LayoutInflater inflater = LayoutInflater.from(this);
//            View view = inflater.inflate(R.layout.layout_tt_box, null);
//
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
//            int start = getTimeInMinutes(lectureModel.getStartTime());
//            params.topMargin = getSizeInPixels(start);
//            int i = lectureModel.getDayOfTheWeek();
//            days[i].addView(view, params);
//            int length = getTimeDifference(lectureModel.getStartTime(), lectureModel.getEndTime());
//            view.getLayoutParams().height = getSizeInPixels(length);
//        }
//        for (int i = 0; i < 6; ++i) {
//            LayoutInflater inflater = LayoutInflater.from(this);
//            int start = times.get(i).first;
//            int length = times.get(i).second;
//            View view = inflater.inflate(R.layout.layout_tt_box, null);
//
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
//            params.topMargin = getSizeInPixels(start);
//            days[i].addView(view, params);
//            view.getLayoutParams().height = getSizeInPixels(length);
//        }
//    }
}
