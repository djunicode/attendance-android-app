package io.github.djunicode.attendanceapp.StudentSide.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.djunicode.attendanceapp.R;
import io.github.djunicode.attendanceapp.StudentSide.DaywiseDetails;
import io.github.djunicode.attendanceapp.StudentSide.Models.SubjectAttendanceModel;

public class SubjectAttendanceAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SubjectAttendanceModel> subjectAttendanceModels;

    public SubjectAttendanceAdapter(Context context, ArrayList<SubjectAttendanceModel> subjectAttendanceModels) {
        this.context = context;
        this.subjectAttendanceModels = subjectAttendanceModels;
    }

    @Override
    public int getCount() {
        return subjectAttendanceModels.size();
    }

    @Override
    public Object getItem(int position) {
        return subjectAttendanceModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final SubjectAttendanceModel sam = subjectAttendanceModels.get(position);

        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("ViewHolder") final View cardView = inflater.inflate(R.layout.student_home_item, parent, false);


        TextView subNameView = cardView.findViewById(R.id.txt_subject);
        TextView percentView = cardView.findViewById(R.id.txt_percent);
        TextView attendedView = cardView.findViewById(R.id.txt_attended);
        TextView outOfView = cardView.findViewById(R.id.txt_outOf);
        TextView predictionView = cardView.findViewById(R.id.txt_need_bunk);
        TextView type = cardView.findViewById(R.id.type);
        double percent = ((double) sam.getAttended() / (double) sam.getConducted()) * 100;
        String predictionText;

        if (percent >= 75.0) {
            predictionView.setTextColor(context.getResources().getColor(R.color.green));
            predictionText = "Can bunk: " + (int)((4*(double)sam.getAttended()-3*(double)sam.getConducted())/3);
        } else if (percent < 75.0 && percent >= 70.0) {
            predictionView.setTextColor(context.getResources().getColor(R.color.yellow));
            predictionText = "Need to attend: " + (3*sam.getConducted()-4*sam.getAttended());
        } else {
            predictionView.setTextColor(context.getResources().getColor(R.color.errorWidgetColor));
            predictionText = "Need to attend: " + (3*sam.getConducted()-4*sam.getAttended());
        }

        predictionView.setText(predictionText);
        subNameView.setText(sam.getName());
        percentView.setText(String.format("%.2f", percent) + "%");
        attendedView.setText("Attended: " + sam.getAttended());
        outOfView.setText("Out Of: " + sam.getConducted());
        type.setText(""+sam.getLectureType());
        cardView.setOnClickListener(v -> {
            Intent toAttendanceDetails=new Intent(cardView.getContext(), DaywiseDetails.class);
            toAttendanceDetails.putExtra("subject",sam.getName());
            toAttendanceDetails.putExtra("type",sam.getLectureType());
            v.getContext().startActivity(toAttendanceDetails);
        });

        return cardView;
    }
}
