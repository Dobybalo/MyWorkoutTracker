package hu.bme.aut.myworkouttracker;

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

        //teszt
        /*
        bar.setMax(50);
        bar.setProgress(20);
        */


        //kell setMax, setMin(?)
        // ki kell számolni hogy mettől meddig

        // max: összes nap
        // min: 0
        // from: elvégzett napok száma (enélkül)
        // to: előző + 1

    }

    @Override
    public void onResume() {
        super.onResume();

        setValues();

        //teszt
        /*
        max *= 100;
        progressBar.setMax(max);
        from = 0;
        to = max;
        */

        /*

        progressBar.setMax(max);
        progressBar.setProgress((int)to);
*/

        progressBar.setMax(max);
        progressBar.setScaleY(4f);

        ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, from, to);
        anim.setDuration(2000);
        progressBar.startAnimation(anim);

      //  ProgressBarAnimation mProgressAnimation = new ProgressBarAnimation(progressBar, 1000);

        /* Update progress later anywhere in code:
        mProgressAnimation.setProgress(from);
        mProgressAnimation.setProgress(to);
        */
    }

    private void setValues() {

        List<WorkoutDay> list = WorkoutDay.getWorkoutDaysListForWorkout(DataManager.getActiveWorkout());

        int counter = 0;
        for (WorkoutDay wd : list) {
            if (wd.hasFinished()) counter++;
        }

        //ebben már benne van a mostani nap is
        from = (counter - 1) * 100;
        to = counter * 100;
        max = list.size() *100;
        Log.i("progress", "From: " + from + " To: " + to + " Max: " + max);

    }


}
