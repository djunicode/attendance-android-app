package io.github.djunicode.attendanceapp.StudentSide;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import io.github.djunicode.attendanceapp.R;
import io.github.djunicode.attendanceapp.RetrofitInterface;
import io.github.djunicode.attendanceapp.StudentSide.Adapters.SubjectAttendanceAdapter;
import io.github.djunicode.attendanceapp.StudentSide.Models.SubjectAttendanceModel;

public class StudentHome extends AppCompatActivity {

    public static RetrofitInterface retrofitInterface;
    private static final String TAG = "StudentHome";
    TextView predictionView, percentView, nameView, initialsView;
    public static String STUDENT_NAME = "Yash Javeri";   //backend people set it!
    private int totalConducted;
    private int totalAttended;
    SubjectAttendanceAdapter subjectAttendanceAdapter;
    ArrayList<SubjectAttendanceModel> subjectModelList;
    ListView subListView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.student_home);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        getSupportActionBar().setTitle("=  Attendance Manager");

        init();
        loadData();
        setAdapter();
    }

    private void init() {
        subListView = findViewById(R.id.list_studentSubjects);
        predictionView = findViewById(R.id.txt_main_bunk);
        percentView = findViewById(R.id.txt_mainPercent);
        nameView = findViewById(R.id.txt_name);
        RelativeLayout relativeLayout = findViewById(R.id.rel_topArea);
        Log.d(TAG, "init: "+relativeLayout);
        initialsView = relativeLayout.findViewById(R.id.txt_initials);
        Log.d(TAG, "init: "+initialsView);

        subjectModelList = new ArrayList<>();
    }

    private void loadData() {
        subjectModelList.add(new SubjectAttendanceModel(50, 60, 12, "AOA"));
        subjectModelList.add(new SubjectAttendanceModel(42, 60, 12, "OSL"));
        subjectModelList.add(new SubjectAttendanceModel(55, 60, 12, "Data Structures"));
        subjectModelList.add(new SubjectAttendanceModel(20, 60, 12, "ECCF"));
        subjectModelList.add(new SubjectAttendanceModel(55, 60, 12, "Operating Systems"));
    }

    private void setAdapter() {
        for (SubjectAttendanceModel sam : subjectModelList) {
            totalConducted += sam.getConducted();
            totalAttended += sam.getAttended();
        }
        double totalPercent = ((double) totalAttended *100)/ (double) totalConducted;
        String predictionText;

        if (totalPercent >= 75.0)
            predictionText = "You can\nbunk: " + ((4 * totalAttended - 3 * totalConducted) / 3);
        else if (totalPercent < 75.0 && totalPercent >= 70.0)
            predictionText = "You need to\nattend: " + (3 * totalConducted - 4 * totalAttended);
        else
            predictionText = "You need to\nattend: " + (3 * totalConducted - 4 * totalAttended);

        percentView.setText(String.format("%.2f", totalPercent) + "%");
        predictionView.setText(predictionText);
        nameView.setText(StudentHome.STUDENT_NAME);
        String sirName = STUDENT_NAME.split(" ")[1];
        Character sirNameInitial = sirName.charAt(0);
        initialsView.setText(STUDENT_NAME.charAt(0)+""+sirNameInitial);

        subjectAttendanceAdapter = new SubjectAttendanceAdapter(this, subjectModelList);
        Log.d(TAG, "setAdapter: " + subjectAttendanceAdapter);
        subListView.setAdapter(subjectAttendanceAdapter);
    }
}
