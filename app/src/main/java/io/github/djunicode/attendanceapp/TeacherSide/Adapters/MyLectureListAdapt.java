package io.github.djunicode.attendanceapp.TeacherSide.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.djunicode.attendanceapp.R;
import io.github.djunicode.attendanceapp.TeacherSide.Models.Lecture;
import io.github.djunicode.attendanceapp.TeacherSide.PickerActivity;

public class MyLectureListAdapt extends BaseAdapter{
    private Context context;
    private ArrayList<Lecture> lectureList;

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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Lecture lo = lectureList.get(i);
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("ViewHolder") View relativeLayoutItem = inflater.inflate(R.layout.lecture_item, viewGroup, false);
        ImageView attendanceTaken=relativeLayoutItem.findViewById(R.id.img_attendanceTaken);
        TextView subNameView = relativeLayoutItem.findViewById(R.id.txt_subName);
        TextView divisionTextView = relativeLayoutItem.findViewById(R.id.txt_class);
        TextView timingView = relativeLayoutItem.findViewById(R.id.txt_timing);
        TextView classRoomView = relativeLayoutItem.findViewById(R.id.txt_classRoom);
        Button takeAttendance = relativeLayoutItem.findViewById(R.id.btn_takeAttendance);

        if(lo.getAttendanceTaken()==1)
        {
            attendanceTaken.setVisibility(View.VISIBLE);
        }

        takeAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inflater.getContext(), PickerActivity.class);
                intent.putExtra("LectureData", lectureList.get(i));
                context.startActivity(intent);
            }
        });

        String divString = lo.getYear() + " " + lo.getDivision();
        subNameView.setText(lo.getSubjectName());
        divisionTextView.setText(divString);
        timingView.setText(lo.getTiming());
        classRoomView.setText(lo.getClassRooomName());

        return relativeLayoutItem;
    }
}
