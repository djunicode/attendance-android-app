package io.github.djunicode.attendanceapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    AutoCompleteTextView mSap,mPassword;
    Button mButton;
    String sap,password;
    SharedPreferences spref;
    SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spref=getApplicationContext().getSharedPreferences("userType",MODE_PRIVATE);
        edit=spref.edit();
        mSap = findViewById(R.id.sap_id);
        mPassword = findViewById(R.id.password);
        mButton = findViewById(R.id.button_login);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sap=mSap.getText().toString();
                password=mPassword.getText().toString();
                //Todo:Check password with sap id and store in SharedPreferrences
                if(sap.equals("teacher"))
                {
                   edit.putString("userType",sap);
                   edit.commit();
                   startActivity(new Intent(LoginActivity.this,MainActivity.class));
                   finish();
                }
                else if (sap.equals("student"))
                {
                    edit.putString("userType",sap);
                    edit.commit();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Wrong credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}