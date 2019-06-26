package com.example.news24;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    private Switch notificationsSwitch;
    DatabaseHelper db;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        notificationsSwitch = findViewById(R.id.notificationSwitch);

        sharedPreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        db = new DatabaseHelper(this);

        boolean notifications = db.notificationsOn(sharedPreferences.getString("username",""));
        notificationsSwitch.setChecked(notifications);
		
		//System.out.println("TESTIRAM NESTO SAMO");

        notificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(notificationsSwitch.isChecked()){
                    db.changeNotificationValue(true, sharedPreferences.getString("username",""));
                }
                else{
                    db.changeNotificationValue(false, sharedPreferences.getString("username",""));
                }
            }
        });
    }
}
