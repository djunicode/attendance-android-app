package io.github.djunicode.attendanceapp.TeacherSide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.github.djunicode.attendanceapp.R;
import io.github.djunicode.attendanceapp.RetrofitInterface;
import io.github.djunicode.attendanceapp.TeacherSide.Adapters.PickerAdapter;
import io.github.djunicode.attendanceapp.TeacherSide.Models.Lecture;
import io.github.djunicode.attendanceapp.TeacherSide.Models.WebSendAttendance;
import io.github.djunicode.attendanceapp.TeacherSide.Models.WebStudents;
import io.github.djunicode.attendanceapp.TeacherSide.Models.WebStudentsList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PickerActivity extends AppCompatActivity implements PickerAdapter.PickerViewHolder.OnMarkedPresent {

    private static final String TAG = "PickerActivity";
    private Toolbar toolbar;
    private SearchView searchView;
    private RecyclerView list;
    private ProgressBar presentPercent;
    private Switch toggleSwitch;
    private ArrayList<WebStudentsList> studentList = new ArrayList<>();
    private PickerAdapter pickerAdapter;
    private int present = 0;
    SharedPreferences spref;
    String batch, subject, startTime, endTime, date, room,type;
    int WebPresent=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);
        spref = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        Intent intent = getIntent();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presentPercent = findViewById(R.id.presentPercent);
        if (intent != null) {
            Lecture lecture = (Lecture) intent.getSerializableExtra("LectureData");
            batch = "" + lecture.getYear() + "_" + lecture.getDivision();
            subject = lecture.getSubjectName();
            startTime = lecture.getTiming().substring(0, 8);
            endTime = lecture.getTiming().substring(11);
            setTitle(lecture.getSubjectName() + ": " + lecture.getDivision());
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            date = sdf.format(Calendar.getInstance().getTime());
            room = lecture.getClassRooomName();
            type=intent.getStringExtra("type");
        }
        if(type.equals("form"))
        {   SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String date = sdf.format(Calendar.getInstance().getTime());
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://wizdem.pythonanywhere.com/Attendance/").addConverterFactory(GsonConverterFactory.create()).build();
            RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
            WebSendAttendance webSendAttendance=new WebSendAttendance(null,subject,batch,room,startTime,endTime,date);
            Call<WebStudents> webStudentsCall = retrofitInterface.formStudentList("Token " + spref.getString("token", null),webSendAttendance);
            webStudentsCall.enqueue(new Callback<WebStudents>() {
                @Override
                public void onResponse(Call<WebStudents> call, Response<WebStudents> response) {
                    WebStudents webStudents = response.body();
                    if (webStudents != null) {
                        for (WebStudentsList e : webStudents.getStudents()) {
                            studentList.add(new WebStudentsList( e.getName(),e.getSapID(), e.getAttendance()));
                            if(e.getAttendance()==1)
                            {
                                WebPresent++;
                            }
                        }
                        present = WebPresent;
                        toolbar.setSubtitle(WebPresent + " out of " + studentList.size() + " present");
                        pickerAdapter = new PickerAdapter(PickerActivity.this, studentList);
                        list = findViewById(R.id.studentList);
                        list.setLayoutManager(new GridLayoutManager(PickerActivity.this, 2));
                        list.setAdapter(pickerAdapter);
                        toggleSwitch = findViewById(R.id.toggleSwitch);
                        toggleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                pickerAdapter.setAllStatus(isChecked);
                                if (isChecked) {
                                    present = studentList.size();
                                    toggleSwitch.setText("Deselect All");
                                } else {
                                    present = 0;
                                    toggleSwitch.setText("Select All");
                                }
                                presentPercent.setProgress(present * 100 / studentList.size());
                                toolbar.setSubtitle(present + " out of " + studentList.size() + " present");
                            }
                        });
                    }
                    else {
                        Toast.makeText(PickerActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<WebStudents> call, Throwable t) {
                    Toast.makeText(PickerActivity.this, "" + t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
        }
        else {
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://wizdem.pythonanywhere.com/Attendance/").addConverterFactory(GsonConverterFactory.create()).build();
            RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
            Call<WebStudents> webStudentsCall = retrofitInterface.studentList("Token " + spref.getString("token", null), subject, batch, date, startTime);
            webStudentsCall.enqueue(new Callback<WebStudents>() {
                @Override
                public void onResponse(Call<WebStudents> call, Response<WebStudents> response) {
                    WebStudents webStudents = response.body();
                    if (webStudents != null) {
                        for (WebStudentsList e : webStudents.getStudents()) {
                            studentList.add(new WebStudentsList(e.getName(), e.getSapID(), e.getAttendance()));
                            if (e.getAttendance() == 1) {
                                WebPresent++;
                            }
                        }
                        present = WebPresent;
                        toolbar.setSubtitle(WebPresent + " out of " + studentList.size() + " present");
                        pickerAdapter = new PickerAdapter(PickerActivity.this, studentList);
                        list = findViewById(R.id.studentList);
                        list.setLayoutManager(new GridLayoutManager(PickerActivity.this, 2));
                        list.setAdapter(pickerAdapter);
                        toggleSwitch = findViewById(R.id.toggleSwitch);
                        toggleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                pickerAdapter.setAllStatus(isChecked);
                                if (isChecked) {
                                    present = studentList.size();
                                    toggleSwitch.setText("Deselect All");
                                } else {
                                    present = 0;
                                    toggleSwitch.setText("Select All");
                                }
                                presentPercent.setProgress(present * 100 / studentList.size());
                                toolbar.setSubtitle(present + " out of " + studentList.size() + " present");
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(PickerActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<WebStudents> call, Throwable t) {
                    Toast.makeText(PickerActivity.this, "" + t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                pickerAdapter.updateList(applySearchResult(""));
                searchView.clearFocus();
                searchView.onActionViewCollapsed();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                pickerAdapter.updateList(applySearchResult(s));
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.submit) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String date = sdf.format(Calendar.getInstance().getTime());
            WebSendAttendance webSendAttendance = new WebSendAttendance(studentList, subject, batch, room, startTime, endTime, date);
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://wizdem.pythonanywhere.com/Attendance/").addConverterFactory(GsonConverterFactory.create()).build();
            RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
            Call<WebSendAttendance> webStudentsListCall = retrofitInterface.sendAttendance("Token " + spref.getString("token", null),webSendAttendance);
            webStudentsListCall.enqueue(new Callback<WebSendAttendance>() {
                @Override
                public void onResponse(Call<WebSendAttendance> call, Response<WebSendAttendance> response) {
                    WebSendAttendance webSendAttendance1 = response.body();
                    if(webSendAttendance1!=null) {
                        Toast.makeText(PickerActivity.this, "Attendance saved successfully", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(PickerActivity.this, "Something went very wrong. Please try again", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<WebSendAttendance> call, Throwable t) {
                    Toast.makeText(PickerActivity.this, "" + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            return true;
        }
        return false;
    }

    public ArrayList<WebStudentsList> applySearchResult(String query) {
        ArrayList<WebStudentsList> resultList = new ArrayList<>();
        for (WebStudentsList student : studentList) {
            query = query.toLowerCase();
            if (student.getName().toLowerCase().contains(query)
                    || student.getSapID().toLowerCase().contains(query))
                resultList.add(student);
        }
        return resultList;
    }

    @Override
    public void onMarkedPresent(boolean isIncreased) {
        if ((isIncreased)) ++present;
        else --present;
        presentPercent.setProgress(present * 100 / studentList.size());
        toolbar.setSubtitle(present + " out of " + studentList.size() + " present");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PickerActivity.this,TeacherHome.class));
        finish();
    }
}
