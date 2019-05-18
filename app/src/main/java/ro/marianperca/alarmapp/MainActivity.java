package ro.marianperca.alarmapp;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static String CHANNEL_ALARMS_ID = "CHANNEL_ALARMS";
    public static String CHANNEL_RIDDLES_ID = "CHANNEL_RIDDLES";

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private PendingIntent riddleIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        createNotificationRiddleChannel();

        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        findViewById(R.id.startAlarm).setOnClickListener(this);
        findViewById(R.id.startRepeatingAlarm).setOnClickListener(this);

        findViewById(R.id.stopRepeatingAlarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alarmMgr != null && riddleIntent != null) {
                    alarmMgr.cancel(riddleIntent);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startAlarm:
                startAlarm();
                break;
            case R.id.startRepeatingAlarm:
                startRiddles();
                break;
        }
    }

    private void startAlarm() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 5 * 1000, alarmIntent);

        Toast.makeText(this, "Alarma va porni in 5 sec.", Toast.LENGTH_LONG).show();
    }

    private void startRiddles() {
        Intent intent = new Intent(this, RiddleReceiver.class);
        riddleIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 5);

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                60 * 1000, // intervalul minim este 1 min
                riddleIntent);

        Toast.makeText(this, "Alarma va porni in 5 sec.", Toast.LENGTH_LONG).show();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ALARMS_ID,
                    "Alarms",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Display alarms");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createNotificationRiddleChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_RIDDLES_ID,
                    "Riddles",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Display riddles");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
