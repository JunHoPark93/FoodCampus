package com.jarvis.foodcampus.util.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.jarvis.foodcampus.R;
import com.jarvis.foodcampus.view.schoolfood.SchoolFoodActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Yongjun on 2016-11-17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;
    // 월요일과 금요일 날짜
    private String startDay;
    private String endDay;

    private String[] mFoodContentTextTemp;
    private ArrayList<String> mFoodContentText = new ArrayList<>();
    private ArrayList<String> mDateText = new ArrayList<>();

    private String[] hFoodContentTextTemp;
    private ArrayList<String> hFoodContentText = new ArrayList<>();
    private ArrayList<String> hDateText = new ArrayList<>();

    private Context context;
    private Intent intent;

    // 명진당,학관 url
    private String myongJinDangUrl;
    private String hakgwanUrl;

    @Override
    public void onReceive(Context context, Intent intent) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        // 이번주 월요일 날짜 계산
        c.add(Calendar.DATE, (Calendar.MONDAY - c.get(Calendar.DAY_OF_WEEK)));
        System.out.println(formatter.format(c.getTime()));

        // 월요일 날짜 초기화
        startDay = formatter.format(c.getTime());

        // 이번주 금요일의 날짜 계산
        c.add(Calendar.DATE, (Calendar.FRIDAY - c.get(Calendar.DAY_OF_WEEK)));
        System.out.println(formatter.format(c.getTime()));

        // 금요일 날짜 초기화
        endDay = formatter.format(c.getTime());

        // 명진당 메뉴 날짜 초기화
        myongJinDangUrl = "http://www.mju.ac.kr/mbs/mjukr/jsp/restaurant/restaurant.jsp?configIdx=36337&firstWeekDay=" + startDay + "&lastWeekDay=" + endDay + "&id=mjukr_051002050000";
        System.out.println("myongJinDangUrl::" + myongJinDangUrl);

        // 학관 메뉴 날짜 초기화
        hakgwanUrl = "http://www.mju.ac.kr/mbs/mjukr/jsp/restaurant/restaurant.jsp?configIdx=36548&firstWeekDay=" + startDay + "&lastWeekDay=" + endDay + "&id=mjukr_051002020000";
        System.out.println("hakgwanUrl::" + hakgwanUrl);

        // 수신받은 context랑 intent 초기화: Async에서 쓸거야
        this.context = context;
        this.intent = intent;

        // Async로 데이터를 요청하자
        getSchoolFoodData();
    }

    private void getSchoolFoodData() {
        PushAsyncTask pushAsyncTask = new PushAsyncTask();
        pushAsyncTask.execute();
    }

    private class PushAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document mDoc = Jsoup.connect(myongJinDangUrl).get();    // url로 document를 가져온다
                // Jsoup의 select안에는 css문 써주면 됨
                Elements mTable = mDoc.select("table.sub tr td");
                mFoodContentTextTemp = new String[mTable.size()];
                int i = 0;
                for (Element link : mTable) {
                    mFoodContentTextTemp[i] = link.text().trim();
                    i++;
                }

                int cnt = 0;
                String temp = "";
                for (int k = 0; k < mTable.size(); k++) {
                    if(mFoodContentTextTemp[k].equals("탕백반")){
                        temp += mFoodContentTextTemp[k] + ": ";
                    }else if(mFoodContentTextTemp[k].equals("일품")){
                        temp += "\n"+mFoodContentTextTemp[k] + ": ";
                    }else if(mFoodContentTextTemp[k].equals("분식")){
                        temp += "";
                    }else if(mFoodContentTextTemp[k].equals("야채김밥,치즈김밥,라면,치즈라면, 떡볶이,순대,공기밥")){
                        temp += "";
                    }else{
                        temp += mFoodContentTextTemp[k] + " ";
                    }

                    cnt++;

                    System.out.println(temp);
                    if (cnt == 6) {
                        mFoodContentText.add(temp);
                        temp = "";
                        cnt = 0;
                    }
                }

                Elements mDate = mDoc.select("table.sub tr th");
                for (Element link : mDate) {
                    mDateText.add(link.text().trim() + " ");
                }
///////////////////////////////////////////////////////////////////////////////////////////////////
                Document hDoc = Jsoup.connect(hakgwanUrl).get();    // url로 document를 가져온다
                // Jsoup의 select안에는 css문 써주면 됨
                Elements hTable = hDoc.select("table.sub tr td");
                hFoodContentTextTemp = new String[hTable.size()];
                i = 0;
                for (Element link : hTable) {
                    hFoodContentTextTemp[i] = link.text().trim();
                    i++;
                }

                cnt = 0;
                temp = "";
                for (int k = 0; k < hTable.size(); k++) {
                    if(hFoodContentTextTemp[k].equals("백반")){
                        temp += hFoodContentTextTemp[k] + ": ";
                    }else if(hFoodContentTextTemp[k].equals("일품")){
                        temp += "\n"+hFoodContentTextTemp[k] + ": ";
                    }else if(hFoodContentTextTemp[k].equals("양식")){
                        temp += "\n"+hFoodContentTextTemp[k] + ": ";
                    }else{
                        temp += hFoodContentTextTemp[k] + " ";
                    }
                    cnt++;

                    if (cnt == 6) {
                        hFoodContentText.add(temp);
                        temp = "";
                        cnt = 0;
                    }
                }

                Elements hDate = hDoc.select("table.sub tr th");
                for (Element link : hDate) {
                    hDateText.add(link.text().trim() + " ");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Calendar cal = Calendar.getInstance();
            // 일:1 월:2 ~.. 그래서 2를 빼서 숫자 맞춰주는거야
            int todayNum = cal.get(Calendar.DAY_OF_WEEK) - 2;
            if (todayNum >= 0 && todayNum < 5) {
                System.out.println("--명진당--\n" + mFoodContentText.get(todayNum));
                System.out.println("--학생회관---\n" + hFoodContentText.get(todayNum));

                // 노티피케이션 생성
                NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                // PendingIntent를 등록하고 notification 클릭시 어떤 클래스를 호출 할 것인지 등록한다.
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, SchoolFoodActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

                Notification.Builder builder = new Notification.Builder(context);
                builder.setSmallIcon(R.drawable.ic_launcher);
                builder.setTicker("FoodCampus 오늘의 학식 입니다.");
                builder.setWhen(System.currentTimeMillis());
                builder.setNumber(1);
                builder.setContentTitle("명지대학교 오늘의 학식");
                builder.setContentText("스크롤을 내려 확인하세요");
                builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);


                Notification.BigTextStyle style = new Notification.BigTextStyle(builder);
                style.setSummaryText("and More +");
                style.setBigContentTitle("명지대학교 오늘의 학식");
                style.bigText("** 명진당 **\n"+mFoodContentText.get(todayNum)+"\n** 학생회관 **\n"+hFoodContentText.get(todayNum));

                builder.setStyle(style);
                Notification notif = builder.build();

                notificationmanager.notify(1, notif);

            }


        }

    }
}