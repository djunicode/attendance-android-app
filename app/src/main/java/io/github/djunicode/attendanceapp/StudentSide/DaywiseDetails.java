package io.github.djunicode.attendanceapp.StudentSide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import io.github.djunicode.attendanceapp.R;
import io.github.djunicode.attendanceapp.RetrofitInterface;
import io.github.djunicode.attendanceapp.StudentSide.Adapters.DaywiseDetailsAdapter;
import io.github.djunicode.attendanceapp.StudentSide.Models.DaywiseDetailsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DaywiseDetails extends AppCompatActivity {

    DaywiseDetailsAdapter daywiseDetailsAdapter;
    ListView daywiseDetailsView;
    String type,subject;
    RelativeLayout empty;
    public SharedPreferences spref;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datewise_details);
        daywiseDetailsView = findViewById(R.id.list_daywise_details);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://wizdem.pythonanywhere.com/Attendance/").addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        empty=findViewById(R.id.empty_screen);
        Intent intent = getIntent();
        if (intent != null) {
           type=intent.getStringExtra("type");
           subject=intent.getStringExtra("subject");
        }
        spref = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        Call<ArrayList<DaywiseDetailsModel>>dayCall=retrofitInterface.getDateWiseDetails("Token " + spref.getString("token", null),subject,type);
        dayCall.enqueue(new Callback<ArrayList<DaywiseDetailsModel>>() {
            @Override
            public void onResponse(Call<ArrayList<DaywiseDetailsModel>> call, Response<ArrayList<DaywiseDetailsModel>> response) {
                ArrayList<DaywiseDetailsModel> daywiseDetailsModels=response.body();
                if(daywiseDetailsModels!=null){
                    if(daywiseDetailsModels.size()!=0)
                    {
                        daywiseDetailsAdapter = new DaywiseDetailsAdapter(DaywiseDetails.this, daywiseDetailsModels);
                        daywiseDetailsView.setAdapter(daywiseDetailsAdapter);
                        daywiseDetailsView.setVisibility(View.VISIBLE);

                    }
                    else
                    {
                        empty.setVisibility(View.VISIBLE);

                    }

                }
                else
                {
                    empty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DaywiseDetailsModel>> call, Throwable t) {
                Toast.makeText(DaywiseDetails.this,""+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

}