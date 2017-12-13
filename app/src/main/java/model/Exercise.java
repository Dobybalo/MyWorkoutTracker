package model;

import android.support.annotation.DrawableRes;

import com.orm.SugarRecord;

/**
 * Created by Balint on 2017. 11. 27..
 */

public class Exercise extends SugarRecord<Exercise>{

    protected WorkoutDay workoutDay;
    protected int sequence;
    protected String name;   // ?
    protected String reps;   // mennyiség - darabszám vagy időbeli

    public Exercise() {}

    public Exercise(String name, WorkoutDay workoutDay, int sequence, String reps) {
        this.name = name;
        this.workoutDay = workoutDay;
        this.sequence = sequence;
        this.reps = reps;
    }

    public String getName() {
        return name;
    }

    public String getReps() {
        return reps;
    }

    public Integer getSequence() { return sequence; }

}
