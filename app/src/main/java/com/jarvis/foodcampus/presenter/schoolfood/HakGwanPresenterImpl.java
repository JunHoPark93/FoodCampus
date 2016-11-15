package com.jarvis.foodcampus.presenter.schoolfood;

import android.content.Context;
import android.os.AsyncTask;

import com.jarvis.foodcampus.view.schoolfood.HakGwanFragmentView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by JunHo on 2016-11-06.
 */

public class HakGwanPresenterImpl implements HakGwanPresenter{
    private Context context;
    private String[] foodContentTextTemp;
    private ArrayList<String> foodContentText = new ArrayList<>();
    private ArrayList<String> dateText = new ArrayList<>();
    private HakGwanFragmentView hakGwanFragmentView;
    private String startDay; // 월요일
    private String endDay; // 금요일

    // 학관 url
    private String hakgwanUrl;


    public HakGwanPresenterImpl(HakGwanFragmentView hakGwanFragmentView , Context context) {
        this.hakGwanFragmentView = hakGwanFragmentView;
        this.context = context;
    }

    public void getSchoolFoodData() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, (Calendar.MONDAY - c.get(Calendar.DAY_OF_WEEK)));
        startDay = formatter.format(c.getTime());
        c.add(Calendar.DATE, (Calendar.FRIDAY - c.get(Calendar.DAY_OF_WEEK)));
        endDay = formatter.format(c.getTime());

        // url 설정
        hakgwanUrl = "http://www.mju.ac.kr/mbs/mjukr/jsp/restaurant/restaurant.jsp?configIdx=36548&firstWeekDay="+startDay+"&lastWeekDay="+endDay+"&id=mjukr_051002020000";

        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
    }


    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(hakgwanUrl).get();    // url로 document를 가져온다

                // Jsoup의 select안에는 css문 써주면 됨
                Elements table = doc.select("table.sub tr td");

                foodContentTextTemp = new String[table.size()];
                int i=0;
                for(Element link : table) {
                    foodContentTextTemp[i] = link.text().trim();
                    i++;
                }

                int cnt=0;
                String temp = "";
                for(int k=0; k<table.size(); k++) {
                    temp += foodContentTextTemp[k]+"\n";
                    cnt++;

                    if(cnt==6) {
                        foodContentText.add(temp);
                        temp="";
                        cnt=0;
                    }
                }

                Elements date = doc.select("table.sub tr th");
                for(Element link : date) {
                    dateText.add(link.text().trim()+"\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            for(int i=0; i<foodContentText.size(); i++) {
                System.out.println("학식-------"+foodContentText.get(i));
                hakGwanFragmentView.addText(dateText, foodContentText);
            }
        }
    }
}
