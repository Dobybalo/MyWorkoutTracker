package model;

import android.support.annotation.DrawableRes;

import hu.bme.aut.myworkouttracker.R;

/**
 * Created by Balint on 2017. 11. 29..
 */

public class Situp extends Exercise {

    public Situp() {}

    public Situp(WorkoutDay wd, int sequence, String reps) {
        super(wd, sequence, reps);
        name = "felülés";
    }

    @Override
    public final long getExerciseId() {
        return 145488769888787L;
    }

    public void finishExercise() {
        // darabszámot várunk
        //this.howManySoFar += Integer.parseInt(reps);
    }

    @Override
    public @DrawableRes int getIcon() {
        return R.drawable.situp_icon;
    }
}
