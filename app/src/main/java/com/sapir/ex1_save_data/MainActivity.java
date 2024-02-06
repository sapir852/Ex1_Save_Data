package com.sapir.ex1_save_data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    MaterialButton main_BTN_login;
    EditText main_EDT_password;
    private BatteryManager batteryManage ;

    private boolean isBatteryOK;
    private boolean isWifi;
    private boolean isVibrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initSystemService();
        initButton();
        
        
    }

    private void initButton() {
        main_BTN_login.setOnClickListener(v -> {

            String password = main_EDT_password.getText().toString();

            if(password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Password Enter Password", Toast.LENGTH_SHORT).show();
            }else{
                getBatteryPercentage(password);
                isWifiOn();
                isVibrateOn();

            } if(isBatteryOK && isWifi && isVibrate ){
                Toast.makeText(MainActivity.this, "Login Succeeded", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }


        });
    }

    private void isVibrateOn(){
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        if(am.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE){
            Toast.makeText(MainActivity.this, "Vibrate is On", Toast.LENGTH_SHORT).show();
            isVibrate=true;

        }
        else{
            isVibrate=false;
        }
    }
    private void isWifiOn(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm == null){
            isWifi = false;
            return;
        }
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isWifi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
        if(isWifi==true) {
            Toast.makeText(MainActivity.this, "WIFI is On", Toast.LENGTH_SHORT).show();
        }
    }
    private void getBatteryPercentage(String password) {
        int battery = batteryManage.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        int pass = Integer.parseInt(password);

        //if password is bigger than battery user can enter
        if(pass > battery){
            isBatteryOK = true;
            Toast.makeText(MainActivity.this, "Password is Success!", Toast.LENGTH_SHORT).show();
        }else{
            isBatteryOK= false;
        }
    }


    private void initSystemService() {
        batteryManage = (BatteryManager) getSystemService(BATTERY_SERVICE);
    }

    private void findViews() {
        main_BTN_login = findViewById(R.id.main_BTN_login);
        main_EDT_password = findViewById(R.id.main_EDT_password);
    }
}