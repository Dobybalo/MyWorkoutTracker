package hu.bme.aut.myworkouttracker.model;

import android.support.annotation.DrawableRes;

public class ExerciseListItem {

    public ExerciseListItem(Exercise e) {
        exercise = e;
    }

    private Exercise exercise;

    public String getExerciseName() {
        return exercise.getName();
    }

    public String getExerciseReps() {
        return exercise.getReps();
    }


    public @DrawableRes
    int getImageResource() {

        // TODO - szükségmegoldás...
        if (exercise.getName().equals("fekvőtámasz")) return DataManager.pushupIcon;
        else if (exercise.getName().equals("felülés")) return DataManager.situpIcon;
        return 0;
    }

}
