package hu.bme.aut.myworkouttracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.List;

import model.DataManager;
import model.WorkoutDay;

public class ProgressActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private float from;
    private float to;
    private int max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onResume() {
        super.onResume();

        setValues();

        progressBar.setMax(max);
        progressBar.setScaleY(4f);

        ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, from, to);
        anim.setDuration(2000);
        progressBar.startAnimation(anim);

    }

    private void setValues() {

        List<WorkoutDay> list = WorkoutDay.getWorkoutDaysListForWorkout(DataManager.getActiveWorkout());

        int counter = 0;
        for (WorkoutDay wd : list) {
            if (wd.hasFinished()) counter++;
        }

        //ebben már benne van a most elvégzett nap is
        // 100-zal szorozzuk az értékeket, így nem annyira "darabos" az animáció
        from = (counter - 1) * 100;
        to = counter * 100;
        max = list.size() *100;

    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ProgressActivity.this, CalendarActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
