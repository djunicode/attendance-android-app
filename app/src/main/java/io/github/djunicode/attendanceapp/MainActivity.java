package io.github.djunicode.attendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        final EditText subjectInput = findViewById(R.id.subject);
        final Spinner divisionSpinner = findViewById(R.id.division);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PickerActivity.class);
                String subject = subjectInput.getText().toString();
                String division = divisionSpinner.getSelectedItem().toString();
                intent.putExtra(Constants.PICKER_ACTIVITY_SUBJECT,subject);
                intent.putExtra(Constants.PICKER_ACTIVITY_DIVISION,division);
                startActivity(intent);
            }
        });
    }
}
