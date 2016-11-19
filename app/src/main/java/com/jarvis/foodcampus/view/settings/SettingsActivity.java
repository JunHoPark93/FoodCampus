package com.jarvis.foodcampus.view.settings;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;

import com.jarvis.foodcampus.R;
import com.jarvis.foodcampus.util.receiver.AlarmReceiver;
import com.jarvis.foodcampus.util.TimePreference;

import java.util.Calendar;

import static android.app.AlarmManager.RTC_WAKEUP;

/**
 * Created by Yongjun on 2016-11-17.
 */

public class SettingsActivity extends AppCompatPreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);
        setOnPreferenceChange(findPreference("switch_notification"));

        PreferenceManager preferenceManager = getPreferenceManager();
        if (preferenceManager.getSharedPreferences().getBoolean("switch_notification", true)) {
            System.out.println("****true****");
            setOnPreferenceChange(findPreference("time_notification"));
        } else {
            System.out.println("****false****");
        }
        System.out.println("@@@@"+preferenceManager.getSharedPreferences().contains("time_notification"));

    }

    private Preference.OnPreferenceChangeListener onPreferenceChangeListener = new Preference.OnPreferenceChangeListener() {

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if (preference instanceof SwitchPreference) {
                System.out.println("SwitchPreference Event");
                System.out.println(PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getBoolean("switch_notification", true)+":::::::::::::::Switch onOff");

                if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("switch_notification", true)) {
                    System.out.println("#ON## "+newValue);
                    setOnPreferenceChange(findPreference("time_notification"));

                } else {

                    //                    이상하게 여기가 off event네
                    System.out.println("###off");

                    Intent intentlocal = new Intent(SettingsActivity.this,AlarmReceiver.class);
                    PendingIntent pilocal = PendingIntent.getBroadcast(SettingsActivity.this,
                            0, intentlocal, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.cancel(pilocal);
                    pilocal.cancel();

                }
            }
            if (preference instanceof TimePreference) {
                System.out.println(newValue);

                setAlarm(newValue);
            }

            return true;
        }
    };

    private void setOnPreferenceChange(Preference mPreference) {
        if (mPreference instanceof TimePreference) {
            System.out.println("mPreference:::::::" + mPreference);

            mPreference.setOnPreferenceChangeListener(onPreferenceChangeListener);
            onPreferenceChangeListener.onPreferenceChange(mPreference,
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(mPreference.getKey(), ""));

        } else if (mPreference instanceof SwitchPreference) {
            System.out.println("mPreference:::::::" + mPreference);

            mPreference.setOnPreferenceChangeListener(onPreferenceChangeListener);
            onPreferenceChangeListener.onPreferenceChange(mPreference,
                    PreferenceManager.getDefaultSharedPreferences(this));


        }
    }

    public void setAlarm(Object time_param) {
        String time = (String) time_param;
        int hour;
        int minute;
        if (time != "") {
            hour = Integer.parseInt(time.split(":")[0]);
            minute = Integer.parseInt(time.split(":")[1]);
        } else {
            hour = 11;
            minute = 50;
        }
        System.out.println("hour::::" + hour);
        System.out.println("minute::::" + minute);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(SettingsActivity.this, AlarmReceiver.class);
        PendingIntent pender = PendingIntent.getBroadcast(SettingsActivity.this, 0, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), hour, minute, 0);

        if (calendar.getTimeInMillis() < System.currentTimeMillis())
            calendar.add(Calendar.DATE, 1); // 과거시간 설정되면 내일로 결정
        alarm.setRepeating(RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, pender);
        System.out.println("@@!!"+calendar.getTime());
        System.out.println("##################  alarm Setting coompleted#####################");
    }
}
