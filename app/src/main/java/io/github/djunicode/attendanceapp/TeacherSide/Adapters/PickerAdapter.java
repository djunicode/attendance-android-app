package io.github.djunicode.attendanceapp.TeacherSide.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.djunicode.attendanceapp.R;
import io.github.djunicode.attendanceapp.TeacherSide.Models.WebStudentsList;


public class PickerAdapter extends RecyclerView.Adapter<PickerAdapter.PickerViewHolder>{

    private ArrayList<WebStudentsList> studentList;
    private PickerViewHolder.OnMarkedPresent mOnMarkedPresent;

    public void setAllStatus(boolean status){
        for (WebStudentsList student : studentList) student.setAttendance((status)? 1 : 0);
        notifyDataSetChanged();
    }

    public PickerAdapter(PickerViewHolder.OnMarkedPresent onMarkedPresent, ArrayList<WebStudentsList> studentList) {
        this.studentList = studentList;
        this.mOnMarkedPresent = onMarkedPresent;
    }

    @NonNull
    @Override
    public PickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record,parent,false);
        return new PickerViewHolder(view,mOnMarkedPresent);
    }

    @Override
    public void onBindViewHolder(@NonNull PickerViewHolder holder, int position) {
        WebStudentsList student = studentList.get(position);
        holder.nameTV.setText(student.getName());
        holder.sapidTV.setText(student.getSapID());
        holder.presentStatus.setChecked((student.getAttendance() == 0) ? false : true);
        holder.setStudent(student);
    }

    @Override
    public int getItemCount() {
        return ((studentList != null || studentList.size() != 0) ? studentList.size() : 0);
    }

    public void updateList(ArrayList<WebStudentsList> newList){
        studentList = newList;
        notifyDataSetChanged();
    }

    public static class PickerViewHolder extends RecyclerView.ViewHolder{

        CheckBox presentStatus;
        TextView nameTV;
        TextView sapidTV;
        View.OnClickListener listener;
        WebStudentsList student;
        public interface OnMarkedPresent{
            void onMarkedPresent(boolean isIncreased);
        }
        OnMarkedPresent onMarkedPresent;

        public PickerViewHolder(final View view, final OnMarkedPresent onMarkedPresent){
            super(view);
            this.nameTV = view.findViewById(R.id.name_tv);
            this.sapidTV = view.findViewById(R.id.sapid_tv);
            this.presentStatus = view.findViewById(R.id.checkBox);
            this.listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v != presentStatus) presentStatus.toggle();
                    student.setAttendance((presentStatus.isChecked())? 1 : 0);
                    onMarkedPresent.onMarkedPresent(presentStatus.isChecked());
                }
            };
            view.setOnClickListener(listener);
            presentStatus.setOnClickListener(listener);
        }

        public void setStudent(WebStudentsList student){
            this.student = student;
        }
    }
}
