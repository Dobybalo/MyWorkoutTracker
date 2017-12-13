package model;

import android.support.annotation.DrawableRes;

/**
 * Created by Balint on 2017. 12. 02..
 */

public class ExerciseListItem {

    public ExerciseListItem(Exercise e) {
        exercise = e;
    }

    private Exercise exercise;

    public String getExerciseName() {
        return exercise.getExerciseName();
    }

    public String getExerciseReps() {
        return exercise.getReps();
    }


    public @DrawableRes
    int getImageResource() {
        return exercise.getIcon();
    }

}
