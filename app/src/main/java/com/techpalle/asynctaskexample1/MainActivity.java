package com.techpalle.asynctaskexample1;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button1;
    ProgressBar progressBar;
    TextView textView, tv;
    int progressStatus;

    public class MyTask extends AsyncTask<Integer, Integer, Float>{
        @Override
        protected void onPreExecute() {
            Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
            super.onPreExecute();
        }

        @Override
        protected Float doInBackground(Integer... p1) {
            // UI TASK no possible in Worker Thread
//            Toast.makeText(MainActivity.this, "p1 = "+p1[0], Toast.LENGTH_SHORT).show();
            Log.d("Batch 34", ""+p1[0]);
            float sum = 0;
            for (int i = 1; i<=10; i++){
                sum = sum+i;
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return sum;
        }

        @Override
        protected void onProgressUpdate(Integer... p2) {
            progressStatus = p2[0] * 10;
            tv.setText(progressStatus + "%...out of 100%");
            progressBar.setProgress(progressStatus);
            super.onProgressUpdate(p2);
        }

        @Override
        protected void onPostExecute(Float p3) {
            textView.setText("Sum = "+p3);
//            Toast.makeText(MainActivity.this, "Sum = "+p3, Toast.LENGTH_SHORT).show();
            super.onPostExecute(p3);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.button1);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        textView = (TextView) findViewById(R.id.text1);
        tv = (TextView) findViewById(R.id.tv);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyTask myTask = new MyTask();
                myTask.execute(10);
            }
        });
    }
}
