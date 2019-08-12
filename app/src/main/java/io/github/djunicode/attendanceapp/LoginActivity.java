package io.github.djunicode.attendanceapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import io.github.djunicode.attendanceapp.StudentSide.StudentHome;
import io.github.djunicode.attendanceapp.TeacherSide.TeacherHome;
import io.github.djunicode.attendanceapp.TeacherSide.TokenRequest;
import io.github.djunicode.attendanceapp.TeacherSide.TokenResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText mSap,mPassword;
    Button mButton;
    String sap,password;
    SharedPreferences spref;
    SharedPreferences.Editor edit;
    RetrofitInterface retrofitInterface;
    ProgressBar mProgressBar;
    ConstraintLayout mForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spref=getApplicationContext().getSharedPreferences("user",MODE_PRIVATE);
        edit=spref.edit();
        mSap = findViewById(R.id.sap_id);
        mPassword = findViewById(R.id.password);
        mButton = findViewById(R.id.button_login);
        mProgressBar = findViewById(R.id.progress_circular);
        mForm = findViewById(R.id.login_form);
        mProgressBar.setVisibility(View.INVISIBLE);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sap=mSap.getText().toString();
                password=mPassword.getText().toString();
                mButton.setVisibility(View.INVISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
               Retrofit retrofit=new Retrofit.Builder().baseUrl("https://wizdem.pythonanywhere.com/Attendance/").addConverterFactory(GsonConverterFactory.create()).build();
                RetrofitInterface retrofitInterface =retrofit.create(RetrofitInterface.class);
                final TokenRequest tokenRequest=new TokenRequest(""+mSap.getText().toString(),""+mPassword.getText().toString());
                Call<TokenResponse> tokenResponseCall=retrofitInterface.login(tokenRequest);
                tokenResponseCall.enqueue(new Callback<TokenResponse>() {
                    @Override
                    public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                        TokenResponse tokenResponse=response.body();
                        if (tokenResponse != null) {
                            edit.putString("name",tokenResponse.getUser().getName());
                            edit.putString("sap",tokenResponse.getUser().getSapID());
                            edit.putString("token",tokenResponse.getToken());
                            edit.commit();
                            if(tokenResponse.getTeacher())
                            {

                                edit.putString("userType","teacher");
                                edit.putString("specialization",tokenResponse.getUser().getSpecialization());
                                edit.commit();
                                startActivity(new Intent(LoginActivity.this,TeacherHome.class));
                                finish();
                            }
                            else
                            {
                                edit.putString("userType","student");
                                edit.commit();
                                startActivity(new Intent(LoginActivity.this, StudentHome.class));
                                finish();
                            }


                        } else {
                            mProgressBar.setVisibility(View.INVISIBLE);
                            mButton.setVisibility(View.VISIBLE);
                            mSap.setText("");
                            mPassword.setText("");
                            Toast.makeText(LoginActivity.this,"SAP Id or password incorrect. Please try again",Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<TokenResponse> call, Throwable t) {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        mButton.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this,""+t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}