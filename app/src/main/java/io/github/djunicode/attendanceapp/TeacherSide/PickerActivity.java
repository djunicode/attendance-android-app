package io.github.djunicode.attendanceapp.TeacherSide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

import io.github.djunicode.attendanceapp.CommonModels.StudentDetailsModel;

import io.github.djunicode.attendanceapp.MainActivity;
import io.github.djunicode.attendanceapp.R;
import io.github.djunicode.attendanceapp.RetrofitInterface;
import io.github.djunicode.attendanceapp.TeacherSide.Adapters.PickerAdapter;
import io.github.djunicode.attendanceapp.TeacherSide.Models.Lecture;
import io.github.djunicode.attendanceapp.TeacherSide.Models.Student;
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
    private ArrayList<Student> studentList=new ArrayList<>();
    private PickerAdapter pickerAdapter;
    private int present = 0;
    SharedPreferences spref;

    String batch, subject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);
        spref = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        Intent intent = getIntent();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presentPercent = findViewById(R.id.presentPercent);
        if (intent != null) {
            Lecture lecture = (Lecture) intent.getSerializableExtra("LectureData");
             batch=""+lecture.getYear()+"_"+lecture.getDivision();
             subject=lecture.getSubjectName();
            setTitle(lecture.getSubjectName() + ": " + lecture.getDivision());
        }
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://wizdem.pythonanywhere.com/Attendance/").addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
Call<WebStudents> webStudentsCall=retrofitInterface.studentList("Token " + spref.getString("token", null),subject,batch);
webStudentsCall.enqueue(new Callback<WebStudents>() {
    @Override
    public void onResponse(Call<WebStudents> call, Response<WebStudents> response) {
        WebStudents webStudents = response.body();
        if (webStudents != null) {
            for (WebStudentsList e : webStudents.getStudents()) {
                studentList.add(new Student(e.getSapID(), e.getName(), false));
            }
            toolbar.setSubtitle("0 out of " + studentList.size() + " present");
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
    }

    @Override
    public void onFailure(Call<WebStudents> call, Throwable t) {
        Toast.makeText(PickerActivity.this,""+t.getMessage(),Toast.LENGTH_LONG).show();
        System.out.println(""+t.getMessage());
    }
});



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



    public ArrayList<Student> applySearchResult(String query) {
        ArrayList<Student> resultList = new ArrayList<>();
        for (Student student : studentList) {
            query = query.toLowerCase();
            if (student.getStudentName().toLowerCase().contains(query)
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

}
