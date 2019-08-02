package io.github.djunicode.attendanceapp.StudentSide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import io.github.djunicode.attendanceapp.UserActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentHome extends AppCompatActivity {

    TextView predictionView, percentView, nameView, initialsView;
    RelativeLayout emptyScreen;

    private int totalConducted;
    private int totalAttended;
    SubjectAttendanceAdapter subjectAttendanceAdapter;
    ArrayList<SubjectAttendanceModel> subjectModelList;
    ListView subListView;
    public SharedPreferences spref;
    public SharedPreferences.Editor edit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.student_home);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        getSupportActionBar().setTitle("Attendance Manager");

        init();
        loadData();

    }

    private void init() {
        emptyScreen = findViewById(R.id.empty_screen);
        subListView = findViewById(R.id.list_studentSubjects);

        percentView = findViewById(R.id.txt_mainPercent);
        nameView = findViewById(R.id.txt_name);
        RelativeLayout relativeLayout = findViewById(R.id.rel_topArea);

        initialsView = relativeLayout.findViewById(R.id.txt_initials);


        subjectModelList = new ArrayList<>();
    }

    private void loadData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://wizdem.pythonanywhere.com/Attendance/").addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        spref = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        edit=spref.edit();
        Call<WebLecturesAttended> webLecturesAttendedCall = retrofitInterface.studentLectures("Token " + spref.getString("token", null));

        webLecturesAttendedCall.enqueue(new Callback<WebLecturesAttended>() {
            @Override
            public void onResponse(Call<WebLecturesAttended> call, Response<WebLecturesAttended> response) {
                WebLecturesAttended webLecturesAttended = response.body();
                if (webLecturesAttended != null) {
                    if (webLecturesAttended.getAttendance().size() != 0) {
                        for (WebIndividualLectures e : webLecturesAttended.getAttendance()) {
                            subjectModelList.add(new SubjectAttendanceModel(Integer.parseInt(e.getAttended()), Integer.parseInt(e.getTotal()), e.getType(), "" + e.getSubject()));
                        }
                        for (SubjectAttendanceModel sam : subjectModelList) {
                            String type=sam.getLectureType();
                            if(!type.equals("Practical")) {
                                totalConducted += sam.getConducted();
                                totalAttended += sam.getAttended();
                            }
                        }
                        double totalPercent = ((double) totalAttended * 100) / (double) totalConducted;
                        subListView.setVisibility(View.VISIBLE);

//                String predictionText;
//
//                if (totalPercent >= 75.0)
//                    predictionText = "You can\nbunk: " + ((4 * totalAttended - 3 * totalConducted) / 3);
//                else if (totalPercent < 75.0 && totalPercent >= 70.0)
//                    predictionText = "You need to\nattend: " + (3 * totalConducted - 4 * totalAttended);
//                else
//                    predictionText = "You need to\nattend: " + (3 * totalConducted - 4 * totalAttended);

                        percentView.setText(String.format("%.2f", totalPercent) + "%");
                        String str = "" + spref.getString("name", "student student");
                        String[] splitStr = str.split("\\s+");

                        nameView.setText(spref.getString("name", "student student"));

                        initialsView.setText("" + splitStr[0].substring(0, 1) + splitStr[splitStr.length-1].substring(0, 1));

                        subjectAttendanceAdapter = new SubjectAttendanceAdapter(StudentHome.this, subjectModelList);

                        subListView.setAdapter(subjectAttendanceAdapter);
                    } else {
                        emptyScreen.setVisibility(View.GONE);

                    }
                } else {
                    emptyScreen.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<WebLecturesAttended> call, Throwable t) {
                Toast.makeText(StudentHome.this, "" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user_details,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_account) {
            startActivity(new Intent(this, UserActivity.class));
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {

    }
}
