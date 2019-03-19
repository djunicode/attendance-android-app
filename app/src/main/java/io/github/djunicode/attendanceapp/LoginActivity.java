package io.github.djunicode.attendanceapp;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    AutoCompleteTextView mSap,mPassword;
    Button mButton;
   // Login login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mSap = findViewById(R.id.sap_id);
        mPassword = findViewById(R.id.password);
        mButton = findViewById(R.id.button_login);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }





    private void showAlertDialogueForErrors(String message){
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton("ok",null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}