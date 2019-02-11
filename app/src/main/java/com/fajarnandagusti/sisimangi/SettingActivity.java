package com.fajarnandagusti.sisimangi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import com.fajarnandagusti.sisimangi.scheduler.DailyReminderReceiver;
import com.fajarnandagusti.sisimangi.scheduler.ReminderPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

import static com.fajarnandagusti.sisimangi.util.Utility.KEY_FIELD_DAILY_REMINDER;
import static com.fajarnandagusti.sisimangi.util.Utility.KEY_HEADER_DAILY_REMINDER;
import static com.fajarnandagusti.sisimangi.util.Utility.TYPE_REMINDER_RECIEVE;

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.dailyReminder)
    Switch dailyReminder;

    public DailyReminderReceiver dailyReminderReceiver;
    public ReminderPreference reminderPreference;
    public SharedPreferences sDailyReminder;
    public SharedPreferences.Editor editorDailyReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        ButterKnife.bind(this);

        dailyReminderReceiver = new DailyReminderReceiver();
        reminderPreference = new ReminderPreference(this);

        setPreference();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    @OnCheckedChanged(R.id.dailyReminder)
    public void setDailyReminder(boolean isChecked){
        editorDailyReminder = sDailyReminder.edit();
        if (isChecked){
            editorDailyReminder.putBoolean(KEY_FIELD_DAILY_REMINDER, true);
            editorDailyReminder.apply();
            dailyReminderOn();
        }else {
            editorDailyReminder.putBoolean(KEY_FIELD_DAILY_REMINDER, false);
            editorDailyReminder.apply();
            dailyReminderOff();
        }

    }

    private void dailyReminderOn() {
        String time = "06:45" ;
        String time2 = "14:45";

        String message = getResources().getString(R.string.daily_reminder_message);
        String message2 = getResources().getString(R.string.sore_reminder_message);

        reminderPreference.setReminderDailyTime(time);
        reminderPreference.setReminderDailyMessage(message);
        dailyReminderReceiver.setAlarm(SettingActivity.this, TYPE_REMINDER_RECIEVE, time, message);


        reminderPreference.setReminderDailyTime2(time2);
        reminderPreference.setReminderDailyMessage2(message2);
        dailyReminderReceiver.setAlarm2(SettingActivity.this, TYPE_REMINDER_RECIEVE, time2, message2);
    }

    private void dailyReminderOff() {
        dailyReminderReceiver.cancelAlarm(SettingActivity.this);

    }


    private void setPreference() {
        sDailyReminder = getSharedPreferences(KEY_HEADER_DAILY_REMINDER, MODE_PRIVATE);

        boolean checkDailyReminder = sDailyReminder.getBoolean(KEY_FIELD_DAILY_REMINDER, false);
        dailyReminder.setChecked(checkDailyReminder);
    }


    public void setBahasa(View view){
        Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
        startActivity(mIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home : {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
