package io.github.djunicode.attendanceapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.djunicode.attendanceapp.StudentSide.StudentHome;
import io.github.djunicode.attendanceapp.TeacherSide.TeacherHome;

public class MainActivity extends AppCompatActivity {
    SharedPreferences spref;
    private TextView noInterentText;
    private ImageView noInternetImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(haveNetworkConnection()){
            spref=getApplication().getSharedPreferences("user",MODE_PRIVATE);
            if(spref.getString("userType","temp").equals("temp"))
            {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
            }
            else if (spref.getString("userType","temp").equals("teacher"))
            {
                startActivity(new Intent(MainActivity.this, TeacherHome.class));
                finish();
            }
            else
            {
                startActivity(new Intent(MainActivity.this, StudentHome.class));
                finish();
            }
        }
        else {
            setContentView(R.layout.activity_main);
            Button retry=findViewById(R.id.retry);
            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    finish();
                }
            });
        }
    }
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())

                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}