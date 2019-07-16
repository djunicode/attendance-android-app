package io.github.djunicode.attendanceapp.StudentSide;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import io.github.djunicode.attendanceapp.R;
import io.github.djunicode.attendanceapp.RetrofitInterface;
import io.github.djunicode.attendanceapp.StudentSide.Adapters.SubjectAttendanceAdapter;
import io.github.djunicode.attendanceapp.StudentSide.Models.SubjectAttendanceModel;
import io.github.djunicode.attendanceapp.TeacherSide.Models.Student;
import io.github.djunicode.attendanceapp.TeacherSide.Models.WebLectureOfDay;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentHome extends AppCompatActivity {

    RetrofitInterface retrofitInterface;
    String TAG = "StudentHome";
    TextView predictionView, percentView, nameView, initialsView;

    private int totalConducted;
    private int totalAttended;
    SubjectAttendanceAdapter subjectAttendanceAdapter;
    ArrayList<SubjectAttendanceModel> subjectModelList;
    ListView subListView;
    public  SharedPreferences spref;
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

    }

    private void init() {
        subListView = findViewById(R.id.list_studentSubjects);

        percentView = findViewById(R.id.txt_mainPercent);
        nameView = findViewById(R.id.txt_name);
        RelativeLayout relativeLayout = findViewById(R.id.rel_topArea);
        Log.d(TAG, "init: "+relativeLayout);
        initialsView = relativeLayout.findViewById(R.id.txt_initials);
        Log.d(TAG, "init: "+initialsView);

        subjectModelList = new ArrayList<>();
    }

    private void loadData() {
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://wizdem.pythonanywhere.com/Attendance/").addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitInterface retrofitInterface =retrofit.create(RetrofitInterface.class);
        spref=getApplicationContext().getSharedPreferences("user",MODE_PRIVATE);
        Call<WebLecturesAttended> webLecturesAttendedCall=retrofitInterface.studentLectures("Token "+spref.getString("token",null));

        webLecturesAttendedCall.enqueue(new Callback<WebLecturesAttended>() {
            @Override
            public void onResponse(Call<WebLecturesAttended> call, Response<WebLecturesAttended> response) {
                WebLecturesAttended webLecturesAttended=response.body();
                for(WebIndividualLectures e:webLecturesAttended.getAttendance())
                {
                    subjectModelList.add(new SubjectAttendanceModel(Integer.parseInt(e.getAttended()), Integer.parseInt(e.getTotal()), e.getType(), ""+e.getSubject()));
                }
                for (SubjectAttendanceModel sam : subjectModelList) {
                    totalConducted += sam.getConducted();
                    totalAttended += sam.getAttended();
                }
                double totalPercent = ((double) totalAttended *100)/ (double) totalConducted;
//                String predictionText;
//
//                if (totalPercent >= 75.0)
//                    predictionText = "You can\nbunk: " + ((4 * totalAttended - 3 * totalConducted) / 3);
//                else if (totalPercent < 75.0 && totalPercent >= 70.0)
//                    predictionText = "You need to\nattend: " + (3 * totalConducted - 4 * totalAttended);
//                else
//                    predictionText = "You need to\nattend: " + (3 * totalConducted - 4 * totalAttended);

                percentView.setText(String.format("%.2f", totalPercent) + "%");

                nameView.setText(spref.getString("name","student student"));

                initialsView.setText(spref.getString("sapId","SAP ID").substring(8));

                subjectAttendanceAdapter = new SubjectAttendanceAdapter(StudentHome.this, subjectModelList);

                subListView.setAdapter(subjectAttendanceAdapter);

            }

            @Override
            public void onFailure(Call<WebLecturesAttended> call, Throwable t) {
                Toast.makeText(StudentHome.this,""+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }


}
