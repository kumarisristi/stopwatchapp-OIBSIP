package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    private TextView timerTextView;
    private Button startBtn;
    private Button pauseBtn;
    private Button resetBtn;
    private long startTime = 0L;
    private Handler handler = new Handler();
    private boolean running;
    private long timeInMillis = 0L;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timer);
        startBtn = findViewById(R.id.start);
        pauseBtn = findViewById(R.id.pause);
        resetBtn = findViewById(R.id.reset);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!running) {
                    startTimer();
                }
            }
        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running) {
                    stopTimer();
                }
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }

    private void startTimer() {
        startTime = System.currentTimeMillis() - timeInMillis;
        handler.postDelayed(updateTimerRunnable, 0);
        running = true;
        updateButtonState();
    }

    private void stopTimer() {
        handler.removeCallbacks(updateTimerRunnable);
        running = false;
        updateButtonState();
    }

    private void resetTimer() {
        stopTimer();
        timeInMillis = 0L;
        updateTimerText();
    }

    private void updateButtonState() {
        startBtn.setEnabled(!running);
        pauseBtn.setEnabled(running);
        resetBtn.setEnabled(!running);
    }

    private void updateTimerText() {
        int hours = (int) (timeInMillis / 3600000);
        int minutes = (int) ((timeInMillis % 3600000) / 60000);
        int seconds = (int) ((timeInMillis % 60000) / 1000);
        String timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timerTextView.setText(timeFormatted);
    }

    private Runnable updateTimerRunnable = new Runnable() {
        @Override
        public void run() {
            timeInMillis = System.currentTimeMillis() - startTime;
            updateTimerText();
            handler.postDelayed(this, 1000);
        }
    };
}
