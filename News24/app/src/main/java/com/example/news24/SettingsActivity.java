package com.example.news24;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.prefs.Preferences;

public class SettingsActivity extends AppCompatActivity {

    private Switch notificationsSwitch;
    DatabaseHelper db;
    SharedPreferences sharedPreferences;
    private RadioButton smallFont;
    private RadioButton mediumFont;
    private RadioButton largeFont;
    private RadioGroup fontGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
      //  getTheme().applyStyle(new Preferences(this).getFontStyle().getResId(), true);

        notificationsSwitch = findViewById(R.id.notificationSwitch);
        smallFont = findViewById(R.id.radioButtonFontSmall);
        mediumFont  = findViewById(R.id.radioButtonFontMedium);
        largeFont  = findViewById(R.id.radioButtonFontLarge);
        fontGroup = findViewById(R.id.radioGroupFont);

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




        //RadioButton checkedRadioButton = (RadioButton)fontGroup.findViewById(fontGroup.getCheckedRadioButtonId());
        fontGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                String size = "Medium";
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    size = checkedRadioButton.getText().toString();
                    Toast.makeText(getApplicationContext(),  checkedRadioButton.getText(), Toast.LENGTH_SHORT).show();
                    adjustFontScale(getResources().getConfiguration(),size);
                }
            }
        });
    }

    public void adjustFontScale(Configuration configuration, String size)
    {
        if(size.equals("Small")){
            configuration.fontScale = (float) 0.8;
        }else if(size.equals("Large")){
            configuration.fontScale = (float) 1.1;
        }else{
            configuration.fontScale = (float) 1.0;
        }

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }

}
