
package io.github.djunicode.attendanceapp.TeacherSide.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.github.djunicode.attendanceapp.R;
import io.github.djunicode.attendanceapp.RetrofitInterface;
import io.github.djunicode.attendanceapp.TeacherSide.Models.Lecture;
import io.github.djunicode.attendanceapp.TeacherSide.Models.WebSendAttendance;
import io.github.djunicode.attendanceapp.TeacherSide.Models.deleteResponse;
import io.github.djunicode.attendanceapp.TeacherSide.PickerActivity;
import io.github.djunicode.attendanceapp.TeacherSide.TeacherHome;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class MyLectureListAdapt extends BaseAdapter{
    private Context context;
    private ArrayList<Lecture> lectureList;
    private SharedPreferences spref;

    public MyLectureListAdapt(Context context, ArrayList<Lecture> lectureList) {
        this.context = context;
        this.lectureList = lectureList;
    }

    @Override
    public int getCount() {
        return lectureList.size();
    }

    @Override
    public Object getItem(int i) {
        return lectureList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, final View view, final ViewGroup viewGroup) {
        Lecture lo = lectureList.get(i);
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        spref = viewGroup.getContext().getSharedPreferences("user", MODE_PRIVATE);

        @SuppressLint("ViewHolder") final View relativeLayoutItem = inflater.inflate(R.layout.lecture_item, viewGroup, false);
        ImageView attendanceTaken=relativeLayoutItem.findViewById(R.id.img_attendanceTaken);
        TextView subNameView = relativeLayoutItem.findViewById(R.id.txt_subName);
        TextView divisionTextView = relativeLayoutItem.findViewById(R.id.txt_class);
        TextView timingView = relativeLayoutItem.findViewById(R.id.txt_timing);
        TextView classRoomView = relativeLayoutItem.findViewById(R.id.txt_classRoom);
        TextView type=relativeLayoutItem.findViewById(R.id.type);
        final Button takeAttendance = relativeLayoutItem.findViewById(R.id.btn_takeAttendance);
        final Button cancelAttendance=relativeLayoutItem.findViewById(R.id.btn_cancel);
        final ProgressBar cancelPbar=relativeLayoutItem.findViewById(R.id.pbar_cancel);
        final ProgressBar takePbar=relativeLayoutItem.findViewById(R.id.pbar_take);
        if(lo.getAttendanceTaken()==1)
        {
            attendanceTaken.setVisibility(View.VISIBLE);
        }
        cancelAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAttendance.setVisibility(View.INVISIBLE);
                cancelPbar.setVisibility(View.VISIBLE);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String date = sdf.format(Calendar.getInstance().getTime());
                WebSendAttendance webSendAttendance=new WebSendAttendance(null,lectureList.get(i).getSubjectName(),
                        lectureList.get(i).getYear()+"_"+lectureList.get(i).getDivision(),
                        lectureList.get(i).getClassRooomName(),
                        lectureList.get(i).getTiming().substring(0,8),lectureList.get(i).getTiming().substring(11),date);
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://wizdem.pythonanywhere.com/Attendance/").addConverterFactory(GsonConverterFactory.create()).build();
                RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
                Call<deleteResponse> checkDelete=retrofitInterface.deleteLecture("Token " + spref.getString("token", null),webSendAttendance);
                checkDelete.enqueue(new Callback<deleteResponse>() {
                    @Override
                    public void onResponse(Call<deleteResponse> call, Response<deleteResponse> response) {
                        deleteResponse response1=response.body();
                        if(response1.getSuccess()==0)
                        {
                            Toast.makeText(view.getContext(),"No such lecture exists",Toast.LENGTH_LONG).show();
                            cancelPbar.setVisibility(View.INVISIBLE);

                        }
                        else
                        {
                            Toast.makeText(view.getContext(),"Lecture deleted successfully",Toast.LENGTH_LONG).show();
                            cancelPbar.setVisibility(View.INVISIBLE);
                            view.getContext().startActivity(new Intent(view.getContext(), TeacherHome.class));
                            try {
                                ((TeacherHome)context).finish();
                            } catch (ClassCastException e){
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<deleteResponse> call, Throwable t) {
                        Toast.makeText(view.getContext(),""+t.getMessage(),Toast.LENGTH_LONG).show();
                        cancelAttendance.setVisibility(View.VISIBLE);
                        cancelPbar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });

        takeAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAttendance.setVisibility(View.INVISIBLE);
                takePbar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(inflater.getContext(), PickerActivity.class);
                intent.putExtra("LectureData", lectureList.get(i));
                intent.putExtra("type","list");
                context.startActivity(intent);
                try {
                    ((TeacherHome)context).finish();
                } catch (ClassCastException e){
                    e.printStackTrace();
                }
            }
        });

        String divString = lo.getYear() + " " + lo.getDivision();
        subNameView.setText(lo.getSubjectName());
        divisionTextView.setText(divString);
        timingView.setText(lo.getTiming().substring(0,5)+" - "+lo.getTiming().substring(11,16));
        classRoomView.setText(lo.getClassRooomName());
        if(lo.getPredicted()==0) {
            type.setText(lo.getType()+" (New)");
        }
        else
        {
            type.setText(lo.getType()+" (Predicted)");
        }
        return relativeLayoutItem;
    }
}