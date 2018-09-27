package com.example.frasercroad.revelstokepowdayalarm;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up alarm tone
        Uri alarmTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        final Ringtone alarmRingtone = RingtoneManager.getRingtone(getApplicationContext(), alarmTone);

        // Set up alarm on toggle button
        final ToggleButton alarmOnToggle = findViewById(R.id.alarmOnToggle);
        alarmOnToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isNowOn) {
                if (isNowOn) {
                    alarmRingtone.play();
                } else {
                    alarmRingtone.stop();
                }
            }
        });
    }
}
