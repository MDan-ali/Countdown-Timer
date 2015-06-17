package com.example.danish.countdowntimer;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends Activity {
    TextView textView,value;
    Long a,b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.textView);
        value=(TextView)findViewById(R.id.textView2);
        new CountDownTimer( 10000, 1000) {

            public void onTick(long millisUntilFinished) {
                textView.setText("Seconds remaining : " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                func();
            }
        }.start();

    }
    public void func(){
        String url="http://spider.nitt.edu/~vishnu/time.php";
        new setup().execute(url);
        maintimer();

    }
    public void maintimer(){
        a=b;
        new CountDownTimer( 1000*a,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText("Seconds remaining : " + millisUntilFinished / 1000);

            }

            @Override
            public void onFinish() {
                func();

            }
        }.start();
    }

    public void exit(View view){

        finish();
        System.exit(0);
    }

    public class setup extends AsyncTask<String,Integer,Long>{
        @Override
        protected Long doInBackground(String... params) {
            long x = 0;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                try {
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Accept-Encoding", "identity");
                    InputStreamReader input = new InputStreamReader(connection.getInputStream());
                    BufferedReader in =new BufferedReader(input);
                    String i= in.readLine();
                    Log.i("danish",i);
                    x = Long.parseLong(i);


                }
                finally {
                    connection.disconnect();
                }
            } catch (MalformedURLException r) {
                r.printStackTrace();
            } catch (IOException t) {
                t.printStackTrace();
            }
            return x;

            }

        @Override
        protected void onPostExecute(Long aLong) {
            value.setText(String.valueOf(aLong));
            b= aLong % 10;

        }
    }
    }
