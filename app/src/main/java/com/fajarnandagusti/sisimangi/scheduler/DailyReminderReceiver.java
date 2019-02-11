package com.fajarnandagusti.sisimangi.scheduler;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.fajarnandagusti.sisimangi.MainActivity;
import com.fajarnandagusti.sisimangi.R;

import java.util.Calendar;

import static com.fajarnandagusti.sisimangi.util.Utility.EXTRA_MESSAGE_PREF;
import static com.fajarnandagusti.sisimangi.util.Utility.EXTRA_MESSAGE_PREF2;
import static com.fajarnandagusti.sisimangi.util.Utility.EXTRA_TYPE_PREF;
import static com.fajarnandagusti.sisimangi.util.Utility.NOTIFICATION_ID;

/**
 * Created by Gustiawan on 2/4/2019.
 */

public class DailyReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        showNotification(context, context.getResources().getString(R.string.msgDailyRemain),
                intent.getStringExtra(EXTRA_MESSAGE_PREF),NOTIFICATION_ID);

        showNotification2(context, context.getResources().getString(R.string.msgDailyRemainSore),
                intent.getStringExtra(EXTRA_MESSAGE_PREF2),NOTIFICATION_ID);

    }

    private void showNotification(Context context, String string, String stringExtra, int notificationId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri uriAlarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = TaskStackBuilder.create(context)
                .addNextIntent(intent)
                .getPendingIntent(NOTIFICATION_ID,PendingIntent.FLAG_UPDATE_CURRENT);

        Vibrator v;
        v=(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(3000);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, stringExtra)
                .setSmallIcon(R.drawable.ic_notifications_none_black_24dp)
                .setContentTitle(string)
                .setContentText(stringExtra)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setSound(uriAlarmSound)
                .setAutoCancel(true);

        notificationManager.notify(notificationId,builder.build());
    }

    public void setAlarm(Context context, String type, String time, String message) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminderReceiver.class); //Tap to detail
        intent.putExtra(EXTRA_MESSAGE_PREF,message);
        intent.putExtra(EXTRA_TYPE_PREF,type);
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND,0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,NOTIFICATION_ID,intent,0);
        assert alarmManager != null;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                                         AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(context, R.string.onReminder, Toast.LENGTH_SHORT).show();
    }


    private void showNotification2(Context context, String string, String stringExtra, int notificationId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri uriAlarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = TaskStackBuilder.create(context)
                .addNextIntent(intent)
                .getPendingIntent(NOTIFICATION_ID,PendingIntent.FLAG_UPDATE_CURRENT);

        Vibrator v;
        v=(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(3000);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, stringExtra)
                .setSmallIcon(R.drawable.ic_notifications_none_black_24dp)
                .setContentTitle(string)
                .setContentText(stringExtra)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setSound(uriAlarmSound)
                .setAutoCancel(true);

        notificationManager.notify(notificationId,builder.build());
    }


    public void setAlarm2(Context context, String type, String time2, String message2) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminderReceiver.class); //Tap to detail
        intent.putExtra(EXTRA_MESSAGE_PREF,message2);
        intent.putExtra(EXTRA_TYPE_PREF,type);
        String timeArray[] = time2.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND,0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,NOTIFICATION_ID,intent,0);
        assert alarmManager != null;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(context, R.string.onReminder, Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,NOTIFICATION_ID,intent,0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context, R.string.offReminder, Toast.LENGTH_SHORT).show();
    }

}
