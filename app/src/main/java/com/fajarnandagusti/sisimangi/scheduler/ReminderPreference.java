package com.fajarnandagusti.sisimangi.scheduler;

import android.content.Context;
import android.content.SharedPreferences;

import static com.fajarnandagusti.sisimangi.util.Utility.KEY_REMINDER_DAILY;
import static com.fajarnandagusti.sisimangi.util.Utility.KEY_REMINDER_DAILY2;
import static com.fajarnandagusti.sisimangi.util.Utility.KEY_REMINDER_MESSAGE_DAILY;
import static com.fajarnandagusti.sisimangi.util.Utility.KEY_REMINDER_MESSAGE_DAILY2;
import static com.fajarnandagusti.sisimangi.util.Utility.PREF_NAME;

/**
 * Created by Gustiawan on 2/4/2019.
 */

public class ReminderPreference {

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public ReminderPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setReminderDailyTime(String time){
        editor.putString(KEY_REMINDER_DAILY,time);
        editor.commit();
    }
    public void setReminderDailyMessage(String message){
        editor.putString(KEY_REMINDER_MESSAGE_DAILY,message);
    }


    public void setReminderDailyTime2(String time2){
        editor.putString(KEY_REMINDER_DAILY2,time2);
        editor.commit();
    }

    public void setReminderDailyMessage2(String message2){
        editor.putString(KEY_REMINDER_MESSAGE_DAILY2,message2);
    }


}
