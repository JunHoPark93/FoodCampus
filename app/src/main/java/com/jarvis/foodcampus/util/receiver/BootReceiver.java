package com.jarvis.foodcampus.util.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;

import static android.app.AlarmManager.RTC_WAKEUP;

/**
 * Created by Yongjun on 2016-11-19.
 */

/**
 * 휴대폰이 꺼졌다 켜졌을때 알람을 다시 등록해주어야해
 * */
public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
//      get Preference
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

//       알림설정 on/off를 먼저 확인하고
        if (sp.getBoolean("switch_notification", true)) {
//            알림 설정이 on으로 되어있으면 등록된 시간을 가져와
            String time = sp.getString("time_notification", "");
            int hour;
            int minute;
//            등록된 시간이 없을경우에는 임의로 시간설정을 해주어야 뻑이 안나겠지
            if (time != "") {
                hour = Integer.parseInt(time.split(":")[0]);
                minute = Integer.parseInt(time.split(":")[1]);
            } else {
                hour = 11;
                minute = 50;
            }
            System.out.println("hour::::" + hour);
            System.out.println("minute::::" + minute);

//            알람매니저를 다시 불러서 알람시간을 셋팅해야해
            AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent bootIntent = new Intent(context, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, bootIntent, 0);

            Calendar calendar = Calendar.getInstance();
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), hour, minute, 0);
//            이거는 24시간마다 주기적으로 반복하게끔 되어있는데 24시간 마다 오는지 테스트를 제대로 하지 못했어 주말에는 알람이 울리지 않게 지정해 놓았거든
//            주말에는 학교에서 밥을 안주니까
            alarm.setRepeating(RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent);
        }

    }
}
