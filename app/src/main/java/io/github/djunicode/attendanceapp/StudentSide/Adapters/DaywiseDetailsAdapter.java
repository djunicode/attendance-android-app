package io.github.djunicode.attendanceapp.StudentSide.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.github.djunicode.attendanceapp.R;
import io.github.djunicode.attendanceapp.StudentSide.Models.DaywiseDetailsModel;

public class DaywiseDetailsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<DaywiseDetailsModel> daywiseDetailsModelArrayList;

    public DaywiseDetailsAdapter(Context context, ArrayList<DaywiseDetailsModel> daywiseDetailsModelArrayList) {
        this.context = context;
        this.daywiseDetailsModelArrayList = daywiseDetailsModelArrayList;
    }

    @Override
    public int getCount() {
        return daywiseDetailsModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return daywiseDetailsModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DaywiseDetailsModel dam = daywiseDetailsModelArrayList.get(position);

        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("ViewHolder") View item = inflater.inflate(R.layout.dateitem, parent, false);


        TextView dateView = item.findViewById(R.id.txt_date);
        TextView timeView = item.findViewById(R.id.txt_time);
        TextView statusView = item.findViewById(R.id.txt_status);

        if (dam.getPresent()==1) {
            statusView.setTextColor(context.getResources().getColor(R.color.green));
            statusView.setText("P");
        } else {
            statusView.setTextColor(context.getResources().getColor(R.color.errorWidgetColor));
            statusView.setText("A");
        }
        SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = inFormat.parse(dam.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
        String day = outFormat.format(date);
        dateView.setText(day +", "+dam.getDate());
        timeView.setText(""+dam.getTime().substring(0,5)+dam.getTime().substring(8,12)+dam.getTime().substring(12,16));

        return item;
    }
}