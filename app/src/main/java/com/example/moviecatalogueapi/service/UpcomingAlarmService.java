package com.example.moviecatalogueapi.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.model.Movie;

import java.util.Calendar;
import java.util.List;

public class UpcomingAlarmService extends BroadcastReceiver {

    private final int NOTIFICATION_UPCOMING = 100;

    public UpcomingAlarmService() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String message = context.getString(R.string.message_daily_upcoming);
        showAlarmNotification(context, title, message);
    }

    private void showAlarmNotification(Context context, String title, String message) {
        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "AlarmManagerUpcoming channel";

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_local_movies_black_24dp)
                .setContentTitle(title)
                .setContentText(title + " " + message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(NOTIFICATION_UPCOMING, notification);
        }

    }

    public void setRepeatingAlarm(Context context, List<Movie> movies) {

        int delayTime = 0;

        for (Movie movie : movies) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, DailyAlarmService.class);
            intent.putExtra("title", movie.getTitle());

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_UPCOMING, intent, 0);
            if (alarmManager != null) {
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + delayTime, AlarmManager.INTERVAL_DAY, pendingIntent);
            }

            delayTime += 10000;
        }

    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, UpcomingAlarmService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_UPCOMING, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

}
