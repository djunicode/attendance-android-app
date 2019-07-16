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

import io.github.djunicode.attendanceapp.StudentSide.StudentHome;
import io.github.djunicode.attendanceapp.TeacherSide.TeacherHome;
import io.github.djunicode.attendanceapp.TeacherSide.TokenRequest;
import io.github.djunicode.attendanceapp.TeacherSide.TokenResponse;
import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    AutoCompleteTextView mSap,mPassword;
    Button mButton;
    String sap,password;
    SharedPreferences spref;
    SharedPreferences.Editor edit;
    RetrofitInterface retrofitInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spref=getApplicationContext().getSharedPreferences("user",MODE_PRIVATE);
        edit=spref.edit();
        mSap = findViewById(R.id.sap_id);
        mPassword = findViewById(R.id.password);
        mButton = findViewById(R.id.button_login);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sap=mSap.getText().toString();
                password=mPassword.getText().toString();

               Retrofit retrofit=new Retrofit.Builder().baseUrl("https://wizdem.pythonanywhere.com/Attendance/").addConverterFactory(GsonConverterFactory.create()).build();
                RetrofitInterface retrofitInterface =retrofit.create(RetrofitInterface.class);
                final TokenRequest tokenRequest=new TokenRequest(""+mSap.getText().toString(),""+mPassword.getText().toString());
                Call<TokenResponse> tokenResponseCall=retrofitInterface.login(tokenRequest);
                tokenResponseCall.enqueue(new Callback<TokenResponse>() {
                    @Override
                    public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                        TokenResponse tokenResponse=response.body();
                        HttpUrl url=response.raw().request().url();
                        if (tokenResponse != null) {
                            edit.putString("name",tokenResponse.getUser().getName());
                            edit.putString("sapId",tokenResponse.getUser().getSapID());
                            edit.putString("token",tokenResponse.getToken());
                            if(tokenResponse.getStudent())
                            {
                                edit.putString("type","student");
                                edit.commit();
                                startActivity(new Intent(LoginActivity.this, StudentHome.class));
                            }
                            else
                            {
                                edit.putString("type","teacher");
                                edit.commit();
                                startActivity(new Intent(LoginActivity.this,TeacherHome.class));
                            }


                        }
                        else
                        {
                            mSap.setText("");
                            mPassword.setText("");
                            Toast.makeText(LoginActivity.this,"SAP Id or password incorrect. Please try again",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TokenResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this,""+t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }
}