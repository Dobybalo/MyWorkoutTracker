package model;

import android.support.annotation.DrawableRes;

import hu.bme.aut.myworkouttracker.R;

/**
 * Created by Balint on 2017. 11. 29..
 */


public class Pushup extends Exercise {

    public Pushup() {}

    public Pushup(WorkoutDay wd, int sequence, String reps) {
        super(wd, sequence, reps);
        name = "fekvőtámasz";
    }

    @Override
    public void finishExercise() {
        // darabszámot várunk
       // this.howManySoFar += Integer.parseInt(reps);
    }

    @Override
    public @DrawableRes int getIcon() {
        return R.drawable.pushup_icon;
    }

    @Override
    public final long getExerciseId() { return 1234512346910L; }

}
