package com.example.frasercroad.revelstokepowdayalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    final long ONE_DAY = 1000 * 60 * 60 * 24;

    private static AlarmManager _alarmManager;
    private static PendingIntent _pendingIntent;
    private static Boolean _alarmIsSet = false;

    private static int _snowfallThreshold = 5;
    private static int _hour;
    private static int _minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up time input
        final EditText timeInput = findViewById(R.id.timeInput);
        timeInput.setText(_hour + ":" + _minute);
        timeInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String value = editable.toString();
                String[] parts = value.split(":");
                if (parts.length == 2) {
                    _hour = Integer.parseInt(parts[0]);
                    _minute = Integer.parseInt(parts[1]);

                    if (_alarmIsSet) setAlarm();
                }
            }
        });

        // Set up snowfall threshold input
        final EditText snowfallInput = findViewById(R.id.snowfallInput);
        snowfallInput.setText(Integer.toString(_snowfallThreshold));
        snowfallInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String value = editable.toString();
                int threshold = Integer.parseInt(value.length() > 0 ? value : "0");
                _snowfallThreshold = threshold;
                if (_alarmIsSet) setAlarm();
            }
        });

        // Set up alarm on toggle button
        final ToggleButton alarmOnToggle = findViewById(R.id.alarmOnToggle);
        alarmOnToggle.setChecked(_alarmIsSet);
        alarmOnToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isNowOn) {
                if (isNowOn) {
                    setAlarm();
                } else {
                    unsetAlarm();
                }
            }
        });
    }

    private void setAlarm() {
        if (_alarmIsSet) _alarmManager.cancel(_pendingIntent);

        _alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, ActiveAlarm.class);
        _pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);

        Calendar alarmTime = Calendar.getInstance();
        alarmTime.set(Calendar.HOUR_OF_DAY, _hour);
        alarmTime.set(Calendar.MINUTE, _minute);
        alarmTime.set(Calendar.SECOND, 0);
        long startTime = alarmTime.getTimeInMillis();
        _alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, ONE_DAY, _pendingIntent);

        _alarmIsSet = true;
    }

    private void unsetAlarm() {
        if (_alarmIsSet) {
            _alarmManager.cancel(_pendingIntent);
        }

        _alarmIsSet = false;
    }
}
