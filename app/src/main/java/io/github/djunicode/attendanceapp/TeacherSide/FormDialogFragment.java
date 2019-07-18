package io.github.djunicode.attendanceapp.TeacherSide;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import io.github.djunicode.attendanceapp.R;

/**
 * Created by Jugal Mistry on 7/18/2019.
 */
public class FormDialogFragment extends DialogFragment implements
        AppCompatSpinner.OnItemSelectedListener,
        View.OnClickListener {

    private AppCompatSpinner yearSelect, subjectSelect, divisionSelect;
    private TextInputEditText startTime, endTime, roomNumber;
    private ImageButton saveDetails;


    public interface OnDetailsSaved{
        void onDetailsSaved(String year, String subject, String startTime, String endTime);
    }

    private OnDetailsSaved mOnDetailsSaved;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_form_dialog, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        yearSelect = view.findViewById(R.id.year_select);
        subjectSelect = view.findViewById(R.id.subject_select);
//        subjectSelect.setEnabled(false);
        divisionSelect = view.findViewById(R.id.division_select);
//        divisionSelect.setEnabled(false);

        startTime = view.findViewById(R.id.start_time_input);
        startTime.setClickable(false);
        startTime.setFocusable(false);
        startTime.setOnClickListener(this);
        endTime = view.findViewById(R.id.end_time_input);
        endTime.setClickable(false);
        endTime.setFocusable(false);
        endTime.setOnClickListener(this);

        roomNumber = view.findViewById(R.id.room_input);
//        roomNumber.setClickable(false);
//        roomNumber.setFocusable(false);
        roomNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent) {
                if(actionID == EditorInfo.IME_ACTION_DONE) {
                    startTime.setClickable(true);
                    startTime.setFocusable(true);
                    startTime.setOnClickListener(FormDialogFragment.this);
                    return true;
                }
                return false;
            }
        });

        saveDetails = view.findViewById(R.id.save_details);
        saveDetails.setEnabled(false);
        saveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnDetailsSaved.onDetailsSaved(yearSelect.getSelectedItem().toString(),
                        subjectSelect.getSelectedItem().toString(),
                        startTime.getText().toString(),
                        endTime.getText().toString());

                FormDialogFragment.this.dismiss();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnDetailsSaved = (OnDetailsSaved) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (adapterView.getId() == R.id.year_select)
            divisionSelect.setEnabled(true);
        else if (adapterView.getId() == R.id.division_select)
            subjectSelect.setEnabled(true);
        else if (adapterView.getId() == R.id. subject_select) {
            roomNumber.setClickable(true);
            roomNumber.setFocusable(false);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(final View view) {
        if (view.getId() == R.id.start_time_input || view.getId() == R.id.end_time_input) {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
                    ((TextInputEditText) view).setText(hour + ":" +minutes);
                }
            };
            TimePickerDialog dialog = new TimePickerDialog(getContext(), timeSetListener, hour, minute, true);
            dialog.setTitle("Select Time");
            dialog.show();

            if (view.getId() == R.id.start_time_input) {
                endTime.setFocusable(true);
                endTime.setClickable(true);
                endTime.setOnClickListener(FormDialogFragment.this);
            } else
                saveDetails.setEnabled(true);
        }
    }
}
