package model;

import android.support.annotation.DrawableRes;

import com.orm.SugarRecord;

/**
 * Created by Balint on 2017. 11. 27..
 */

public abstract class Exercise extends SugarRecord<Exercise>{

    protected WorkoutDay workoutDay;
    protected int sequence;
    protected String name;   // ?
    protected String reps;   // mennyiség - darabszám vagy időbeli

    public Exercise() {}

    public Exercise(WorkoutDay wd, int sequence, String reps) {
        workoutDay = wd;
        this.sequence = sequence;
        this.reps = reps;
    }

    public String getExerciseName() {
        return name;
    }

    public String getReps() {
        return reps;
    }

    public Integer getSequence() { return sequence; }

    // azonosító, hogy tudjuk, milyen példány (instanceof nélkül)
    public abstract long getExerciseId();

    // egy drawable id-t ad vissza
    public abstract @DrawableRes int getIcon();

    public abstract void finishExercise();

}
