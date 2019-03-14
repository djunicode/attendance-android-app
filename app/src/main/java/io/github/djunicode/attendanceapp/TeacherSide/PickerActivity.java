package io.github.djunicode.attendanceapp.TeacherSide;

import android.content.Intent;
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

import java.util.ArrayList;

import io.github.djunicode.attendanceapp.CommonModels.StudentDetailsModel;
<<<<<<< HEAD
import io.github.djunicode.attendanceapp.Constants;
=======
>>>>>>> yash/development
import io.github.djunicode.attendanceapp.MainActivity;
import io.github.djunicode.attendanceapp.R;
import io.github.djunicode.attendanceapp.TeacherSide.Adapters.PickerAdapter;
import io.github.djunicode.attendanceapp.TeacherSide.Models.Lecture;
import io.github.djunicode.attendanceapp.TeacherSide.Models.Student;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickerActivity extends AppCompatActivity implements PickerAdapter.PickerViewHolder.OnMarkedPresent {

    private static final String TAG = "PickerActivity";
    private Toolbar toolbar;
    private SearchView searchView;
    private RecyclerView list;
    private ProgressBar presentPercent;
    private Switch toggleSwitch;
    private ArrayList<Student> studentList;
    private PickerAdapter pickerAdapter;
    private int present = 0;
<<<<<<< HEAD
    String sem,subject,division;
=======
    String sem, subject, division;
>>>>>>> yash/development

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);
        Intent intent = getIntent();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presentPercent = findViewById(R.id.presentPercent);
        if (intent != null) {
            Lecture lecture = (Lecture) intent.getSerializableExtra("LectureData");
            String subject = lecture.getSubjectName();
            String division = lecture.getDivision();
            setTitle(subject + ": " + division);
        }
        studentList = getDummyList();
        toolbar.setSubtitle("0 out of " + studentList.size() + " present");
        //TODO:GET sem , subject and division values from intent

        Call<ArrayList<StudentDetailsModel>> studentDetailsModelCall = MainActivity.retrofitInterface.studentList(sem, subject, division);
        studentDetailsModelCall.enqueue(new Callback<ArrayList<StudentDetailsModel>>() {
            @Override
            public void onResponse(Call<ArrayList<StudentDetailsModel>> call, Response<ArrayList<StudentDetailsModel>> response) {
                ArrayList<StudentDetailsModel> studentDetailsModelArrayList = response.body();

                //TODO:FRONTEND USE THIS LIST OF STUDENTS
            }

            @Override
            public void onFailure(Call<ArrayList<StudentDetailsModel>> call, Throwable t) {

            }
        });
        pickerAdapter = new PickerAdapter(this, studentList);
        list = findViewById(R.id.studentList);
        list.setLayoutManager(new GridLayoutManager(this, 2));
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

    public ArrayList<Student> getDummyList() {
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student("60004170001", "William\tHodges", false));
        students.add(new Student("60004170002", "Phil\tPaige", false));
        students.add(new Student("60004170003", "Sophie\tRees", false));
        students.add(new Student("60004170004", "Liam\tDyer", false));
        students.add(new Student("60004170005", "Joshua\tRussell", false));
        students.add(new Student("60004170006", "Rachel\tJohnston", false));
        students.add(new Student("60004170007", "Ella\tDickens", false));
        students.add(new Student("60004170008", "Rachel\tHardacre", false));
        students.add(new Student("60004170009", "Isaac\tMackenzie", false));
        students.add(new Student("60004170010", "Carl\tSimpson", false));
        students.add(new Student("60004170011", "Boris\tShort", false));
        students.add(new Student("60004170012", "Madeleine\tManning", false));
        students.add(new Student("60004170013", "Lauren\tRees", false));
        students.add(new Student("60004170014", "Stephanie\tBrown", false));
        students.add(new Student("60004170015", "James\tHamilton", false));
        students.add(new Student("60004170016", "John\tHodges", false));
        students.add(new Student("60004170017", "Bernadette\tBell", false));
        students.add(new Student("60004170018", "Steven\tPaterson", false));
        students.add(new Student("60004170019", "Megan\tWatson", false));
        students.add(new Student("60004170020", "Kimberly\tKing", false));
        students.add(new Student("60004170021", "Caroline\tNorth", false));
        students.add(new Student("60004170022", "Max\tWalker", false));
        students.add(new Student("60004170023", "Michelle\tChapman", false));
        students.add(new Student("60004170024", "Amanda\tNolan", false));
        students.add(new Student("60004170025", "Joe\tButler", false));
        students.add(new Student("60004170026", "Eric\tWalker", false));
        students.add(new Student("60004170027", "Megan\tFisher", false));
        students.add(new Student("60004170028", "Alexandra\tMitchell", false));
        students.add(new Student("60004170029", "Alexandra\tInce", false));
        students.add(new Student("60004170030", "Jason\tNolan", false));
        students.add(new Student("60004170031", "Phil\tMiller", false));
        students.add(new Student("60004170032", "Wanda\tLambert", false));
        students.add(new Student("60004170033", "Eric\tStewart", false));
        students.add(new Student("60004170034", "Angela\tDickens", false));
        students.add(new Student("60004170035", "Jack\tReid", false));
        students.add(new Student("60004170036", "Vanessa\tSpringer", false));
        students.add(new Student("60004170037", "Christopher\tSanderson", false));
        students.add(new Student("60004170038", "Massive Dummy List", false));
        students.add(new Student("60004170039", "I'm getting tired", false));
        students.add(new Student("60004170040", "That's enough", false));
        return students;
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
