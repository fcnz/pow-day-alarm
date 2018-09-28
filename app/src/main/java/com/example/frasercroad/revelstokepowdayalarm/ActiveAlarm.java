package com.example.frasercroad.revelstokepowdayalarm;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActiveAlarm extends AppCompatActivity {

    private static Ringtone _ringtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_alarm);

        if (_ringtone == null) {
            // Fetch and play alarm
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            _ringtone = RingtoneManager.getRingtone(ActiveAlarm.this, uri);
        }
        if (!_ringtone.isPlaying()) {
            _ringtone.play();
        }

        Button dismissButton = findViewById(R.id.dismissButton);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _ringtone.stop();

                Intent intent = new Intent(ActiveAlarm.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
