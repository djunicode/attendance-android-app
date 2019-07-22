package io.github.djunicode.attendanceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity implements
        View.OnClickListener,
        ChangePasswordDialogFragment.OnPasswordChange{

    private TextView mInitialsText, mNameText, mSapIDText, mMiscText;
    private Button logoutButton, mChangePassword;

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        sharedPreferences = getApplicationContext().getSharedPreferences("user",MODE_PRIVATE);
        edit=sharedPreferences.edit();
        String name = sharedPreferences.getString("name", "TestName");
        String sapId = sharedPreferences.getString("sap", "60000000001");
        String[] split = name.split("\\s+");
        String initials = ("" + split[0].substring(0,1) + split[1].substring(0,1) + "").toUpperCase();
        String spec = sharedPreferences.getString("specialization", "");


        mInitialsText = findViewById(R.id.text_initials);
        mNameText = findViewById(R.id.text_name);
        mSapIDText = findViewById(R.id.text_sap_id);
        mMiscText = findViewById(R.id.text_misc);
        mChangePassword = findViewById(R.id.change_password);

        logoutButton = findViewById(R.id.button_logout);

        mNameText.setText(name);
        mSapIDText.setText(sapId);
        mInitialsText.setText(initials);
        mMiscText.setText(spec);

        mChangePassword.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.change_password){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ChangePasswordDialogFragment fragment = new ChangePasswordDialogFragment();
            fragment.show(transaction, "password-dialog-fragment");
        } else if (view.getId() == R.id.button_logout) {
            edit.clear();
            edit.commit();
            startActivity(new Intent(UserActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public void onPasswordChange(String oldPassword, String newPassword) {
//        TODO: add New password Code here
    }
}
